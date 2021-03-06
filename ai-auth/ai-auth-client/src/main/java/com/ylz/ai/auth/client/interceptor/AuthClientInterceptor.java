package com.ylz.ai.auth.client.interceptor;

import com.ylz.ai.auth.client.annotation.CheckClientToken;
import com.ylz.ai.auth.client.annotation.IgnoreClientToken;
import com.ylz.ai.auth.client.service.AuthClientService;
import com.ylz.ai.common.exception.auth.ClientTokenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description 服务token认证
 * @Author haifeng.lv
 * @Date 2019/12/16 17:31
 */
public class AuthClientInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private AuthClientService authClientService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String method = request.getMethod();
        if (HttpMethod.OPTIONS.matches(method)) {
            return super.preHandle(request, response, handler);
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        CheckClientToken annotation = handlerMethod.getBeanType().getAnnotation(CheckClientToken.class);
        // 配置该注解，说明不进行服务拦截
        IgnoreClientToken ignoreClientToken = handlerMethod.getMethodAnnotation(IgnoreClientToken.class);
        if (annotation == null) {
            annotation = handlerMethod.getMethodAnnotation(CheckClientToken.class);
        }
        if (annotation == null || ignoreClientToken != null) {
            return super.preHandle(request, response, handler);
        } else {
            // 设置当前客户端信息
            boolean flag = authClientService.setCurrentClientInfo(request);
            if (!flag) {
                throw new ClientTokenException("客户端 token为空!");
            }
            return super.preHandle(request, response, handler);
        }
    }
}
