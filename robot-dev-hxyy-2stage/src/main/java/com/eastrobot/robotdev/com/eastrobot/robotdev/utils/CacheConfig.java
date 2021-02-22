package com.eastrobot.robotdev.com.eastrobot.robotdev.utils;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @dec :
 * @Date: 2020/3/10
 * @Auther: pengbo.zhao
 * @version: 1.0
 */
public class CacheConfig {

    private static final Logger LOG = LoggerFactory.getLogger(CacheConfig.class);



    private CacheConfig(){

    }

    public static final Cache<String,String> cache = CacheBuilder.newBuilder()

                                            .concurrencyLevel(Runtime.getRuntime().availableProcessors()*2)
                                            .initialCapacity(20000)
                                            .maximumSize(18000)
                                            .expireAfterWrite(20, TimeUnit.HOURS)	//创建缓存后，5分钟失效
                                            .removalListener(new RemovalListener<String, String>() {
                                                @Override
                                                public void onRemoval(RemovalNotification<String,String> notification) {
                                                    LOG.info(notification.getKey()+" - "+notification.getValue()+" is remove");
                                                }
                                            }).build();
}
