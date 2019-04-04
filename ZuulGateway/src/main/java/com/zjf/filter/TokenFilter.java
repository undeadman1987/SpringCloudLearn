package com.zjf.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: zhangjunfeng
 * @Email: junfeng1987@163.com
 * @Description:
 * @Date: Created on 2019/3/28 18:46
 */
@Component
public class TokenFilter extends ZuulFilter {

    private final Logger logger = LoggerFactory.getLogger(TokenFilter.class);

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        //获取当前请求上下文
        RequestContext ctx = RequestContext.getCurrentContext();
        //取出当前请求
        HttpServletRequest request = ctx.getRequest();
        logger.info("进入访问过滤器，访问的url:{}，访问的方法：{}",request.getRequestURL(),request.getMethod());
        //从headers中取出key为accessToken值
        String accessToken = request.getHeader("accessToken");//这里我把token写进headers中了
        //这里简单校验下如果headers中没有这个accessToken或者该值为空的情况
        //那么就拦截不入放行，返回401状态码
//        if(accessToken != null && !"".equals(accessToken)) {
//            logger.info("当前请求没有accessToken");
//            //使用Zuul来过滤这次请求
//            ctx.setSendZuulResponse(false);
//            ctx.setResponseStatusCode(401);
//            return null;
//        }
        logger.info("请求通过过滤器");
        return null;
    }
}
