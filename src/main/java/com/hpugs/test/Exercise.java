package com.hpugs.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Exercise {

    public static void main(String[] args) {

        String result = checkPwd("zhangs", "kA82123jkjas");
        System.out.println(result);

    }

    private static String checkPwd(String userName, String pwd){
        if(userName == null || pwd == null){
            return "密码不符合要求：用户名或密码为空";
        }

        if(pwd.length() < 8 || pwd.length() > 16){
            return "密码不符合要求：密码长度请控制在8~16个字符";
        }

        String pwdUpperCase = pwd.toUpperCase();
        String userNameUpperCase = userName.toUpperCase();
        if(pwdUpperCase.indexOf(userNameUpperCase) >= 0){
            return "密码不符合要求：不能包含用户名";
        }

        String regex = "(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=]).+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(pwd);
        if (!matcher.matches()) {
            return "密码不符合要求：至少包含一个大写字母、一个小写字母、一个数字和一个特殊字符（如@、#、$等）";
        }

        return "密码符合要求";
    }
}
