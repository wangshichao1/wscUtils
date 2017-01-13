<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>生成数据文件</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<style type="text/css">

    .body{
		font-family: "微软雅黑";
		font-size: 18px;
    }
    a{
    	text-decoration: none;
    }
    #content {
    	margin-top:20px;
        width: 500px;
        height: 300px;
        margin: 0px auto;
        border: 1px solid #272822;
        border-radius: 10px;
        background: #624118;
        padding-left: 20px;
    }

    #content>div{
    	margin-top: 20px;
    }
    
    select {
        background: #A2A2A0;
        font-size: 20px;
        font-weight: bold;
        color: #211503;
        border-radius: 5px;
    }
    #operaDiv{
    	width:125px;
    	height: 30px;
    	margin: 0px auto;
    	text-align: center;
    }
    #operaDiv a{
    	width: 125px;
    	height: 30px;
    	border:1px solid blue;
    	border-radius: 5px;
    	line-height: 30px;
		display: inline-block;
    	margin: 0px auto;
    	background: #B2C8BB;
    	font-weight: bold;
    }
    #operaDiv a:hover{
    	width:125px;
    	height: 30px;
    	margin: 0px auto;
    	background: #458994;
    	font-weight: bold;
    	color:white;
    	border-color: white;
    }
    #result span{
		color:#F8931D;
		font-weight: bold;
		font-size: 20px;
		margin-bottom: 0px;
    }
    #res{
    	margin: 0px;
    	border:1px solid #5F5C33;
    	border-radius: 10px;
    	width:400px;
    	height: 120px;
    	background: #CDB380;
    	color:#054B67;
    	padding: 8px;
    	font-size: 15px;
    	overflow:hidden; 
    	resize:none; 
    }
    </style>
    <script type="text/javascript" src="js/jquery-1.7.2.js"></script>
    <script type="text/javascript">
    $(function() {
        initDateSlect();
        $("#createFile").click(function(){
        	createFile();
        });
    });

    function initDateSlect() {
        var nowDate = new Date();
        var year = nowDate.getFullYear();
        $("#yearSelect").val(year);
        var month = nowDate.getMonth() + 1;
        $("#monthSelect").val(year);
        var day = nowDate.getDate();
        var maxDay = new Date(year, nowDate.getMonth(), 0).getDate();
        var con = "";
        for (var i = 1; i <= maxDay; i++) {
            con = con + "<option value='" + i + "'>" + i + "</option>";
        }
        $("#daySelect").empty();
        $("#daySelect").html(con);
        $("#daySelect").val(day);
    }
    
    function createFile()
    {
    	var year = $("#yearSelect").val();
    	var month = $("#monthSelect").val();
    	var day = $("#daySelect").val();
    	var param = {"year":year,"month":month,"day":day};
		var modal;
		$.ajax( {
			url : '<%=basePath%>data2file.do',
			type : "post",
			async : true,
			beforeSend : function() {
				modal = window.top.showModal("正在生成数据文件，请稍等······");
			},
			data : param,
			dataType : "json",
			error : function() {
				$("#res").val("发送请求失败");
				modal.close();
			},
			success : function(data) {
				var isSuccess = data.isSuccess;
				var reason = data.reason;
				var fileNames = data.FileNames;
				if(isSuccess)
				{
					if(!fileNames)
					{
						$("#res").val("生成数据文件失败！\n返回信息："+reason);
					}else{
						$("#res").val("生成数据文件成功！\n返回信息："+reason+"\n文件信息："+fileNames);
					}
				}else{
					$("#res").val("生成数据文件失败！\n返回信息："+reason);
				}
				modal.close();
			}
		});
    }
    </script>
  </head>
  
  <body>
    <div id="content">
        <div class="dateDiv">
        	请选择日期：
            <select name="yearSelect" id="yearSelect">
                <option value="2016">2016</option>
                <option value="2017">2017</option>
                <option value="2018">2018</option>
                <option value="2019">2019</option>
                <option value="2020">2020</option>
            </select>
            年
            <select name="monthSelect" id="monthSelect">
                <option value="1">1</option>
                <option value="2">2</option>
                <option value="3">3</option>
                <option value="4">4</option>
                <option value="5">5</option>
                <option value="6">6</option>
                <option value="7">7</option>
                <option value="8">8</option>
                <option value="9">9</option>
                <option value="10">10</option>
                <option value="11">11</option>
                <option value="12">12</option>
            </select>
            月
            <select name="daySelect" id="daySelect">
            </select>
            日
        </div>
        <div id="operaDiv">
            <a id="createFile" href="javascript:volid(0);">生成数据文件</a>
        </div>
        <div id="result">
        	<span>运行结果：</span>
        	<textarea id="res" disabled></textarea>
        </div>
    </div>
  </body>
</html>
