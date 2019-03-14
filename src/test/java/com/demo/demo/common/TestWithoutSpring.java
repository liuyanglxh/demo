package com.demo.demo.common;

import com.demo.demo.common.utils.common.CollectionUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestWithoutSpring {


    @Test
    public void test8() throws JsonProcessingException {
        List<Category> categories = new ArrayList<>();
        categories.add(new Category(1, 0));
        categories.add(new Category(2, 1));
        categories.add(new Category(3, 1));
        categories.add(new Category(4, 1));
        categories.add(new Category(5, 2));
        categories.add(new Category(6, 3));
        categories.add(new Category(7, 3));
        categories.add(new Category(8, 3));
        categories.add(new Category(9, 4));
        categories.add(new Category(10, 4));
        categories.add(new Category(11, 0));
        categories.add(new Category(12, 11));
        categories.add(new Category(13, 11));
        categories.add(new Category(14, 12));
        categories.add(new Category(15, 13));

        Collections.shuffle(categories);

        List<Category> tops = CollectionUtil.buildTree(0, categories, Category::getId, Category::getPid, Category::setSubs);

        String str2 = new ObjectMapper().writeValueAsString(tops);
        System.out.println(str2);
    }

    @Test
    public void test9() {
        Pattern scenePattern = Pattern.compile("scene-[0-9]{4}");
        Matcher matcher = scenePattern.matcher("bid>19986|bname>Yum Creamery|ic>|yh>1174576|pf>miniprogram|pgn>local-biz-detail|scene-10811");
        if (matcher.find()) {
            System.out.println(matcher.group(0));
        }
    }

    @Test
    public void test77() throws JsonProcessingException {
        Pattern pattern = Pattern.compile("scene>[0-9]+");
        Matcher matcher = pattern.matcher("bid>19986|bname>Yum Creamery|ic>|yh>1174576|pf>miniprogram|pgn>local-biz-detail|scene>1089");
        if (matcher.find()) {
            System.out.println(matcher.group(0));
        }
    }

    class Category {
        private Integer id;
        private Integer pid;
        private List<Category> subs;

        public Category(Integer id, Integer pid) {
            this.id = id;
            this.pid = pid;
        }

        public Integer getId() {
            return id;
        }

        public Integer getPid() {
            return pid;
        }

        public List<Category> getSubs() {
            return subs;
        }

        public Category setSubs(List<Category> subs) {
            this.subs = subs;
            return this;
        }
    }

    class TTT {

        Integer i1;
        Integer i2;
        Integer i3;

        int count;

        public TTT(Integer i1, Integer i2, Integer i3, int count) {
            this.i1 = i1;
            this.i2 = i2;
            this.i3 = i3;
            this.count = count;
        }

        public Integer getI1() {
            return i1;
        }

        public Integer getI2() {
            return i2;
        }

        public Integer getI3() {
            return i3;
        }

        @Override
        public String toString() {
            return "" + count;
        }
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












