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
                            <li class="breadcrumb-item active" aria-current="page">新增员工</li>
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
                            <form class="needs-validation" id="employee-add-form" novalidate>
                                <div class="form-group">
                                    <label for="real_name">姓名</label>
                                    <input type="text" id="real_name" class="form-control" name="real_name" value="" placeholder="请输入真实姓名" required autofocus maxlength="20">
                                    <div class="valid-feedback">valid</div>
                                    <div class="invalid-feedback">invalid</div>
                                </div>
                                <div class="form-group">
                                    <label for="photo_dropify_event"><small>请上传员工头像</small></label>
                                    <input type="file" class="dropify-fr" id="photo_dropify_event" data-default-file="/image/photo-default.jpg" name="photo" data-allowed-file-extensions="jpg jpeg png gif bmp" data-max-file-size="100K">
                                    <div class="valid-feedback"></div>
                                    <div class="invalid-feedback"></div>
                                </div>
                                <div class="form-group">
                                    <label for="dept_id">部门</label>
                                    <div class="form-group">
                                        <select id="dept_id" name="dept_id">
                                            <c:forEach items="${DLIST}" var="dep">
                                                <option value="${dep.id}">${dep.deptName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="birth_date">出生日期</label>
                                    <div class="input-group mb-3">
                                        <input id="birth_date" data-provide="datepicker" data-date-autoclose="true" class="form-control" name="birth_date" data-date-format="yyyy-mm-dd">
                                    </div>
                                    <div class="valid-feedback"></div>
                                    <div class="invalid-feedback"></div>
                                </div>
                                <div class="form-group">
                                    <label>性别</label>
                                    <div class="form-group">
                                        <label class="fancy-radio">
                                            <input type="radio" name="gender" value="0" checked >
                                            <span><i></i>保密</span>
                                        </label>
                                        <label class="fancy-radio">
                                            <input type="radio" name="gender" value="1">
                                            <span><i></i>男</span>
                                        </label>
                                        <label class="fancy-radio">
                                            <input type="radio" name="gender" value="2">
                                            <span><i></i>女</span>
                                        </label>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="hire_date">入职时间</label>
                                    <div class="input-group mb-3">
                                        <input id="hire_date" data-provide="datepicker" data-date-autoclose="true" class="form-control" name="hire_date" data-date-format="yyyy-mm-dd">
                                    </div>
                                    <div class="valid-feedback"></div>
                                    <div class="invalid-feedback"></div>
                                </div>
                                <div class="form-group">
                                    <label for="phone_num">手机</label>
                                    <input type="text" id="phone_num" class="form-control" name="phone" value="" placeholder="请输入手机号" required>
                                    <div class="valid-feedback"></div>
                                    <div class="invalid-feedback"></div>
                                </div>
                                <div class="form-group">
                                    <label for="captcha_input">图形验证码</label>
                                    <div class="input-group">
                                        <input type="text" id="captcha_input" class="form-control" name="captcha" placeholder="请输入验证码" required>
                                        <img src="/kaptcha.jpg" alt="验证码" id="captcha_img" onclick="return document.getElementById('captcha_img').src='/kaptcha.jpg?s=' + Math.random()" style="cursor: pointer;">
                                    </div>
                                    <div class="valid-feedback"></div>
                                    <div class="invalid-feedback"></div>
                                </div>
                                <div class="form-group">
                                    <label for="sms_code">短信验证码</label>
                                    <div class="input-group">
                                        <input type="text" id="sms_code" class="form-control" name="smsCode" value="" placeholder="请输入短信验证码" required>
                                        <a href="javascript:void(0);" class="btn btn-info" id="emp_send_sms">发送短信验证</a>
                                    </div>
                                    <div class="valid-feedback"></div>
                                    <div class="invalid-feedback"></div>
                                </div>
                                <br/>
                                <input id="submit" type="button" class="btn btn-primary" value="保存"/>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

<%@include file="../../layout/foot.jsp"%>