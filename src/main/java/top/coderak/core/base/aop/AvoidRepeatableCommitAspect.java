package top.coderak.core.base.aop;

import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import top.coderak.core.base.annotation.AvoidRepeatableCommit;
import top.coderak.core.utils.GenerateSequenceUtil;
import top.coderak.core.utils.IpUtil;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * 自定义切入点Aspect
 *
 * @param
 * @author zyh
 * @date 2019/9/8 0008
 * @return
 */
@Aspect
@Component
public class AvoidRepeatableCommitAspect {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * @param point
     */
    @Around("@annotation(top.coderak.core.base.annotation.AvoidRepeatableCommit)")
    public Object around(ProceedingJoinPoint point) throws Throwable {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        String ip = IpUtil.getServerIpAddr(request);

        //获取注解
        MethodSignature signature = (MethodSignature) point.getSignature();

        Method method = signature.getMethod();

        //目标类、方法
        String className = method.getDeclaringClass().getName();

        String name = method.getName();

        String ipKey = String.format("%s#%s", className, name);

        int hashCode = Math.abs(ipKey.hashCode());

        String key = String.format("%s_%d", ip, hashCode);

        AvoidRepeatableCommit avoidRepeatableCommit = method.getAnnotation(AvoidRepeatableCommit.class);

        long timeout = avoidRepeatableCommit.timeout();

        if (timeout < 0) {

            //过期时间3秒

            timeout = 3000;
        }

        System.out.println(redisTemplate.opsForValue().get(key));

        System.out.println(key);

        System.out.println(redisTemplate);

        String value = (String) redisTemplate.opsForValue().get(key);

        if (StringUtils.isNotBlank(value)) {

            System.out.println("重复提交");

            return "请勿重复提交";
        } else {

            System.out.println("提交正常");
        }

        redisTemplate.opsForValue().set(key, GenerateSequenceUtil.generateSequenceNo(), timeout, TimeUnit.MILLISECONDS);

        System.out.println(redisTemplate.opsForValue());

        //执行方法
        Object object = point.proceed();

        return object;
    }
}
