package com.xwgoss.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.sstoehr.harreader.model.HarHeader;

public class ResponseBean {
private String text;
private String status;
private String version;
private List<HarHeader> headers;
private Map<String,String> json;
public String getText() {
	return text;
}
public void setText(String text) {
	this.text = text;
}
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}
public String getVersion() {
	return version;
}
public void setVersion(String version) {
	this.version = version;
}



public List<HarHeader> getHeaders() {
	if(headers==null)
		headers=new ArrayList<HarHeader>();
	return headers;
}
public void setHeaders(List<HarHeader> headers) {
	this.headers = headers;
}
public Map<String, String> getJson() {
	return json;
}
public void setJson(Map<String, String> json) {
	this.json = json;
}

}
