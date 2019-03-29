<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!doctype html>
<html lang="zh-CN">
<head>
    <title>CRM | 重置密码</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <meta name="description" content="">
    <base href="<%=basePath%>/">
    <link rel="shortcut icon" href="/image/favicon.ico" type="image/x-icon">
    <link rel="stylesheet" href="/css/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="/css/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" href="/css/animate-css/vivify.min.css">
    <link rel="stylesheet" href="/css/c3/c3.min.css"/>
    <link rel="stylesheet" href="/css/bootstrap-datepicker3.min.css">
    <link rel="stylesheet" href="/css/dataTables.bootstrap4.min.css">
    <link rel="stylesheet" href="/css/dropify.min.css">
    <link rel="stylesheet" href="/css/sweetalert.css">
    <!-- MAIN CSS -->
    <link rel="stylesheet" href="/css/site.min.css">
</head>
<body class="theme-orange">
<div class="pattern">
    <span class="red"></span>
    <span class="indigo"></span>
    <span class="blue"></span>
    <span class="green"></span>
    <span class="orange"></span>
</div>
<div class="auth-main particles_js">
    <div class="auth_div vivify popIn">
        <div class="auth_brand">
            <a class="navbar-brand" href="javascript:void(0);"><img src="/image/icon.svg" width="30" height="30" class="d-inline-block align-top mr-2" alt="">CRM</a>
        </div>
        <div class="card forgot-pass">
            <div class="header">
                <%@include file="../../layout/messages.jsp"%>
            </div>
            <div class="body">
                <p class="lead mb-3"><strong>重置密码</strong></p>
<%--                <p>请输入手机号重置密码.</p>--%>
                <form class="form-auth-small" id="login-forgot-password-form" novalidate>
                    <div class="form-group">
                        <label for="phone" class="control-label sr-only">手机号</label>
                        <input type="text" class="form-control round" id="phone" name="phone" value="" placeholder="请输入手机号">
                    </div>
                    <div class="form-group">
                        <label for="smsCode" class="control-label sr-only">短信验证码</label>
                        <div class="input-group">
                            <input type="text" id="smsCode" class="form-control round" name="smsCode" value="" placeholder="请输入短信验证码" required>
                            <a href="javascript:void(0);" class="btn btn-info" id="login_send_sms" style="margin: 0;">发送短信验证</a>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="password" class="control-label sr-only">新密码</label>
                        <input type="password" class="form-control round" id="password" name="password" value="" placeholder="请输入新密码">
                    </div>

                    <input id="submit" type="button" class="btn btn-primary btn-round btn-block" value="重置密码"/>
                    <div class="bottom">
                        <span class="helper-text">想起密码? <a href="/login/toLogin.do">直接登录</a></span>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <div id="particles-js"></div>
</div>

<script src="/js/jquery.min.js"></script>
<script src="/js/libscripts.bundle.js"></script>
<script src="/js/vendorscripts.bundle.js"></script>
<script src="/js/c3.bundle.js"></script>
<script src="/js/bootstrap-datepicker.min.js"></script>
<script src="/js/mainscripts.bundle.js"></script>
<script src="/js/dropify.js"></script>
<script src="/js/dropify-form.js"></script>
<script src="/js/commonUtils.js"></script>
<script src="/js/commonService.js"></script>
<script src="/js/sweetalert.min.js"></script>
</body>
</html>
