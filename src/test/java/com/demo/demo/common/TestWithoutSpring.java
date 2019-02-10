package com.demo.demo.common;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestWithoutSpring {

    @Test
    public void test8() {
        Pattern pattern = Pattern.compile("[0-9]+");
        System.out.println(pattern.matcher("").matches());
    }

    @Test
    public void test7() {
        String regex = "[0-9]+";

        String name = "【修改点赞数】：「+0」→「+11」";

        Matcher matcher = Pattern.compile(regex).matcher(name);
        if (matcher.find()) {
            System.out.println(matcher.group());
        } else {
            System.out.println("nothing found");
        }
    }

    @Test
    public void test6() {
        String regex = "name>(.*?)>";
        Pattern pattern = Pattern.compile(regex);

        String str1 = "name>liuyang>";
        Matcher matcher1 = pattern.matcher(str1);
        while (matcher1.find()) {
            System.out.println(matcher1.group(1));
        }
    }

    @Test
    public void test5() {
        String returnXml = "<resultdescription>单据  16613dd7d9a00000000000000000000vouchergl0  开始处理...单据  16613dd7d9a00000000000000000000vouchergl0  处理完毕!"
                + "</resultdescription>"
                + "<content>2018.09-记账凭证-5</content>"
                + "<billpk></billpk><bdocid>16613dd7d9a00000000000000000000vouchergl0</bdocid>"
                + "<filename>vouchergld861102.xml</filename><resultcode>1</resultcode>"
                + "<resultdescription>单据  16613dd7d9a00000000000000000000vouchergl0  开始处理...单据  16613dd7d9a00000000000000000000vouchergl0  处理完毕!"
                + "</resultdescription>"
                + "<content>2018.09-记账凭证-6</content></sendresult>";
        String regex = "<content>(.*?)</content>";
        List<String> list = new ArrayList<>();
//        List<String> extvounoLists = new ArrayList<>();
        Pattern pattern = Pattern.compile(regex);
        Matcher m = pattern.matcher(returnXml);
        while (m.find()) {
            list.add(m.group(1));
        }
        for (String str : list) {
            System.out.println(str);
//            String[] strs = str.split("-");
//            extvounoLists.add(strs[strs.length - 1]);
        }

//        System.out.println("---------------");
//        for (String string : extvounoLists) {
//            System.out.println(string);
//        }
    }

    @Test
    public void test4() {
        String regex = "[0-9]*";

        Pattern compile = Pattern.compile(regex);

        String name1 = "未命名";

        Matcher matcher1 = compile.matcher(name1);
        if (matcher1.find()) {
            System.out.println(matcher1.group());
        }

        String name2 = "未命名2";
        Matcher matcher2 = compile.matcher(name2);
        if (matcher2.find()) {
            System.out.println(matcher2.group());
        }

    }

    @Test
    public void test3() {

        String regex = "\\w+";
        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher("Evening is full of the linnet's wings");

        while (matcher.find()) {
            System.out.println(matcher.group());
        }

    }


}












