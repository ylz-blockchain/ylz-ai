package com.ylz.ai.auth.server.feign;

import com.ylz.ai.api.vo.user.FrontAuthenticationRequest;
import com.ylz.ai.auth.server.configuration.FeignConfiguration;
import com.ylz.ai.common.vo.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Description 前端用户 请求
 * @Author haifeng.lv
 * @Date 2020/4/17 14:12
 */
@FeignClient(value = "ai-mobile", configuration = FeignConfiguration.class)
public interface IFrontUserFeign {
    /**
     * @Description 验证前端用户
     * @Author haifeng.lv
     * @param: request
     * @Date 2020/4/17 14:18
     * @return: com.ylz.ai.common.vo.Result<java.util.Map<java.lang.String,java.lang.String>>
     */
    @PostMapping("/frontUser/validate")
    Result<String> validate(@RequestBody FrontAuthenticationRequest request);
}
