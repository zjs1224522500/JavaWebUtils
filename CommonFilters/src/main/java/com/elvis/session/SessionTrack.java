package com.elvis.session;

import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Version:v1.0 (description:  ) Date:2017/11/1 0001  Time:18:48
 */
@WebServlet(name = "Hello", urlPatterns = { "/sessionTrack" }, loadOnStartup = 1)
public class SessionTrack extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		// getSession(boolean created)
		//如果不存在 session 会话，则创建一个 session 对象
		HttpSession session = req.getSession(true);

		// 获取 session 创建时间(时间戳)
		Date createTime = new Date(session.getCreationTime());
		// 获取该网页的最后一次访问时间(时间戳)
		Date lastAccessTime = new Date(session.getLastAccessedTime());

		//设置日期输出的格式
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String title = "Servlet Session 实例 ";
		String visitCountKey = "visitCount";
		Integer visitCount = 0;
		String userIDKey = "userID";
		String userID = "elvis";

		if (session.isNew()) {
			session.setAttribute(userIDKey, userID);
		} else {
			Integer counts = (Integer) session.getAttribute(visitCountKey);
			visitCount = null == counts ? visitCount : counts;
			visitCount += 1;
			userID = (String) session.getAttribute(userIDKey);
		}

		session.setAttribute(visitCountKey, visitCount);

		int timeoutStart = session.getMaxInactiveInterval() / 60;
		session.setMaxInactiveInterval(11 * 60);
		int timeoutEnd = session.getMaxInactiveInterval() / 60;

		resp.setContentType("text/html;charset=UTF-8");
		PrintWriter out = resp.getWriter();

		String docType = "<!DOCTYPE html>\n";

		out.println(docType + "<html>\n" + "<head><title>" + title + "</title></head>\n"
				+ "<body bgcolor=\"#f0f0f0\">\n" + "<h1 align=\"center\">" + title + "</h1>\n"
				+ "<h2 align=\"center\">Session 信息</h2>\n"
				+ "<table border=\"1\" align=\"center\">\n" + "<tr bgcolor=\"#949494\">\n"
				+ "  <th>Session 信息</th><th>值</th></tr>\n" + "<tr>\n" + "  <td>id</td>\n" + "  <td>"
				+ session.getId() + "</td></tr>\n" + "<tr>\n" + "  <td>创建时间</td>\n" + "  <td>" + df
				.format(createTime) + "  </td></tr>\n" + "<tr>\n" + "  <td>最后访问时间</td>\n" + "  <td>"
				+ df.format(lastAccessTime) + "  </td></tr>\n" + "<tr>\n" + "  <td>用户 ID</td>\n"
				+ "  <td>" + userID + "  </td></tr>\n" + "<tr>\n" + "  <td>访问统计：</td>\n" + "  <td>"
				+ visitCount + "</td></tr>\n" + "<tr>\n" + " <td>初始session过期时间（min）</td>\n"
				+ "  <td>" + timeoutStart + "</td></tr>\n" + "<tr>\n"
				+ " <td>设置后的session过期时间（min）</td>\n" + "  <td>" + timeoutEnd + "</td></tr>\n"
				+ "</table>\n"
				+ " <br /><div align=\"center\"><button align=\"center\" value=\"refresh\" onclick=\"window.location.href='http://localhost:8080/sessionTrack'\">Refresh</button></div>"
				+ "<form action=\"/sessionTrack\" method=\"post\" align=\"center\">\n"
				+ "<input type=\"submit\" value=\"clear session\"/>\n" + "</form></body></html>");

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		HttpSession session = req.getSession();
		//session 失效
		session.invalidate();
		doGet(req, resp);
	}
}
