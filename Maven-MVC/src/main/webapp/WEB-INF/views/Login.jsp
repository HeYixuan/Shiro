<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ page import="org.apache.shiro.authc.ExcessiveAttemptsException"%>
<%@ page import="org.apache.shiro.authc.IncorrectCredentialsException"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html lang="zh-CN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1.0" />
<title>Bootstrap响应式登录界面模板</title>
<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/login.min.css" />
<script src="${ctx}/resources/js/jquery-3.0.0.min.js"></script>
</head>

<body>
<div class="box">
		<div class="login-box">
			<div class="login-title text-center">
				<h1><small>登录</small></h1>
			</div>
			<div class="login-content ">
			<div class="form">
			<form action="${ctx}/doLogin" method="post">
				<div class="form-group">
					<div class="col-xs-12  ">
						<div class="input-group">
							<span class="input-group-addon"><span class="glyphicon glyphicon-user"></span></span>
							<input type="text" id="username" name="username" class="form-control" maxlength="20" placeholder="用户名" />
						</div>
					</div>
				</div>
				<div class="form-group">
					<div class="col-xs-12  ">
						<div class="input-group">
							<span class="input-group-addon"><span class="glyphicon glyphicon-lock"></span></span>
							<input type="password" id="password" name="password" class="form-control" maxlength="20" placeholder="密码" />
						</div>
					</div>
				</div>
				<div class="form-group">
					<div class="col-xs-4  ">
						<div class="input-group">
							<!-- <span class="input-group-addon"><span class="glyphicon glyphicon-lock"></span></span> -->
							<input id="rememberMe" name="rememberMe" type="hidden"/>
							<input type="text" id="captcha" name="captcha" class="form-control" maxlength="4" placeholder="验证码" />
							<img style="height: 35px; width: 100px;" id="code_img" src="${ctx}/kaptcha" title="点击刷新图片" />
						</div>
					</div>
				</div>
				<div class="form-group form-actions">
					<div class="col-xs-4 col-xs-offset-4 ">
						<button type="submit" class="btn btn-sm btn-info"><span class="glyphicon glyphicon-off"></span> 登录</button>
					</div>
				</div>
				<div class="form-group">
					<div class="col-xs-6 link">
						<p class="text-center remove-margin"><small>忘记密码？</small> <a href="javascript:void(0)" ><small>找回</small></a>
						</p>
					</div>
					<div class="col-xs-6 link">
						<p class="text-center remove-margin"><small>还没注册?</small> <a href="javascript:void(0)" ><small>注册</small></a>
						</p>
					</div>
				</div>
			</form>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
$(function(){
	$("#code_img").click(function(){
		flash(this);
	});
});

	function flash(obj) {
		//获取当前的时间作为参数，无具体意义
		var NowTime = new Date().getTime();
		//每次请求需要一个不同的参数，否则可能会返回同样的验证码
		//这和浏览器的缓存机制有关系，也可以把页面设置为不缓存，这样就不用这个参数了。
		obj.src="${ctx}/kaptcha?date"+NowTime;
	}
</script>
</body>
</html>