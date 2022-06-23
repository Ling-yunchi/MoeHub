package tv.moehub.aop;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import tv.moehub.annotation.Login;
import tv.moehub.model.BaseResult;

/**
 * @author wangrong
 * @date 2022/6/23 9:57
 */
@Aspect
@Component
@Slf4j
public class LoginAspect {
    @Pointcut("@annotation(tv.moehub.annotation.Login)")
    public void loginPointcut() {
    }

    @Around("loginPointcut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        log.info("==登录拦截==");
        var signature = (MethodSignature) point.getSignature();
        var method = signature.getMethod();
        var login = method.getAnnotation(Login.class);
        var required = login.required();
        if (required) {
            var subject = SecurityUtils.getSubject();
            if (!subject.isAuthenticated()) {
                log.info("未登录");
                return new BaseResult<Void>(false, "未登录,请先登录");
            }
        }
        return point.proceed();
    }
}
