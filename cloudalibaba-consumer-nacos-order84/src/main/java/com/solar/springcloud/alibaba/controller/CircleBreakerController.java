package com.solar.springcloud.alibaba.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.solar.springcloud.alibaba.service.PaymentService;
import com.solar.springcloud.entities.CommonResult;
import com.solar.springcloud.entities.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * CircleBreakerController控制器
 */
@RestController
@Slf4j
public class CircleBreakerController {
    public static final String SERVICE_URL = "http://nacos-payment-provider";

    @Resource
    private RestTemplate restTemplate;

    @GetMapping("/consumer/fallbak/{id}")
    //@SentinelResource(value = "fallback")//没有配置
    //@SentinelResource(value = "fallback",fallback = "handlerFallBack")//fallback只负责业务异常
    //@SentinelResource(value = "fallback",blockHandler = "blockHandler")//blockHandler只负责sentinel控制台配置违规
    @SentinelResource(value = "fallback",blockHandler = "blockHandler",fallback = "handlerFallBack",
            exceptionsToIgnore = {IllegalArgumentException.class,NullPointerException.class})
    public CommonResult<Payment> fallback(@PathVariable("id") Long id){
        CommonResult<Payment> result = restTemplate.getForObject(SERVICE_URL + "/paymentSQL/" + id, CommonResult.class);

        if(id == 4){
            throw new IllegalArgumentException("IllegalArgumentException,非法参数异常");
        }else if(result.getData() == null){
            throw new NullPointerException("NullPointerException,该ID没有对应记录,空指针异常");
        }

        return result;
    }
    //本例是fallback
    public CommonResult handlerFallBack(@PathVariable("id") Long id, Throwable e){
        Payment payment = new Payment(id, "null");
        return new CommonResult(4444, "兜底异常handlerFallBack, exception内容: " + e.getMessage(), payment);
    }

    //本例是blockHandler
    public CommonResult blockHandler(@PathVariable("id") Long id, BlockException blockException){
        Payment payment = new Payment(id, "null");
        return new CommonResult(445, "blockHandler-sentinel限制,无此流水: blockHandler   " + blockException.getMessage());
    }

    //=============OpenFeign
    @Resource
    private PaymentService paymentService;

    @GetMapping("/consumer/openfeign/{id}")
    public CommonResult<Payment> paymentSQL(@PathVariable("id") Long id){
        return paymentService.paymentSQL(id);
    }
}
