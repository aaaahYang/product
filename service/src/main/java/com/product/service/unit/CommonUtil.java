package com.product.service.unit;

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


}
