package com.demo.demo.common;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestWithoutSpring {

    @Test
    public void test3(){
        BigDecimal n1 = new BigDecimal(1);
        BigDecimal n2 = new BigDecimal(4);
        float f = n1.subtract(n2).divide(n2).floatValue();
        System.out.println(f);
    }

    @Test
    public void test2(){
        String patterm = "[0-9]*";
        Pattern p = Pattern.compile(patterm);
        Matcher matcher = p.matcher("23123123");
        System.out.println(matcher.matches());
    }

    @Test
    public void test1(){
        String screenName = "pageName>cityName>categoryName>countyName>";

        int lastIndex = 0;
        int index = 0;
        index = screenName.indexOf(">");//第一个 > 的位置
        System.out.println(screenName.substring(0, index));
        lastIndex = index;

        //第二个 >
        index = screenName.indexOf(">", index + 1);
        if (index > lastIndex+1){//第一个和第二个 > 的位置相差超过1说明City那个位置有值
            String cityName = screenName.substring(lastIndex + 1, index);
            System.out.println(cityName);
        }
        //第三个 >
        lastIndex = index;
        index = screenName.indexOf(">", index + 1);
        if (index > lastIndex + 1) {

        }

        //第四个 >
        lastIndex = index;
        index = screenName.indexOf(">", index + 1);
        if (index > lastIndex + 1) {
            CharSequence countyName = screenName.subSequence(lastIndex + 1, index);
            System.out.println(countyName);
        }

    }


}












