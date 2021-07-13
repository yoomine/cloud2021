package com.solar.springcloud.test;

import java.time.ZonedDateTime;

/**
 * 测试类
 */
public class T {
    public static void main(String[] args) {
        ZonedDateTime zobj = ZonedDateTime.now();
        System.out.println(zobj);
        //2021-06-29T14:22:29.545+08:00[Asia/Shanghai]
    }
}
