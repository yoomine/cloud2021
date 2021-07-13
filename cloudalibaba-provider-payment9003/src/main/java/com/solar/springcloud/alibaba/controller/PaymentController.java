package com.solar.springcloud.alibaba.controller;

import com.solar.springcloud.entities.CommonResult;
import com.solar.springcloud.entities.Payment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * PaymentController控制器
 */
@RestController
public class PaymentController {
    @Value("${server.port}")
    private String serverPort;

    public static Map<Long, Payment> hashMap = new HashMap<>();

    static {
        hashMap.put(1L, new Payment(1L, "b84b8d8f-9853-487d-bcc4-359ea1416c8f"));
        hashMap.put(2L, new Payment(2L, "f0517e3b-d642-4734-874a-15bbd455f93e"));
        hashMap.put(3L, new Payment(3L, "baf802cd-5411-4b9c-8e3a-d3275625d67c"));
    }

    @GetMapping(value = "/paymentSQL/{id}")
    public CommonResult<Payment> paymentSQL(@PathVariable("id") Long id){
        Payment payment = hashMap.get(id);
        CommonResult<Payment> result = new CommonResult<>(200, "from mysql,serverPort: " + serverPort,payment);
        return result;
    }
}
