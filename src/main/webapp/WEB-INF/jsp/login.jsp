<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%--
  Created by IntelliJ IDEA.
  User: wliu
  Date: 2017/12/10 0010
  Time: 16:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登录</title>
    <link href="/css/login.css" rel="stylesheet"/>
</head>
<body>
    <div class="wrap">
        <form action="/user/login" method="post">
            <section class="loginForm">
                <header>
                    <div align="center" style="position: relative; margin-top: 10px;">
                        <font size="7" class="float_left" style="font-family:LiSu;">飞马追书</font>
                    </div>
                </header>
                <div class="loginForm_content">
                    <fieldset>
                        <div class="inputWrap">
                            <input name="userName" placeholder="请输入用户名" type="text" value="${userName}">
                        </div>
                        <div class="inputWrap">
                            <input name="password" placeholder="请输入密码" type="password">
                        </div>
                        <div >
                            <font color="red" style="margin-left: 10px">&emsp;${errorMsg}</font>
                        </div>
                    </fieldset>
                    <fieldset>
                        <input name="remberLogin" checked="checked" type="checkbox">
                        <span>下次自动登录</span>
                    </fieldset>
                    <fieldset>
                        <input value="登录" type="submit">
                        <a href="#">忘记密码？</a> <a href="#">注册</a>
                    </fieldset>
                </div>
            </section>
        </form>
    </div>
</body>
</html>
