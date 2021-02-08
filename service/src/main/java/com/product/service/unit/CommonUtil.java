package com.product.service.unit;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtil {

    /**
     * 判断是否为数字
     * @param str
     * @return
     */
    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if(!isNum.matches()){
            return false;
        }
        return true;
    }


    /**
     * 前面补零
     * @param seq 序列
     * @param len 总长度
     * @return
     */
    public static String addZero(Integer seq,int len){
        String s = seq +"";
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0 ; i<len-s.length() ; i++){
            stringBuilder.append("0");
        }
        return stringBuilder.append(seq).toString();
    }


    public static Boolean validInt(Integer i){
        if(i == null || i < 0){
            return false;
        }
        return true;
    }

    public static Boolean validStr(String s){
        if(s == null || s.isEmpty()){
            return false;
        }
        return true;
    }

    /**
     * 获取每月第一天
     * @param y  like 2021
     * @param m  like 2
     * @return
     */
    public static Date getMonthFirstDay(int y ,int m){

        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.YEAR,y);
        calendar.set(Calendar.MONTH,m-1);

        int firstDay = calendar.getMinimum(Calendar.DATE);

        calendar.set(Calendar.DAY_OF_MONTH,firstDay);

        return calendar.getTime();

    }

    public static Date getMonthLastDay(int y ,int m){
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.YEAR,y);
        calendar.set(Calendar.MONTH,m-1);
        int lastDay = calendar.getActualMaximum(Calendar.DATE);
        calendar.set(Calendar.DAY_OF_MONTH,lastDay);
        return calendar.getTime();
    }

}
