package com.lml.aspect;

import com.google.gson.Gson;
import com.lml.annotation.LogAnnotation;
import com.lml.entity.MapAddress;
import com.lml.service.UserService;
import io.goeasy.GoEasy;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

@Aspect
@Configuration
public class LogAspect {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private UserService userService;
    @Around("@annotation(com.lml.annotation.LogAnnotation)")
    public Object addLog(ProceedingJoinPoint proceedingJoinPoint) throws IOException {
        HttpSession session=request.getSession();
        session.setAttribute("admin","admin");
        String admin= (String) session.getAttribute("admin");
        Date date=new Date();
        SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date1=sd.format(date);
        String name=proceedingJoinPoint.getSignature().getName();
        MethodSignature signature= (MethodSignature) proceedingJoinPoint.getSignature();
        LogAnnotation annotation = signature.getMethod().getAnnotation(LogAnnotation.class);
        String value=annotation.value();

        HashOperations<String, Object, Object> stringObjectObjectHashOperations = stringRedisTemplate.opsForHash();
        String realPath=request.getSession().getServletContext().getRealPath("/log");
        File file=new File(realPath);
        if (!file.exists()){
            file.mkdirs();
        }
        File f=new File(realPath,"log.txt");
        Writer writer = new FileWriter(realPath + "/log.txt", true);
        BufferedWriter bw=new BufferedWriter(writer);




        try {
            Object proceed=proceedingJoinPoint.proceed();

            //GoEasy请求实时更改统计图
            Integer day1=userService.queryByDate("男",1);
            Integer day7=userService.queryByDate("男",7);
            Integer day30=userService.queryByDate("男",30);
            Integer day365=userService.queryByDate("男",365);
            Map<String, List<Integer>> map=new HashMap();
            map.put("man", Arrays.asList(day1,day7,day30,day365));
            Integer aday1=userService.queryByDate("女",1);
            Integer aday7=userService.queryByDate("女",7);
            Integer aday30=userService.queryByDate("女",30);
            Integer aday365=userService.queryByDate("女",365);
            map.put("woman", Arrays.asList(aday1,aday7,aday30,aday365));

            List<MapAddress> list=userService.queryByLocation();
            System.out.println("goeasy的数据");
            System.out.println(list);
            GoEasy goEasy=new GoEasy("http://rest-hangzhou.goeasy.io","BC-c3176d1a9bca4e1ab7a4d6a81d7ddc75");
            goEasy.publish("cmfz1", new Gson().toJson(list));



            String status="success";
            System.out.println("添加redis");
            String str=admin+"在"+date1+"做了"+value+status;
            stringObjectObjectHashOperations.put("cmfz",admin+" "+date1,str);
            bw.write(str);
            bw.newLine();
            bw.close();
            writer.close();
            return proceed;
        } catch (Throwable throwable) {
            String status="error";
            String str=admin+"在"+date1+"做了"+value+status;
            stringObjectObjectHashOperations.put("cmfz",admin+" "+date1,str);
            bw.write(str);
            bw.newLine();
            bw.close();
            writer.close();
            throwable.printStackTrace();
            return null;
        }


    }
}
