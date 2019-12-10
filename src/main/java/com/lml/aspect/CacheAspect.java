package com.lml.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Aspect
@Configuration
public class CacheAspect {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Around("@annotation(com.lml.annotation.AddCache)")
    public Object addLog(ProceedingJoinPoint joinPoint) throws Throwable {
        String clazzName = joinPoint.getTarget().getClass().getName();//获取类名
        String methodName = joinPoint.getSignature().getName();//获取方法名
        Object[] args = joinPoint.getArgs();//获取参数
        String arg = Arrays.toString(args);//参数拼接的字符串
        String key = methodName + "," + arg;//redis中的key  方法名+参数
        stringRedisTemplate.setKeySerializer(new StringRedisSerializer());
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        stringRedisTemplate.setHashKeySerializer(new StringRedisSerializer());
        stringRedisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        HashOperations<String, Object, Object> hash = stringRedisTemplate.opsForHash();

        Object o = hash.get(clazzName, key);
        if (o != null) {
            System.out.println("从redis中获取的数据");
            return o;
        }
        System.out.println("从数据库查的数据");
        Object proceed = joinPoint.proceed();
        hash.put(clazzName, key, proceed);
        return proceed;
    }

    @Around("@annotation(com.lml.annotation.DelCache)")
    public Object delLog(ProceedingJoinPoint joinPoint) throws Throwable {
        Object proceed = joinPoint.proceed();
        String clazzName = joinPoint.getTarget().getClass().getName();//获取类名
        stringRedisTemplate.delete(clazzName);
        return proceed;
    }

    ;
}
