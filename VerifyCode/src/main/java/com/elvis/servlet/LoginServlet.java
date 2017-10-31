package com.elvis.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Version:v1.0 (description:  ) Date:2017/10/29 0029  Time:15:29
 */
public class LoginServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String picCode = req.getSession().getAttribute("picCode").toString();
		String submitCode = req.getParameter("verifyCode");
		submitCode = submitCode.toUpperCase();
		resp.setContentType("text/html;charset=utf-8");
		PrintWriter out = resp.getWriter();
		if (picCode.equals(submitCode)) {
			out.println("验证码输入正确");
		} else {
			out.println("验证码输入错误");
		}
		out.flush();
		out.close();
	}
}
