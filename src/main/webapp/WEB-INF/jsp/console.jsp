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
    <title>后台管理</title>
    <!-- 导入easyui类库 -->
    <link rel="stylesheet" type="text/css" href="../../easyui/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="../../easyui/themes/icon.css">
    <script type="text/javascript" src="../../easyui/jquery.min.js"></script>
    <script type="text/javascript" src="../../easyui/jquery.easyui.min.js"></script>

    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <link rel="icon" href="/favicon.ico" type="image/x-icon" />
    <link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
    <link rel="bookmark" href="/favicon.ico" type="image/x-icon" />
    <link href="${pageContext.request.contextPath }/css/bootstrap.min.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath }/css/section.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath }/css/bootstrap-theme.min.css" rel="stylesheet"/>

    <script type="text/javascript" src="${pageContext.request.contextPath }/js/bootstrap.min.js"></script>
    <style type="text/css">
        #table_search{
            width: 100%;
            border-radius: 10px;
            border-collapse:collapse;
            border-spacing:1px;
        }
        table td{
            padding:5px;
        }
        table th{
            padding:5px;
        }
        button{
            border-radius: 5px;
        }

        #index_div_search_phone{
            display: none;
        }
        #w{
            z-index:1000;
            width:500px;
            height:200px;
            padding:1px;
        }
        @media screen and (max-width:1086px){
            #index_div_search_phone{
                display: block;
            }
            #index_div_search_pc{
                display: none;
            }
            #w{
                z-index:1000;
                width:300px;
                height:100px;
                padding:1px;
            }
        }
    </style>
    <script>
        //关闭窗口后隐藏蒙皮div
        function search_window_close(){
            $("#div_covering").addClass("hidden")
        }
        //查询函数
        function goSearch(){
            var bookName = $("#search_pc").val();
            var bookNamePhone = $("#search_phone").val();
            if (bookName == null || bookName == undefined || bookName == ''){
                if(bookNamePhone == null || bookNamePhone == undefined || bookNamePhone == ''){
                    $.messager.alert('警告','请输入书名后重新搜索！');
                    return
                }
                bookName = bookNamePhone;
            }
            bookName = encodeURI(bookName);
            $.get("/book/search",{"bookName":bookName},function(data){
                //显示搜索窗口

                if (data.length == 0){
                    $.messager.alert('搜索结果','暂无此书籍','info');
                    return;
                }
                var htmlStr = "<tr><th>书名</th><th>作者</th><th>最新章节</th></tr>";
                for (var j = 0; j < data.length; j++){
                    htmlStr += "<td><a href='/book/catalog/"+ data[j].id +"'>" + data[j].name + "</a></td><td>"+ data[j].author +"</td><td><a href='"+ data[j].lasterPath + "'>" + data[j].lasterSection + "</a></td>"
                }
                $("#table_search").html(htmlStr);


                $("#w").panel("open");
                $("#div_covering").removeClass("hidden");
            });
        }
        function go_bookrack(){
            var userId = $("#userId").val();
            window.location.href = "/book/bookrack?user=" + userId;
        }


        function addDelete(){
            //获取选中的行
            var rowsArr = $('#bookAdd').datagrid("getSelections");
            if(rowsArr.length<1){
                $.messager.alert('删除警告','必须选中一行!','wraning');
                return;
            };
            var idArr = new Array();
            for(var i = 0 ;i < rowsArr.length;i++){
                idArr.push(rowsArr[i].id);
            }
            var idStr = idArr.join(",");

            $.post("/console/book/sourceDel",{"idStr":idStr},function (data){
                if(data.code == 0){
                    $.messager.show({
                        title:'结果提示',
                        msg:'删除推荐成功！',
                        timeout:500,
                        style:{
                            right:'400px',
                            top:'200px'
                        },
                        /*showType:'slide'*/
                    });
                    $('#bookAdd').datagrid("reload");
                    $('#bookAdd').datagrid("uncheckAll");
                }else{
                    $.messager.alert('删除警告','删除推荐失败!','wraning');
                }

            });
        }


        function doAddSource(){
            //获取选中的行
            var rowsArr = $('#bookAdd').datagrid("getSelections");
            if(rowsArr.length<1){
                $.messager.alert('添加提示','必须选中一行!','wraning');
                return;
            };
            var idArr = new Array();
            for(var i = 0 ;i < rowsArr.length;i++){
                idArr.push(rowsArr[i].id);
            }
            var idStr = idArr.join(",");
            alert(idStr);
        }

        var bookListColumns = [ [ {
            field : 'id',
            checkbox : true
        },{
            field : 'name',
            title : '书籍名称',
            width : 130,
            align : 'center',
            formatter : function(data,row, index){
                return "<a href='"+ row.rootPath +"' target='_blank'>" + data + "</a>"
            }
        }, {
            field : 'author',
            title : '作者',
            width : 100,
            align : 'center'
        }, {
            field : 'status',
            title : '状态',
            width : 50,
            align : 'center',
            //进项转换
            formatter : function(data,row, index){
                if(data=="1"){
                    return "连载中";
                }else{
                    return "已完结";
                }
            }
        }, {
            field : 'source',
            title : '来源',
            width : 50,
            align : 'center'
        }, {
            field : 'rootPath',
            title : '根路径',
            width : 180,
            align : 'center',
            formatter : function(data,row, index){
                return "<a href='"+ data +"' target='_blank'>" + data + "</a>"
            }
        }, {
            field : 'lasterSection',
            title : '最新章节名称',
            width : 215,
            align : 'center'
        }, {
            field : 'createTime',
            title : '创建时间',
            width : 100,
            align : 'center',
            formatter : function(data,row, index){
                return new Date(data).toLocaleDateString();
            }
        }] ];

        var bookAddColumns = [ [ {
            field : 'id',
            checkbox : true,
        },{
            field : 'name',
            title : '书籍名称',
            width : 220,
            align : 'center',
            formatter : function(data,row, index){
                return "<a href='"+ row.rootSource +"' target='_blank'>" + data + "</a>"
            }
        }, {
            field : 'author',
            title : '作者',
            width : 150,
            align : 'center'
        }, {
            field : 'rootSource',
            title : '来源',
            width : 250,
            align : 'center',
            formatter : function(data,row, index){
                return "<a href='"+ data +"' target='_blank'>" + data + "</a>"
            }
        },{
            field : 'userName',
            title : '提交人',
            width : 100,
            align : 'center'
        },{
            field : 'createTime',
            title : '创建时间',
            width : 105,
            align : 'center',
            formatter : function(data,row, index){
                return new Date(data).toLocaleDateString();
            }
        }] ];
        //工具栏
        var toolbar = [ {
                id : 'button-view',
                text : '查询',
                iconCls : 'icon-search'
                /*handler : doView*/
            },
            {
                id : 'button-add',
                text : '增加',
                iconCls : 'icon-add'
            },{
                id : 'button-eidt',
                text : '修改',
                iconCls : 'icon-edit'
            }
        ];
        var bookAddToolbar = [
            {
                id : 'button-add',
                text : '入库推荐',
                iconCls : 'icon-add',
                handler : doAddSource
            },{
                id : 'button-delete',
                text : '删除推荐',
                iconCls : 'icon-cancel',
                handler: addDelete
            }
        ];

        $(function(){
            $("#w").panel("close");

            $("#bt_gocatalog").click(function (){
                var bookId = $("#bt_gocatalog").attr("value");
                window.location.href = "/book/catalog/" + bookId;
            });

            $('#grid').datagrid( {
                fit : true,
                border : false,
                rownumbers : true,
                striped : true,
                pageList: [15,30,50],
                pageSize:15,
                pagination : true,
                toolbar : toolbar,
                url : "/console/book/list",
                idField : 'id',
                columns : bookListColumns,
                onDblClickRow : function (index,data){
                    /*$("#addStaffWindow").form("load",data);
                    $('#addStaffWindow').window("open");*/
                }
            });

            $('#bookAdd').datagrid( {
                fit : true,
                border : false,
                rownumbers : true,
                pageList: [15,30,50],
                pageSize:15,
                pagination : true,
                toolbar : bookAddToolbar,
                url : "/console/book/sourceOffer",
                idField : 'id',
                columns : bookAddColumns,
                onDblClickRow : function (index,data){
                    /*$("#addStaffWindow").form("load",data);
                     $('#addStaffWindow').window("open");*/
                }
            });


        });

    </script>
</head>
<body>
    <div id="div_covering" class="hidden" style="position: absolute; width: 100%;height: 700px;background:#000;z-index:998;opacity:0.6;"></div>
    <div id="w" class="easyui-window" title="搜索结果" data-options="onClose:search_window_close,iconCls:'icon-search',collapsible:false,minimizable:false,maximizable:false">
        <table id="table_search" align="center">
        </table>
    </div>

    <div id="div_body" class="container-fluid" style="display: block; z-index:100;">
        <div class="row" style="height: auto;">
            <div class="col-md-2" style="background: border-box;">
            </div>
            <div class="col-md-8" style=" position: relative;">
                <div class="row">
                    <div class="col-md-4 float_left" style="height: 40px;">
                        <font size="5" class="float_left" style="font-family:LiSu,serif"><img id="img_logo" src="/img/kaideng.png" height="40" width="40"/>飞马追书</font>
                    </div>
                    <div class="col-md-4 float_right">
                        <div class="row" style="height: 20px; margin-top: 8px;margin-right: 20px">
                            <font class="float_right" style="margin-right: 4px;">管理员：${sessionScope.user.userName}&nbsp;|&nbsp;<a href="/user/outlogin">退出</a></font>
                        </div>
                    </div>
                </div>
                <div id="search_div" class="row" style="height: 40px">
                    <div align="center" style="margin-right: 0; margin-top: 5px;">
                        <form class="form-search" action="/book/search">
                            <div id="index_div_search_pc">
                                <input id="search_pc" name="bookName" type="text" placeholder="请输入书名" style="height: 30px;width: 350px;border: 1px solid #99CCFF"/>
                                <button id="btn_submit_pc" onclick="goSearch()" type="button" class="btn-sm" style="background-color: #99CCFF;">搜索</button>
                            </div>
                            <div id="index_div_search_phone">
                                <input id="search_phone" name="bookName" type="text" placeholder="请输入书名" style="height: 30px;width: 240px;border: 1px solid #99CCFF"/>
                                <button id="btn_submit_phone" onclick="goSearch()" type="button" class="btn-sm" style="background-color: #99CCFF;">搜索</button>
                            </div>
                            <input id="userId" name="userId" type="hidden" value="${user.id}">

                        </form>
                    </div>
                </div>

                <div class="row">

                    <div id="tt" class="easyui-tabs" style="width:100%;height:590px; margin-top: 15px">

                        <div title="推荐管理" style="padding:20px;display:none;">
                            <table id="bookAdd"></table>
                        </div>

                        <div title="书籍管理" style="padding:20px;display:none;">
                            <table id="grid"></table>
                        </div>
                        <div title="Tab3" style="padding:20px;display:none;">tab3 </div>
                    </div>
                </div>
            </div>
            <div class="col-md-2"></div>
        </div>
    </div>
    <div style="position: relative;margin-top: 1px;align-self: center">
        <div id="index_div_bottom" class="navbar-fixed-bottom" style="position: relative;z-index:50;margin-bottom: 0px;align-content: center;align-self: center" align="center">
            <div align="center" style="position:relative;top: 10px;height: 10px;">
                <a>www.fmzs365.cn</a>&nbsp;|&nbsp;<nb></nb><a>飞马追书</a>
            </div>
        </div>

    </div>
</body>
</html>
