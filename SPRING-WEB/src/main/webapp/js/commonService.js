/**
 * 业务相关的JS
 */
'use strict';

// ****************************** 员工管理 ******************************** //

// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 添加员工开始 <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< //
// 获取员工添加表单
// TODO 待优化
var emp_add_form = document.getElementById("employee-add-form");
if (null != emp_add_form) {
    // 实例化验证器，添加验证规则
    var empVld = new Validator();
    empVld.add(emp_add_form.real_name, [{strategy: 'isNonEmpty', errorMsg: '姓名不能为空'}]);
    empVld.add(emp_add_form.birth_date, [{strategy: 'isNonEmpty', errorMsg: '出生日期不能为空'}]);
    empVld.add(emp_add_form.hire_date, [{strategy: 'isNonEmpty', errorMsg: '入职时间不能为空'}]);
    empVld.add(emp_add_form.phone_num,
        [
            {strategy: 'isNonEmpty', errorMsg: '手机号不能为空'},
            {strategy: 'isPhone', errorMsg: '手机号格式不正确'}
        ]
    );
    empVld.add(emp_add_form.captcha_input, [{strategy: 'isNonEmpty', errorMsg: '图形验证码不能为空'}]);
    empVld.add(emp_add_form.sms_code, [{strategy: 'isNonEmpty', errorMsg: '短信验证码不能为空'}]);

    var submitEmpAddForm;

    /**
     * 表单提交函数(被装饰者函数)
     */
    submitEmpAddForm = function () {
        var formData = new FormData(emp_add_form);

        swal({
            title: "确定保存信息",
            text: "",
            type: "info",
            showCancelButton: true,
            closeOnConfirm: false,
            showLoaderOnConfirm: true,
        }, function () {
            $.ajax({
                url: "/employee/add.do",
                type: "post",
                data: formData,
                dataType: "json",
                processData: false,
                contentType: false,
                success: function (data) {
                    console.log(data);
                    var flag = data.flag;
                    var msg = data.msg;
                    if (flag === true) { // 注册成功
                        swal(msg);
                        // Message.success(msg);
                        setTimeout(function () {
                            window.location.href = "/employee/list.do";
                        }, 2000);
                    } else { // 注册失败
                        swal(msg);
                    }
                },
                error: function (e) {
                    console.log(e);
                    Message.error(e);
                }
            });

        });
    };

    // 添加装饰者,表单提交之前执行校验
    submitEmpAddForm = submitEmpAddForm.before(empVld.start.bind(empVld));
    // 表单提交按钮点击事件，触发表单提交，发异步请求
    emp_add_form.submit.onclick = function () {
        submitEmpAddForm();
    };

    // 员工发送短信按钮验证规则
    var empSendSmsVld = new Validator();
    empSendSmsVld.add(emp_add_form.captcha, [{strategy: 'isNonEmpty', errorMsg: '图形验证码不能为空'}]);
    empSendSmsVld.add(emp_add_form.phone_num,
        [
            {strategy: 'isNonEmpty', errorMsg: '手机号不能为空'},
            {strategy: 'isPhone', errorMsg: '手机号格式不正确'}
        ]
    );

    var sendEmpSms;
    // 处理员工注册页的发送短信
    sendEmpSms = function () {
        var params = {
            "captcha": emp_add_form.captcha.value,
            "phone": emp_add_form.phone_num.value
        };

        $.post("/employee/sendSms.do", params, function (data) {
            if (data.flag === true) {
                Message.success(data.msg);
            } else {
                Message.error(data.msg);
            }
        }, "json");
    };
    // 添加装饰者，发送短信的前置操作
    sendEmpSms = sendEmpSms.before(empSendSmsVld.start.bind(empSendSmsVld));

    var emp_send_sms = document.getElementById("emp_send_sms");
    // 员工注册页面发送短信操作
    emp_send_sms.onclick = function () {
        console.log("sendSMS");
        sendEmpSms();
    };
}


// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 编辑员工开始 <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< //
// 获取员工编辑表单
var emp_edit_form = document.getElementById("employee-edit-form");

if (null != emp_edit_form) {
    // 实例化验证器，添加验证规则
    var empEditVld = new Validator();
    empEditVld.add(emp_edit_form.real_name, [{strategy: 'isNonEmpty', errorMsg: '姓名不能为空'}]);
    empEditVld.add(emp_edit_form.birth_date, [{strategy: 'isNonEmpty', errorMsg: '出生日期不能为空'}]);
    empEditVld.add(emp_edit_form.hire_date, [{strategy: 'isNonEmpty', errorMsg: '入职时间不能为空'}]);
    empEditVld.add(emp_edit_form.phone_num,
        [
            {strategy: 'isNonEmpty', errorMsg: '手机号不能为空'},
            {strategy: 'isPhone', errorMsg: '手机号格式不正确'}
        ]
    );

    var submitEmpEditForm;
    /**
     * 表单提交函数(被装饰者函数)
     */
    submitEmpEditForm = function () {
        var formData = new FormData(emp_edit_form);

        swal({
            title: "确定保存信息",
            text: "",
            type: "info",
            showCancelButton: true,
            closeOnConfirm: false,
            showLoaderOnConfirm: true,
        }, function () {
            $.ajax({
                url: "/employee/edit.do",
                type: "post",
                data: formData,
                dataType: "json",
                processData: false,
                contentType: false,
                success: function (data) {
                    console.log(data);
                    var flag = data.flag;
                    var msg = data.msg;
                    if (flag === true) { // 注册成功
                        swal(msg);
                        // Message.success(msg);
                        setTimeout(function () {
                            window.location.href = "/employee/list.do";
                        }, 2000);
                    } else { // 注册失败
                        swal(msg);
                    }
                },
                error: function (e) {
                    console.log(e);
                    Message.error(e);
                }
            });

        });


    };

    // 添加装饰者,表单提交之前执行校验
    submitEmpEditForm = submitEmpEditForm.before(empEditVld.start.bind(empEditVld));
    // 表单提交按钮点击事件，触发表单提交，发异步请求
    emp_edit_form.submit.onclick = function () {
        submitEmpEditForm();
    };
}

$(".deptRemove").click(function () {
    var emp_id = $(this).attr("data-id");
    var currentRow = $(this).parents("tr");
    swal({
        title: "确定删除此员工?",
        text: "此操作将会永久删除该员工",
        type: "warning",
        showCancelButton: true,
        confirmButtonColor: "#dc3545",
        confirmButtonText: "确定删除",
        cancelButtonText: "取消",
        closeOnConfirm: false
    }, function () {
        $.ajax({
            url: "/employee/remove.do",
            type: "post",
            data: {id: emp_id},
            dataType: "json",
            success: function (data) {
                console.log(data);
                var flag = data.flag;
                var msg = data.msg;
                if (flag === true) { // 操作成功
                    swal("已删除!", "该员工已被删除.", "success");
                    currentRow.remove();
                } else { // 操作失败
                    swal("删除失败", msg, "danger");
                }
            },
            error: function (e) {
                console.log(e);
                Message.error(e);
            }
        });
    });
});