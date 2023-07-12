package com.isteer.module;

public class ImageModule {
	
	private int fileId;
	private String filePath;
	private String fileUUID;
	public ImageModule() {
		super();
	}
	public ImageModule(int fileId, String filePath, String fileUUID) {
		super();
		this.fileId = fileId;
		this.filePath = filePath;
		this.fileUUID = fileUUID;
	}
	public ImageModule(String filePath, String fileUUID) {
		super();
		this.filePath = filePath;
		this.fileUUID = fileUUID;
	}
	public int getFileId() {
		return fileId;
	}
	public void setFileId(int fileId) {
		this.fileId = fileId;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getFileUUID() {
		return fileUUID;
	}
	public void setFileUUID(String fileUUID) {
		this.fileUUID = fileUUID;
	}

	
}
