<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/11/21 0021
  Time: 21:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>在线人的信息与网站点击量的实现</title>
</head>
<body>
<h3>在线人的信息与网站点击量的实现</h3>
<a href='<c:url value="servlet/ShowServlet"></c:url>'>查看在线人信息</a>
<hr/>
<form action='<c:url value="servlet/LoginServlet"></c:url>' method="post">
    姓名:<input type="text" name="name"/>
    <input type="submit" value="登录">
</form>

<br/>点击量:${count}
</body>
</html>
