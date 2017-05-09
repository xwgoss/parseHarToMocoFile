package com.xwgoss.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public interface HarFileService {
	public Map<String,String> saveHarFile(MultipartFile file);
}
