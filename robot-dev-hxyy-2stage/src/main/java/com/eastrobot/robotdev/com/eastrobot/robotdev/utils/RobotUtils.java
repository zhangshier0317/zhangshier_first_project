package com.eastrobot.robotdev.com.eastrobot.robotdev.utils;

import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * @dec : 工具类
 * @Date: 2020/3/5
 * @Auther: pengbo.zhao
 * @version: 1.0
 * @demand:
 */
@Component
public class RobotUtils {

    /**
     * 度量时间区间
     * @param startTime 时 分 秒
     * @param endTime 时 分 秒
     * @return
     */
    public long DurationSecond(LocalDateTime startTime, LocalDateTime endTime) {
        if(startTime == null) {
            return 0L;
        }else {
            return Duration.between(startTime, endTime).toMillis();
        }
    }

}
