<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
    <title>目录-${bookName}</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <link href="/css/bootstrap.min.css" rel="stylesheet"/>
    <link href="/css/section.css" rel="stylesheet"/>
    <link href="/css/bootstrap-theme.min.css" rel="stylesheet"/>
    <script type="text/javascript" src="/js/jquery-1.8.3.js"></script>
    <script type="text/javascript" src="/js/bootstrap.min.js"></script>
    <style type="text/css">
        table {
            width: 95%;
            border-radius: 20px;
            /*border: 2px solid #92B8B1;*/
            border-collapse: collapse;
            border-spacing: 1px;
        }

        table td {
            padding: 5px;
            /*border: 1px solid #92B8B1;*/
        }

        table th {
            padding: 5px;
            /*border: 1px solid #92B8B1;*/
        }

        button {
            border-radius: 5px;
        }

        @media screen and (max-width:1086px){
            #catalog_div_search{
                display: none;
            }
        }
    </style>
    <script>

        $(function () {
            $("#go_index").click(function () {
                window.location.href = "/book/bookstorage"
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
                    <font size="5" class="float_left" style="font-family:LiSu,serif"><img id="img_logo"
                                                                                          src="/img/kaideng.png"
                                                                                          height="40"
                                                                                          width="40"/>飞马追书</font>
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
                    <div id="catalog_div_search" class="row" style="height: 50px;">
                        <div class="float_right" style="margin-left: 110px; margin-top: 10px;">
                            <form class="form-search" style="position: relative; margin-left: 100px">
                                <input id="search" type="text" placeholder="请输入书名"
                                       style="height: 23px; width: 180px; border: 1px solid cadetblue;"/>
                                <button type="button" class="btn-xs" style="background-color: yellowgreen;">搜索</button>
                            </form>
                        </div>
                    </div>

                </div>

            </div>

            <div class="row">
                <div class="col-md-6">
                    <div class="float_left" style="margin-top: 5px;">
                        <font size="3" >
                            <a id="home_page" href="/book/bookstorage">首页</a>&ensp;|&nbsp;
                            <a id="bookrack" href="/book/bookrack">我的书架</a>
                        </font>
                    </div>
                </div>
            </div>


            <div class="row">
                <div id="div_booklist" style="position:relative;top: 10px;width: 100%; background-color: gainsboro; border-radius: 20px">

                    <table align="center" style="border-radius: 20px;">
                        <tr align="center">
                            <td style="align-content: center;font-size:xx-large; font-family: 隶书,serif" colspan="6">
                                ${bookName}
                            </td>
                        </tr>
                        <tr>
                            <c:forEach var="section" items="${sectionList}" varStatus="status">
                            <td width="600">
                                <a style="font-size: small" href="/page/${section.bookId}/${section.sequenceSite}" target="_blank">${section.sectionName}</a>
                            </td>
                                <c:if test="${(status.index + 1) % 3 == 0}">
                        </tr>
                        <tr>
                                </c:if>
                            </c:forEach>
                    </table>

                </div>
            </div>
            <div class="navbar-fixed-bottom" style="margin-bottom: 10px">
                <div align="center" style="position:relative;bottom: 5px;height: 20px;">
                    <a>www.fmzs365.cn</a>&nbsp;|&nbsp;
                    <nb></nb>
                    <a>飞马追书</a>
                </div>
            </div>
        </div>
        <div class="col-md-2"></div>
    </div>
</div>
</body>
</html>
