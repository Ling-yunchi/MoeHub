package tv.moehub.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.Objects;

/**
 * @author wangrong
 * @date 2022/6/22 9:34
 */
@Order(1)
@Aspect
@Slf4j
@Component
public class LogAspect {
    @Around("execution(* tv.moehub.controller.*.*(..))")
    public Object webProcess(ProceedingJoinPoint point) throws Throwable {
        //正在被通知的方法相关信息
        MethodSignature signature = (MethodSignature) point.getSignature();
        //获取被拦截的方法
        Method method = signature.getMethod();
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String url = request.getRequestURL().toString();
        Enumeration<String> list = request.getParameterNames();
        String attr;
        String temp;
        StringBuilder cont = new StringBuilder();
        while (list.hasMoreElements()) {
            attr = list.nextElement();
            temp = request.getParameter(attr);
            cont.append(attr).append("=").append(temp).append(';');
        }
        String params = cont.toString();
        log.info("-----------------------------------------------------");
        log.info("请求地址：{}", url);
        log.info("请求方法：{}", method.getName());
        log.info("请求参数：{}", params);
        Object result;
        try {
            result = point.proceed();
        } catch (Throwable throwable) {
            log.error("请求异常：{}", getRealMessage(throwable));
            log.info("-----------------------------------------------------");
            throw throwable;
        }
        ObjectMapper mapper = new ObjectMapper();
        log.info("返回结果：{}", mapper.writeValueAsString(result));
        log.info("-----------------------------------------------------");
        return result;
    }

    public static String getRealMessage(Throwable e) {
        Throwable c = e.getCause();
        if (c != null) {
            return getRealMessage(c);
        }
        return e.getMessage();
    }
}