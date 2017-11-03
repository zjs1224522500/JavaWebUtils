package com.elvis.controller;

import com.elvis.events.FileUploadAndDownloadEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

/**
 * Version:v1.0 (description:  ) Date:2017/11/3 0003  Time:13:20
 */
@Controller
public class FileController {

	@Autowired
	private FileUploadAndDownloadEvent fileUploadAndDownloadEvent;

	ServletContext servletContext = ContextLoader.getCurrentWebApplicationContext()
			.getServletContext();

	@RequestMapping(value = "/spring/upload", method = RequestMethod.POST)
	public String uploadFile(MultipartFile file, HttpServletRequest request) {
		String directory = servletContext.getRealPath("/") + File.separator + servletContext
				.getInitParameter("uploadPath");
		boolean success = fileUploadAndDownloadEvent.uploadFile(file, directory);
		if (success) {
			request.setAttribute("message", "文件上传成功!");
		} else {
			request.setAttribute("message", "文件上传失败!");
		}
		return "message";
	}

}
