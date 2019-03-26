package com.csthink.crm.service.impl;

import com.csthink.crm.dao.DepartmentDao;
import com.csthink.crm.entity.Department;
import com.csthink.crm.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("departmentService")
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentDao departmentDao;

    @Override
    public int add(Department department) {
        return departmentDao.insert(department);
    }

    @Override
    public int remove(Integer id) {
        return departmentDao.delete(id);
    }

    @Override
    public int edit(Department department) {
        return departmentDao.update(department);
    }

    @Override
    public Department get(Integer id) {
        return departmentDao.selectById(id);
    }

    @Override
    public List<Department> getAll() {
        return departmentDao.selectAll();
    }
}
