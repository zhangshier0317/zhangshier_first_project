package com.eastrobot.robotdev.com.eastrobot.robotdev.controller;

import com.eastrobot.robotdev.com.eastrobot.robotdev.bean.User;
import com.eastrobot.robotdev.com.eastrobot.robotdev.utils.RobotUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @dec : 全局处理器
 * @Date: 2020/3/5
 * @Auther: pengbo.zhao
 * @version: 1.0
 * @demand:
 */
@ControllerAdvice
public class GlobalController {


    @ModelAttribute(name="invokerStartTime")
    public LocalDateTime globalBindData() {
        System.out.println("startTime ... 被调用了");
        return LocalDateTime.now();
    }

    @ModelAttribute(name="invokerUser")
    public User globalBindUser() {

        User user = new User();

        user.setSerialNo(LocalDateTime.now().getNano()+"");
        user.setClientName("张三");
        user.setClientSex("1");
        user.setClientBirthday("1990-01-01");
        user.setCertificateNo("110111199001011234");
        user.setProductType("0011");
        user.setApplicationDate("2020-03-03");
        user.setMobile("138111111111");
        user.setUnitName("华夏银行信用卡");
        user.setUnitAddress("石景山路万达广场");
        user.setUnitTelephone("010-400-9785");
        user.setHomeAddress("北京市石景山区老山街道西区71号楼501室");

        return user;
    }


}
