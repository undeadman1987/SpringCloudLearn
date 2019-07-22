package com.zjf.controller;

import com.zjf.FeginService.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: zhangjunfeng
 * @Email: junfeng1987@163.com
 * @Description:
 * @Date: Created on 2019/3/28 16:50
 */
@RestController
@RequestMapping("feign")
@Slf4j
public class FeignController {
    @Autowired
    private TestService service;

    @GetMapping("/test")
    public String testFeign(){
        log.info("===================testFeign=================");
        return service.test();
    }

    @GetMapping("/test/{name}")
    public String testFeignParam(@PathVariable String name){
        log.info("===================testFeignParam:{}=================",name);
        return service.testParam(name);
    }

    @GetMapping("/hello/{name}")
    public String helloFeign(@PathVariable String name){
        log.info("===================helloFeign:{}=================",name);
        return service.sayHello(name);
    }
}
