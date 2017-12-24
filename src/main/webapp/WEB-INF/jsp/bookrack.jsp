<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%--
  Created by IntelliJ IDEA.
  User: wliu
  Date: 2017/12/9 0009
  Time: 8:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>我的书架</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <link href="/css/bootstrap.min.css" rel="stylesheet"/>
    <link href="/css/section.css" rel="stylesheet"/>
    <link href="/css/bootstrap-theme.min.css" rel="stylesheet"/>
    <script type="text/javascript" src="/js/jquery-1.8.3.js"></script>
    <script type="text/javascript" src="/js/bootstrap.min.js"></script>
    <style type="text/css">
        .op_bt{
            height: 22px;
            width: 75px;
            background-color: goldenrod;
        }
        table{
            width: 95%;
            border-radius: 20px;
            /*border: 2px solid #92B8B1;*/
            border-collapse:collapse;
            border-spacing:1px;
        }
        table td{
            padding:5px;
            /*border: 1px solid #92B8B1;*/
        }
        table th{
            padding:5px;
            /*border: 1px solid #92B8B1;*/
        }
        button{
            border-radius: 5px;
        }
        @media screen and (max-width:1086px){
            #bookrack_div_search{
                display: none;
            }

        }
    </style>
    <script>

        $(function(){
            $("#go_index").click(function(){
                window.location.href = "/book/bookstorage/1"
            })
        })
    </script>
</head>
<body>
    <div id="div_body" class="container-fluid">
        <div class="row" style="height: auto;">
            <div class="col-md-2" style="background: border-box;">
            </div>
            <div class="col-md-8">
                <div class="row">
                    <div class="col-md-4 float_left" style="height: 60px;">
                        <font size="5" class="float_left" style="font-family:LiSu,serif"><img id="img_logo" src="/img/kaideng.png" height="40" width="40"/>飞马追书</font>
                    </div>
                    <div class="col-md-6 float_right">
                        <div class="row" style="height: 10px; margin-top: 0px;">
                            <c:if test="${empty user.userName}">
                                <a href="#" class="float_right">登录</a></font>
                            </c:if>
                            <c:if test="${not empty user.userName}">
                                <font class="float_right" style="margin-right: 4px;">用户：${user.userName}&nbsp;|&nbsp;<a href="/user/outlogin">退出</a></font>
                            </c:if>
                        </div>
                        <div id="bookrack_div_search" class="row" style="height: 50px;">
                            <div class="col-md-12" style="position: absolute;margin-left: 200px; margin-top: 15px;">
                                <form class="form-search">
                                    <input id="search"  type="text" placeholder="请输入书名" style="height: 30px; width: 180px; border: 1px solid cadetblue;"/>
                                    <button type="button" class="btn-sm" style="background-color: cadetblue;">搜索</button>
                                </form>
                            </div>
                        </div>

                    </div>

                </div>
                <div class="row">
                    <div id="div_bookrack" class="col-md-2 float_left">
                        <button id="go_index" style="position: relative; left: 5px" id="bt_bookrack">首页</button>
                    </div>
                </div>
                <div class="row">
                    <div id="div_booklist" style="position:relative;top: 10px;width: 100%; <c:if test="${not empty size}">height:580px;</c:if>background-color:darkgray; border-radius: 15px">
                        <table align="center" style="border-radius: 20px;">
                            <tr align="center">
                                <td style="align-content: center;font-size:xx-large; font-family: 隶书,serif" colspan="6">我的书架</td>
                            </tr>
                            <tr>
                                <th width="250">书名</th>
                                <th width="150">作者</th>
                                <th width="400">阅读进度</th>
                                <th width="400">最新章节</th>
                                <th width="120">是否完结</th>
                                <th width="100">追书</th>
                            </tr>
                            <c:forEach var="bookrack" items="${bookracks}">
                                <tr bgcolor="#ffffff">
                                    <td >
                                        <a href="/book/catalog/${bookrack.book.id}">${bookrack.book.name}</a>
                                    </td>
                                    <td >${bookrack.book.author}</td>
                                    <td >
                                        <c:if test="${not empty bookrack.readProgress}">
                                            <a href="${bookrack.readPath}" target="_blank">${bookrack.readProgress}</a>
                                        </c:if>
                                        <c:if test="${empty bookrack.readProgress}">
                                            <font color="red">未阅读</font>
                                        </c:if>
                                    </td>
                                    <td >
                                        <a href="${bookrack.book.lasterPath}" target="_blank">${bookrack.book.lasterSection}</a>
                                    </td>
                                    <td >
                                        <c:if test="${bookrack.book.status == 0}">完本</c:if>
                                        <c:if test="${bookrack.book.status == 1}">连载中</c:if>
                                    </td>
                                    <td style=" font-size: small">
                                        <c:if test="${bookrack.tracerStatus == 0}">
                                            <button class="op_bt">开启追书</button>
                                        </c:if>
                                        <c:if test="${bookrack.tracerStatus == 1}">追更中</c:if>
                                    </td>
                                </tr>

                            </c:forEach>

                        </table>
                    </div>
                </div>
                <div class="navbar-fixed-bottom" style="margin-bottom: 10px">
                    <div align="center" style="position:relative;top: 5px;height: 20px;">
                        <a>www.fmzs365.cn</a>&nbsp;|&nbsp;<nb></nb><a>飞马追书</a>
                    </div>
                </div>
            </div>
            <div class="col-md-2"></div>
        </div>
    </div>
</body>
</html>
