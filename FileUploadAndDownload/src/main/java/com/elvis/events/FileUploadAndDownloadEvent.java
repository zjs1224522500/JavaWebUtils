package com.elvis.events;

import org.springframework.web.multipart.MultipartFile;

/**
 * Version:v1.0 (description:  ) Date:2017/11/3 0003  Time:09:05
 */
public interface FileUploadAndDownloadEvent {

	String EVENT_NAME = "fileEvent";

	boolean uploadFile(MultipartFile file,String directory);

	byte[] downloadFile(String fileName,String directory) throws Exception;
}
