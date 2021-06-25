package com.solar.springcloud.lb;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 自定义负责均衡策略
 */
@Component
@Slf4j
public class MyLB implements LoadBalancer{
    private AtomicInteger atomicInteger = new AtomicInteger(0);

    public final int getAndIncrement(){
        int current;
        int next;
        do {
            current = atomicInteger.get();
            next = current + 1;
        } while (!this.atomicInteger.compareAndSet(current, next));
        log.info("第几次访问,次数next: " + next);
        return next;
    }

    @Override
    public ServiceInstance instances(List<ServiceInstance> serviceInstances) {
        if(serviceInstances == null){
            return null;
        }
        //轮询策略
        int index = getAndIncrement() % serviceInstances.size();
        //随机策略
        //int index = new Random().nextInt(serviceInstances.size());
        log.info("***********index: "+index);
        return serviceInstances.get(index);
    }
}
