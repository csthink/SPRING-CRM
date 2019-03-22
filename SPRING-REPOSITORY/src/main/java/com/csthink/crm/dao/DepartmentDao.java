package com.csthink.crm.dao;

import com.csthink.crm.entity.Department;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("departmentDao")
public interface DepartmentDao {

    int insert(Department department);

    int delete(Integer id);

    int update(Department department);

    Department selectById(Integer id);

    List<Department> selectAll();

}
