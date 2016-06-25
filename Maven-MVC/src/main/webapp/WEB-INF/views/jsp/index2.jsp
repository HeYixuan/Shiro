<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="../js/jquery-1.8.0.js" ></script>
</head>
<body>
<form name="form1" id="form1" action="../home/uploads" method="POST" enctype="multipart/form-data">
<!-- name:<input type="text" name="username" class="username"/><br/>
sex:<input type="radio" checked="checked" name="sex" value="0"/>男 &nbsp;&nbsp;&nbsp;<input type="radio" name="sex" value="1"/>女 <br/> -->
file:<input type="file" name="files" class="file"/><br/>
file:<input type="file" name="files" class="file"/><br/>
file:<input type="file" name="files" class="file"/><br/>

<input type="button" class="btnSave" value="保存"/>
</form>
</body>
<script type="text/javascript">
$(function(){
	$(".btnSave").click(function(){
		checkFiles();
	});
});

function checkFiles(){
	var filepath=$(".file").val();
	var extStart=filepath.lastIndexOf(".");
	var ext=filepath.substring(extStart,filepath.length).toUpperCase();
	if(filepath.length==0){
		alert("请选择一张图片");
		return false;
	}else if(ext!=".BMP" && ext!=".PNG" && ext!=".JPG" && ext!=".JPEG"){
		alert("图片限于png,jpeg,jpg格式");
		return false;
	}else{
		$("#form1").submit();
	}
}

</script>
</html>