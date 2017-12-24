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
    <title>飞马追书</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <link rel="icon" href="/favicon.ico" type="image/x-icon" />
    <link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
    <link rel="bookmark" href="/favicon.ico" type="image/x-icon" />
    <link href="/css/bootstrap.min.css" rel="stylesheet"/>
    <link href="/css/section.css" rel="stylesheet"/>
    <link href="/css/bootstrap-theme.min.css" rel="stylesheet"/>
    <script type="text/javascript" src="/js/jquery-1.8.3.js"></script>
    <script type="text/javascript" src="/js/bootstrap.min.js"></script>
    <style type="text/css">
        .op_bt{
            height: 22px;
            width: 70px;
            background-color: goldenrod;
        }
        table{
            width: 95%;
            border-radius: 20px;
            border: 2px solid #92B8B1;
            border-collapse:collapse;
            border-spacing:1px;
        }
        table td{
            padding:5px;
            border: 1px solid #92B8B1;
        }
        table th{
            padding:5px;
            border: 1px solid #92B8B1;
        }
        button{
            border-radius: 5px;
        }
        #index_div_search_phone{
            display: none;
        }
        #div_bookrack_phone{
            display: none;
        }
        #div_pagination{
            position:relative;top: 15px;width: 100%;height: 25px;
        }
        @media screen and (max-width:1086px){
            #index_div_search_phone{
                display: block;
            }
            #index_div_search_pc{
                display: none;
            }
            #div_bookrack_phone{
                display: block;
            }
            #div_bookrack{
                display: none;
            }
            #div_pagination{
                font-size: x-small;
                position:relative;top: 15px;width: 80%;height: 25px;
                background-color: gray;
            }
            #index_div_bottom_web{
                /*background-color: gray;*/
                display: none;

            }
        }
    </style>
    <script>
        function go_bookrack(){
            var userId = $("#userId").val();
            window.location.href = "/book/bookrack?user=" + userId;
        }
        $(function(){
            $("#bt_gocatalog").click(function (){
                var bookId = $("#bt_gocatalog").attr("value");
                window.location.href = "/book/catalog/" + bookId;
            });
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
                    <div class="col-md-4 float_right">
                        <div class="row" style="height: 20px; margin-top: 8px;margin-right: 20px">
                            <c:if test="${empty sessionScope.user.userName}">
                                <a href="/page/login" class="float_right">登录</a></font>
                            </c:if>
                            <c:if test="${not empty sessionScope.user.userName}">
                                <font class="float_right" style="margin-right: 4px;">用户：${sessionScope.user.userName}&nbsp;|&nbsp;<a href="/user/outlogin">退出</a></font>
                            </c:if>
                        </div>
                    </div>
                </div>
                <div id="search_div" class="row" style="height: 40px">
                    <div align="center" style="margin-right: 0; margin-top: 5px;">
                        <form class="form-search">
                            <div id="index_div_search_pc">
                                <input id="search_pc"  type="text" placeholder="请输入书名" style="height: 30px;width: 350px;border: 1px solid cadetblue"/>
                                <button type="button" class="btn-sm" style="background-color: cadetblue;">搜索</button>
                            </div>
                            <div id="index_div_search_phone">
                                <input id="search_phone"  type="text" placeholder="请输入书名" style="height: 30px;width: 240px;border: 1px solid cadetblue"/>
                                <button type="button" class="btn-sm" style="background-color: cadetblue;">搜索</button>
                            </div>
                            <input id="userId" name="userId" type="hidden" value="${user.id}">

                        </form>
                    </div>
                </div>
                <div class="row">
                    <div id="div_bookrack" class="col-md-2 float_right">
                        <button id="go_bookrack" onclick="go_bookrack()" class="float_right" style="position: relative; right: 0px">我的书架</button>
                    </div>
                    <div id="div_bookrack_phone" class="col-md-2 float_left">
                        <button id="go_bookrack_phone" onclick="go_bookrack()" class="float_left btn-xs" style="position: relative; right: 0px">我的书架</button>
                    </div>
                </div>
                <div class="row">
                    <div id="div_booklist" style="position:relative;top: 10px;width: 100%;<c:if test="${not empty size}">height:500px;</c:if>background-color:#92B8B1; border-radius: 15px">
                        <table align="center" border="1" style="border-radius: 20px;">
                            <tr>
                                <th width="250">书名</th>
                                <th width="150">作者</th>
                                <th width="400">最新章节</th>
                                <th width="80">完结</th>
                                <th style="text-align: center" width="160">操作</th>
                            </tr>
                            <c:forEach var="book" items="${bookList}">
                                <tr>
                                    <td >
                                        <a href="/book/catalog/${book.id}">${book.name}</a>
                                    </td>
                                    <td >${book.author}</td>
                                    <td >
                                        <a href="${book.lasterPath}" target="_blank" >${book.lasterSection}</a>
                                    </td>
                                    <td >
                                        <c:if test="${book.status == 0}">完本</c:if>
                                        <c:if test="${book.status == 1}">连载中</c:if>
                                    </td>
                                    <td align="center" style=" font-size: small">
                                        <c:if test="${fn:contains(bookIds, book.id) == false}">
                                            <button id="bt_kaideng" class="op_bt">加入书架</button>&nbsp;
                                        </c:if>
                                        <button id="bt_gocatalog" value="${book.id}" class="b_bt">目录</button>
                                    </td>
                                </tr>

                            </c:forEach>

                        </table>
                    </div>
                </div>

                <div id="index_div_bottom" class="navbar-fixed-bottom" style="margin-bottom: 10px" align="center">
                    <div id="div_pagination" align="center" style="border-radius: 5px;margin-bottom: 5px">
                        <c:forEach  items="${pagination.pageView}" var="page">
                            ${page}
                        </c:forEach>
                        <%--<button>首页</button>&emsp;<a>1</a>&emsp;<a>2</a>&emsp;<a>3</a>&emsp;<button>末页</button>--%>
                    </div>
                    <div id="index_div_bottom_web">
                        <div align="center" style="position:relative;top: 5px;width: 75%;height: 5px;">
                            <hr align="center" style="width: 80% ;height:1px;border:none;border-top:1px dashed #0066CC;" />

                        </div>
                        <div align="center" style="position:relative;top: 8px;height: 20px;">
                            <a>www.fmzs365.cn</a>&nbsp;|&nbsp;<nb></nb><a>飞马追书</a>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-md-2"></div>
        </div>
    </div>
</body>
</html>
