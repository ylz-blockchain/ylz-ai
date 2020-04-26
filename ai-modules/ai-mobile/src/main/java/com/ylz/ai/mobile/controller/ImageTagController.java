package com.ylz.ai.mobile.controller;

import com.ylz.ai.mobile.service.IImageTagService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;

import com.ylz.ai.auth.client.annotation.CheckClientToken;
import com.ylz.ai.auth.user.annotation.CheckUserToken;

 /**
 * @Description: 照片标签
 * @Author: haifeng.lv
 * @Date: 2020-04-26 15:02
 */
@Slf4j
@Api(tags="照片标签")
@RestController
@RequestMapping("/imageTag")
@CheckClientToken
@CheckUserToken
public class ImageTagController {
	@Autowired
	private IImageTagService imageTagService;
}
