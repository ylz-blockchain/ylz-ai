package com.ylz.ai.mobile.controller;

import com.ylz.ai.api.vo.user.FrontAuthenticationRequest;
import com.ylz.ai.api.vo.user.FrontAuthenticationResponse;
import com.ylz.ai.auth.client.annotation.IgnoreClientToken;
import com.ylz.ai.auth.user.annotation.IgnoreUserToken;
import com.ylz.ai.common.context.BaseContextHandler;
import com.ylz.ai.common.vo.Result;
import com.ylz.ai.mobile.entity.FrontUser;
import com.ylz.ai.mobile.service.IFrontUserService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ylz.ai.mobile.vo.request.AddFrontUser;
import com.ylz.ai.mobile.vo.response.FrontStatisticsInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import com.ylz.ai.auth.client.annotation.CheckClientToken;
import com.ylz.ai.auth.user.annotation.CheckUserToken;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @Description: 前端用户
 * @Author: haifeng.lv
 * @Date: 2020-04-17 14:22
 */
@Slf4j
@Api(tags = "前端用户")
@RestController
@RequestMapping("/frontUser")
@CheckClientToken
@CheckUserToken
public class FrontUserController {
    @Autowired
    private IFrontUserService frontUserService;

    @PostMapping(value = "/login")
    @ApiIgnore
    @IgnoreUserToken
    public Result<FrontAuthenticationResponse> login(@RequestBody FrontAuthenticationRequest request, HttpServletRequest httpServletRequest) {
        Result<FrontAuthenticationResponse> result = new Result<>();
        FrontAuthenticationResponse response = frontUserService.login(request, httpServletRequest);
        result.setSuccess(true);
        result.setResult(response);
        return result;
    }

    /**
     * 编辑
     *
     * @param addFrontUser
     * @return
     */
    @ApiOperation(value = "前端用户-编辑", notes = "前端用户-编辑")
    @PutMapping(value = "/changeFrontUserById")
    public Result<FrontUser> changeFrontUserById(@RequestBody AddFrontUser addFrontUser) {
        Result<FrontUser> result = new Result<>();
        frontUserService.alterFrontUserById(addFrontUser);
        result.success("编辑成功！");
        return result;
    }

    @ApiOperation(value = "前端用户-统计", notes = "前端用户-统计")
    @GetMapping(value = "/getFrontStatistics")
    public Result<FrontStatisticsInfo> getFrontStatistics() {
        Result<FrontStatisticsInfo> result = new Result<>();
        FrontStatisticsInfo frontStatisticsInfo = frontUserService.findFrontStatistics();
        result.setResult(frontStatisticsInfo);
        result.setSuccess(true);
        return result;
    }

}
