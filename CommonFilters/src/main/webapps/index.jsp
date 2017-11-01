<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>Hello,Filters!</h1>
<form action="<%=request.getContextPath()%>/test" method="post">
    <input type="text" name="name" value="script"/>
    <input type="submit" value="提交"/>
</form>

<form action="<%=request.getContextPath()%>/sessionTrack" method="get">
    <input type="text" value="查看Session" />
    <input type="submit" value="提交"/>
</form>

</body>
</html>
