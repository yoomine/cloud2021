package com.solar.springcloud.alibaba.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * StorageDao
 */
@Mapper
public interface StorageDao {
    //扣减kucun
    void decrease(@Param("productId") Long productId, @Param("count") Integer count);
}
