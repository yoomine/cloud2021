package com.solar.springcloud.alibaba.controller;

import com.solar.springcloud.alibaba.domain.CommonResult;
import com.solar.springcloud.alibaba.domain.Order;
import com.solar.springcloud.alibaba.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * OrderController控制器
 */
@RestController
public class OrderController {
    @Resource
    private OrderService orderService;

    @GetMapping("/order/create")
    public CommonResult create(Order order){
        orderService.create(order);
        return new CommonResult(200,"订单创建成功");
    }
}
