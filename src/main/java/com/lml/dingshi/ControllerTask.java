package com.lml.dingshi;

import com.alibaba.excel.EasyExcel;
import com.lml.dao.BannerDao;
import com.lml.entity.Banner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class ControllerTask {
    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;
    @Autowired
    private BannerDao bannerDao;
    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskExecutor(){
        return new ThreadPoolTaskScheduler();
    }
    public void run(){
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                String fileName = "C:\\Users\\李梦琳\\Desktop\\"+new Date().getTime()+"DemoData.xlsx";
                List<Banner> list=bannerDao.select(new Banner());
                EasyExcel.write(fileName,Banner.class).sheet().doWrite(list);
                System.out.println("定时器工作............");
            }
        };
        threadPoolTaskScheduler.schedule(runnable,new CronTrigger("1 0/3 * * * ?"));
    }
    public void shutdown(){
        threadPoolTaskScheduler.shutdown();
    }
}
