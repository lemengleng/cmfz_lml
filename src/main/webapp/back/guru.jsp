<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<% request.setAttribute("path",request.getContextPath());%>
<script>
    $(function(){
        //表格初始化
        $("#poemlist").jqGrid({
            //width:800,
            styleUI:"Bootstrap",//设置为bootstrap风格的表格
            url:"${path}/guru/queryByPage",//获取服务端数据url 注意获取结果要json
            datatype:"json",//预期服务器返回结果类型
            mtype:"post",//请求方式
            colNames:["编号","名字","头像","昵称","状态"],//列名称数组
            colModel:[
                {name:"id",align:'center'},//colModel中全部参数都写在列配置对象
                {name:"name",editable:true,},
                {name:"photo",editable:true,
                formatter:function (value,options,row) {
                    return "<img width='150px' src='"+row.photo+"'>";
                },edittype: "file",editoptions: {enctype:"multipart/form-data"}},
                {name:"nick_name",editable:true,},
                {name:"status",editable:true,formatter:function (row) {
                    if (row==1){
                        return "展示";
                    }else{
                        return "冻结";
                    }
                 },edittype:"select",editoptions:{value:"1:展示;2:冻结"}},
            ],//列数组值配置列对象
            pager:"#pager",//设置分页工具栏html
            // 注意: 1.一旦设置分页工具栏之后在根据指定url查询时自动向后台传递page(当前页) 和 rows(每页显示记录数)两个参数
            rowNum:8,//这个代表每页显示记录数
            rowList:[2,3,4,8],//生成可以指定显示每页展示多少条下拉列表
            viewrecords:true,//显示总记录数
            caption:"图片列表",//表格标题
            cellEdit:true,//开启单元格编辑功能
            editurl:"${path}/guru/cud",//开启编辑时执行编辑操作的url路径  添加  修改  删除
            autowidth:true,//自适应外部容器
            height:300,//指定表格高度
            multiselect:true
        });/*.navGrid("#pager",
            {},
            {closeAfterEdit:true,editCaption:"编辑诗词信息",reloadAfterSubmit:true},
            {closeAfterAdd:true,addCaption:"诗词添加",reloadAfterSubmit:true},
            {},
            {
                sopt:['cn','eq','ne']
            }
        );//开启增删改工具按钮  注意:1.这里存在一个bug surl为实现*/
        $("#poemlist").jqGrid('navGrid','#pager',
            {edit : true,add : true,del : true,
                edittext:"编辑",addtext:"添加",deltext:"删除"
            },
            {
                closeAfterEdit:true,reloadAfterSubmit:true,
                beforeShowForm:function (frm) {
                    
                },
                afterSubmit : function (response,postData) {
                    var guruID=response.responseJSON.guruId;
                    $.ajaxFileUpload({

                        url:"${path}/guru/upload",
                        datatype: "json",
                        type:"post",
                        data:{guruId:guruID},
                        fileElementId:"photo",
                        success:function (data) {
                            $("#poemlist").trigger("reloadGrid");
                        }
                    })
                    return postData;
                }
            },{
                closeAfterAdd:true,reloadAfterSubmit:true,
                afterSubmit : function (response,postData) {
                    var guruID=response.responseJSON.guruId;
                    $.ajaxFileUpload({
                        url:"${path}/guru/upload",
                        datatype: "json",
                        type:"post",
                        data:{guruId:guruID},
                        fileElementId:"photo",
                        success:function (data) {
                            $("#poemlist").trigger("reloadGrid");
                        }
                    })
                    return postData;
                }
            })
    });
</script>
<div>
    <!--创建表格-->
    <table id="poemlist"></table>

    <!--分页工具栏-->
    <div id="pager"></div>
</div>