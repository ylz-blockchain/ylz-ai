package com.ylz.ai.auth.server.modules.user.controller;

import com.ylz.ai.api.vo.user.FrontAuthenticationRequest;
import com.ylz.ai.auth.server.modules.client.service.IAuthClientService;
import com.ylz.ai.auth.server.modules.user.service.IFrontUserService;
import com.ylz.ai.common.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description 前台客户端接口
 * @Author haifeng.lv
 * @Date 2020/4/17 13:59
 */
@RestController
@RequestMapping("/frontAuthClient")
@Api(tags = "前端登录")
@Slf4j
public class FrontUserController {
    @Autowired
    private IFrontUserService frontUserService;

    /**
     * @Description 获取 token
     * @Author haifeng.lv
     * @param: request
     * @Date 2020/4/17 14:35
     * @return: com.ylz.ai.common.vo.Result<java.lang.String>
     */
    @RequestMapping(value = "/getToken", method = RequestMethod.POST)
    @ApiOperation(value="登录获取 token", notes="登录获取 token")
    public Result<String> getToken(@RequestBody FrontAuthenticationRequest request) throws Exception {
        Result<String> result = new Result();
        result.setResult(frontUserService.getToken(request));
        return result;
    }

}
