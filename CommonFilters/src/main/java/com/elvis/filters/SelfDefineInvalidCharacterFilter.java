package com.elvis.filters;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Version:v1.0 (description: 非法字符过滤器  ) Date:2017/10/30 0030  Time:21:35
 */
public class SelfDefineInvalidCharacterFilter implements Filter{

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain filterChain) throws IOException, ServletException {
		String parameterName = null;
		String parameterValue = null;
		// 获取请求的参数
		@SuppressWarnings("unchecked")
		Enumeration<String> allParameter = servletRequest.getParameterNames();

		// 遍历参数名对应的枚举
		while(allParameter.hasMoreElements()){
			parameterName = allParameter.nextElement();
			//获取参数名对应的参数值
			parameterValue = servletRequest.getParameter(parameterName);
			if(null != parameterValue){
				// 检索参数值是否包含在非法字符中
				for(String str : invalidCharacter){
					if (StringUtils.containsIgnoreCase(parameterValue, str)){
						servletRequest.setAttribute("errorMessage", "非法字符：" + str);
						RequestDispatcher requestDispatcher = servletRequest.getRequestDispatcher("/error.jsp");
						requestDispatcher.forward(servletRequest, servletResponse);
						return;
					}
				}
			}
		}
		// 执行目标资源，放行
		filterChain.doFilter(servletRequest, servletResponse);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void destroy() {

	}

	// 需要过滤的非法字符
	private static String[] invalidCharacter = new String[]{
			"script","select","insert","document","window","function",
			"delete","update","prompt","alert","create","alter",
			"drop","iframe","link","where","replace","function","onabort",
			"onactivate","onafterprint","onafterupdate","onbeforeactivate",
			"onbeforecopy","onbeforecut","onbeforedeactivateonfocus",
			"onkeydown","onkeypress","onkeyup","onload",
			"expression","applet","layer","ilayeditfocus","onbeforepaste",
			"onbeforeprint","onbeforeunload","onbeforeupdate",
			"onblur","onbounce","oncellchange","oncontextmenu",
			"oncontrolselect","oncopy","oncut","ondataavailable",
			"ondatasetchanged","ondatasetcomplete","ondeactivate",
			"ondrag","ondrop","onerror","onfilterchange","onfinish","onhelp",
			"onlayoutcomplete","onlosecapture","onmouse","ote",
			"onpropertychange","onreadystatechange","onreset","onresize",
			"onresizeend","onresizestart","onrow","onscroll",
			"onselect","onstaronsubmit","onunload","IMgsrc","infarction"
	};

}
