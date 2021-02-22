package com.eastrobot.robotdev.com.eastrobot.robotdev.thread;

import java.util.Collection;
import java.util.concurrent.*;

/**
 * @dec :
 * @Date: 2020/3/8
 * @Auther: pengbo.zhao
 * @version: 1.0
 */
public class VolatileTest {

    public static void main(String[] args) {
        try {
            String color = EnumTest.Color(1);
            System.out.println("color = " + color);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
