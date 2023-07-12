package com.isteer.module;

import java.util.List;

public class FileResponse {
	
	private int statusCode;
	private String msg;
	private List<String> resourceUrl;
	public FileResponse(int statusCode, String msg, List<String> resourceUrl) {
		super();
		this.statusCode = statusCode;
		this.msg = msg;
		this.resourceUrl = resourceUrl;
	}
	public FileResponse() {
		super();
	}
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public List<String> getResourceUrl() {
		return resourceUrl;
	}
	public void setResourceUrl(List<String> resourceUrl) {
		this.resourceUrl = resourceUrl;
	}

}
