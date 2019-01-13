package com.zpkj.exam.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 姚洪强 on 2018/7/26.
 */
public class NumberUtil {
    public static void main(String[] args) {
        //隐藏手机号
        String testPhoneStr = "测试替换手机号15212793109哇哈哈18909801234欧米茄";
        System.out.println("<<<手机号替换前:"+testPhoneStr);
        testPhoneStr  =	hideAllPhoneNum(testPhoneStr);
        System.out.println("<<<手机号替换后:"+testPhoneStr);

        //隐藏身份证号
        String testIDCardStr = "测试替换身份证号34012519920410182X兰博基尼55112519920410182X玛莎拉蒂";
        System.out.println("<<<手机号替换前:"+testIDCardStr);
        testIDCardStr  = hideAllIdCardNum(testIDCardStr);
        System.out.println("<<<手机号替换后:"+testIDCardStr);
    }


    /**
     * 隐藏并替换字符串中所有的手机号
     * @param contentStr
     * @return
     */
    public static String hideAllPhoneNum(String contentStr){
        Pattern pattern = Pattern.compile("((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}");
        Matcher matcher = pattern.matcher(contentStr);
        StringBuffer sb = new StringBuffer();
        try {
            while(matcher.find()) {
                String phoneStr = matcher.group();
                phoneStr = phoneStr.substring(0, 3) + "****" + phoneStr.substring(7, phoneStr.length());
                matcher.appendReplacement(sb,phoneStr);
            }
            matcher.appendTail(sb);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * 隐藏并替换所有的身份证号码
     * @param contentStr
     * @return
     */
    public static String hideAllIdCardNum(String contentStr){
        Pattern pattern = Pattern.compile("(\\d{6})(19|20)(\\d{2})(1[0-2]|0[1-9])(0[1-9]|[1-2][0-9]|3[0-1])(\\d{3})(\\d|X|x)");
        Matcher matcher = pattern.matcher(contentStr);
        StringBuffer sb = new StringBuffer();
        try {
            while(matcher.find()) {
                String idCardStr = matcher.group();
                int len=idCardStr.length();
                if(len>=9){
                    idCardStr =  idCardStr.replaceAll("(.{"+(len<12?3:6)+"})(.*)(.{4})", "$1" + "****" + "$3");
                }
                matcher.appendReplacement(sb,idCardStr);
            }
            matcher.appendTail(sb);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return sb.toString();
    }

}
