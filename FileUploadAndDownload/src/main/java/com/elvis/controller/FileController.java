package com.elvis.controller;

import com.elvis.events.FileUploadAndDownloadEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.multipart.MultipartFile;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

	@GetMapping("/spring/download")
	public void downloadFile(HttpServletRequest request, HttpServletResponse response,
			String fileName) {
		String directory = servletContext.getRealPath("/") + File.separator + servletContext
				.getInitParameter("uploadPath");

		//		BASE64Decoder decoder = new BASE64Decoder();
		try {
			//String name = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
			String encodeFileName = URLEncoder.encode(fileName, "UTF-8");
//			request.setCharacterEncoding("UTF-8");
//			response.setCharacterEncoding("UTF-8");
			byte[] bytes = fileUploadAndDownloadEvent.downloadFile(fileName, directory);
			response.setContentType(servletContext.getMimeType(fileName));
			response.setHeader("Content-Disposition", "attachment;filename=" + encodeFileName);
			ServletOutputStream out = response.getOutputStream();
			out.write(bytes);
			out.flush();
			out.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@GetMapping("/spring/mvc/download")
	public ResponseEntity<byte[]> download(String fileName) {
		String directory = servletContext.getRealPath("/") + File.separator + servletContext
				.getInitParameter("uploadPath");
		HttpHeaders headers = new HttpHeaders();


		try {
			String encodeFileName = URLEncoder.encode(fileName, "UTF-8");
			byte[] bytes = fileUploadAndDownloadEvent.downloadFile(fileName, directory);
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			headers.setContentDispositionFormData("attachment",encodeFileName);
			return new ResponseEntity<byte[]>(bytes,headers, HttpStatus.OK);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

}
