package com.ylz.ai.mobile.controller;

import com.ylz.ai.common.vo.Result;
import com.ylz.ai.mobile.entity.UserImageRedirect;
import com.ylz.ai.mobile.entity.UserLike;
import com.ylz.ai.mobile.service.IUserImageRedirectService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ylz.ai.mobile.vo.request.AddImageRedirect;
import com.ylz.ai.mobile.vo.request.AddUserLike;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import com.ylz.ai.auth.client.annotation.CheckClientToken;
import com.ylz.ai.auth.user.annotation.CheckUserToken;

 /**
 * @Description: 用户照片转发
 * @Author: haifeng.lv
 * @Date: 2020-04-24 16:06
 */
@Slf4j
@Api(tags="用户照片转发")
@RestController
@RequestMapping("/userImageRedirect")
@CheckClientToken
@CheckUserToken
public class UserImageRedirectController {
	@Autowired
	private IUserImageRedirectService userImageRedirectService;

	 /**
	  * @Description 用户照片转发-添加
	  * @Author haifeng.lv
	  * @param: addImageRedirect
	  * @Date 2020/4/24 16:11
	  * @return: com.ylz.ai.common.vo.Result<com.ylz.ai.mobile.entity.UserLike>
	  */
	 @ApiOperation(value = "用户照片转发-添加", notes = "用户照片转发-添加")
	 @PostMapping(value = "/generateUserImageRedirect")
	 public Result<UserLike> generateUserImageRedirect(@RequestBody AddImageRedirect addImageRedirect) {
		 Result<UserLike> result = new Result<>();
		 userImageRedirectService.createUserImageRedirect(addImageRedirect.getImageId(), addImageRedirect.getRedirectPlace(), addImageRedirect.getDescription());
		 result.success("转发成功！");
		 return result;
	 }
	
}
