package com.isteer.exception;

public class ExceptionHandlers {
	
	private int statusCode;
	private String msg;
	private String errorMsg;
	public ExceptionHandlers(int statusCode, String msg, String errorMsg) {
		super();
		this.statusCode = statusCode;
		this.msg = msg;
		this.errorMsg = errorMsg;
	}
	public ExceptionHandlers() {
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
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

}
