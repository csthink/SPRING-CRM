<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@include file="../../layout/head.jsp"%>

<div id="main-content">
    <div class="block-header">
        <div class="row clearfix">
            <div class="col-md-6 col-sm-12">
                <!-- <h1>个人信息</h1> -->
                <nav aria-label="breadcrumb">
                    <ol class="breadcrumb">
                        <li class="breadcrumb-item"><a href="javascript:void(0);">员工管理</a></li>
                        <li class="breadcrumb-item active" aria-current="page">员工列表</li>
                    </ol>
                </nav>
            </div>
            <div class="col-md-6 col-sm-12 text-right hidden-xs">
                <a href="/employee/toAdd.do" class="btn btn-sm btn-primary btn-round" title="">新增员工</a>
            </div>
        </div>
    </div> <!-- /block-header -->

    <div class="container-fluid">
        <div class="row clearfix">
            <div class="col-lg-8 col-md-8 offset-2">
                <div class="table-responsive">
                    <table class="table table-hover table-custom spacing5">
                        <thead>
                        <tr>
                            <th>员工编号</th>
                            <th>手机号</th>
                            <th>姓名</th>
                            <th>入职时间</th>
                            <th>所在部门</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${LIST}" var="emp">
                            <tr>
                                <td><span>${emp.id}</span></td>
                                <td><span>${emp.phone}</span></td>
                                <td><span>${emp.realName}</span></td>
                                <td><span><fmt:formatDate value="${emp.hireDate}" pattern="yyyy-MM-dd"></fmt:formatDate></span></td>
                                <td><span>${emp.department.deptName}</span></td>
                                <td>
                                    <a href="/employee/toEdit.do?id=${emp.id}" class="btn btn-primary btn">编辑</a>
                                    <a href="/employee/detail.do?id=${emp.id}" class="btn btn-dark btn">详情</a>
                                    <a href="javascript:void(0)" data-id="${emp.id}" class="btn btn-danger deptRemove">删除</a>
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