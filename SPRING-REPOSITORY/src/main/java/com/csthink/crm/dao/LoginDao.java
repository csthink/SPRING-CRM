package com.csthink.crm.dao;

import com.csthink.crm.entity.Employee;
import org.springframework.stereotype.Repository;

@Repository("loginDao")
public interface LoginDao {

    Employee selectByUsername(String username);

    Employee selectByPhone(String phone);
}
