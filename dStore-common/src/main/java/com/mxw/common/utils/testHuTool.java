package com.mxw.common.utils;

import cn.hutool.core.collection.BoundedPriorityQueue;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.*;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.lang.TypeReference;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class testHuTool {

    public static void main(String[] args) {

//        int a = 1;
//        //aStr为"1"
//        String aStr = Convert.toStr(a);

//        Object[] a = {"a", "你", "好", "a", 1};
//        List<?> objects = Convert.toList(a);
//        Object[] a = { "a", "你", "好", "", 1 };
//        List<String> list = Convert.convert(new TypeReference<List<String>>() {}, a);
//        for (String object : list) {
//            System.out.print(object+" ");
//        }

//        long s = 4535345;
//
//        //结果为：4535345小时->多少天
//        long minutes = Convert.convertTime(s, TimeUnit.HOURS, TimeUnit.DAYS);
//
//        System.out.println(minutes/365);

        //当前时间字符串，格式：yyyy-MM-dd HH:mm:ss
        String now = DateUtil.now();
        Date date = DateUtil.parse(now);//结果 2017/03/01
        String format = DateUtil.format(date, "yyyy/MM/dd");
        //获得年的部分
        int year = DateUtil.year(date);
        //一天的开始，结果：2017-03-01 00:00:00
        Date beginOfDay = DateUtil.beginOfDay(date);
        //结果：2017-03-03 22:33:23
        Date newDate = DateUtil.offset(date, DateField.DAY_OF_MONTH, 2);
        //上周
        DateTime dateTime = DateUtil.lastWeek();
        //相差一个月，31天
        long betweenDay = DateUtil.between(date, dateTime, DateUnit.DAY);

        // "狗"
        String chineseZodiac = DateUtil.getChineseZodiac(1997);
        // "摩羯座"
        String zodiac = DateUtil.getZodiac(Month.OCTOBER.getValue(), 10);
        //年龄
        int i = DateUtil.ageOfNow("1997-10-11");
        DateTime dateTimea = new DateTime("2017-01-05 12:34:23", DatePattern.PURE_DATETIME_MS_FORMAT);

        System.out.println(now);
        System.out.println(date);
        System.out.println(format);
        System.out.println(year);
        System.out.println(beginOfDay);
        System.out.println(newDate);
        System.out.println(dateTime);
        System.out.println(betweenDay);
        System.out.println(chineseZodiac);
        System.out.println(zodiac);
        System.out.println(i);



        BufferedInputStream in = FileUtil.getInputStream("d:/test.txt");
        BufferedOutputStream out = FileUtil.getOutputStream("d:/test2.txt");
        long copySize = IoUtil.copy(in, out, IoUtil.DEFAULT_BUFFER_SIZE);

        BoundedPriorityQueue<Integer> queue = new BoundedPriorityQueue<Integer>(5);


    }
}
