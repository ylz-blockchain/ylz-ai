package com.ylz.ai.mobile.controller;

import com.ylz.ai.common.vo.Result;
import com.ylz.ai.mobile.entity.TagDictionary;
import com.ylz.ai.mobile.service.ITagDictionaryService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import com.ylz.ai.auth.client.annotation.CheckClientToken;
import com.ylz.ai.auth.user.annotation.CheckUserToken;

import java.util.List;

/**
 * @Description: 标签字典
 * @Author: haifeng.lv
 * @Date: 2020-04-21 14:54
 */
@Slf4j
@Api(tags="标签字典")
@RestController
@RequestMapping("/tagDictionary")
@CheckClientToken
@CheckUserToken
public class TagDictionaryController {
	@Autowired
	private ITagDictionaryService tagDictionaryService;
	
	/**
	 * 分页列表查询
	 * @param tagDictionary
	 * @param pageNo
	 * @param pageSize
	 * @param sortProp
	 * @param sortType
	 * @return
	 */
	@ApiOperation(value="标签字典-分页列表查询", notes="标签字典-分页列表查询")
	@GetMapping(value = "/getTagDictionaryPageList")
	public Result<IPage<TagDictionary>> getTagDictionaryPageList(TagDictionary tagDictionary,
									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                                      @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                                      @RequestParam(name="sortProp", required = false) String sortProp,
                                      @RequestParam(name="sortType", required = false) String sortType) {
        Result<IPage<TagDictionary>> result = new Result<>();
		IPage<TagDictionary> pageList = tagDictionaryService.findTagDictionaryPageList(tagDictionary, pageNo, pageSize, sortProp, sortType);
		result.setSuccess(true);
		result.setResult(pageList);
		return result;
	}

	 /**
	  * @Description 获取标签列表
	  * @Author haifeng.lv
	  * @Date 2020/4/24 16:27
	  * @return: com.ylz.ai.common.vo.Result<java.util.List<com.ylz.ai.mobile.entity.RecognitionType>>
	  */
	 @ApiOperation(value="识别类型-获取标签列表", notes="识别类型-获取标签列表")
	 @GetMapping(value = "/getTagDictionarys")
	 public Result<List<TagDictionary>> getTagDictionarys() {
		 Result<List<TagDictionary>> result = new Result<>();
		 List<TagDictionary> recognitionTypes = tagDictionaryService.findTagDictionarys();
		 result.setSuccess(true);
		 result.setResult(recognitionTypes);
		 return result;
	 }
	
	/**
	 * 添加
	 * @param tagDictionary
	 * @return
	 */
	@ApiOperation(value="标签字典-添加", notes="标签字典-添加")
	@PostMapping(value = "/generateTagDictionary")
	public Result<TagDictionary> generateTagDictionary(@RequestBody TagDictionary tagDictionary) {
		Result<TagDictionary> result = new Result<>();
        tagDictionaryService.createTagDictionary(tagDictionary);
        result.success("添加成功！");
		return result;
	}
	
	/**
	 * 编辑
	 * @param tagDictionary
	 * @return
	 */
	@ApiOperation(value="标签字典-编辑", notes="标签字典-编辑")
	@PutMapping(value = "/changeTagDictionaryById")
	public Result<TagDictionary> changeTagDictionaryById(@RequestBody TagDictionary tagDictionary) {
		Result<TagDictionary> result = new Result<>();
        tagDictionaryService.alterTagDictionaryById(tagDictionary);
        result.success("编辑成功！");
		return result;
	}
	
	/**
	 * 通过id删除
	 * @param id
	 * @return
	 */
	@ApiOperation(value="标签字典-通过id删除", notes="标签字典-通过id删除")
	@DeleteMapping(value = "/expurgateTagDictionaryById")
	public Result<?> expurgateTagDictionaryById(@RequestParam(name="id",required=true) String id) {
        tagDictionaryService.dropTagDictionaryById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@ApiOperation(value="标签字典-批量删除", notes="标签字典-批量删除")
	@DeleteMapping(value = "/expurgateTagDictionaryBatch")
	public Result<?> expurgateTagDictionaryBatch(@RequestParam(name="ids",required=true) String ids) {
        tagDictionaryService.dropTagDictionaryBatch(ids);
		return Result.ok("删除成功!");
	}
	
	/**
	 * 通过id查询
	 * @param id
	 * @return
	 */
	@ApiOperation(value="标签字典-通过id查询", notes="标签字典-通过id查询")
	@GetMapping(value = "/getTagDictionaryById")
	public Result<TagDictionary> getTagDictionaryById(@RequestParam(name="id",required=true) String id) {
		Result<TagDictionary> result = new Result<>();
		TagDictionary tagDictionary = tagDictionaryService.findTagDictionaryById(id);
        result.setResult(tagDictionary);
        result.setSuccess(true);
		return result;
	}

}
