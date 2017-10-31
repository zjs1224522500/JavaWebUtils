package com.elvis.filters;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Version:v1.0 (description: 打印出请求头中的相关信息  ) Date:2017/10/30 0030  Time:22:05
 */
public class HelloServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/html;charset=UTF-8");

		PrintWriter out = resp.getWriter();
		String title = "HTTP Header 请求实例 - 菜鸟教程实例";
		String docType = "<!DOCTYPE html> \n";
		out.println(docType + "<html>\n" + "<head><meta charset=\"utf-8\"><title>" + title
				+ "</title></head>\n" + "<body bgcolor=\"#f0f0f0\">\n" + "<h1 align=\"center\">"
				+ title + "</h1>\n" + "<table width=\"100%\" border=\"1\" align=\"center\">\n"
				+ "<tr bgcolor=\"#949494\">\n" + "<th>Header 名称</th><th>Header 值</th>\n"
				+ "</tr>\n");

		Enumeration headerNames = req.getHeaderNames();

		while (headerNames.hasMoreElements()) {
			String paramName = (String) headerNames.nextElement();
			out.print("<tr><td>" + paramName + "</td>\n");
			String paramValue = req.getHeader(paramName);
			out.println("<td> " + paramValue + "</td></tr>\n");
		}
		String error = (String) (null == req.getAttribute("errorMessage") ?
				"" :
				req.getAttribute("errorMessage"));
		out.print("<tr><td>error</td>\n");
		out.println("<td> " + error + "</td></tr>\n");
		out.println("</table>\n</body></html>");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}
}
