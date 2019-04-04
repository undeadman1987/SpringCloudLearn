package com.zjf.FeginService;

import com.zjf.FeignInterface.HelloInterface;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Author: zhangjunfeng
 * @Email: junfeng1987@163.com
 * @Description:
 * @Date: Created on 2019/3/28 16:44
 */
@FeignClient(value = "learn-service")
public interface TestService extends HelloInterface {

    @RequestMapping(value = "/test",method = RequestMethod.GET)
    String test();

    @RequestMapping(value = "/test/{name}",method = RequestMethod.GET)
    String testParam(@PathVariable("name") String name);
}
