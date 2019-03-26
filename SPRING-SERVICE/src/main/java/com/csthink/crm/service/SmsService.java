package com.csthink.crm.service;

import com.csthink.crm.entity.Sms;

import java.util.List;
import java.util.Map;

public interface SmsService {

    /**
     * 发送短信
     *
     * @param phone 手机号
     * @param content 短信内容
     * @return Map
     */
    Map<String, Object> send(String phone, String content);


    /**
     * 获取指定手机号的短信信息
     *
     * @param phone 手机号
     * @return 短信对象
     */
    Sms get(String phone);

    /**
     * 获取所有的员工信息
     *
     * @return 包含所有员工信息的集合
     */
    List<Sms> getAll();
}
