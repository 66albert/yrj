<%--
  Created by IntelliJ IDEA.
  User: 刘彬
  Date: 2023/2/26
  Time: 12:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>云R记</title>
    <link href="static/css/login.css" rel="stylesheet" type="text/css"/>
    <script src="static/js/jquery-1.11.3.js" type=text/javascript></script>
    <script src="static/js/config.js" type=text/javascript></script>
    <script src="static/js/util.js" type="text/javascript"></script>

</head>
<body>
<!--head-->
<div id="head">
    <div class="top">
        <div class="fl yahei18">开启移动办公新时代！</div>
    </div>
</div>

<!--login box-->
<div class="wrapper">
    <div id="box">
        <div class="loginbar">用户登录</div>
        <div id="tabcon">
            <div class="box show">
                <form action="user" method="post" id="loginForm">
                    <%--  actionName表示用户行为，通过这个参数可以在UserServlet中判断用户当前想要的操作  --%>
                    <input type="hidden" name="actionName" value="login"/>
                    <input type="text" class="user yahei16" id="userName" value="${resultInfo.result.uname}" name="userName"/><br/><br/>
                    <input type="password" class="pwd yahei16" id="userPwd" value="${resultInfo.result.upwd}" name="userPwd"/><br/><br/>
                    <input name="rem" type="checkbox" value="1" class="inputcheckbox"/> <label>记住我</label>&nbsp; &nbsp;
                    <span id="msg" style="color: red; font-size: 12px">${resultInfo.msg}</span><br/><br/>
                    <input type="button" class="log jc yahei16" value="登 录" onclick="checkLogin()"/>&nbsp; &nbsp; &nbsp;
                    <input type="reset" value="取 消" class="reg jc yahei18"/>
                </form>
            </div>
        </div>
    </div>
</div>

<div id="flash">
    <div class="pos">
        <a bgUrl="static/images/banner-bg1.jpg" id="flash1" style="display:block;"><img
                src="static/images/banner_pic1.png"></a>
        <a bgUrl="static/images/banner-bg2.jpg" id="flash2"><img src="static/images/banner-pic2.jpg"></a>
    </div>
    <div class="flash_bar">
        <div class="dq" id="f1" onclick="changeflash(1)"></div>
        <div class="no" id="f2" onclick="changeflash(2)"></div>
    </div>
</div>

<!--bottom-->
<div id="bottom">
    <div id="copyright">
        <div class="quick">
            <ul>
                <li><input type="button" class="quickbd iphone" onclick="location.href=''"/></li>
                <li><input type="button" class="quickbd android" onclick="location.href=''"/></li>
                <li><input type="button" class="quickbd pc" onclick="location.href=''"/></li>
                <div class="clr"></div>
            </ul>
            <div class="clr"></div>
        </div>
        <div class="text">
            Copyright © 2006-2026 <a href="#">Alert Liew</a> All Rights Reserved
        </div>
    </div>
</div>
</body>

</html>

