package com.csthink.crm.global.aspect;

import com.csthink.crm.entity.Employee;
import com.csthink.crm.entity.Log;
import com.csthink.crm.service.LogService;
import com.csthink.crm.utils.AccessUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 日志切面
 */
@Component
@Aspect
public class LogAdvice {

    @Autowired
    private LogService logService;

    @AfterReturning(pointcut = "execution(* com.csthink.crm.controller.*.*(..)) && ! execution(* com.csthink.crm.controller.LoginController.*(..)))", returning = "returnValue")
    public void operationLog(JoinPoint joinPoint, Object returnValue) {
        Log log = log(joinPoint, "");
        log.setRequestResult("操作成功");
        logService.addOperationLog(log);
    }

    @AfterThrowing(pointcut = "execution(* com.csthink.crm.controller.*.*(..))", throwing = "e")
    public void systemLog(JoinPoint joinPoint, Throwable e) {
        Log log = log(joinPoint, "");
        log.setRequestResult(e.getClass().getSimpleName());
        logService.addSystemLog(log);
    }

    @After("execution(* com.csthink.crm.controller.LoginController.login(..))")
    public void loginLog(JoinPoint joinPoint) {
        Log log = log(joinPoint, "登入操作");
        logService.addLoginLog(log);
    }

    @Before("execution(* com.csthink.crm.controller.LoginController.logout(..))")
    public void logoutLog(JoinPoint joinPoint) {
        Log log = log(joinPoint, "登出操作");
        logService.addLoginLog(log);
    }

    private Log log(JoinPoint joinPoint, String actionDesc) {
        HttpServletRequest request = (HttpServletRequest) joinPoint.getArgs()[0];
        HttpSession session = request.getSession();
        Object obj = session.getAttribute("USER");
        Employee employee = (Employee) obj;

        Log log = new Log();
        if (null == employee) {
            log.setOperator(request.getParameter("username")); // 操作人员
            log.setRequestResult(actionDesc + "失败");
        } else {
            log.setOperator(employee.getUsername()); // 操作人员
            log.setRequestResult(actionDesc + "成功");
        }

        log.setModule(joinPoint.getTarget().getClass().getSimpleName()); // 操作模块
        log.setAction(joinPoint.getSignature().getName()); // 操作行为
        log.setRequestIp(AccessUtils.getIpAddress(request)); // 客户端IP

        return log;
    }

}
