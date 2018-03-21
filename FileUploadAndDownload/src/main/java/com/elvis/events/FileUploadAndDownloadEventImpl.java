package com.elvis.events;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Version:v1.0 (description:  ) Date:2017/11/3 0003  Time:13:02
 */
@Service(FileUploadAndDownloadEvent.EVENT_NAME)
public class FileUploadAndDownloadEventImpl implements FileUploadAndDownloadEvent {

	@Override
	public byte[] downloadFile(String fileName, String directory) throws Exception {

		File file = new File(directory+ File.separator + fileName);
		FileInputStream in = new FileInputStream(file);
		byte[] bytes = new byte[in.available()];
		in.read(bytes);

		return bytes;
	}

	@Override
	public boolean uploadFile(MultipartFile file, String directory) {
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
