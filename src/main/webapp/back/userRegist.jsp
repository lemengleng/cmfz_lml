<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<% request.setAttribute("path",request.getContextPath());%>
<html>
<head>
    <title>Title</title>
</head>
<body>
<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
<div id="main" style="width: 600px;height:400px;"></div>
<script type="text/javascript">
    $.post("${path}/user/queryByDate",function (map) {
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('main'));

        // 指定图表的配置项和数据
        var option = {
            title: {
                text: '注册日期统计'
            },
            tooltip: {},
            legend: {
                data:['男','女']
            },
            xAxis: {
                data: ["1天","7天","30天","365天"]
            },
            yAxis: {},
            series: [{
                name: '男',
                type: 'bar',
                data: map.man,
            },
                {
                    name: '女',
                    type: 'bar',
                    data: map.woman,
                }]
        };

        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
    })

</script>
</body>
</html>
