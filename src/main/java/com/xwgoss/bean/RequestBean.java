package com.xwgoss.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.sstoehr.harreader.model.HarHeader;
import de.sstoehr.harreader.model.HarPostDataParam;
import de.sstoehr.harreader.model.HarQueryParam;

public class RequestBean {
//请求URI
private String uri;
//请求内容
private String text;
private List<HarQueryParam> queries;
private String method;
private String version;
private List<HarHeader> headers;
private List<HarPostDataParam> forms;
public String getUri() {
	return uri;
}
public void setUri(String uri) {
	this.uri = uri;
}
public String getText() {
	return text;
}
public void setText(String text) {
	this.text = text;
}

public String getMethod() {
	return method;
}
public void setMethod(String method) {
	this.method = method;
}
public String getVersion() {
	return version;
}
public void setVersion(String version) {
	this.version = version;
}



public List<HarPostDataParam> getForms() {
	if(forms==null)
		forms=new ArrayList<HarPostDataParam>();
	return forms;
}
public void setForms(List<HarPostDataParam> forms) {
	this.forms = forms;
}
public List<HarQueryParam> getQueries() {
	if(queries==null)
		queries=new ArrayList<HarQueryParam>();
	return queries;
}
public void setQueries(List<HarQueryParam> queries) {
	this.queries = queries;
}
public List<HarHeader> getHeaders() {
	if(headers==null)
		headers=new ArrayList<HarHeader>();
	return headers;
}
public void setHeaders(List<HarHeader> headers) {
	this.headers = headers;
}


}
