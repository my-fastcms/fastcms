<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>XXX微商平台 -- 登陆</title>
<%-- <link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/assets/ico/favicon.ico" type="image/x-icon" /> --%>    
<!-- Css files -->
<link href="${pageContext.request.contextPath}/resources/assets/css/bootstrap.min.css?v=3.3.6" rel="stylesheet"/>		
<link href="${pageContext.request.contextPath}/resources/assets/css/font-awesome.min.css?v=4.4.0" rel="stylesheet"/>		
<link href="${pageContext.request.contextPath}/resources/assets/css/animate.css" rel="stylesheet"/>
<link href="${pageContext.request.contextPath}/resources/assets/css/style.css?v=4.1.0" rel="stylesheet"/>
<script src="${pageContext.request.contextPath}/resources/assets/js/jquery.min.js?v=2.1.4"></script>
<script src="${pageContext.request.contextPath}/resources/assets/js/bootstrap.min.js?v=3.3.6"></script>	
<script src="${pageContext.request.contextPath}/resources/js/obz.js"></script>
<!-- end: JavaScript-->
</head>
<body class="gray-bg">
<div class="middle-box text-center loginscreen  animated fadeInDown">
    <div>
        <div>
            <h1 class="logo-name"><img width="100" height="80" src="${pageContext.request.contextPath}/resources/images/dianbuLogo.png" alt="" /></h1>
        </div>
        <h3>欢迎使用XXX微商城平台</h3>

        <form class="m-t" role="form" action="index.html">
            <div class="form-group">
                <input id="username" name="username" type="text" class="form-control" placeholder="账号" required=""/>
            </div>
            <div class="form-group">
                <input id="password" name="password" type="password" class="form-control" placeholder="密码" required=""/>
            </div>
            <div class="form-group">
				<div class="controls">
					<label class="input-group col-sm-5">
						<input class="form-control" type="text" id="captcha" name="captchaToken" placeholder="验证码"/>
					</label>
					<label class="input-group col-sm-5">
						<img id="captchaImage" src="${pageContext.request.contextPath}/captcha?width=100&height=35&fontsize=30" alt="换一张" />
					</label>
				</div>
			</div>
            <button id="loginBtn" type="button" class="btn btn-primary block full-width m-b">登 录</button>
            <%-- <p class="text-muted text-center"> <a href="login.html#"><small>忘记密码了？</small></a> | 
            <a href="${pageContext.request.contextPath}/register">注册一个新账号</a>
            </p> --%>
        </form>
    </div>
</div>
<script type="text/javascript">
$(function(){
	var UA = navigator.userAgent.toLowerCase();
	var browerKernel = {
		isWebkit: function() {
			if (/webkit/i.test(UA)) return true;
			else return false;
		},
		isFirefox: function() {
			if (/firefox/i.test(UA)) return true;
			else return false;
		}
	}

	if(!browerKernel.isWebkit()){
		alert("您当前浏览器无法兼容本系统！\n推荐使用Google Chrome 浏览器！\n\n其它兼容浏览器：\n360浏览器极速模式\n搜狗高速浏览器3\n世界之窗极速版\n猎豹浏览器\nQQ浏览器\n其它浏览器请自测");
	}
	
	var $captcha = $("#captcha");
	var $captchaImage = $("#captchaImage");
	var change_captcha = function() {
		var timestamp = (new Date()).valueOf();
		var imageSrc = $captchaImage.attr("src");
		if(imageSrc.indexOf("?") >= 0) {
			imageSrc = imageSrc.substring(0, imageSrc.indexOf("?"));
		}
		imageSrc = imageSrc + "?timestamp=" + timestamp + "&width=100&height=35&fontsize=30";
		$captchaImage.attr("src", imageSrc);
	};

	$captchaImage.click(change_captcha);
	
	function login(){
		var username = $("#username").val();
		var password = $("#password").val();
		
		if(username == "" || password == ""){
			//BootstrapDialog.alert({title:'提示', message:'请填写账号密码！'});
			alert("请填写账号密码！");
			return;
		}
		
		var captcha = $("#captcha").val();
		
		if(captcha == null || captcha ==""){
			alert("请输入验证码");
			return;
		}
		
		var params = {};
		params.username = username;
		params.password = password;
		params.captchaToken = captcha;
		
		obz.ajaxJson("${pageContext.request.contextPath}/auth", params, function(resp){
			if(resp.code != 200){
				//BootstrapDialog.alert({title:'提示', message:resp.msg});
				alert(resp.msg);
				change_captcha();
				$("#captcha").val("");
				return;
			}
			location.href = "${pageContext.request.contextPath}/";
		});
	}
	
	$("#loginBtn").click(function(){
		login();
	});
	
	$(document).keyup(function(event){
		if(event.keyCode ==13){
			login();
		}
	});
	
});

</script>
</body>
</html>	