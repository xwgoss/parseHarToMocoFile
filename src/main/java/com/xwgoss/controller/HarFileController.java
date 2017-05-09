package com.xwgoss.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.xwgoss.service.HarFileService;

@RestController
@RequestMapping("/har")
public class HarFileController {
	@Autowired
	private HarFileService iHarFileService;
	@RequestMapping(path="/upload",method=RequestMethod.POST)
	public Map uploadHarFile(@RequestParam("file") MultipartFile file){
		return iHarFileService.saveHarFile(file);
	}
}
