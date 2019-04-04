package com.zjf.controller;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import com.zjf.service.RobbinService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: zhangjunfeng
 * @Email: junfeng1987@163.com
 * @Description:
 * @Date: Created on 2019/3/27 16:52
 */
@RestController
public class RobbinController {
    private final Logger logger = LoggerFactory.getLogger(RobbinController.class);
    @Autowired
    private RobbinService service;

    @GetMapping(value = "/test")
    public String testCustomer(){
        return service.testServer();
    }

    @GetMapping(value = "/test-cache")
    public void testCache(){
        //初始化Hystrix请求上下文
        HystrixRequestContext.initializeContext();
        //开启请求缓存并测试两次
        service.openCacheByExtends();
        //清除缓存
        service.clearCacheByExtends();
        //再次开启请求缓存并测试两次
        service.openCacheByExtends();
    }

    /**
     * 注解方式请求缓存，第二种
     */
    @GetMapping("/test-cache2")
    public void openCacheByAnnotation2(){
        //初始化Hystrix请求上下文
        HystrixRequestContext.initializeContext();
        //访问并开启缓存
        Integer result1 = service.openCacheByAnnotation2(2L);
        Integer result2 = service.openCacheByAnnotation2(2L);
        logger.info("first request result is:{} ,and secend request result is: {}", result1, result2);
        //清除缓存
        service.flushCacheByAnnotation2(2L);
        //再一次访问并开启缓存
        Integer result3 = service.openCacheByAnnotation2(2L);
        Integer result4 = service.openCacheByAnnotation2(2L);
        logger.info("first request result is:{} ,and secend request result is: {}", result3, result4);
    }
}
