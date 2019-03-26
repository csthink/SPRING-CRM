package com.csthink.crm.dao;

import com.csthink.crm.entity.Employee;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("employeeDao")
public interface EmployeeDao {

    int insert(Employee employee);

    int delete(Integer id);

    int update(Employee employee);

    Employee selectById(Integer id);

    List<Employee> selectAll();

}
