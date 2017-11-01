<%@ page contentType="text/html;charset=UTF-8" language="java"  pageEncoding="utf-8" %>
<html>
<head>
    <title>Title</title>
    <script type="text/javascript">
        function reloadCode() {
            var time = new Date().getTime();
            document.getElementById("imageCode").src="<%=request.getContextPath()%>/servlet/ImageServlet?d=" + time;
        }
    </script>
</head>
<body>
    <form action="<%=request.getContextPath()%>/servlet/LoginServlet" method="get">

    验证码：<input type="text" id="verifyCode" name="verifyCode" size="6"/>
    <img alt="验证码" id="imageCode" src="<%=request.getContextPath()%>/servlet/ImageServlet">
    <a href="javascript:reloadCode();">看不清楚</a>
    <input type="submit" value="提交">
    </form>
<br>
</body>
</html>
