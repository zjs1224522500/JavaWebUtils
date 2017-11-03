package com.elvis.events;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * Version:v1.0 (description:  ) Date:2017/11/3 0003  Time:09:05
 */
public interface FileUploadAndDownloadEvent {

	String EVENT_NAME = "fileEvent";

	boolean uploadFile(MultipartFile file,String directory);
}
