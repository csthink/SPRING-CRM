package com.csthink.crm.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.csthink.crm.dao.SmsDao;
import com.csthink.crm.entity.Sms;
import com.csthink.crm.service.SmsService;
import com.zhenzi.sms.ZhenziSmsClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service("smsService")
public class SmsServiceImpl implements SmsService {

    private String appId;

    private String appSecret;

    private String apiUrl;

    private ZhenziSmsClient client;

    @Autowired
    private SmsDao smsDao;

    public SmsServiceImpl() {
        Properties properties = this.getProperties();
        this.appId = (String) properties.get("APP_ID");
        this.appSecret = (String) properties.get("APP_SECRET");
        this.apiUrl = (String) properties.get("API_URL");
        client = new ZhenziSmsClient(apiUrl, appId, appSecret);
    }

    /**
     * 发送单条短信
     *
     * @param phone   手机号
     * @param content 短信内容
     * @return
     */
    public Map<String, Object> send(String phone, String content) {
        JSONObject jsonObject;
        boolean flag = false; // 发送是否成功
        String msg; // 发送结果
        int sendStatus; // 发送状态
        Map<String, Object> resultMap = new HashMap<>();

        try {
            // 参数无效
            if (StringUtils.isEmpty(phone) && StringUtils.isEmpty(content)) {
                msg = "参数缺失";
//                logger.info("SMSService -> send 请求参数缺失");
            } else {
                String sendResult = client.send(phone, content); // 单条发送短信
                jsonObject = JSONObject.parseObject(sendResult); // 解析短信发送结果
                int resultCode = jsonObject.getIntValue("code");
//                String data = jsonObject.getString("data");
//                logger.info("phone: " + phone + " content: " + content + " code: " + resultCode + " data: " + data);

                if (resultCode != 0) {
                    sendStatus = 3; // 发送失败
                    msg = "短信发送失败";
                } else {
                    flag = true;
                    sendStatus = 0; // 发送成功
                    msg = "短信发送成功";
                }

                Sms sms = new Sms();
                sms.setPhone(phone);
                sms.setContent(content);
                sms.setSendStatus(sendStatus);
                sms.setSendResult(msg);
                sms.setChannel(1); // 后期待修改，使用配置
                sms.setCreateTime(new Date());

                // 记录短信
                smsDao.insert(sms);
            }

            resultMap.put("flag", flag);
            resultMap.put("msg", msg);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultMap;
    }

    @Override
    public Sms get(String phone) {
        return smsDao.selectByPhone(phone);
    }

    @Override
    public List<Sms> getAll() {
        return smsDao.selectAll();
    }


    /**
     * 读取properties配置方式一 >>> 基于ClassLoader读取配置文件
     * 优点: 该方式只能读取类路径下的配置文件
     */
    private Properties getProperties() {
        Properties properties = new Properties();
        InputStream is = null;
        InputStreamReader isr = null;

        try {
            is = this.getClass().getClassLoader().getResourceAsStream("sms.properties");
            isr = new InputStreamReader(is, StandardCharsets.UTF_8); // 解决中文乱码
            properties.load(isr);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != isr) {
                    isr.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (null != is) {
                        is.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return properties;
    }


    /**
     * 读取properties配置方式二 >>> 基于 InputStream 读取配置文件
     * 优点: 可以读取任意路径下的配置文件
     */
    /*
    private void getProperties() {
        Properties properties = new Properties();
        String basePath = System.getProperty("user.dir");
        String propertiesPath = basePath + File.separator + "Common" + File.separator + "src"
                + File.separator + "main" + File.separator + "resources" + File.separator + "sms.properties";

        FileInputStream fis = null;
        try {
            // 使用 InPutStream 流读取properties文件
            fis = new FileInputStream(propertiesPath);
            properties.load(fis);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != fis) {
                    fis.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return properties;
    }
    */
}
