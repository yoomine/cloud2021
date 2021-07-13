package com.solar.springcloud.alibaba.controller;

import com.solar.springcloud.alibaba.domain.CommonResult;
import com.solar.springcloud.alibaba.service.StorageService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * StorageController控制器
 */
@RestController
public class StorageController {
    @Resource
    private StorageService storageService;

    /**
     * 扣减kucun
     */
    @RequestMapping("/storage/decrease")
    public CommonResult decrease(Long productId, Integer count) {
        storageService.decrease(productId, count);
        return new CommonResult(200,"扣减库存成功");
    }
}
