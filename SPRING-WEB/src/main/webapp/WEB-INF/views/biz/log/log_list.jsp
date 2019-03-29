<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@include file="../../layout/head.jsp"%>

<div id="main-content">
    <div class="block-header">
        <div class="row clearfix">
            <div class="col-md-6 col-sm-12">
                <!-- <h1>个人信息</h1> -->
                <nav aria-label="breadcrumb">
                    <ol class="breadcrumb">
                        <li class="breadcrumb-item"><a href="javascript:void(0);">日志管理</a></li>
                        <li class="breadcrumb-item active" aria-current="page">${TYPE}</li>
                    </ol>
                </nav>
            </div>
<%--            <div class="col-md-6 col-sm-12 text-right hidden-xs">--%>
<%--                <a href="/employee/toAdd.do" class="btn btn-sm btn-primary btn-round" title=""></a>--%>
<%--            </div>--%>
        </div>
    </div> <!-- /block-header -->

    <div class="container-fluid">
        <div class="row clearfix">
            <div class="col-lg-8 col-md-8 offset-2">
                <div class="table-responsive">
                    <table class="table table-hover table-custom spacing5">
                        <thead>
                        <tr>
                            <th>操作人员</th>
                            <th>操作模块</th>
                            <th>操作行为</th>
                            <th>IP</th>
                            <th>结果</th>
                            <th>操作时间</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${LIST}" var="log">
                            <tr>
                                <td><span>${log.operator}</span></td>
                                <td><span>${log.module}</span></td>
                                <td><span>${log.action}</span></td>
                                <td><span>${log.requestIp}</span></td>
                                <td><span>${log.requestResult}</span></td>
                                <td><span><fmt:formatDate value="${log.createTime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></span></td>
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