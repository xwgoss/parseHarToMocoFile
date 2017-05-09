package com.xwgoss.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="har")
public class Config {
private String filePath;
private int threads;
public String getFilePath() {
	return filePath;
}
public void setFilePath(String filePath) {
	this.filePath = filePath;
}
public int getThreads() {
	return threads;
}
public void setThreads(int threads) {
	this.threads = threads;
}

}
