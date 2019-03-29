package com.csthink.crm.dao;

import com.csthink.crm.entity.Log;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("logDao")
public interface LogDao {

    int insert(Log log);

    List<Log> selectByType(String logType);

}
