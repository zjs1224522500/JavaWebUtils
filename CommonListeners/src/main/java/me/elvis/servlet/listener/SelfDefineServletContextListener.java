package me.elvis.servlet.listener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Version:v1.0 (description:  ) Date:2017/11/21 0021  Time:13:24
 */
public class SelfDefineServletContextListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext servletContext = sce.getServletContext();
		String path = servletContext.getRealPath("/count.txt");

		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
			String line = bufferedReader.readLine();
			Integer count = Integer.valueOf(line);
			servletContext.setAttribute("count", count);
		} catch (Exception e) {
			e.printStackTrace();
			servletContext.setAttribute("count", new Integer(0));
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		ServletContext servletContext = sce.getServletContext();
		String path = servletContext.getRealPath("/count.txt");

		try {
			PrintWriter pw = new PrintWriter(path);
			pw.write("" + servletContext.getAttribute("count"));
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}
}
