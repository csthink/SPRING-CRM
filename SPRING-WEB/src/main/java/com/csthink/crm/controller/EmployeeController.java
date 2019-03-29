package com.csthink.crm.controller;

import com.csthink.crm.entity.Department;
import com.csthink.crm.entity.Employee;
import com.csthink.crm.service.DepartmentService;
import com.csthink.crm.service.EmployeeService;
import com.csthink.crm.service.SmsService;
import com.csthink.crm.utils.CommonUtils;
import com.csthink.crm.utils.FileUploadUtils;
import com.csthink.crm.utils.JsonUtils;
import com.csthink.crm.utils.VerifyCodeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller("employeeController")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private SmsService smsService;

    private String viewPath = "/WEB-INF/views/biz/employee/";

    /**
     * 员工列表
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    public void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Employee> list = employeeService.getAll();
        request.setAttribute("LIST", list);
        request.getRequestDispatcher(viewPath + "employee_list.jsp").forward(request, response);
    }

    public void toAdd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Department> departments = departmentService.getAll();
        request.setAttribute("DLIST", departments);
        request.getRequestDispatcher(viewPath + "employee_add.jsp").forward(request, response);
    }

    /**
     * 发送短信验证码
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void sendSms(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.getSession().setAttribute("error", "测试内容");

        System.out.println("sendSms >>> ");
        String phone = request.getParameter("phone"); // 获取用户页面输入的手机号
        String captcha = request.getParameter("captcha"); // 获取用户页面输入的验证码
        Map<String, Object> data = new HashMap<>();
        if (StringUtils.isBlank(phone) || StringUtils.isBlank(captcha)) {
            data.put("flag", false);
            data.put("msg", "手机号或图形验证码必须输入");
            JsonUtils.json(response, data);
            return;
        }

        // 从session中取出servlet生成的验证码text值
        String captchaExpected = (String) request.getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);

        // 校验验证码是否正确
        if (!captchaExpected.equalsIgnoreCase(captcha)) {
            data.put("flag", false);
            data.put("msg", "图形验证码校验失败");
            JsonUtils.json(response, data);
            return;
        }

        // 发送短信验证码
        String smsCode = CommonUtils.generateRandomNumber(4); // 生成随机验证码
        String smsContent = "小不点提醒您,短信验证码为: " + smsCode + " ,请妥善保管，并尽快完成验证，有效期5分钟";
        System.out.println(smsContent);
        Map<String, Object> sendResult = smsService.send(phone, smsContent);

        // 验证码写入session
        VerifyCodeUtils.saveToSession(request.getSession(), smsCode, "emp_add_sms");
        JsonUtils.json(response, sendResult);
    }

    public void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            Map<String, String> params = FileUploadUtils.upload(request); // 获取请求参数
            Map<String, Object> data = new HashMap<>(); // 响应结果
            System.out.println("resultMap: " + params);

            if (params.size() > 0) {
                String realName = params.get("real_name");
                String phone = params.get("phone");
                String gender = params.get("gender");
                String photo = !StringUtils.isBlank(params.get("fileName")) ? params.get("fileName") : "default-photo.jpg";
                String captcha = params.get("captcha");
                String smsCode = params.get("smsCode");

                HttpSession session = request.getSession();

                // 校验图形验证码


                // 校验短信验证码
                Map<String, Object> smsResult = VerifyCodeUtils.checkCode(smsCode, session, "emp_add_sms", 60);
                if (null == smsResult.get("flag") || !(Boolean) smsResult.get("flag")) {
                    data.put("flag", false);
                    data.put("msg", smsResult.get("msg").toString());
                    JsonUtils.json(response, data);
                    return;
                }

                Employee employee = new Employee();
                employee.setRealName(realName);
                employee.setPhone(phone);
                employee.setGender(gender);
                employee.setPhoto(photo);

                Integer deptId = null;
                Date birthDate = null;
                Date hireDate = null;

                try {
                    deptId = Integer.parseInt(params.get("dept_id"));
                    birthDate = new SimpleDateFormat("yyyy-MM-dd").parse(params.get("birth_date"));
                    hireDate = new SimpleDateFormat("yyyy-MM-dd").parse(params.get("hire_date"));
                } catch (NumberFormatException | ParseException e) {
                    e.printStackTrace();
                }

                employee.setDeptId(deptId);
                employee.setBirthDate(birthDate);
                employee.setHireDate(hireDate);

                if (employeeService.add(employee) > 0) { // 注册成功
                    request.getSession().removeAttribute("emp_add_sms");
                    data.put("flag", true);
                    data.put("msg", "注册成功");
                } else { // 注册失败
                    data.put("flag", true);
                    data.put("msg", "注册失败");
                }

                JsonUtils.json(response, data);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // 记录日志
            System.out.println(e.getMessage());
        }
    }

    public void toEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Department> departments = departmentService.getAll();
        if (StringUtils.isBlank(request.getParameter("id"))) {
            response.sendRedirect("/WEB-INF/views/error/404.jsp");
        }

        Integer id = Integer.parseInt(request.getParameter("id"));
        Employee employee = employeeService.get(id);
        if (null == employee) {
            response.sendRedirect("/WEB-INF/views/error/404.jsp");
        }

        request.setAttribute("DLIST", departments);
        request.setAttribute("OBJ", employee);
        request.getRequestDispatcher(viewPath + "employee_edit.jsp").forward(request, response);
    }

    public void edit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            Map<String, String> params = FileUploadUtils.upload(request); // 获取请求参数
            Map<String, Object> data = new HashMap<>(); // 响应结果

            if (params.size() > 0) {
                String realName = params.get("real_name");
                String phone = params.get("phone");
                String gender = params.get("gender");

                Employee employee = null;
                try {
                    employee = employeeService.get(Integer.parseInt(params.get("id")));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                if (null == employee) {
                    data.put("flag", false);
                    data.put("msg", "员工信息获取失败");
                    JsonUtils.json(response, data);
                    return;
                }
                System.out.println("查到的对象: " + employee);
                employee.setRealName(realName);
                employee.setPhone(phone);
                employee.setGender(gender);

                Integer deptId = null;
                Date birthDate = null;
                Date hireDate = null;

                try {
                    deptId = Integer.parseInt(params.get("dept_id"));
                    birthDate = new SimpleDateFormat("yyyy-MM-dd").parse(params.get("birth_date"));
                    hireDate = new SimpleDateFormat("yyyy-MM-dd").parse(params.get("hire_date"));
                } catch (NumberFormatException | ParseException e) {
                    e.printStackTrace();
                }

                employee.setDeptId(deptId);
                employee.setBirthDate(birthDate);
                employee.setHireDate(hireDate);

                if (employeeService.edit(employee) > 0) { // 注册成功
                    data.put("flag", true);
                    data.put("msg", "保存成功");
                } else { // 注册失败
                    data.put("flag", true);
                    data.put("msg", "保存失败");
                }

                JsonUtils.json(response, data);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // 记录日志
            System.out.println(e.getMessage());
        }
    }

    public void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Department> departments = departmentService.getAll();

        Integer id = null;
        try {
            id = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendRedirect("/WEB-INF/views/error/404.jsp");
        }

        Employee employee = employeeService.get(id);
        if (null == employee) {
            response.sendRedirect("/WEB-INF/views/error/404.jsp");
        }

        request.setAttribute("DLIST", departments);
        request.setAttribute("OBJ", employee);
        request.getRequestDispatcher(viewPath + "employee_detail.jsp").forward(request, response);
    }

    public void remove(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, Object> data = new HashMap<>(); // 响应结果
        Integer id = null;
        try {
            id = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendRedirect("/WEB-INF/views/error/404.jsp");
        }

        Employee employee = employeeService.get(id);
        if (null == employee) {
            data.put("flag", false);
            data.put("msg", "员工信息获取失败");
            JsonUtils.json(response, data);
            return;
        }

        if (employeeService.remove(id) > 0) {
            data.put("flag", true);
            data.put("msg", "删除成功");
        } else {
            data.put("flag", false);
            data.put("msg", "删除失败");
        }

        JsonUtils.json(response, data);
    }

}
