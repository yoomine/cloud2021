package com.solor.springcloud.alibaba.myhandler;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.solar.springcloud.entities.CommonResult;
import com.solar.springcloud.entities.Payment;

public class CustomerBlockHandler {
    public static CommonResult handlerException(BlockException blockException){
        return new CommonResult(444444,"按客户自定义, global handlerException--------1");
    }

    public static CommonResult handlerException2(BlockException blockException){
        return new CommonResult(444444,"按客户自定义, global handlerException-----------2");
    }

}
