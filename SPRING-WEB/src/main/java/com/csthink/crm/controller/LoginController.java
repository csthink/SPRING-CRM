package com.csthink.crm.controller;

import com.csthink.crm.entity.Employee;
import com.csthink.crm.service.LoginService;
import com.csthink.crm.service.SmsService;
import com.csthink.crm.utils.CommonUtils;
import com.csthink.crm.utils.JsonUtils;
import com.csthink.crm.utils.VerifyCodeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Controller("loginController")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private SmsService smsService;

    /**
     * 渲染登录页面
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void toLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("title", "登录页面");
        request.getRequestDispatcher("/WEB-INF/views/biz/login/login.jsp").forward(request, response);
    }

    /**
     * 处理用户名密码登录
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        Map<String, Object> data = new HashMap<>();

        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            data.put("flag", false);
            data.put("msg", "参数缺失");
            JsonUtils.json(response, data);
            return;
        }

        Employee employee = loginService.loginByUsername(username, password);
        if (null == employee) {
            data.put("flag", false);
            data.put("msg", "用户名或密码错误");
            JsonUtils.json(response, data);
            return;
        }

        request.getSession().setAttribute("USER", employee);
        data.put("flag", true);
        data.put("msg", "登录成功");
        JsonUtils.json(response, data);
    }

    /**
     * 发送手机登录的验证短信
     * 发送重置密码短信
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void sendSms(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String phone = request.getParameter("phone");
        String type = request.getParameter("type");
        Map<String, Object> data = new HashMap<>();

        if (StringUtils.isBlank(type)) {
            data.put("flag", false);
            data.put("msg", "缺失参数");
            JsonUtils.json(response, data);
            return;
        }

        if (StringUtils.isBlank(phone)) {
            data.put("flag", false);
            data.put("msg", "手机号不能为空");
            JsonUtils.json(response, data);
            return;
        }

        // 检查手机号是否已注册
        Employee employee = loginService.loginByPhone(phone);
        if (null == employee) {
            data.put("flag", false);
            data.put("msg", "该手机号尚未注册");
            JsonUtils.json(response, data);
            return;
        }

        // 发送短信验证码
        String smsCode = CommonUtils.generateRandomNumber(4); // 生成随机验证码
        String smsContent = "小不点提醒您,短信验证码为: " + smsCode + " ,请妥善保管，并尽快完成登录验证，有效期5分钟";
        System.out.println(smsContent);
        Map<String, Object> sendResult = smsService.send(phone, smsContent);

        // 验证码写入session
        if ("login".equals(type)) {
            VerifyCodeUtils.saveToSession(request.getSession(), smsCode, "login_sms");
        } else if ("forgotPassword".equals(type)) {
            VerifyCodeUtils.saveToSession(request.getSession(), smsCode, "reset_pwd_sms");
        }

        JsonUtils.json(response, sendResult);
    }

    /**
     * 处理手机号短信登录
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void login2(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String phone = request.getParameter("phone");
        String smsCode = request.getParameter("smsCode");

        Map<String, Object> data = new HashMap<>();
        if (StringUtils.isBlank(phone) || StringUtils.isBlank(smsCode)) {
            data.put("flag", false);
            data.put("msg", "参数缺失");
            JsonUtils.json(response, data);
            return;
        }

        // 检查手机号是否已注册
        Employee employee = loginService.loginByPhone(phone);
        if (null == employee) {
            data.put("flag", false);
            data.put("msg", "该手机号尚未注册");
            JsonUtils.json(response, data);
            return;
        }
        // 校验短信验证码是否有效
        Map<String, Object> checkResult = VerifyCodeUtils.checkCode(smsCode, request.getSession(), "login_sms", 300);
        if (!(Boolean) checkResult.get("flag")) {
            JsonUtils.json(response, checkResult);
        }

        request.getSession().setAttribute("USER", employee);
        request.getSession().removeAttribute("login_sms");
        data.put("flag", true);
        data.put("msg", "登录成功");
        JsonUtils.json(response, data);

    }

    /**
     * 渲染忘记密码页面
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void toForgotPassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("title", "忘记密码");
        request.getRequestDispatcher("/WEB-INF/views/biz/login/forgot_password.jsp").forward(request, response);
    }

    /**
     * 修改密码
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void changePassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String phone = request.getParameter("phone");
        String smsCode = request.getParameter("smsCode");
        String password = request.getParameter("password");

        Map<String, Object> data = new HashMap<>();

        if (StringUtils.isBlank(phone) || StringUtils.isBlank(smsCode) || StringUtils.isBlank(password)) {
            data.put("flag", false);
            data.put("msg", "参数缺失");
            JsonUtils.json(response, data);
            return;
        }

        // 检查手机号是否已注册
        Employee employee = loginService.loginByPhone(phone);
        if (null == employee) {
            data.put("flag", false);
            data.put("msg", "该手机号尚未注册");
            JsonUtils.json(response, data);
            return;
        }
        // 校验短信验证码是否有效
        Map<String, Object> checkResult = VerifyCodeUtils.checkCode(smsCode, request.getSession(), "reset_pwd_sms", 300);
        // 短信验证码未校验通过
        if (!(Boolean) checkResult.get("flag")) {
            JsonUtils.json(response, checkResult);
            return;
        }

        if (!loginService.changePassword(employee.getId(), password)) {
            data.put("flag", false);
            data.put("msg", "密码修改失败");
            JsonUtils.json(response, data);
            return;
        }

        request.getSession().removeAttribute("reset_pwd_sms");
        data.put("flag", true);
        data.put("msg", "密码修改成功");
        JsonUtils.json(response, data);
    }

    public void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Enumeration em = request.getSession().getAttributeNames();
        while (em.hasMoreElements()) {
            request.getSession().removeAttribute(em.nextElement().toString());
        }

        response.sendRedirect("/login/toLogin.do");
    }
}
