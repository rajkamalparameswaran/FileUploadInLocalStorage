package com.isteer.service;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.isteer.module.FileResponse;

public interface ImageService {

	public FileResponse addFileToFileSystem(List<MultipartFile> files);

	public ResponseEntity<Resource> getFileFromFileSystem(String fileUUID,String choise);
	
	public List<String> getAllFilesFromFileSystem();

}
