/*
 * Project: workload（工作量计算系统）
 * File: RegionManagerController.java
 * Author: 张健顺
 * Email: 1224522500@qq.com
 * Copyright: Copyright (c) 2017 OSTEC. All rights reserved.
 */
package cn.edu.uestc.ostec.workload;

/**
 * Description: Session域常量
 * Version:v1.0 (author:刘文哲 update: 无 )
 */
public interface SessionConstants {

	/**
	 * 用户标识符
	 */
	String SESSION_USER_IDENTIFIER = "userId";

	/**
	 * 用户标识符
	 */
	String SESSION_USER_INFO_ENTITY = "user";

	/**
	 * 用户名称
	 */
	String SESSION_USER_NAME="userName";

	/**
	 * 用户角色信息(拦截器注入session)<br/>
	 * &lt;UserType.code,List&lt;Integer&gt;relatedId&gt;
	 */
	String SESSION_USER_ROLE_MAP = "userRoleMap";

	/**
	 * 用户角色信息(拦截器注入session)<br/>
	 * List&lt;RoleInfo&gt;
	 */
	String SESSION_USER_ROLE_LIST = "userRoleList";

	String SESSION_CURRENT_YEAR = "currentYear";

	String SESSION_CURRENT_SCHEME = "currentScheme";

	String SESSION_CURRENT_SCHOOL_YEARS = "years";

	String SESSION_CAS_USER_PROFILE_URL = "userProfile";

}
