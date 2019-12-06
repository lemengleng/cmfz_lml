<%@page contentType="text/html;UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<html>
<head>
    <meta charset="utf-8">
    <title>love</title>
    <link href="favicon.ico" rel="shortcut icon" />
    <link href="./boot/css/bootstrap.min.css" rel="stylesheet">
    <script src="./boot/js/jquery-2.2.1.min.js"></script>
    <script>
        function changePic(img) {
            img.src="${pageContext.request.contextPath}/admin/security?"+Math.random();
        }
        function login() {
            if($('#username').val()==null||$('#password').val()==null){

            }else {
                $.ajax({
                    url:"${pageContext.request.contextPath}/admin/login",
                    type:"POST",
                    data:$("#loginForm").serialize(),
                    success:function (data) {
                        if (data!=null&data!=""){
                            console.log(data);
                            $("#msg").html("<span class='error' style='color: red'>"+data+"</span>")

                        }else {
                            location.href = "${pageContext.request.contextPath}/back/home.jsp";
                        }
                    }

                })
            }

        }

    </script>
</head>
<body style=" background: url(./img/3e4d03381f30e924eebbff0d40086e061d95f7b0.jpg); background-size: 100%;">


<div class="modal-dialog" style="margin-top: 10%;">
    <div class="modal-content">
        <div class="modal-header">

            <h4 class="modal-title text-center" id="myModalLabel">持明法洲</h4>
        </div>
        <form id="loginForm" method="post" action="${pageContext.request.contextPath}/user/login">
            <div class="modal-body" id = "model-body">
                <div class="form-group">
                    <input type="text" class="form-control"placeholder="用户名" id="username" autocomplete="off" name="username">
                </div>
                <div class="form-group">
                    <input type="password" class="form-control" placeholder="密码" id="password" autocomplete="off" name="password">
                </div>
                <div class="form-group">
                    验证码<img src="${pageContext.request.contextPath}/admin/security" id="securityCode" onclick="changePic(this)">
                    <input type="text" class="form-control" placeholder="验证码" autocomplete="off" name="securityCode">
                </div>
                <span id="msg"></span>
            </div>
            <div class="modal-footer">
                <div class="form-group">
                    <button type="button" class="btn btn-primary form-control" id="log" onclick="login()">登录</button>
                </div>
            </div>
        </form>
    </div>
</div>
</body>
</html>
