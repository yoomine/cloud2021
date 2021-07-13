package com.solor.springcloud.alibaba.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * FlowLimitController控制器
 */
@RestController
@Slf4j
public class FlowLimitController {

    @GetMapping("/testA")
    public String testA(){
        try {
            TimeUnit.MILLISECONDS.sleep(800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("-----testA 线程："+Thread.currentThread().getName());
        return Thread.currentThread().getName()+"\t--------testA";
    }

    @GetMapping("/testB")
    public String testB(){
        log.info("-----testB 线程："+Thread.currentThread().getName());
        return Thread.currentThread().getName()+"\t---------testB";
    }

    /**
     * 测试RT
     * QPS>=5并且平均响应时间超过阈值,触发断路器打开
     */
    @GetMapping("/testD")
    public String testD(){
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("-----testD 线程："+Thread.currentThread().getName());
        return Thread.currentThread().getName()+"\t---------testD";
    }

    /**
     * 测试异常比例
     * QPS>=5并且异常比例超过阈值,触发断路器打开,进而服务熔断,经过设置的时间窗口期,断路器关闭,恢复服务正常调用
     * 异常比例取值范围0~1,0.2表示20%
     */
    @GetMapping("/testE")
    public String testE(){
        log.info("-----testE 测试异常比例 线程："+Thread.currentThread().getName());
        int a = 10/0;
        return Thread.currentThread().getName()+"\t---------testE";
    }

    /**
     * 测试异常数
     * 异常数按分钟统计，时间窗口设置要超过1分钟
     * 分钟内的异常数超过阈值触发熔断
     */
    @GetMapping("/testF")
    public String testF(){
        log.info("-----testF 测试异常比例 线程："+Thread.currentThread().getName());
        int a = 10/0;
        return Thread.currentThread().getName()+"\t---------testF";
    }

    @GetMapping("/testHotKey")
    @SentinelResource(value = "testHotKey",blockHandler = "deal_testHotKey")
    public String testHotKey(@RequestParam(value = "p1",required = false) String p1,
                             @RequestParam(value = "p2",required = false) String p2){
        //int a = 10/0;
        return "-----------testHotKey";
    }

    public String deal_testHotKey(String p1, String p2, BlockException blockException){
        return "---------deal_testHotKey,/(ㄒoㄒ)/~~";    //sentinel系统默认提示：Blocked by Sentinel (flow limiting)
    }
}
