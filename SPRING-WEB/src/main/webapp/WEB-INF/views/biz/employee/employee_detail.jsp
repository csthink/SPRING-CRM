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
                            <li class="breadcrumb-item active" aria-current="page">员工详情</li>
                        </ol>
                    </nav>
                </div>
                <div class="col-md-6 col-sm-12 text-right hidden-xs">
                    <a href="/employee/list.do" class="btn btn-sm btn-primary btn-round" title="">员工列表</a>
                </div>
            </div>
        </div>

        <div class="container-fluid">
            <div class="row clearfix">
                <div class="col-md-6 offset-3">
                    <div class="card">
                        <div class="header">
                            <%@include file="../../layout/messages.jsp"%>
                        </div>
                        <div class="body">
                            <%-- novalidate 表示取消表单默认验证，自己来处理 --%>
                            <form class="needs-validation" id="employee-edit-form" novalidate>
                                <input type="hidden" value="${OBJ.id}" name="id"/>
                                <div class="form-group">
                                    <label for="real_name">姓名</label>
                                    <input type="text" id="real_name" class="form-control" name="real_name" value="${OBJ.realName}" placeholder="请输入真实姓名" required autofocus maxlength="20" disabled>
                                </div>
                                <%--<div class="form-group">--%>
                                    <%--<label for="photo_dropify_event"><small>请上传员工头像${OBJ.photo}</small></label>--%>
                                    <%--<input type="file" class="dropify-fr" id="photo_dropify_event" data-default-file="/WEB-INF/upload/${OBJ.photo}" name="photo" data-allowed-file-extensions="jpg jpeg png gif bmp" data-max-file-size="100K">--%>
                                    <%--<div class="valid-feedback"></div>--%>
                                    <%--<div class="invalid-feedback"></div>--%>
                                <%--</div>--%>
                                <div class="form-group">
                                    <label for="dept_id">部门</label>
                                    <div class="form-group">
                                        <select id="dept_id" name="dept_id" disabled>
                                            <c:forEach items="${DLIST}" var="dep">
                                                <c:choose>
                                                    <c:when test="${dep.id == OBJ.deptId}">
                                                        <option value="${dep.id}" selected>${dep.deptName}</option>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <option value="${dep.id}">${dep.deptName}</option>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="birth_date">出生日期</label>
                                    <div class="input-group mb-3">
                                        <input type="text" id="birth_date" data-provide="datepicker" data-date-autoclose="true" class="form-control" name="birth_date" data-date-format="yyyy-mm-dd" value="<fmt:formatDate value="${OBJ.birthDate}" pattern="yyyy-MM-dd"/>" disabled>
                                    </div>
                                    <div class="valid-feedback"></div>
                                    <div class="invalid-feedback"></div>
                                </div>
                                <div class="form-group">
                                    <label>性别</label>
                                    <div class="form-group">
                                        <label class="fancy-radio">
                                            <input type="radio" name="gender" value="0" <c:out value="${OBJ.gender == 0 ? 'checked': ''}" /> disabled>
                                            <span><i></i>保密</span>
                                        </label>
                                        <label class="fancy-radio">
                                            <input type="radio" name="gender" value="1" <c:out value="${OBJ.gender == 1 ? 'checked': ''}" /> disabled>
                                            <span><i></i>男</span>
                                        </label>
                                        <label class="fancy-radio">
                                            <input type="radio" name="gender" value="2" <c:out value="${OBJ.gender == 2 ? 'checked': ''}" /> disabled>
                                            <span><i></i>女</span>
                                        </label>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="hire_date">入职时间</label>
                                    <div class="input-group mb-3">
                                        <input id="hire_date" data-provide="datepicker" data-date-autoclose="true" class="form-control" name="hire_date" data-date-format="yyyy-mm-dd" value="<fmt:formatDate value="${OBJ.hireDate}" pattern="yyyy-MM-dd"/>" disabled>
                                    </div>
                                    <div class="valid-feedback"></div>
                                    <div class="invalid-feedback"></div>
                                </div>
                                <div class="form-group">
                                    <label for="phone_num">手机</label>
                                    <input type="text" id="phone_num" class="form-control" name="phone" value="${OBJ.phone}" placeholder="请输入手机号" disabled>
                                    <div class="valid-feedback"></div>
                                    <div class="invalid-feedback"></div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

<%@include file="../../layout/foot.jsp"%>