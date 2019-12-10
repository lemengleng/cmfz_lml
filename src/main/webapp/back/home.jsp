<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<% request.setAttribute("path",request.getContextPath());%>
<!doctype html>
<html>
<head>
    <title>使用jqgrid表格</title>
    <!--引入css bootstrap -->
    <link rel="stylesheet" href="../boot/css/bootstrap.min.css">
    <!--引入jqgrid的css-->
    <link rel="stylesheet" href="../jqgrid/css/trirand/ui.jqgrid-bootstrap.css">
    <!--引入 jquery -->
    <script src="../boot/js/jquery-2.2.1.min.js"></script>
    <!--引入 jqgrid-->
    <script src="../jqgrid/js/trirand/jquery.jqGrid.min.js"></script>
    <!--引入 jqgrid 国际化-->
    <script src="../jqgrid/js/trirand/i18n/grid.locale-cn.js"></script>
    <!--引入 boot的js-->
    <script src="../boot/js/bootstrap.min.js"></script>
    <script src="../boot/js/ajaxfileupload.js"></script>
    <script src="../kindeditor/kindeditor-all-min.js"></script>
    <script src="../kindeditor/lang/zh-CN.js"></script>
    <script charset="UTF-8" src="../echarts/echarts.min.js"></script>
    <script type="text/javascript" src="../echarts/china.js" charset="UTF-8"></script>
    <script type="text/javascript" src="http://cdn.goeasy.io/goeasy-1.0.3.js"></script>
    <script>
        KindEditor.ready(function (K) {
            window.editor=K.create('#editor_id',{
               width:'600px',
                /*上传路径*/
                uploadJson:"${path}/article/uploadImg",
                allowFileManager:true,
                fileManagerJson:"${path}/article/showAllImgs",
                afterBlur:function () {
                    this.sync();
                }
            });
        });

    </script>
</head>
<body>
<nav class="navbar navbar-default">
    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
        <ul class="nav navbar-nav">
            <li>
                <a href="#">持明法舟后台管理系统</a>
            </li>
        </ul>
        <ul class="nav navbar-nav navbar-right">
            <li>
                <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true"
                   aria-expanded="false">欢迎<shiro:principal></shiro:principal></a>
            </li>
            <li>
                <a href="${path}/admin/logout" class="dropdown-toggle" data-toggle="dropdown" role="button"
                   aria-haspopup="true" aria-expanded="false">退出登录</a>
            </li>
        </ul>
    </div>
    <!-- /.navbar-collapse -->
    </div>
    <!-- /.container-fluid -->
</nav>
<div class="container-fluid">
    <div class="row">
        <div class="col-md-2">
            <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
                <div class="panel panel-default">
                    <div class="panel-heading" role="tab" id="headingOne">
                        <h4 class="panel-title">
                            <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
                                用户管理
                            </a>
                        </h4>
                    </div>
                    <div id="collapseOne" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">
                        <div class="panel-body">
                            <div class="list-group">
                                <a href="javascript:$('#neirong').load('${path}/back/user.jsp')" class="list-group-item">
                                    用户列表
                                </a>
                                <a href="javascript:$('#neirong').load('${path}/back/userRegist.jsp')" class="list-group-item">
                                    日期统计
                                </a>
                                <a href="javascript:$('#neirong').load('${path}/back/userLocation.jsp')" class="list-group-item">
                                    地图统计
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="panel panel-default">
                    <div class="panel-heading" role="tab" id="headingTwo">
                        <h4 class="panel-title">
                            <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
                                上师管理
                            </a>
                        </h4>
                    </div>
                    <div id="collapseTwo" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingTwo">
                        <div class="panel-body">
                            <div class="list-group">
                                <a href="javascript:$('#neirong').load('${path}/back/guru.jsp')" class="list-group-item">
                                    上师列表
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="panel panel-default">
                    <div class="panel-heading" role="tab" id="headingThree">
                        <h4 class="panel-title">
                            <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseThree" aria-expanded="false" aria-controls="collapseThree">
                                文章管理
                            </a>
                        </h4>
                    </div>
                    <div id="collapseThree" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingThree">
                        <div class="panel-body">
                            <div class="list-group">
                                <a href="javascript:$('#neirong').load('${path}/back/article.jsp')" class="list-group-item">
                                    文章信息
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="panel panel-default">
                    <div class="panel-heading" role="tab" id="headingFour">
                        <h4 class="panel-title">
                            <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseFour" aria-expanded="false" aria-controls="collapseFour">
                                专辑管理
                            </a>
                        </h4>
                    </div>
                    <div id="collapseFour" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingFour">
                        <div class="panel-body">
                            <div class="list-group">
                                <a href="javascript:$('#neirong').load('${path}/back/album.jsp')" class="list-group-item">
                                    专辑信息
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="panel panel-default">
                    <div class="panel-heading" role="tab" id="headingFive">
                        <h4 class="panel-title">
                            <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseFive" aria-expanded="false" aria-controls="collapseFive">
                                轮播图管理
                            </a>
                        </h4>
                    </div>
                    <div id="collapseFive" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingFive">
                        <div class="panel-body">
                            <div class="list-group">
                                <a href="javascript:$('#neirong').load('${path}/back/picture.jsp')" class="list-group-item">
                                    轮播图展示
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-md-10">
            <div class="container-fluid" id="neirong">
                <div class="jumbotron">
                    <h3>欢迎使用持明法舟后台管理系统!</h3>
                </div>
                <div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
                    <!-- Indicators -->
                    <ol class="carousel-indicators">
                        <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
                        <li data-target="#carousel-example-generic" data-slide-to="1"></li>
                        <li data-target="#carousel-example-generic" data-slide-to="2"></li>
                    </ol>

                    <!-- Wrapper for slides -->
                    <div class="carousel-inner" role="listbox">
                        <div class="item active">
                            <img src="${path}/img/3e4d03381f30e924eebbff0d40086e061d95f7b0.jpg" alt="...">
                            <div class="carousel-caption">
                                ...
                            </div>
                        </div>
                        <div class="item">
                            <img src="${path}/img/009e9dfd5266d016d30938279a2bd40735fa3576.jpg" alt="...">
                            <div class="carousel-caption">
                                ...
                            </div>
                        </div>
                        <div class="item">
                            <img src="${path}/img/微信图片_20180716115911.jpg" alt="...">
                            <div class="carousel-caption">
                                ...
                            </div>
                        </div>
                    </div>

                    <!-- Controls -->
                    <a class="left carousel-control" href="#carousel-example-generic" role="button" data-slide="prev">
                        <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
                        <span class="sr-only">Previous</span>
                    </a>
                    <a class="right carousel-control" href="#carousel-example-generic" role="button" data-slide="next">
                        <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                        <span class="sr-only">Next</span>
                    </a>
                </div>
            </div>

        </div>

    </div>

</div>

<%--模态框--%>
<div class="modal fade" id="kind" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">文章信息</h4>
            </div>
            <div class="modal-body">
                <form role="form" enctype="multipart/form-data" id="kindfrm">
                    <input name="id" type="hidden" id="formid">
                    <div class="form-group">
                        <label for="name">标题</label>
                        <input type="text" class="form-control" name="title" id="name" placeholder="请输入名称">
                    </div>
                    <div class="form-group">
                        <label for="inputfile">封面上传</label>
                        <input type="file" name="articleImg" id="inputfile">
                    </div>
                    <div class="form-group">
                        <label for="name">所属上师</label>
                        <select class="form-control" id="guruList" name="guru_id" >

                        </select>
                    </div>
                    <div class="form-group">
                        <label for="editor_id">内容</label>
                        <textarea id="editor_id" name="content" style="width:700px;height:300px;">
&lt;strong&gt;HTML内容&lt;/strong&gt;
</textarea>
                    </div>
                </form>
            </div>
            <div class="modal-footer" id="modal_foot">
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
<footer>
    <div style="text-align: center">
        @百知教育 baizhi@zparkhr.com.cn
    </div>
</footer>
</body>

</html>