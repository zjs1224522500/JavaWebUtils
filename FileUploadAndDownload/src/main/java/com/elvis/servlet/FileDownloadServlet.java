package com.elvis.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Version:v1.0 (description:  ) Date:2017/11/2 0002  Time:19:59
 */
@WebServlet(name = "DownloadServlet", urlPatterns = "/download", initParams = {
		@WebInitParam(name = "filePath", value = "data") })
public class FileDownloadServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		Object fileNameAttribute = req.getAttribute("fileName");
		String fileName = null == fileNameAttribute ? "4006.doc" : (String) fileNameAttribute;
		fileName = new String(fileName.getBytes("ISO-8859-1"), "UTF-8");

		ServletConfig servletConfig = getServletConfig();
		ServletContext servletContext = req.getServletContext();
		String path = servletContext.getRealPath("/") + servletConfig.getInitParameter("filePath")
				+ File.separator + fileName;

		/**
		 * Content-type:表示后面的文档属于哪种类型，浏览器会根据类型决定如何显示这些内容
		 * Text：用于标准化地表示的文本信息，文本消息可以是多种字符集和或者多种格式的；
		 * Multipart：用于连接消息体的多个部分构成一个消息，这些部分可以是不同类型的数据；
		 * Application：用于传输应用程序数据或者二进制数据；
		 * Message：用于包装一个E-mail消息；
		 * Image：用于传输静态图片数据；
		 * Audio：用于传输音频或者音声数据；
		 * Video：用于传输动态影像数据，可以是与音频编辑在一起的视频数据格式。
		 * subtype用于指定type的详细形式。content-type/subtype配对的集合和与此相关的参数，将随着时间而增长。为了确保这些值在一个有序而且公开的状态下开发，MIME使用Internet Assigned Numbers Authority (IANA)作为中心的注册机制来管理这些值。
		 * parameter可以用来指定附加的信息，更多情况下是用于指定text/plain和text/htm等的文字编码方式的charset参数。MIME根据type制定了默认的subtype，当客户端不能确定消息的subtype的情况下，消息被看作默认的subtype进行处理。Text默认是text/plain，Application默认是application/octet-stream而Multipart默认情况下被看作multipart/mixed。
		 */
		resp.setContentType(servletContext.getMimeType(fileName));

		/**
		 * Content-Disposition: disposition-type;fileName-param
		 * disposition-type: 直接打开(.txt,.jpg) 或 附件形式下载
		 * fileName-param: 默认的文件名参数，可以携带路径，但会被UA忽略
		 *
		 * 注意：使用attachment时，不能禁止浏览器缓存，否则会在IE浏览器中下载出现问题
		 */
		//Content-Disposition 头对应的value：attachment（附件形式 - 下载方式）;fileName（下载的默认的文件名）
		resp.setHeader("Content-Disposition", "attachment;filename=" + fileName);

		File file = new File(path);
		FileInputStream fileInputStream = new FileInputStream(file);
		byte[] bytes = new byte[fileInputStream.available()];
		fileInputStream.read(bytes);
		ServletOutputStream out = resp.getOutputStream();
		out.write(bytes);
		out.flush();
		out.close();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

	}
}
