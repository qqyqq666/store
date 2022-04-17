package com.example.store.aop;



import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @author JlX
 * @create 2022-04-15 20:13
 */
@Aspect  //将当前类标记为切面类
@Component  //将当前类的对象创建使用维护交由Spring容器维护
public class TimerAspect {
    //将当前环绕通知映射到某个页面上(指定连接点)
    @Around("execution(* com.example.store.service.impl.*.*(..))")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        // 记录起始时间
        long start = System.currentTimeMillis();
        // 执行连接点方法，即切面所在位置对应的方法。本项目中表示执行注册或执行登录等
        Object result = pjp.proceed();//调用目标方法
        // 记录结束时间
        long end = System.currentTimeMillis();
        // 计算耗时
        System.err.println("耗时：" + (end - start) + "ms.");
        // 返回连接点方法的返回值
        return result;
    }
}
