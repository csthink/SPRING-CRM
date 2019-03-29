package com.csthink.crm.service.impl;

import com.csthink.crm.dao.EmployeeDao;
import com.csthink.crm.entity.Employee;
import com.csthink.crm.service.EmployeeService;
import com.csthink.crm.service.SmsService;
import com.csthink.crm.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("employeeService")
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private SmsService smsService;

    @Override
    public int add(Employee employee) {
        // 1. 系统生成用户名和密码，保存到redis，设置过期时间，短信通知员工，并提醒员工及时修改，限时操作
        String username = CommonUtils.generateRandomUsername(11);
        String plainPassword = ""; // 明文密码
        String password = ""; // DB 中保存的加密密码
        try {
            plainPassword = CommonUtils.generateRandomPassword(6); // 明文密码
            System.out.println("系统生成的密码: " + plainPassword);
            password = CommonUtils.encryptByMD5(plainPassword);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        employee.setUsername(username);
        employee.setPassword(password);
        employee.setCreateTime(new Date());
        employee.setUpdateTime(new Date());
        System.out.println("employee From service: " + employee);

        int insertResult = employeeDao.insert(employee);
        if (insertResult > 0) {
            String smsContent = "小不点提醒您，您已注册成功，可以直接使用该账号登录，系统已为您开通账号，账号: " + username  + " ,临时密码: "+ plainPassword +" ,请妥善保管，并尽快在登录后及时修改密码。";
            Map<String, Object> sendResult = smsService.send(employee.getPhone(), smsContent);

            if (!(Boolean) sendResult.get("flag")) {
                // 记录日志
                System.out.println("员工信息保存成功，账号信息短信发送失败");
            }
        }

        return insertResult;
    }

    @Override
    public int remove(Integer id) {
        return employeeDao.delete(id);
    }

    @Override
    public int edit(Employee employee) {
        return employeeDao.update(employee);
    }

    @Override
    public Employee get(Integer id) {
        return employeeDao.selectById(id);
    }

    @Override
    public List<Employee> getAll() {
        return employeeDao.selectAll();
    }
}
