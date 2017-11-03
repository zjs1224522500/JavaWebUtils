package com.elvis.events;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

/**
 * Version:v1.0 (description:  ) Date:2017/11/3 0003  Time:13:02
 */
@Service(FileUploadAndDownloadEvent.EVENT_NAME)
public class FileUploadAndDownloadEventImpl implements FileUploadAndDownloadEvent {

	@Override
	public boolean uploadFile(MultipartFile file,String directory) {
		String fileName = file.getOriginalFilename();
		String filePath = directory + File.separator + fileName;
		File newFile = new File(filePath);
		try {
			file.transferTo(newFile);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
