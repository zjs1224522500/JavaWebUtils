package me.elvis.interceptor.pojo;

/**
 * Version:v1.0 (description:  ) Date:2017/12/28 0028  Time:00:26
 */
public class User {

	private String userId;

	private String userName;

	private String userRole;

	private String desc;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	@Override
	public String toString() {
		return "User{" + "userId='" + userId + '\'' + ", userName='" + userName + '\''
				+ ", userRole='" + userRole + '\'' + ", desc='" + desc + '\'' + '}';
	}
}
