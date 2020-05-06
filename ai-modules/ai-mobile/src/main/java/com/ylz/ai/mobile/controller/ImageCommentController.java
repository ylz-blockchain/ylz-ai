package com.ylz.ai.mobile.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ylz.ai.common.vo.Result;
import com.ylz.ai.mobile.entity.ImageComment;
import com.ylz.ai.mobile.service.IImageCommentService;
import com.ylz.ai.mobile.vo.request.AddImageComment;
import com.ylz.ai.mobile.vo.request.UserCommentRequest;
import com.ylz.ai.mobile.vo.response.ImageCommentInfo;
import com.ylz.ai.mobile.vo.response.ImageCommentMinInfo;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import com.ylz.ai.auth.client.annotation.CheckClientToken;
import com.ylz.ai.auth.user.annotation.CheckUserToken;

import javax.validation.Valid;
import java.util.List;

/**
 * @Description: 照片评论
 * @Author: haifeng.lv
 * @Date: 2020-04-20 14:27
 */
@Slf4j
@Api(tags="照片评论")
@RestController
@RequestMapping("/imageComment")
@CheckClientToken
@CheckUserToken
public class ImageCommentController {
	@Autowired
	private IImageCommentService imageCommentService;

	/**
	 * 分页列表查询
	 * @param request
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@ApiOperation(value = "用户照片转发-分页列表查询", notes = "用户照片转发-分页列表查询")
	@GetMapping(value = "/getImageCommentPageList")
	public Result<IPage<ImageCommentMinInfo>> getImageCommentPageList(UserCommentRequest request,
																	  @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
																	  @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
		Result<IPage<ImageCommentMinInfo>> result = new Result<>();
		IPage<ImageCommentMinInfo> pageList = imageCommentService.findImageCommentPageList(request, pageNo, pageSize);
		result.setSuccess(true);
		result.setResult(pageList);
		return result;
	}

	 /**
	  * 通过照片 id查询
	  * @param id 照片 id
	  * @return
	  */
	 @ApiOperation(value="照片评论-通过照片 id查询", notes="照片评论-通过照片 id查询")
	 @GetMapping(value = "/getImageCommentByImageId")
	 public Result<List<ImageCommentInfo>> getTagDictionaryById(@RequestParam(name="id",required=true) String id) {
		 Result<List<ImageCommentInfo>> result = new Result<>();
		 List<ImageCommentInfo> imageComments = imageCommentService.findTagDictionaryById(id);
		 result.setResult(imageComments);
		 result.setSuccess(true);
		 return result;
	 }
	
	/**
	 * 添加
	 * @param addImageComment
	 * @return
	 */
	@ApiOperation(value="照片评论-添加", notes="照片评论-添加")
	@PostMapping(value = "/generateImageComment")
	public Result<ImageComment> generateImageComment(@Valid @RequestBody AddImageComment addImageComment) {
		Result<ImageComment> result = new Result<>();
        imageCommentService.createImageComment(addImageComment);
        result.success("添加成功！");
		return result;
	}
	
	/**
	 * 通过id删除
	 * @param id
	 * @return
	 */
	@ApiOperation(value="照片评论-通过id删除", notes="照片评论-通过id删除")
	@DeleteMapping(value = "/expurgateImageCommentById")
	public Result<?> expurgateImageCommentById(@RequestParam(name="id",required=true) String id) {
        imageCommentService.dropImageCommentById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@ApiOperation(value="照片评论-批量删除", notes="照片评论-批量删除")
	@DeleteMapping(value = "/expurgateImageCommentBatch")
	public Result<?> expurgateImageCommentBatch(@RequestParam(name="ids",required=true) String ids) {
        imageCommentService.dropImageCommentBatch(ids);
		return Result.ok("删除成功!");
	}

}
