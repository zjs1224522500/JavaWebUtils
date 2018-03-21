<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>文件上传实例</title>
</head>
<body>
<h1>文件上传实例</h1>
<form method="post" action="/spring/upload" enctype="multipart/form-data">
    选择一个文件:
    <input type="file" name="file" />
    <br/><br/>
    <input type="submit" value="上传" />
</form>

<br />
<br />

<form action="/download" method="get">
    <input type="hidden" name="fileName" value="4006.doc" />
    <input type="submit" value="下载文件servlet" />
</form>

<form action="/spring/download" method="get">
    <input type="text" name="fileName" />
    <input type="submit" value="下载文件spring" />
</form>

<form action="/spring/mvc/download" method="get">
    <input type="text" name="fileName" />
    <input type="submit" value="下载文件springmvc" />
</form>
</body>
</html>