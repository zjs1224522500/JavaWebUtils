package com.elvis.servlet;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 * Version:v1.0 (description:  ) Date:2017/11/3 0003  Time:09:39
 */
@MultipartConfig
@WebServlet(name = "UploadServlet", urlPatterns = "/upload", initParams = {
		@WebInitParam(name = "uploadPath", value = "data") })
public class NewFileUploadServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		ServletContext servletContext = req.getServletContext();
		ServletConfig servletConfig = getServletConfig();
		String directory = servletContext.getRealPath("/") + File.separator + servletConfig
				.getInitParameter("uploadPath") + File.separator;

		Collection<Part> parts = req.getParts();

		if (parts.size() == 1) {
			Part part = req.getPart("file");
			String header = part.getHeader("content-disposition");
			String fileName = getFileName(header);
			part.write(directory + fileName);
			req.setAttribute("message", "文件上传成功!");
		} else {
			for (Part part : parts) {
				String header = part.getHeader("content-disposition");
				String fileName = getFileName(header);
				part.write(directory + fileName);
				req.setAttribute("message", "文件上传成功!");
			}
		}

		req.getServletContext().getRequestDispatcher("/message.jsp").forward(req, resp);
	}

	public String getFileName(String header) {
		/**
		 * String[] tempArr1 = header.split(";");代码执行完之后，在不同的浏览器下，tempArr1数组里面的内容稍有区别
		 * 火狐或者google浏览器下：tempArr1={form-data,name="file",filename="snmp4j--api.zip"}
		 * IE浏览器下：tempArr1={form-data,name="file",filename="E:\snmp4j--api.zip"}
		 */
		String[] tempArr1 = header.split(";");
		/**
		 *火狐或者google浏览器下：tempArr2={filename,"snmp4j--api.zip"}
		 *IE浏览器下：tempArr2={filename,"E:\snmp4j--api.zip"}
		 */
		String[] tempArr2 = tempArr1[2].split("=");
		//获取文件名，兼容各种浏览器的写法
		String fileName = tempArr2[1].substring(tempArr2[1].lastIndexOf("\\") + 1)
				.replaceAll("\"", "");
		return fileName;
	}
}
