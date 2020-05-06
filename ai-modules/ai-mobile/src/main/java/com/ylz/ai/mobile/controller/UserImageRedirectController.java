package com.ylz.ai.mobile.controller;

import com.ylz.ai.auth.user.annotation.IgnoreUserToken;
import com.ylz.ai.common.vo.Result;
import com.ylz.ai.mobile.entity.UserLike;
import com.ylz.ai.mobile.service.IUserImageRedirectService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ylz.ai.mobile.vo.request.AddImageRedirect;
import com.ylz.ai.mobile.vo.request.UserImageRedirectRequest;
import com.ylz.ai.mobile.vo.response.UserImageRedirectInfo;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import com.ylz.ai.auth.client.annotation.CheckClientToken;
import com.ylz.ai.auth.user.annotation.CheckUserToken;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @Description: 用户照片转发
 * @Author: haifeng.lv
 * @Date: 2020-04-24 16:06
 */
@Slf4j
@Api(tags = "用户照片转发")
@RestController
@RequestMapping("/userImageRedirect")
@CheckClientToken
@CheckUserToken
public class UserImageRedirectController {
    @Autowired
    private IUserImageRedirectService userImageRedirectService;

    /**
     * 分页列表查询
     * @param request
     * @param pageNo
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "用户照片转发-分页列表查询", notes = "用户照片转发-分页列表查询")
    @GetMapping(value = "/getUserImageRedirectPageList")
    public Result<IPage<UserImageRedirectInfo>> getUserImageRedirectPageList(UserImageRedirectRequest request,
																			 @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
																			 @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        Result<IPage<UserImageRedirectInfo>> result = new Result<>();
        IPage<UserImageRedirectInfo> pageList = userImageRedirectService.findUserImageRedirectPageList(request, pageNo, pageSize);
        result.setSuccess(true);
        result.setResult(pageList);
        return result;
    }

    /**
     * @Description 用户照片转发-添加
     * @Author haifeng.lv
     * @param: addImageRedirect
     * @Date 2020/4/24 16:11
     * @return: com.ylz.ai.common.vo.Result<com.ylz.ai.mobile.entity.UserLike>
     */
    @ApiOperation(value = "用户照片转发-添加", notes = "用户照片转发-添加")
    @PostMapping(value = "/generateUserImageRedirect")
    @IgnoreUserToken
    public Result<UserLike> generateUserImageRedirect(@Valid @RequestBody AddImageRedirect addImageRedirect, HttpServletRequest request) throws Exception {
        Result<UserLike> result = new Result<>();
        userImageRedirectService.createUserImageRedirect(addImageRedirect.getImageId(), addImageRedirect.getRedirectPlace(), addImageRedirect.getDescription(), request);
        result.success("转发成功！");
        return result;
    }

}
