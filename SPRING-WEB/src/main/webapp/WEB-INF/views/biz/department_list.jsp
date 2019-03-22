<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
//    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
//    request.setAttribute("basePath", basePath);
%>
<!doctype html>
<html lang="zh-CN">
<head>
    <title>CRM | 部门列表</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <meta name="description" content="">
    <%--<base href="<%=basePath%>/">--%>
    <link rel="icon" href="/image/favicon.ico" type="image/x-icon">
    <!-- VENDOR CSS -->
    <link rel="stylesheet" href="/css/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="/css/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" href="/css/animate-css/vivify.min.css">
    <link rel="stylesheet" href="/css/c3/c3.min.css"/>
    <link rel="stylesheet" href="/css/bootstrap-datepicker3.min.css">
    <link rel="stylesheet" href="/css/dataTables.bootstrap4.min.css">
    <!-- MAIN CSS -->
    <link rel="stylesheet" href="/css/site.min.css">
</head>
<body class="theme-orange">

<!-- Page Loader -->
<div class="page-loader-wrapper">
    <div class="loader">
        <div class="m-t-30"><img src="/image/icon.svg" width="40" height="40" alt="CRM"></div>
        <p>系统加载中...</p>
    </div>
</div>

<!-- Overlay For Sidebars -->
<div class="overlay"></div>

<div id="wrapper">
    <nav class="navbar navbar-fixed-top">
        <div class="container-fluid">

            <div class="navbar-left">
                <div class="navbar-btn">
                    <a href="index.html"><img src="/image/icon.svg" alt="Oculux Logo" class="img-fluid logo"></a>
                    <button type="button" class="btn-toggle-offcanvas"><i class="lnr lnr-menu fa fa-bars"></i></button>
                </div>
            </div>

            <div class="navbar-right">
                <div id="navbar-menu">
                    <ul class="nav navbar-nav">
                        <li><a href="login.html" class="icon-menu"><i class="icon-power"></i></a></li>
                    </ul>
                </div>
            </div>
        </div>
        <div class="progress-container"><div class="progress-bar" id="myBar"></div></div>
    </nav>

    <div id="megamenu" class="megamenu particles_js">
        <div id="particles-js"></div>
    </div>

    <div id="left-sidebar" class="sidebar">
        <div class="navbar-brand">
            <a href="index.html"><img src="/image/icon.svg" alt="CRM Logo" class="img-fluid logo"><span>CRM</span></a>
            <button type="button" class="btn-toggle-offcanvas btn btn-sm float-right"><i class="lnr lnr-menu fa fa-chevron-circle-left"></i></button>
        </div>
        <div class="sidebar-scroll">
            <div class="user-account">
                <div class="user_div">
                    <img src="/image/user.png" class="user-photo" alt="User Profile Picture">
                </div>
                <div class="dropdown">
                    <span>Welcome,</span>
                    <a href="javascript:void(0);" class="dropdown-toggle user-name" data-toggle="dropdown"><strong>Admin</strong></a>
                    <ul class="dropdown-menu dropdown-menu-right account vivify flipInY">
                        <li><a href="page-profile.html"><i class="icon-user"></i>我的信息</a></li>
                        <li><a href="javascript:void(0);"><i class="icon-settings"></i>设置</a></li>
                        <li class="divider"></li>
                        <li><a href="page-login.html"><i class="icon-power"></i>退出系统</a></li>
                    </ul>
                </div>
            </div>
            <nav id="left-sidebar-nav" class="sidebar-nav">
                <ul id="main-menu" class="metismenu">
                    <!-- <li class="header">Main</li> -->
                    <li class="active"><a href="index.html"><i class="icon-logout"></i><span>退出系统</span></a></li>
                    <li class="header">系统管理</li>
                    <li>
                        <a href="#uiElements" class="has-arrow"><i class="icon-users"></i><span>人员管理</span></a>
                        <ul>
                            <li><a href="ui-icons.html">员工管理</a></li>
                            <li><a href="ui-icons-line.html">部门管理</a></li>
                        </ul>
                    </li>
                    <li>
                        <a href="#uiElements" class="has-arrow"><i class="icon-doc"></i><span>日志信息</span></a>
                        <ul>
                            <li><a href="ui-bootstrap.html">操作日志</a></li>
                            <li><a href="ui-typography.html">登录日志</a></li>
                            <li><a href="ui-colors.html">系统日志</a></li>
                        </ul>
                    </li>
                    <li>
                        <a href="#forms" class="has-arrow"><i class="icon-home"></i><span>个人中心</span></a>
                        <ul>
                            <li><a href="forms-basic.html">个人信息</a></li>
                            <li><a href="forms-advanced.html">修改密码</a></li>
                        </ul>
                    </li>
                </ul>
            </nav>
        </div>
    </div>

    <div id="main-content">
        <div class="block-header">
            <div class="row clearfix">
                <div class="col-md-6 col-sm-12">
                    <!-- <h1>个人信息</h1> -->
                    <nav aria-label="breadcrumb">
                        <ol class="breadcrumb">
                            <li class="breadcrumb-item"><a href="#">个人中心</a></li>
                            <li class="breadcrumb-item active" aria-current="page">个人信息</li>
                        </ol>
                    </nav>
                </div>
                <div class="col-md-6 col-sm-12 text-right hidden-xs">
                    <a href="javascript:void(0);" class="btn btn-sm btn-primary btn-round" title="">创建员工</a>
                </div>
            </div>
        </div>

        <div class="container-fluid">
            <div class="row clearfix">
                <div class="col-lg-4 col-md-4 offset-4">
                    <div class="table-responsive">
                        <table class="table table-hover table-custom spacing5">
                            <thead>
                            <tr>
                                <th>部门编号</th>
                                <th>部门名称</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${LIST}" var="dep">
                                <tr>
                                    <td><span>${dep.id}</span></td>
                                    <td><span>${dep.deptName}</span></td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Javascript -->
<script src="/js/libscripts.bundle.js"></script>
<script src="/js/vendorscripts.bundle.js"></script>
<script src="/js/c3.bundle.js"></script>
<script src="/js/bootstrap-datepicker.min.js"></script>
<script src="/js/mainscripts.bundle.js"></script>
<script src="/js/index.js"></script>
</body>
</html>
