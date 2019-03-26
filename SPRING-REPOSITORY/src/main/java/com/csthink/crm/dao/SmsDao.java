package com.csthink.crm.dao;

import com.csthink.crm.entity.Sms;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("smsDao")
public interface SmsDao {

    int insert(Sms sms);

    Sms selectByPhone(String phone);

    List<Sms> selectAll();

}
