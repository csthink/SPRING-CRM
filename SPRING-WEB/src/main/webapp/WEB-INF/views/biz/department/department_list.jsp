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
                        <li class="breadcrumb-item active" aria-current="page">部门列表</li>
                    </ol>
                </nav>
            </div>
            <div class="col-md-6 col-sm-12 text-right hidden-xs">
                <a href="/department/toAdd.do" class="btn btn-sm btn-primary btn-round" title="">创建部门</a>
            </div>
        </div>
    </div> <!-- /block-header -->

    <div class="container-fluid">
        <div class="row clearfix">
            <div class="col-lg-4 col-md-4 offset-4">
                <div class="table-responsive">
                    <table class="table table-hover table-custom spacing5">
                        <thead>
                        <tr>
                            <th>部门编号</th>
                            <th>部门名称</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${LIST}" var="dep">
                            <tr>
                                <td><span>${dep.id}</span></td>
                                <td><span>${dep.deptName}</span></td>
                                <td>
                                    <a href="/department/toEdit.do?id=${dep.id}" class="btn btn-primary btn-sm">编辑</a>
                                    <a href="/department/remove.do?id=${dep.id}" class="btn btn-danger btn-sm deptRemove">删除</a>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div> <!-- /container-fluid -->
</div> <!-- /main-content -->

<%@include file="../../layout/foot.jsp"%>