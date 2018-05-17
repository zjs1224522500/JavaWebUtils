package me.elvis.interceptor.demo;

import com.sun.org.apache.xml.internal.serializer.utils.SerializerMessages_es;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import me.elvis.interceptor.pojo.User;

import static me.elvis.interceptor.constants.SessionConstants.SESSION_USER_IDENTIFIER;
import static me.elvis.interceptor.constants.SessionConstants.SESSION_USER_INFO_ENTITY;

/**
 * Version:v1.0 (description:  ) Date:2017/12/27 0027  Time:23:46
 */
public class UserRoleAuthorizationInterceptor extends HandlerInterceptorAdapter {

	private String[] authorizedRoles;

	public final void setAuthorizedRoles(String[] authorizedRoles) {
		this.authorizedRoles = authorizedRoles;
	}


	private static final String DEFAULT_PAGE = "default";

	private static final String SIGN_OUT = "logout";

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler) throws Exception {

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(SESSION_USER_INFO_ENTITY);

		//检查用户认证功能情况
		boolean isSignIn = checkUserAuthorization(request);
		if (!isSignIn) {
			//TODO 对未登录对请求进行跳转登录操作
			return super.preHandle(request, response, handler);
		}

		return super.preHandle(request, response, handler);
	}

	private boolean checkUserAuthorization(HttpServletRequest request) throws Exception {
		String currentUrl = request.getServletPath();
		HttpSession session = request.getSession();

		if (currentUrl.contains(DEFAULT_PAGE)) {
			return false;
		}
		Integer userId = (Integer) session.getAttribute(SESSION_USER_IDENTIFIER);

		//判断是否登录，已登录返回true，否则返回false
		//登出请求不进行后续处理
		return !currentUrl.contains(SIGN_OUT) && userId != null;

	}
}
