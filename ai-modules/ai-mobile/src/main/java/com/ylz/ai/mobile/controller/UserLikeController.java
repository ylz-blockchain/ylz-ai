package com.ylz.ai.mobile.controller;

import com.ylz.ai.api.vo.BaseRequest;
import com.ylz.ai.common.vo.Result;
import com.ylz.ai.mobile.entity.UserLike;
import com.ylz.ai.mobile.service.IUserLikeService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ylz.ai.mobile.vo.request.AddUserLike;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import com.ylz.ai.auth.client.annotation.CheckClientToken;
import com.ylz.ai.auth.user.annotation.CheckUserToken;

 /**
 * @Description: 用户点赞
 * @Author: haifeng.lv
 * @Date: 2020-04-22 10:46
 */
@Slf4j
@Api(tags="用户点赞")
@RestController
@RequestMapping("/userLike")
@CheckClientToken
@CheckUserToken
public class UserLikeController {
	@Autowired
	private IUserLikeService userLikeService;

	/**
	 * 添加
	 * @param addUserLike
	 * @return
	 */
	@ApiOperation(value="用户点赞-添加", notes="用户点赞-添加")
	@PostMapping(value = "/generateUserLike")
	public Result<UserLike> generateUserLike(@RequestBody AddUserLike addUserLike) {
		Result<UserLike> result = new Result<>();
        userLikeService.createUserLike(addUserLike.getImageId());
        result.success("点赞成功！");
		return result;
	}
	
	/**
	 * 通过照片id删除
	 * @param request
	 * @return
	 */
	@ApiOperation(value="用户点赞-通过照片id删除", notes="用户点赞-通过照片id删除")
	@DeleteMapping(value = "/expurgateUserLikeByImageId")
	public Result<?> expurgateUserLikeByImageId(@RequestBody BaseRequest request) {
        userLikeService.dropUserLikeByImageId(request.getId());
		return Result.ok("取消点赞成功!");
	}
	
}
