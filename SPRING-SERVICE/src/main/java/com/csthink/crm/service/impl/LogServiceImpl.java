package com.csthink.crm.service.impl;

import com.csthink.crm.dao.LogDao;
import com.csthink.crm.entity.Log;
import com.csthink.crm.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("logService")
public class LogServiceImpl implements LogService {

    private String SYSTEM_LOG = "系统";
    private String LOGIN_LOG = "登录";
    private String OPERATION_LOG = "操作";

    @Autowired
    private LogDao logDao;

    @Override
    public void addSystemLog(Log log) {
        log.setLogType(SYSTEM_LOG);
        log.setCreateTime(new Date());
        logDao.insert(log);
    }

    @Override
    public void addLoginLog(Log log) {
        log.setLogType(LOGIN_LOG);
        log.setCreateTime(new Date());
        logDao.insert(log);
    }

    @Override
    public void addOperationLog(Log log) {
        log.setLogType(OPERATION_LOG);
        log.setCreateTime(new Date());
        logDao.insert(log);
    }

    @Override
    public List<Log> getSystemLog() {
        return logDao.selectByType(SYSTEM_LOG);
    }

    @Override
    public List<Log> getLoginLog() {
        return logDao.selectByType(LOGIN_LOG);
    }

    @Override
    public List<Log> getOperationLog() {
        return logDao.selectByType(OPERATION_LOG);
    }
}
