package com.isteer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.isteer.module.FileResponse;
import com.isteer.service.ImageService;

@RestController
@RequestMapping("/file")
public class ImageController {

	@Autowired
	private ImageService imageService;

	@PostMapping("/uploadToFileSystem")
	public ResponseEntity<FileResponse> addImage(@RequestParam("file") List<MultipartFile> files) throws Exception {
		return new ResponseEntity<FileResponse>(imageService.addFileToFileSystem(files), HttpStatus.CREATED);
	}

	@GetMapping("/gettIngFRomFileSystem/{choise}/{fileUUID}")
	public ResponseEntity<Resource> getImage(@PathVariable String choise,@PathVariable String fileUUID) throws Exception {
		return imageService.getFileFromFileSystem(fileUUID,choise);
	}
	
	@GetMapping("/getAllImagesFromFileSystem")
	public ResponseEntity<List<String>> getAllImages() {
		
		return new ResponseEntity<List<String>>(imageService.getAllFilesFromFileSystem(),HttpStatus.OK);
	}

}
