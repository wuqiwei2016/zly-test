package com.zpkj.exam.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**组织机构代码匹配
 * Created by wuqiw on 2018/6/4.
 */
public class PidUtil {

    public static Boolean validatePid(String pid){
        boolean flag = false;
        String regex = "\\w{8}-\\w{1}";
        Pattern p=Pattern.compile(regex);
        Matcher m=p.matcher(pid);
        if(m.matches()){
            flag = true;
        }else{
            flag = false;
        }
        return flag;
    }
}
