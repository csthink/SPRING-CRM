<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@include file="../../layout/head.jsp"%>

<div id="main-content">
        <div class="block-header">
            <div class="row clearfix">
                <div class="col-md-6 col-sm-12">
                    <!-- <h1>个人信息</h1> -->
                    <nav aria-label="breadcrumb">
                        <ol class="breadcrumb">
                            <li class="breadcrumb-item"><a href="javascript:void(0);">部门管理</a></li>
                            <li class="breadcrumb-item active" aria-current="page">新增部门</li>
                        </ol>
                    </nav>
                </div>
                <div class="col-md-6 col-sm-12 text-right hidden-xs">
                    <a href="/department/list.do" class="btn btn-sm btn-primary btn-round" title="">部门列表</a>
                </div>
            </div>
        </div>

        <div class="container-fluid">
            <div class="row clearfix">
                <div class="col-md-6 offset-3">
                    <div class="card">
                        <div class="header">
                            <h2>新增部门</h2>
                        </div>
                        <div class="body">
                            <form id="basic-form" method="post" action="/department/add.do"  novalidate>
                                <div class="form-group">
                                    <label for="deptName">部门名</label>
                                    <input type="text" id="deptName" class="form-control" name="deptName" value="" placeholder="请输入部门名称" required>
                                </div>
                                <br>
                                <button type="submit" class="btn btn-primary">保存</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

<%@include file="../../layout/foot.jsp"%>