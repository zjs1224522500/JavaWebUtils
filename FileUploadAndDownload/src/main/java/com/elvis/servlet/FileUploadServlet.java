package com.elvis.servlet;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Version:v1.0 (description:  ) Date:2017/11/2 0002  Time:19:58
 */
@WebServlet(name = "UploadServlet", urlPatterns = "/upload", initParams = {
		@WebInitParam(name = "uploadPath", value = "data") })
public class FileUploadServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	// 上传配置
	private static final int MEMORY_THRESHOLD = 1024 * 1024 * 3;  // 3MB

	private static final int MAX_FILE_SIZE = 1024 * 1024 * 40; // 40MB

	private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 50; // 50MB

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// 检测是否为多媒体上传
		if (!ServletFileUpload.isMultipartContent(req)) {
			// 如果不是则停止
			PrintWriter writer = resp.getWriter();
			writer.println("Error: 表单必须包含 enctype=multipart/form-data");
			writer.flush();
			return;
		}

		// 配置上传参数
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// 设置内存临界值 - 超过后将产生临时文件并存储于临时目录中
		factory.setSizeThreshold(MEMORY_THRESHOLD);
		// 设置临时存储目录
		factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

		ServletFileUpload upload = new ServletFileUpload(factory);

		// 设置最大文件上传值
		upload.setFileSizeMax(MAX_FILE_SIZE);

		// 设置最大请求值 (包含文件和表单数据)
		upload.setSizeMax(MAX_REQUEST_SIZE);

		// 中文处理
		upload.setHeaderEncoding("UTF-8");

		// 构造临时路径来存储上传的文件
		// 这个路径相对当前应用的目录
		ServletContext servletContext = req.getServletContext();
		ServletConfig servletConfig = getServletConfig();

		String uploadPath = servletContext.getRealPath("/") + File.separator + servletConfig
				.getInitParameter("uploadPath");

		// 如果目录不存在则创建
		File uploadDir = new File(uploadPath);
		if (!uploadDir.exists()) {
			uploadDir.mkdir();
		}

		try {
			// 解析请求的内容提取文件数据
			@SuppressWarnings("unchecked")
			List<FileItem> formItems = upload.parseRequest(req);

			if (formItems != null && formItems.size() > 0) {
				// 迭代表单数据
				for (FileItem item : formItems) {
					// 处理不在表单中的字段
					if (!item.isFormField()) {
						String fileName = new File(item.getName()).getName();
						String filePath = uploadPath + File.separator + fileName;
						File storeFile = new File(filePath);
						// 在控制台输出文件的上传路径
						System.out.println(filePath);
						// 保存文件到硬盘
						item.write(storeFile);
						req.setAttribute("message", "文件上传成功!");
					}
				}
			}
		} catch (Exception ex) {
			req.setAttribute("message", "错误信息: " + ex.getMessage());
		}
		// 跳转到 message.jsp
		req.getServletContext().getRequestDispatcher("/message.jsp").forward(req, resp);
	}
}
