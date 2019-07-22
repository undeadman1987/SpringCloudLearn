package com.zjf.controller;

import com.zjf.FeignInterface.HelloInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;

/**
 * @Author: zhangjunfeng
 * @Email: junfeng1987@163.com
 * @Description:
 * @Date: Created on 2019/3/27 13:04
 */
@RestController
@RefreshScope
public class TestController implements HelloInterface {
    @Autowired
    private DiscoveryClient client;

    @Value("${spring.cloud.config.test}")
    private Integer test;

    private final Logger logger = LoggerFactory.getLogger(TestController.class);

    @GetMapping(value = "/test")
    public String testClient(){
        for( String s :  client.getServices()){
            logger.info("services " + s);
            List<ServiceInstance> serviceInstances =  client.getInstances(s);
            for(ServiceInstance si : serviceInstances){
                logger.info("services:" + s + ":getHost()=" + si.getHost());
                logger.info("services:" + s + ":getPort()=" + si.getPort());
                logger.info("services:" + s + ":getServiceId()=" + si.getServiceId());
                logger.info("services:" + s + ":getUri()=" + si.getUri());
                logger.info("services:" + s + ":getMetadata()=" + si.getMetadata());
                logger.info("--------------------------");
            }

        }
        return "test:ok";
    }

    @GetMapping(value = "/test/cache")
    public Integer getRandomInteger(){
        Random random = new Random();
        int next = random.nextInt(99999);
        return next;
    }

    @GetMapping(value = "/test/{name}")
    public String testParam(@PathVariable String name){
        return "hello:"+name;
    }

    @Override
    public String sayHello(@PathVariable String name) {
        return "hello:" + name;
    }

    @GetMapping(value = "/config/test")
    public String testConfigRefresh(){
        return this.test+"";
    }
}
