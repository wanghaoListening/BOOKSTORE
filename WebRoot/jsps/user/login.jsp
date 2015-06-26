<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>登录</title>

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
	background-image: url("<c:url value='/book_img/login.jpg'/>") ;
}

form {
	border: 3px;
	margin-top: 220px;
	
}
</style>
<script type="text/javascript">
	//创建异步对象
	function createXMLHttpRequest() {
		try {
			return new XMLHttpRequest();//大多数浏览器
		} catch (e) {
			try {
				return ActvieXObject("Msxml2.XMLHTTP");//IE6.0
			} catch (e) {
				try {
					return ActvieXObject("Microsoft.XMLHTTP");//IE5.5及更早版本	
				} catch (e) {
					alert("哥们儿，您用的是什么浏览器啊？");
					throw e;
				}
			}
		}
	}

	function linkServer(name, ele) {
		var xmlHttp = createXMLHttpRequest();
		xmlHttp.open("post", "/BOOKSTORE/LoginServlet", "true");
		xmlHttp.setRequestHeader("Content-Type",
				"application/x-www-form-urlencoded");
		xmlHttp.send(name + ele.value);

		xmlHttp.onreadystatechange = function() {//当xmlHttp的状态发生变化时执行
			// 双重判断：xmlHttp的状态为4（服务器响应结束），以及服务器响应的状态码为200（响应成功）
			if (xmlHttp.readyState == 4 && xmlHttp.status == 200) {
				// 获取服务器的响应结束
				var text = xmlHttp.responseText;
				var num = text.charAt(0);
				var message = text.substring(3, text.length);
				if (num == '1') {
					var username = document.getElementById("us");
					username.innerHTML = message;
				} else if (num == '2') {
					var password = document.getElementById("pw");
					password.innerHTML = message;
				} else {
					var verify = document.getElementById("vf");
					verify.innerHTML = message;
				}
			}
		};

	}

	window.onload = function() {
		var userEle = document.getElementById("user");
		userEle.onblur = function() {//注册失去焦点事件
			linkServer("username=", userEle);
		};

		var passEle = document.getElementById("pass");
		passEle.onblur = function() {//注册失去焦点事件
			var us = "flag=open&" + "username=" + userEle.value + "&";
			linkServer(us + "password=", passEle);
		};

		var verifyEle = document.getElementById("verify");
		verifyEle.onblur = function(verifyEle) {//注册失去焦点事件

			var vf = "username=" + userEle.value + "&" + "password="
					+ passEle.value + "&";
			linkServer(vf + "Verify=", verifyEle);
		};

	};
	//动态的改变验证码
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
		<h1>图书商城登录</h1>&nbsp;&nbsp;
		<a href="<c:url value='/jsps/main.jsp'/>" style="margin-left: 860px;color: green;">首页</a>&nbsp;&nbsp;
		<a href="<c:url value='/jsps/user/regist.jsp'/>" style=" color: red;">注册</a>
		
		
		<hr color="green">		
		<marquee bgcolor="gray" style="font-size: 30px;font-style: italic;color: red;">欢迎登录网上商城</marquee>
		<form action="<c:url value='/LoginServlet'/>" method="post"
			target="_top">
			<input type="hidden" name="vf" value="open"> 用户名：<input
				type="text" name="username" value="${requestScope.form.username }"
				id="user" /> <span id="us" style="color: red;"></span> <br /> 密 码：<input
				type="password" name="password" id="pass" /> <span id="pw"
				style="color: red;"></span> <br /> 验证码：<input type="text"
				name="Verify" size="4px" id="verify"> <img id="imgChange"
				src='<c:url value="/VerifyCodeServlet"/>'> <input
				type="button" value="换一张" onclick="imageChange()"> <span
				id="vf" style="color: red;"></span> <br /> <input type="submit"
				value="登录" />

		</form>
	</center>
</body>
</html>
