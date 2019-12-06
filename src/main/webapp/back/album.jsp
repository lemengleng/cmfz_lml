<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<% request.setAttribute("path",request.getContextPath());%>
<script>
    $(function(){
        //表格初始化
        $("#poemlist").jqGrid({
            //width:800,
            styleUI:"Bootstrap",//设置为bootstrap风格的表格
            url:"${path}/album/queryByPage",//获取服务端数据url 注意获取结果要json
            datatype:"json",//预期服务器返回结果类型
            mtype:"post",//请求方式
            colNames:["编号","标题","星级","作者","播音","集数","简介","状态","创建日期","封面"],//列名称数组
            colModel:[
                {name:"id",align:'center'},//colModel中全部参数都写在列配置对象
                {name:"title",editable:true,},
                {name:"score",editable:true,},
                {name:"author",editable:true,},
                {name:"broadcast",editable:true,},
                {name:"count",editable:false,},
                {name:"desc",editable:true,},
                {name:"status",editable:true,},
                {name:"create_date",editable:false,},
                {name:"cover",editable:true,
                formatter:function (value,options,row) {
                    return "<img width='150px' src='"+row.cover+"'>";
                },edittype: "file",editoptions: {enctype:"multipart/form-data"}},
            ],//列数组值配置列对象
            pager:"#pager",//设置分页工具栏html
            // 注意: 1.一旦设置分页工具栏之后在根据指定url查询时自动向后台传递page(当前页) 和 rows(每页显示记录数)两个参数
            rowNum:8,//这个代表每页显示记录数
            rowList:[2,3,4,8],//生成可以指定显示每页展示多少条下拉列表
            viewrecords:true,//显示总记录数
            caption:"图片列表",//表格标题
            cellEdit:true,//开启单元格编辑功能
            editurl:"${path}/album/cud",//开启编辑时执行编辑操作的url路径  添加  修改  删除
            autowidth:true,//自适应外部容器
            height:300,//指定表格高度
            multiselect:true,
            subGrid:true,
            caption: "章节列表",
            autowidth: true,
            styleUI: "Bootstrap",
            subGridRowExpanded: function (subgrid_id,row_id) {
                addTable(subgrid_id,row_id);
                
            },
            subGridRowColapsed:function (subgrid_id,row_id) {
                
            }
        });
        $("#poemlist").jqGrid('navGrid','#pager',
            {edit : true,add : true,del : true,
                edittext:"编辑",addtext:"添加",deltext:"删除"
            },
            {
                closeAfterEdit:true,reloadAfterSubmit:true,
                beforeShowForm:function (frm) {
                    
                },
                afterSubmit : function (response,postData) {
                    var albumID=response.responseJSON.albumId;
                    $.ajaxFileUpload({

                        url:"${path}/album/upload",
                        datatype: "json",
                        type:"post",
                        data:{albumId:albumID},
                        fileElementId:"cover",
                        success:function (data) {
                            $("#poemlist").trigger("reloadGrid");
                        }
                    })
                    return postData;
                }
            },{
                closeAfterAdd:true,reloadAfterSubmit:true,
                afterSubmit : function (response,postData) {
                    var albumID=response.responseJSON.albumId;
                    $.ajaxFileUpload({
                        url:"${path}/album/upload",
                        datatype: "json",
                        type:"post",
                        data:{albumId:albumID},
                        fileElementId:"cover",
                        success:function (data) {
                            $("#poemlist").trigger("reloadGrid");
                        }
                    })
                    return postData;
                }
            })
    });

    function addTable(subgrid_id,row_id) {
        var subgridTable=subgrid_id+"table";
        var subgridPage=subgrid_id+"page";
        $("#"+subgrid_id).html("<table id='"+subgridTable+"'><div style='height: 50px' id='"+subgridPage+"'></div></table>")
        $("#"+subgridTable).jqGrid({
            url:"${path}/chapter/queryByPage?album_id="+row_id,
            datatype:"json",
            colNames:['编号','名称','音频','大小','时长','创建日期','所属专辑id'],
            colModel: [
                {name: "id",editable:false},
                {name: "title",editable:true},
                {name: "url",align: "center",editable:true,formatter:function (value,options,row) {
                    var result="";
                    result += "<a href='javascript:void(0)' onclick=\"playAudio('" + row.url + "')\" class='btn btn-lg' title='播放'><span class='glyphicon glyphicon-play-circle'></span></a>";
                    result += "<a href='javascript:void(0)' onclick=\"downloadAudio('" + row.url + "')\" class='btn btn-lg' title='下载'><span class='glyphicon glyphicon-download'></span></a>";
                    return result;
                 },edittype: "file",editoptions: {enctype:"multipart/form-data"}},
                {name: "size",editable:false},
                {name: "time",editable:false},
                {name: "create_time",editable:false},
                {name: "album_id",editable:false},
            ],
            rowNum: 20,
            pager: "#"+subgridPage,
            height: "100%",
            styleUI:"Bootstrap",
            autowidth: true,
            viewrecords:true,
            editurl:"${path}/chapter/cud?album_id="+row_id,
            multiselect:true,

        });
        $("#"+subgridTable).jqGrid('navGrid','#'+subgridPage,
            {edit : true,add : true,del : true,
                edittext:"编辑",addtext:"添加",deltext:"删除"
            },
            {
                closeAfterEdit:true,reloadAfterSubmit:true,
                beforeShowForm:function (frm) {

                },
                afterSubmit : function (response,postData) {
                    var chapterID=response.responseJSON.chapterId;
                    $.ajaxFileUpload({

                        url:"${path}/chapter/upload",
                        datatype: "json",
                        type:"post",
                        data:{chapterId:chapterID},
                        fileElementId:"url",
                        success:function (data) {
                            $("#"+subgridTable).trigger("reloadGrid");
                        }
                    })
                    return postData;
                }
            },{
                closeAfterAdd:true,reloadAfterSubmit:true,
                afterSubmit : function (response,postData) {
                    var chapterID=response.responseJSON.chapterId;
                    $.ajaxFileUpload({
                        url:"${path}/chapter/upload",
                        datatype: "json",
                        type:"post",
                        data:{chapterId:chapterID},
                        fileElementId:"url",
                        success:function (data) {
                            $("#poemlist").trigger("reloadGrid");
                        }
                    })
                    return postData;
                }
            })
    }
    function playAudio(data) {
        $("#myModal").modal("show");
        $("#myaudio").attr("src",data);
    }
    function downloadAudio(data) {
        location.href = "${path}/chapter/down?url="+data;
    }
</script>
<div>
    <!--创建表格-->
    <table id="poemlist"></table>

    <!--分页工具栏-->
    <div id="pager"></div>
    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <audio src="" id="myaudio" controls></audio>
    </div>
</div>