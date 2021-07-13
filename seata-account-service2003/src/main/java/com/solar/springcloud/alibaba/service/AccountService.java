package com.solar.springcloud.alibaba.service;

import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

public interface AccountService {
    /**
     * 扣减账户余额
     */
    void decrease(@Param("userId") Long userId, @Param("money") BigDecimal money);
}
