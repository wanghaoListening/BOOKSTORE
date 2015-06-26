<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>注册</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<meta http-equiv="content-type" content="text/html;charset=utf-8">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<style type="text/css">
body {
	text-align: center;
	margin-left: auto;
	margin-right: auto;
	background-image: url("<c:url value='/book_img/registe.jpg'/>") ;
}

form {
	border: 3px;
	margin-top: 300px;
	
}
</style>
<script type="text/javascript">
	function imageChange() {
		var srcNode = document.getElementById("imgChange");
		srcNode.src = "/BOOKSTORE/VerifyCodeServlet?time="
				+ new Date().getTime();
		//后面加参数的目的是确保每次请求的URL都不同，防止浏览器缓存图片。
	}
</script>
</head>

<body>
	
	<center>
		<h1>用户信息注册</h1>
		<marquee bgcolor="gray" style="font-size: 30px;font-style: italic;color: red;">欢迎注册网上商城</marquee>
		<form action='<c:url value="/UserServlet"/>' method="Post">
			
				用户名：<input type="text" name="username"
					value="${requestScope.form.username }" /> <span
					style="color: red; font-weight: 900">${errors.username }</span> <br />
			
			
				密 码：<input type="password" name="password" /> <span
					style="color: red; font-weight: 900">${errors.password }</span> <br />
			
			
				邮 箱：<input type="text" name="email"
					value="${requestScope.form.email}" /> <span
					style="color: red; font-weight: 900">${errors.email }</span> <br />
			
			验证码：<input type="text" name="Verify" size="4px"> <img
				id="imgChange" src='<c:url value="/VerifyCodeServlet"/>'> <input
				type="button" value="换一张" onclick="imageChange()"> <span
				style="color: red; font-weight: 900">${errors.Verify}</span> <br />
			
			<input type="submit" value="注册" />
		</form>
	</center>
</body>
</html>
