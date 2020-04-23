package com.ylz.ai.mobile.controller;

import com.ylz.ai.api.vo.BaseRequest;
import com.ylz.ai.common.vo.Result;
import com.ylz.ai.mobile.entity.AttentionUser;
import com.ylz.ai.mobile.service.IAttentionUserService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ylz.ai.mobile.vo.response.UserInfo;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import com.ylz.ai.auth.client.annotation.CheckClientToken;
import com.ylz.ai.auth.user.annotation.CheckUserToken;

import java.util.List;

/**
 * @Description: 关注用户
 * @Author: haifeng.lv
 * @Date: 2020-04-20 14:27
 */
@Slf4j
@Api(tags="关注用户")
@RestController
@RequestMapping("/attentionUser")
@CheckClientToken
@CheckUserToken
public class AttentionUserController {
	@Autowired
	private IAttentionUserService attentionUserService;

	@ApiOperation(value="查询关注用户", notes="查询关注用户")
	@GetMapping(value = "/getAttentionUsers")
	public Result<List<UserInfo>> getAttentionUsers() {
		Result<List<UserInfo>> result = new Result<>();
		List<UserInfo> userInfos = attentionUserService.findAttentionUsers();
        result.setResult(userInfos);
        result.setSuccess(true);
		return result;
	}

	/**
	 * @Description 查询被关注用户
	 * @Author haifeng.lv
	 * @param: id 被关注的用户 id
	 * @Date 2020/4/23 10:07
	 * @return: com.ylz.ai.common.vo.Result<java.util.List<com.ylz.ai.mobile.vo.response.UserInfo>>
	 */
	@ApiOperation(value="查询被关注用户", notes="查询被关注用户")
	@GetMapping(value = "/getBeAttentionUsers")
	public Result<List<UserInfo>> getBeAttentionUsers(@RequestParam(name="id",required=true) String id) {
		Result<List<UserInfo>> result = new Result<>();
		List<UserInfo> userInfos = attentionUserService.findBeAttentionUsers(id);
        result.setResult(userInfos);
        result.setSuccess(true);
		return result;
	}

	/**
	 * @Description 关注用户
	 * @Author haifeng.lv
	 * @param: id 被关注的用户 id
	 * @Date 2020/4/23 10:07
	 * @return: com.ylz.ai.common.vo.Result<java.util.List<com.ylz.ai.mobile.vo.response.UserInfo>>
	 */
	@ApiOperation(value="关注用户", notes="关注用户")
	@PostMapping(value = "/attentionUsers")
	public Result attentionUsers(@RequestBody BaseRequest request) {
		Result result = new Result<>();
		attentionUserService.attentionUsers(request.getId());
		result.success("关注成功！");
		return result;
	}

	/**
	 * @Description 取消关注用户
	 * @Author haifeng.lv
	 * @param: id 被关注的用户 id
	 * @Date 2020/4/23 10:07
	 * @return: com.ylz.ai.common.vo.Result<java.util.List<com.ylz.ai.mobile.vo.response.UserInfo>>
	 */
	@ApiOperation(value="取消关注用户", notes="取消关注用户")
	@PostMapping(value = "/cleanAttentionUsers")
	public Result cleanAttentionUsers(@RequestBody BaseRequest request) {
		Result result = new Result<>();
		attentionUserService.cleanAttentionUsers(request.getId());
		result.success("取消关注用户成功！");
		return result;
	}

}
