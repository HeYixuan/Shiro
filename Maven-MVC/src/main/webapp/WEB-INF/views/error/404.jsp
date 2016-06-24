<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="zh-CN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1.0" />
<title>404页面</title>

<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/error.min.css" />
</head>
<body>


<div id="container">
	<img class="png" src="${ctx}/resources/images/404.png" />
	<img class="png msg" src="${ctx}/resources/images/404_msg.png" />
	<p><a href="javascript:void(0)" target="_blank"><img class="png" src="${ctx}/resources/images/404_to_index.png" /></a> </p>
</div>

<div id="cloud" class="png"></div>


 
</body>
</html>