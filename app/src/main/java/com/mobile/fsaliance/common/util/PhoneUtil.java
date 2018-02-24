package com.mobile.fsaliance.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;


/**
 * @author yuanxueyuan
 * @Title: PhoneUtil
 * @Description: 检验手机号码
 * @date 2018/2/24  10:09
 */
public class PhoneUtil {

    /**
     * @param str 手机号码
     * @author yuanxueyuan
     * @Title: checkPhoneNum
     * @Description: 检验是否为手机号码
     * @date 2018/2/24 10:10
     */
    public static boolean checkPhoneNum(String str) throws PatternSyntaxException {
        String regExp = "^((13[0-9])|(15[^4,\\D])|(18[0-9])|(14[57])|(17[013678]))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }
}
