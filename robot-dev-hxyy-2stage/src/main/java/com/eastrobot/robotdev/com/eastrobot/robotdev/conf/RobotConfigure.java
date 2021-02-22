package com.eastrobot.robotdev.com.eastrobot.robotdev.conf;

import com.eastrobot.robotdev.com.eastrobot.robotdev.utils.CacheConfig;
import com.google.common.cache.Cache;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @dec : 配置文件类
 * @Date: 2020/3/6
 * @Auther: pengbo.zhao
 * @version: 1.0
 */

@Configuration
@PropertySource(value = {"classpath:app.properties"})
public class RobotConfigure {

    @Value("${queryInfoURL}")
    public static String queryInfo_URL;

    @Value("${callResultURL}")
    public static String callResult_URL;

    @Value("${uploadURL}")
    public static String upload_URL;



    @Bean(name = "hxCache")
    public Cache cache(){
        return CacheConfig.cache;
    }

    @Qualifier("hxCache")
    public Cache HXCache;


}
