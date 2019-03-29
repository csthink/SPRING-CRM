package com.csthink.crm.service;

import com.csthink.crm.entity.Log;

import java.util.List;

public interface LogService {

    void addSystemLog(Log log);

    void addLoginLog(Log log);

    void addOperationLog(Log log);

    List<Log> getSystemLog();

    List<Log> getLoginLog();

    List<Log> getOperationLog();

}
