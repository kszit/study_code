<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %> 
<base href="<%=basePath%>">
<style type="text/css">
body{
	padding:15px;
}
li{
	margin:10px;
}

</style>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>ui 列表</title>
</head>

<body>
	<li><a href="popWin/1/test.html" target="_blank">弹出框(ajax)</a></li>
	
	<li><a href="popWin/2/index.html" target="_blank">弹出框(跨域)</a></li>
	
	<li><a href="frame/ace/index.html" target="_blank">页面框架(ace)</a></li>
	
</body>
</html>