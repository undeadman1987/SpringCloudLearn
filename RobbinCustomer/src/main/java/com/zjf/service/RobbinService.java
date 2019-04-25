package com.zjf.service;

import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheKey;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheRemove;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;
import com.zjf.command.CacheCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @Author: zhangjunfeng
 * @Email: junfeng1987@163.com
 * @Description:
 * @Date: Created on 2019/3/28 9:14
 */
@Service
public class RobbinService {

    private final Logger logger = LoggerFactory.getLogger(RobbinService.class);

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "testServerFallBack")
    public String testServer(){
        return restTemplate.getForEntity("http://provider-service/test", String.class).getBody();
    }

    public String testServerFallBack(){
        return "调用失败";
    }

    /**
     * 继承方式开启请求缓存,注意commandKey必须与清除的commandKey一致
     */
    public void openCacheByExtends(){
        CacheCommand command1 = new CacheCommand(com.netflix.hystrix.HystrixCommand.Setter.withGroupKey(
                HystrixCommandGroupKey.Factory.asKey("group")).andCommandKey(HystrixCommandKey.Factory.asKey("test")),
                restTemplate,1L);
        CacheCommand command2 = new CacheCommand(com.netflix.hystrix.HystrixCommand.Setter.withGroupKey(
                HystrixCommandGroupKey.Factory.asKey("group")).andCommandKey(HystrixCommandKey.Factory.asKey("test")),
                restTemplate,1L);
        Integer result1 = command1.execute();
        Integer result2 = command2.execute();
        logger.info("first request result is:{} ,and secend request result is: {}", result1, result2);
    }

    /**
     * 继承方式清除请除缓存
     */
    public void clearCacheByExtends(){
        CacheCommand.flushRequestCache(1L);
        logger.info("请求缓存已清空！");
    }

    /**
     * 使用注解请求缓存 方式2
     * @CacheResult  标记这是一个缓存方法，结果会被缓存
     * @CacheKey 使用这个注解会把最近的参数作为cacheKey,不使用该注解会把所有参数当作key
     *
     * 注意：有些教程中说使用这个可以指定参数，比如：@CacheKey("id") , 但是我这么用会报错，网上只找到一个也出这个错误的贴子没解决
     *          而且我发现有一个问题是有些文章中有提到 “不使用@CacheResult，只使用@CacheKey也能实现缓存” ，经本人实测无用
     */
    @CacheResult
    @HystrixCommand(commandKey = "commandKey2")
    public Integer openCacheByAnnotation2(@CacheKey Long id){
        //此次结果会被缓存
        return restTemplate.getForObject("http://provider-service/test/cache", Integer.class);
    }

    /**
     * 使用注解清除缓存 方式2
     * @CacheRemove 必须指定commandKey才能进行清除指定缓存
     */
    @CacheRemove(commandKey = "commandKey2")
    @HystrixCommand
    public void flushCacheByAnnotation2(@CacheKey Long id){
        logger.info("请求缓存已清空！");
        //这个@CacheRemove注解直接用在更新方法上效果更好
    }
}
