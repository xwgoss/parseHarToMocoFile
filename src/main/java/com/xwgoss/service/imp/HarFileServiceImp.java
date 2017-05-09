package com.xwgoss.service.imp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.xwgoss.bean.MocoBean;
import com.xwgoss.bean.RequestBean;
import com.xwgoss.bean.ResponseBean;
import com.xwgoss.config.Config;
import com.xwgoss.service.HarFileService;

import de.sstoehr.harreader.HarReader;
import de.sstoehr.harreader.HarReaderException;
import de.sstoehr.harreader.model.Har;
import de.sstoehr.harreader.model.HarEntry;
import de.sstoehr.harreader.model.HarRequest;
import de.sstoehr.harreader.model.HarResponse;
import net.minidev.json.JSONArray;

@Component
public class HarFileServiceImp implements HarFileService {
	private static final Logger logger=Logger.getLogger(HarFileServiceImp.class);
	private static final String filename=".json";
	private static final String Encoding="utf-8";
	@Autowired
	private Config config;
	private String mocosDir;
	private ExecutorService mService;
	private Set<String> operatorFiles;
	public HarFileServiceImp(){
		mService=Executors.newFixedThreadPool(config.getThreads());
		operatorFiles=new HashSet<String>();
	}
	@Override
	public synchronized Map<String, String> saveHarFile(MultipartFile file) {
		// TODO Auto-generated method stub
		operatorFiles.add(file.getOriginalFilename());
		String path = config.getFilePath() == null ? "" : config.getFilePath() + File.separator + file.getOriginalFilename();
		File f = new File(path);
		Map<String,String> map=new HashMap<String,String>();
		map.put("recode", "0");
		try {
			if (!f.exists())
				f.createNewFile();
			mService.execute(new ConerHar(f));
			file.transferTo(f);
		} catch (IllegalStateException | IOException e) {
			// TODO Auto-generated catch block
			logger.error("保存har文件时发生错误,错误信息：", e.fillInStackTrace());
			map.put("recode", "1");
			map.put("errormssage", "保存文件"+ file.getOriginalFilename()+"发生错误");
		}
		operatorFiles.remove(file.getOriginalFilename());
		return map;
	}
	class ConerHar implements Runnable{
		private File f;
		private HarReader hr;
		public ConerHar(File f) {
			// TODO Auto-generated constructor stub
			this.f=f;
			hr=new HarReader();
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			do{
				//若文件尚未保存完则休眠该线程
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					logger.error("线程休眠时发生错误", e.fillInStackTrace());
				}
			}while(operatorFiles.contains(f.getName()));
			try {
				//转换har文件
				List<MocoBean> mocos=convertHarToMocos(hr.readFromFile(f));
				//保存moco文件
				JSONArray ja=new JSONArray();
				for(MocoBean mb:mocos){
					JSONObject jo=new JSONObject();
					String path=mkFileToMoco(mb);
					jo.put("include", path);
					ja.add(jo);
				}
			} catch (HarReaderException e) {
				// TODO Auto-generated catch block
				logger.error("解析har内容时发生错误：", e.fillInStackTrace());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.error("文件操作时发生错误：",e.fillInStackTrace());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				logger.error("json转换时报错：",e.fillInStackTrace());
			}
		}
		
		private String mkFileToMoco(MocoBean mb) throws IOException {
			// TODO Auto-generated method stub
			String path=mb.getRequest().getUri().replaceAll("///",File.separator)+filename;
			File f=new File(mocosDir+File.separator+path);
		    if(!f.exists()){
		    	if(!f.getParentFile().exists())
		    		f.getParentFile().mkdirs();
		    	f.createNewFile();
		    }
		    FileUtils.write(f,new Gson().toJson(mb), Encoding);
			return path;
		}
		private List<MocoBean> convertHarToMocos(Har har){
			List<MocoBean> mocos=new ArrayList<MocoBean>();
			for(HarEntry he:har.getLog().getEntries()){
				MocoBean mb=new MocoBean();
				mb.setRequest(getRequestFromHar(he.getRequest()));
				mb.setResponse(getResponseFromHar(he.getResponse()));
				mocos.add(mb);
			}
			return mocos;
		}
		
		private RequestBean getRequestFromHar(HarRequest hr){
			RequestBean rb=new RequestBean();
			rb.setUri(hr.getUrl());
			rb.setMethod(hr.getMethod().name());
			if(hr.getQueryString()!=null&&hr.getQueryString().size()>0)
				rb.setQueries(hr.getQueryString());
			if(hr.getHeaders()!=null&&hr.getHeaders().size()>0)
				rb.setHeaders(hr.getHeaders());
			if(hr.getPostData()!=null&&hr.getPostData().getParams()!=null&&hr.getPostData().getParams().size()>0)
				rb.setForms(hr.getPostData().getParams());
			return rb;
		}
		private ResponseBean getResponseFromHar(HarResponse hr){
			ResponseBean rb=new ResponseBean();
			rb.setStatus(hr.getStatus()+"");
			if(hr.getHeaders()!=null&&hr.getHeaders().size()>0)
				rb.setHeaders(hr.getHeaders());
			if(hr.getContent().getText()!=null)
				rb.setText(hr.getContent().getText());
			return rb;
		}
		
	}
}
