package com.isteer.dao;

import java.util.List;

import com.isteer.module.ImageModule;

public interface ImageDao {
	
	public void addFileToFileSystem(List<ImageModule> modules);
	
	public String getFileFromFileSystem(String fileUUID);
	
	public List<String> getAllFilesFromFileSystem();
	
	

}
