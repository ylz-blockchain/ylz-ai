package com.ylz.ai.mobile.controller;

import com.ylz.ai.api.vo.BaseRequest;
import com.ylz.ai.common.vo.Result;
import com.ylz.ai.mobile.entity.FrontUser;
import com.ylz.ai.mobile.entity.UserLike;
import com.ylz.ai.mobile.service.IUserLikeService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ylz.ai.mobile.vo.request.AddUserLike;
import com.ylz.ai.mobile.vo.response.ImageInfo;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import com.ylz.ai.auth.client.annotation.CheckClientToken;
import com.ylz.ai.auth.user.annotation.CheckUserToken;

import java.util.List;

/**
 * @Description: 用户点赞
 * @Author: haifeng.lv
 * @Date: 2020-04-22 10:46
 */
@Slf4j
@Api(tags = "用户点赞")
@RestController
@RequestMapping("/userLike")
@CheckClientToken
@CheckUserToken
public class UserLikeController {
    @Autowired
    private IUserLikeService userLikeService;

    /**
     * 添加
     *
     * @param addUserLike
     * @return
     */
    @ApiOperation(value = "用户点赞-添加", notes = "用户点赞-添加")
    @PostMapping(value = "/generateUserLike")
    public Result<UserLike> generateUserLike(@RequestBody AddUserLike addUserLike) {
        Result<UserLike> result = new Result<>();
        userLikeService.createUserLike(addUserLike.getImageId());
        result.success("点赞成功！");
        return result;
    }

    /**
     * 通过照片id删除
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "用户点赞-通过照片id删除", notes = "用户点赞-通过照片id删除")
    @DeleteMapping(value = "/expurgateUserLikeByImageId")
    public Result<?> expurgateUserLikeByImageId(@RequestBody BaseRequest request) {
        userLikeService.dropUserLikeByImageId(request.getId());
        return Result.ok("取消点赞成功!");
    }

    /**
     * @Description 获取当前用户点赞照片列表
     * @Author haifeng.lv
     * @param: pageNo
     * @param: pageSize
     * @Date 2020/4/24 15:14
     * @return: com.ylz.ai.common.vo.Result<java.util.List<com.ylz.ai.mobile.vo.response.ImageInfo>>
     */
    @ApiOperation(value = "获取当前用户点赞照片列表", notes = "获取当前用户点赞照片列表")
    @GetMapping(value = "/getLikeImagesByCurrent")
    public Result<IPage<ImageInfo>> getLikeImagesByCurrent(@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                          @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        Result<IPage<ImageInfo>> result = new Result<>();
        IPage<ImageInfo> imageInfos = userLikeService.findLikeImagesByCurrent(pageNo, pageSize);
        result.setResult(imageInfos);
        result.setSuccess(true);
        return result;
    }

}
