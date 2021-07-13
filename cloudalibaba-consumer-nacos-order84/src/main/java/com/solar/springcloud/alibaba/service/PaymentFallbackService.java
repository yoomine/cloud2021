package com.solar.springcloud.alibaba.service;

import com.solar.springcloud.entities.CommonResult;
import com.solar.springcloud.entities.Payment;
import org.springframework.stereotype.Component;

/**
 * PaymentFallbackService
 */
@Component
public class PaymentFallbackService implements PaymentService{
    @Override
    public CommonResult<Payment> paymentSQL(Long id) {
        return new CommonResult<>(444, "服务降级返回,------PaymentFallbackService", new Payment(id, "erroSerial"));
    }
}
