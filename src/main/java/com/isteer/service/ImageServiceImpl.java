package com.isteer.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.uuid.Generators;
import com.isteer.dao.ImageDao;
import com.isteer.exception.ImageNameNotFoundException;
import com.isteer.module.FileResponse;
import com.isteer.module.ImageModule;

@Service
@Transactional
public class ImageServiceImpl implements ImageService {

	@Autowired
	ImageDao dao;

	private final String DIRECTORY = "C:/Users/rajkamal.parameswara/Pictures/Screenshots/images/";

	@Override
	public FileResponse addFileToFileSystem(List<MultipartFile> files) {
		List<String> resourceUrl = new ArrayList<String>();
		List<ImageModule> modules = new ArrayList<>();

		try {

			modules = files.stream().map(file -> {
				String fileName = StringUtils.cleanPath(file.getOriginalFilename());
				Path filePath = Paths.get(DIRECTORY, fileName).toAbsolutePath().normalize();
				try {
					Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
				} catch (IOException e) {
					e.printStackTrace();
				}
				String fileUUID = Generators.timeBasedGenerator().generate().toString();
				resourceUrl.add(ServletUriComponentsBuilder.fromCurrentContextPath()
						.path("/file/gettIngFRomFileSystem/download/").path(fileUUID).toUriString());
				resourceUrl.add(ServletUriComponentsBuilder.fromCurrentContextPath()
						.path("/file/gettIngFRomFileSystem/view/").path(fileUUID).toUriString());

				return new ImageModule(DIRECTORY + fileName, fileUUID);
			}).collect(Collectors.toList());
			dao.addFileToFileSystem(modules);
			return new FileResponse(1, "File upload sucessfully", resourceUrl);
		} catch (Exception e) {
			throw new ImageNameNotFoundException(0, "Cannot upload the file", e.getLocalizedMessage());
		}
	}

	@Override
	public ResponseEntity<Resource> getFileFromFileSystem(String fileUUID, String choise) {
		String fileLocation = dao.getFileFromFileSystem(fileUUID);
		if (fileLocation == null) {
			throw new ImageNameNotFoundException(0, "fail to fetch data", "image id not found");
		}
		Path filePath = Paths.get(fileLocation).toAbsolutePath().normalize();

		if (!Files.exists(filePath, LinkOption.NOFOLLOW_LINKS)) {
			throw new ImageNameNotFoundException(0, "Data cannot fetch", "File not exists in this id");
		}

		try {
			Resource resource = new UrlResource(filePath.toUri());
			HttpHeaders headers = new HttpHeaders();
			headers.add("File-Name", resource.getFilename());
			if (choise.equalsIgnoreCase("download")) {
				headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; File-Name=" + resource.getFilename());
			} else {
				headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; File-Name=" + resource.getFilename());
			}
			try {
				return ResponseEntity.status(HttpStatus.OK).headers(headers)
						.contentType(MediaType.parseMediaType(Files.probeContentType(filePath))).body(resource);
			} catch (IOException e) {
				throw new ImageNameNotFoundException(0, "fail to fetch data", e.getLocalizedMessage());
			}
		} catch (MalformedURLException e) {
			throw new ImageNameNotFoundException(0, "fail to fetch data", "image not found");
		}
	}

	@Override
	public List<String> getAllFilesFromFileSystem() {
		List<String> allFilesUUID = dao.getAllFilesFromFileSystem();
		if (allFilesUUID.isEmpty()) {
			throw new ImageNameNotFoundException(0, "data download failed", "No data found");
		}
		List<String> ResourceUrl = new ArrayList<>();
		for (String fileUUID : allFilesUUID) {
			ResourceUrl.add(ServletUriComponentsBuilder.fromCurrentContextPath()
					.path("/file/gettIngFRomFileSystem/download/").path(fileUUID).toUriString());
			ResourceUrl.add(ServletUriComponentsBuilder.fromCurrentContextPath()
					.path("/file/gettIngFRomFileSystem/view/").path(fileUUID).toUriString());
		}
		return ResourceUrl;
	}
}
