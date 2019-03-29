package com.csthink.crm.controller;

import com.csthink.crm.entity.Log;
import com.csthink.crm.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller("logController")
public class LogController {

    @Autowired
    private LogService logService;

    private String path = "/WEB-INF/views/biz/log/log_list.jsp";

    public void operationLog(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Log> list = logService.getOperationLog();

        request.setAttribute("LIST", list);
        request.setAttribute("TYPE", "操作日志");
        request.getRequestDispatcher(path).forward(request, response);
    }

    public void systemLog(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Log> list = logService.getSystemLog();

        request.setAttribute("LIST", list);
        request.setAttribute("TYPE", "系统日志");
        request.getRequestDispatcher(path).forward(request, response);
    }

    public void loginLog(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Log> list = logService.getLoginLog();

        request.setAttribute("LIST", list);
        request.setAttribute("TYPE", "登录日志");
        request.getRequestDispatcher(path).forward(request, response);
    }


}
