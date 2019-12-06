<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<% request.setAttribute("path",request.getContextPath());%>

<script>
    $(function(){
        //表格初始化
        $("#poemlist").jqGrid({
            //width:800,
            styleUI:"Bootstrap",//设置为bootstrap风格的表格
            url:"${path}/article/queryByPage",//获取服务端数据url 注意获取结果要json
            datatype:"json",//预期服务器返回结果类型
            mtype:"post",//请求方式
            colNames:["编号","内容","标题","图片","上传日期","发行日期","状态","上师Id","操作"],//列名称数组
            colModel:[
                {name:"id",align:'center'},//colModel中全部参数都写在列配置对象
                {name:"content",editable:true,hidden:true},
                {name:"title",editable:true,},
                {name:"img",editable:true,
                    formatter:function (value,options,row) {
                        return "<img width='150px' src='"+row.img+"'>";
                    },edittype: "file",editoptions: {enctype:"multipart/form-data"}},
                {name:"create_date",editable:false},
                {name:"publish_date",editable:false},
                {name:"status",editable:true,formatter:function (row) {
                        if (row==1){
                            return "展示";
                        }else{
                            return "冻结";
                        }
                    },edittype:"select",editoptions:{value:"1:展示;2:冻结"}},
                {name:"guru_id",editable:true},
                {name:'option',formatter:function (cellvalue, options, rowObject) {
                        var result = '';
                        result += "<a href='javascript:void(0)' onclick=\"showModel('" + rowObject.id + "')\" class='btn btn-lg' title='查看详情'> <span class='glyphicon glyphicon-th-list'></span></a>";
                        return result;
                    }},
            ],//列数组值配置列对象
            pager:"#pager",//设置分页工具栏html
            // 注意: 1.一旦设置分页工具栏之后在根据指定url查询时自动向后台传递page(当前页) 和 rows(每页显示记录数)两个参数
            rowNum:8,//这个代表每页显示记录数
            rowList:[2,3,4,8],//生成可以指定显示每页展示多少条下拉列表
            viewrecords:true,//显示总记录数
            caption:"文章列表",//表格标题
            cellEdit:true,//开启单元格编辑功能
            autowidth:true,//自适应外部容器
            height:300,//指定表格高度
            multiselect:true,
            editurl:"${path}/article/cud"
        });
        $("#poemlist").jqGrid('navGrid','#pager',{
            add:false,
            edit:false,
            del:true,
            deltext:"删除"
        });
    });
    //打开模态框
    function addArticle() {
        //清除表单数据
        $("#kindfrm")[0].reset();
        KindEditor.html("#editor_id","");
        // 未提供查询上师信息 发送ajax请求查询
        $.post("${path}/guru/queryAll",function (list) {
            $('#guruList').empty();
            $('#guruList').append($('<option value="1">通用文章</option>'));
            $.each(list,function (index,g) {
                $('#guruList').append($("<option value='"+g.id+"'>"+g.nick_name+"</option>"));
            })
        },"json");
        $("#modal_foot").html("<button type=\"button\" class=\"btn btn-danger\" data-dismiss=\"modal\">关闭</button>"+
            "<button type=\"button\" class=\"btn btn-primary\" onclick=\"insertArticle()\">添加</button>")
        $("#kind").modal("show");
    }
    //编辑文章
    function showModel(id) {
        var data=$("#poemlist").jqGrid("getRowData",id);
        $("#name").val(data.title);
        $("#formid").val(data.id);
        $.post("${path}/guru/queryAll",function (list) {
            $('#guruList').empty();
            $('#guruList').append($('<option value="1">通用文章</option>'));
            $.each(list,function (index,g) {
                if (data.guru_id==g.id){
                    $('#guruList').append($("<option value='"+g.id+"' selected>"+g.nick_name+"</option>"));
                }else {
                    $('#guruList').append($("<option value='"+g.id+"'>"+g.nick_name+"</option>"));
                }

            })
        },"json");
        KindEditor.html("#editor_id",data.content);
        editor.sync();/*防止回显bug*/
        $("#modal_foot").html("<button type=\"button\" class=\"btn btn-danger\" data-dismiss=\"modal\">关闭</button>"+
            "<button type=\"button\" class=\"btn btn-primary\" onclick=\"updateArticle()\">修改</button>")
        $("#kind").modal("show");
    }
    //添加文章
    function insertArticle() {
        $.ajaxFileUpload({
            url:"${path}/article/insertArticle",
            datatype:"json",
            type:"post",
            fileElementId:"inputfile",
            // ajaxFileUpload 不支持序列化数据上传id=111&&title="XXX"
            //                只支持 Json格式上传数据
            // 解决方案 : 1.更改 ajaxFileUpload 源码 2. 手动封装Json格式
            data:{id:$("#formid").val(),title:$("#name").val(),guru_id:$("#guruList").val(),content:$("#editor_id").val()},
            success:function (data) {
                $("#kind").modal("hide");
                $("#poemlist").trigger("reloadGrid");
            }
        })
    }
    function updateArticle() {
        $.ajaxFileUpload({
            url:"${pageContext.request.contextPath}/article/updateArticle",
            datatype:"json",
            type:"post",
            fileElementId:"inputfile",
            // ajaxFileUpload 不支持序列化数据上传id=111&&title="XXX"
            //                只支持 Json格式上传数据
            // 解决方案 : 1.更改 ajaxFileUpload 源码 2. 手动封装Json格式
            data:{id:$("#formid").val(),title:$("#name").val(),guru_id:$("#guruList").val(),content:$("#editor_id").val()},
            success:function (data) {
                $("#kind").modal("hide");
                $("#poemlist").trigger("reloadGrid");
            }
        })
    }
</script>
<div>
    <ul class="nav nav-tabs">
        <li role="presentation" class=""><a href="#">文章列表</a></li>
        <li role="presentation"><a href="#" onclick="addArticle()">添加文章</a></li>
    </ul>
</div>
<div>
    <!--创建表格-->
    <table id="poemlist"></table>

    <!--分页工具栏-->
    <div id="pager"></div>
</div>

