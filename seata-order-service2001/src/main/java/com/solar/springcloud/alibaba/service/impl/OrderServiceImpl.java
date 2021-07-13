package com.solar.springcloud.alibaba.service.impl;

import com.solar.springcloud.alibaba.dao.OrderDao;
import com.solar.springcloud.alibaba.domain.Order;
import com.solar.springcloud.alibaba.service.AccountService;
import com.solar.springcloud.alibaba.service.OrderService;
import com.solar.springcloud.alibaba.service.StorageService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * OrderServiceImpl
 * 创建订单-->调用库存服务扣减库存->调用账户服务扣减账户余额->修改订单状态
 * 简单说：
 * 下订单->减库存->减余额->该状态
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Resource
    private OrderDao orderDao;
    @Resource
    private StorageService storageService;
    @Resource
    private AccountService accountService;

    @Override
    @GlobalTransactional(name = "fsp-create-order", rollbackFor = Exception.class)
    public void create(Order order) {
        log.info("---------->开始新建订单");

        //1 创建订单
        orderDao.create(order);

        log.info("------------->订单微服务开始调用库存,做扣减");
        //2 扣减库存
        storageService.decrease(order.getProductId(), order.getCount());
        log.info("------------->订单微服务开始调用库存,做扣减end");

        log.info("------->订单微服务开始调用账号,做扣减Money");
        //3 扣减账户
        accountService.decrease(order.getUserId(), order.getMoney());
        log.info("------->订单微服务开始调用账号,做扣减end");

        //4 修改订单状态, 从0到1, 1代表已完成
        log.info("---------->修改订单状态开始");
        orderDao.update(order.getUserId(),0);
        log.info("---------->修改订单状态结束");

        log.info("---------->下订单结束了,O(∩_∩)O哈哈~~");
    }
}
