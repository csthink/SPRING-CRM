<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!doctype html>
<html lang="zh-CN">
<head>
    <title>CRM | 404 页面</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <meta name="description" content="">
    <base href="<%=basePath%>/">
    <link rel="shortcut icon" href="/image/favicon.ico" type="image/x-icon">
    <!-- VENDOR CSS -->
    <link rel="stylesheet" href="/css/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="/css/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" href="/css/animate-css/vivify.min.css">
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
                <a class="navbar-brand" href="javascript:void(0);"><img src="./image/icon.svg" width="30" height="30" class="d-inline-block align-top mr-2" alt="">CRM</a>
            </div>
            <div class="card page-400">
                <div class="body">
                    <p class="lead mb-3"><span class="number left">404 </span><span class="text">Oops! <br/>Page Not Found</span></p>
                    <p>The page you were looking for could not be found, please <a href="javascript:void(0);">contact us</a> to report this issue.</p>
                    <div class="margin-top-30">
                        <a href="javascript:history.go(-1)" class="btn btn-round btn-default btn-block"><i class="fa fa-arrow-left"></i> <span>Go Back</span></a>
                        <a href="index.html" class="btn btn-round btn-primary btn-block"><i class="fa fa-home"></i> <span>Home</span></a>
                    </div>
                </div>
            </div>
        </div>
        <div id="particles-js"></div>
    </div>

<!-- Javascript -->
<script src="/js/libscripts.bundle.js"></script>
<script src="/js/vendorscripts.bundle.js"></script>
<script src="/js/mainscripts.bundle.js"></script>
</body>
</html>
