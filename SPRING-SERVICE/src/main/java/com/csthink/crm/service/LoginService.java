package com.csthink.crm.service;

import com.csthink.crm.entity.Employee;

public interface LoginService {

    /**
     * 根据用户名和密码获取员工对象
     *
     * @param username
     * @param password
     * @return
     */
    Employee loginByUsername(String username, String password);

    /**
     * 根据手机号获取员工对象
     *
     * @param phone
     * @return
     */
    Employee loginByPhone(String phone);

    /**
     *
     * @param id
     * @param password
     * @return
     */
    Boolean changePassword(Integer id, String password);
}
