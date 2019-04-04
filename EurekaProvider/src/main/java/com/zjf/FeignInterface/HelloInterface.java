package com.zjf.FeignInterface;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author: zhangjunfeng
 * @Email: junfeng1987@163.com
 * @Description:
 * @Date: Created on 2019/3/28 17:13
 */
public interface HelloInterface {

    @GetMapping("/hello/{name}")
    String sayHello(@PathVariable("name") String name);
}
