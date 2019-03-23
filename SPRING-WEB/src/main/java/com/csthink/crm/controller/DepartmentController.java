package com.csthink.crm.controller;

import com.csthink.crm.entity.Department;
import com.csthink.crm.service.DepartmentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller("departmentController")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    public void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Department> list = departmentService.getAll();
        request.setAttribute("LIST", list);
        request.getRequestDispatcher("/WEB-INF/views/biz/department/department_list.jsp").forward(request, response);
    }

    public void toAdd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/biz/department/department_add.jsp").forward(request, response);
    }

    public void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (StringUtils.isEmpty(request.getParameter("deptName"))) {
            request.getRequestDispatcher("/department/toAdd.do").forward(request, response);
            return;
        }

        String deptName = request.getParameter("deptName");
        Department department = new Department();
        department.setDeptName(deptName);

        if (departmentService.add(department) > 0) {
            response.sendRedirect("/department/list.do");
        } else {
            request.getRequestDispatcher("/department/toAdd.do").forward(request, response);
        }
    }


    public void toEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (StringUtils.isEmpty(request.getParameter("id"))) {
            // 记录日志，缺失参数
            response.sendRedirect("/department/list.do");
        }

        Integer id = Integer.parseInt(request.getParameter("id"));
        Department department = departmentService.get(id);
        if (null != department) {
            request.setAttribute("OBJ", department);
            request.getRequestDispatcher("/WEB-INF/views/biz/department/department_edit.jsp").forward(request, response);
        } else {
            response.sendRedirect("/WEB-INF/views/error/404.jsp");
        }
    }

    public void edit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String deptName = request.getParameter("deptName");
        if (StringUtils.isEmpty(request.getParameter("id")) || StringUtils.isEmpty(deptName)) {
            // 记录日志，缺失参数
            request.setAttribute("WARN", "缺失参数");
            request.getRequestDispatcher("/department/toEdit.do").forward(request, response);
            return;
        }

        Integer id = Integer.parseInt(request.getParameter("id"));
        Department department = departmentService.get(id);
        department.setDeptName(deptName);

        if (departmentService.edit(department) > 0) {
            response.sendRedirect("/department/list.do");
        } else {
            request.getRequestDispatcher("/department/toEdit.do").forward(request, response);
        }
    }

    public void remove(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (StringUtils.isNotEmpty(request.getParameter("id"))) {
            Integer id = Integer.parseInt(request.getParameter("id"));
            Department department = departmentService.get(id);

            if (null != department) {
                departmentService.remove(id);
            }
        }

        response.sendRedirect("/department/list.do");
    }
}
