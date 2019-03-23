package com.csthink.crm.global;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class DispatcherServlet extends GenericServlet {

    private ApplicationContext applicationContext;

    @Override
    public void init() throws ServletException {
        super.init();
        applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)res;

        /*
           设计URL 规则
           1. /employee/add.do --> EmployeeController.add()
           2. /login.do -> CommonController.login()
           3. /about.do -> CommonController.about()

           /toLogin.do ---> 用于渲染要执行动作的页面，比如这里用于渲染登录页面
           /login.do ---> 表示处理执行动作的过程，比如这里用于处理登录数据，完成登录操作

          对应的方法都必须接收两个参数
           public void add(HttpServletRequest request, HttpServletResponse response)
         */

        // 获取请求url "employee/add.do",去掉开头的斜线
        String path = request.getServletPath().substring(1);
        int index = path.indexOf("/");
        String beanName;
        String methodName;

        if (index != -1) { // 请求路径类似 employee/add.do
            beanName = path.substring(0, index) + "Controller";
            methodName = path.substring(index + 1, path.indexOf(".do"));
        } else { // 请求路径类似 login.do
            beanName = "CommonController";
            methodName = path.substring(0, path.indexOf(".do"));
        }

        // 使用 IOC 获取对象
        Object obj = applicationContext.getBean(beanName);
        try {
            // 使用反射获取方法
            Method method = obj.getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            // 调用对象对应的方法，并按照规则传入固定的两个参数 HttpServletRequest request, HttpServletResponse response
            method.invoke(obj, request, response);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

    }
}
