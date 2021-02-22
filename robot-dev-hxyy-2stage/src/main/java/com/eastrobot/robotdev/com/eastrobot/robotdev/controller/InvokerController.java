package com.eastrobot.robotdev.com.eastrobot.robotdev.controller;

import com.eastrobot.robotdev.com.eastrobot.robotdev.bean.User;
import com.eastrobot.robotdev.com.eastrobot.robotdev.conf.RobotConfigure;
import com.eastrobot.robotdev.com.eastrobot.robotdev.utils.HttpUtils;
import com.eastrobot.robotdev.com.eastrobot.robotdev.utils.RobotUtils;
import com.google.common.base.Strings;
import com.google.common.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.time.LocalDateTime;

/**
 * @dec : IvnokerController
 * @Date: 2020/3/5
 * @Auther: pengbo.zhao
 * @version: 1.0
 * @demand:
 *
 *  {@link #getUserInfo(String, LocalDateTime,User)}
 *
 *  {@link #callResult(String, String, String)}
 *
 *
 */

@RestController
@ResponseBody
@PropertySource(value = {"classpath:app.properties"})
public class InvokerController {

    private static final Logger LOG = LoggerFactory.getLogger(InvokerController.class);

    @Autowired
    private HttpUtils httpUtils;

    @Autowired
    private RobotUtils robotUtils;

    @Autowired
    private RobotConfigure robotConfigure;

    @Autowired
    private Cache HXCache;;

    /*
     * 获取userInfo信息
     * @param  mobile 传入的手机号
     * @param  调用该方法的起始时间
     * @return 
     * @throws
     */
    @ModelAttribute(name="invokerStartTime")
    @GetMapping("/getUserInfo/{mobile}")
    public JSONObject getUserInfo(@PathVariable("mobile") String mobile,
                                  @ModelAttribute(name="invokerStartTime")LocalDateTime startTime,
                                  @ModelAttribute(name="invokerUser")User invokerUser){
        JSONObject jsonObject = null;
        
        try{
            HXCache.put("k1","v1");
            String result = httpUtils.sendGet(robotConfigure.queryInfo_URL + "mobile="+mobile);
            System.err.println("cache ......"+HXCache.getIfPresent("k1"));
            if(!Strings.isNullOrEmpty(result) && !"-1".equals(result)){
                return new JSONObject(result);
            }else{
                return new JSONObject(invokerUser.toString());//测试数据
            }

        }catch (Exception e){
            LOG.error("xxxxxxxxxx get user Info error ",e);
        }

        LOG.debug("<------ get user info response {}",jsonObject);
        LOG.info("<--------- 调用 {} 耗时 {} ms", robotConfigure.queryInfo_URL,robotUtils.DurationSecond(startTime, LocalDateTime.now()));
        return jsonObject;
    }
    
    /*
     * 通话结果反馈
     * @param   mobile          手机号
     * @param   outCallResult   1-承诺申请、2-拒绝申请、3-不是本人、4-身份证错误、 5-住宅地址错误、6-其它
     * @param   outCallRemark   是否还款的关键话述
     * @return
     * @throws
     */
    @GetMapping("/getUserInfo/{mobile}/{outCallResult}/{outCallRemark}")
    private void callResult(String mobile,String outCallResult,String outCallRemark){

        if(!Strings.isNullOrEmpty(mobile) && !Strings.isNullOrEmpty(outCallResult)
                && !Strings.isNullOrEmpty(outCallRemark)){
            httpUtils.sendGet(robotConfigure.callResult_URL + mobile + outCallResult + URLEncoder.encode(outCallRemark));
        }
    }


}
