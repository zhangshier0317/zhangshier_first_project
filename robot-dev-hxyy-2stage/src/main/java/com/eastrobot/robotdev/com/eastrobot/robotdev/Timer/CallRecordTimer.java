package com.eastrobot.robotdev.com.eastrobot.robotdev.Timer;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @dec :   定时任务
 * @Date: 2020/3/6
 * @Auther: pengbo.zhao
 * @version: 1.0
 */
@Component
@PropertySource(value = {"classpath:app.properties"})
public class CallRecordTimer {

    @Value("${taskTimer}")
    public String taskTimer;

    @Scheduled(cron ="${taskTimer}")  //每秒执行一次
    public void callRecordTimer( ){
        System.out.println("定时器被执行了......"+taskTimer);
    }

    /***
     * 1.先查询出当天的总条数
     *
     * 2.每次查询一定量的记录
     *
     * 4.查询完后写文件
     *
     * 5.写完成后上传文件
     *
     */
}
