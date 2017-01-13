<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>VGOP下发号码反馈后台</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<style type="text/css">
body {
	text-align: center
}

.headDiv {
	margin: 0 auto;
	width: 800px;
	height: 50px;
	background-color: #467995;
}

.headDiv #logo {
	margin: 10 20 auto;
	float: left;
	text-align: center;
	font-weight: bold;
	font-size: 20px;
	color: white;
}

.headDiv #info {
	margin: 10px auto;
	float: right;
	width: 300px;
}

#info div {
	margin: 0px 10px 0px;
	float: left;
	width: 120px;
}

#info div a {
	text-decoration: none;
	color: white;
}

#info div a:hover {
	color: #333;
}

#userId {
	color: white;
}

.div {
	margin: 0 auto;
	width: 800px;
	height: 600px;
	border: 1px solid none;
}

.head {
	width: 800px;
	height: 34px;
	background-color: #9ba2aa;
}

.head ul {
	padding: 0px;
	margin: 0px
}

.head ul li {
	float: left;
	display: block;
	font-weight: bold;
	font-size: 14px
}

.head ul li a {
	float: left;
	background: url(images/nav_link.gif) bottom no-repeat;
	width: 106px;
	height: 35px;
	line-height: 34px;
	color: #333;
	text-decoration: none;
	text-align: center
}

.head ul li a:hover {
	color: #333
}

.head ul li a.thisclass,.nav ul li a.thisclass:hover {
	background: url(images/nav_current.gif) bottom no-repeat;
}

.content {
	height: 100%;
	width: 99%;
	border: 2px solid #BFBFBF;
}

.content iframe {
	height: 100%;
	width: 100%;
	background-color: transparent;
}
</style>

<script type="text/javascript" src="js/jquery-1.7.2.js"></script>
<script type="text/javascript" src="js/jquery.simplemodal.js"></script>

<script type="text/javascript">

$(document).ready(function() {

	$("#head").find("a").click(function(){

		$("#head").find("a").css("background","url('images/nav_link.gif')");

		$(this).css("background","url('images/nav_current.gif')");

	});
});
function showModal(msg)
	{
		var modal = $.modal("<img src='images/sl_img.gif'/><div>"+msg+"</div>",{
			opacity:80,
  			overlayCss: {backgroundColor:"#242424"},
  			minHeight:200,
  			minWidth:200,
  			containerCss:{color:"red"}
		});
		return modal;
	}
function colseModal(modal)
	{
		modal.close;
	}
</script>
</head>

<body>
	<div class="headDiv">
		<div id="logo">VGOP下发号码反馈后台</div>
		<div id="info">
		</div>
	</div>
	<div class="div">

		<div class="head" id="head">
			<ul>
				<li><a class="thisclass" id="menu_datafile"
					href="<%=basePath%>createDataFile.jsp" target="main">生成数据文件</a></li>
				<li><a href="<%=basePath%>sftpInfo.jsp" id="menu_sftpinfo"
					target="main">SFTP信息管理</a></li>
			</ul>

		</div>

		<div class="content" id="content">
			<iframe id="main" name="main" frameborder="0"
				src="<%=basePath%>createDataFile.jsp"></iframe>
		</div>
	</div>
</body>
</html>
