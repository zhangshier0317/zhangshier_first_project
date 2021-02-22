package com.eastrobot.robotdev.com.eastrobot.robotdev.thread;

/**
 * @dec :
 * @Date: 2020/3/10
 * @Auther: pengbo.zhao
 * @version: 1.0
 */
public enum EnumTest {

    RED(1,"red"), GREEN(2,"green"),BLACK(3,"black"), YELLO(4,"yello");

    private String color;

    private int index;

    private EnumTest(int index,String color){
        this.index = index;
        this.color = color;
    }

    //普通方法
    public static String Color(int index){
        for (EnumTest enumTest : EnumTest.values()){
            if(enumTest.getIndex() == index){
                return enumTest.getColor();
            }
        }
        return null;
    }

    public String getColor() {
        return color;
    }

    public int getIndex() {
        return index;
    }
}
