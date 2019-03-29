package com.csthink.crm.service.impl;

import com.csthink.crm.dao.EmployeeDao;
import com.csthink.crm.dao.LoginDao;
import com.csthink.crm.entity.Employee;
import com.csthink.crm.service.LoginService;
import com.csthink.crm.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.Date;

@Service("loginService")
public class LoginServiceImpl implements LoginService {

    @Autowired
    private LoginDao loginDao;

    @Autowired
    private EmployeeDao employeeDao;

    @Override
    public Employee loginByUsername(String username, String password) {
        if (null == username || "".equals(username.trim()) || null == password || "".equals(password.trim())) {
            return null;
        }

        Employee employee = loginDao.selectByUsername(username);
        if (null == employee) {
            return null;
        }

        try {
            // 校验密码
            if (CommonUtils.checkPassword(password, employee.getPassword())) {
                return employee;
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Employee loginByPhone(String phone) {
        if (null == phone || "".equals(phone.trim())) {
            return null;
        }

        return loginDao.selectByPhone(phone);
    }

    @Override
    public Boolean changePassword(Integer id, String password) {
        String encryptedPassword = "";
        Employee employee = employeeDao.selectById(id); // 获取员工对象
        if (null == employee) { // 员工对象获取失败
            return false;
        }

        try {
            encryptedPassword = CommonUtils.encryptByMD5(password);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        employee.setPassword(encryptedPassword);
        employee.setUpdateTime(new Date());

        return employeeDao.update(employee) > 0;
    }
}
