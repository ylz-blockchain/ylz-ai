package com.ylz.ai.mobile.controller;

import com.ylz.ai.common.vo.Result;
import com.ylz.ai.mobile.entity.RecognitionType;
import com.ylz.ai.mobile.service.IRecognitionTypeService;
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
 * @Description: 识别类型
 * @Author: haifeng.lv
 * @Date: 2020-04-24 14:38
 */
@Slf4j
@Api(tags="识别类型")
@RestController
@RequestMapping("/recognitionType")
@CheckClientToken
@CheckUserToken
public class RecognitionTypeController {
	@Autowired
	private IRecognitionTypeService recognitionTypeService;
	
	/**
	 * 分页列表查询
	 * @param recognitionType
	 * @param pageNo
	 * @param pageSize
	 * @param sortProp
	 * @param sortType
	 * @return
	 */
	@ApiOperation(value="识别类型-分页列表查询", notes="识别类型-分页列表查询")
	@GetMapping(value = "/getRecognitionTypePageList")
	public Result<IPage<RecognitionType>> getRecognitionTypePageList(RecognitionType recognitionType,
									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                                      @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                                      @RequestParam(name="sortProp", required = false) String sortProp,
                                      @RequestParam(name="sortType", required = false) String sortType) {
        Result<IPage<RecognitionType>> result = new Result<>();
		IPage<RecognitionType> pageList = recognitionTypeService.findRecognitionTypePageList(recognitionType, pageNo, pageSize, sortProp, sortType);
		result.setSuccess(true);
		result.setResult(pageList);
		return result;
	}

	/**
	 * @Description 获取识别列表
	 * @Author haifeng.lv
	 * @Date 2020/4/24 16:27
	 * @return: com.ylz.ai.common.vo.Result<java.util.List<com.ylz.ai.mobile.entity.RecognitionType>>
	 */
	@ApiOperation(value="识别类型-获取识别列表", notes="识别类型-获取识别列表")
	@GetMapping(value = "/getRecognitionTypes")
	public Result<List<RecognitionType>> getRecognitionTypes() {
		Result<List<RecognitionType>> result = new Result<>();
		List<RecognitionType> recognitionTypes = recognitionTypeService.findRecognitionTypes();
		result.setSuccess(true);
		result.setResult(recognitionTypes);
		return result;
	}
	
	/**
	 * 添加
	 * @param recognitionType
	 * @return
	 */
	@ApiOperation(value="识别类型-添加", notes="识别类型-添加")
	@PostMapping(value = "/generateRecognitionType")
	public Result<RecognitionType> generateRecognitionType(@RequestBody RecognitionType recognitionType) {
		Result<RecognitionType> result = new Result<>();
        recognitionTypeService.createRecognitionType(recognitionType);
        result.success("添加成功！");
		return result;
	}
	
	/**
	 * 编辑
	 * @param recognitionType
	 * @return
	 */
	@ApiOperation(value="识别类型-编辑", notes="识别类型-编辑")
	@PutMapping(value = "/changeRecognitionTypeById")
	public Result<RecognitionType> changeRecognitionTypeById(@RequestBody RecognitionType recognitionType) {
		Result<RecognitionType> result = new Result<>();
        recognitionTypeService.alterRecognitionTypeById(recognitionType);
        result.success("编辑成功！");
		return result;
	}
	
	/**
	 * 通过id删除
	 * @param id
	 * @return
	 */
	@ApiOperation(value="识别类型-通过id删除", notes="识别类型-通过id删除")
	@DeleteMapping(value = "/expurgateRecognitionTypeById")
	public Result<?> expurgateRecognitionTypeById(@RequestParam(name="id",required=true) String id) {
        recognitionTypeService.dropRecognitionTypeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@ApiOperation(value="识别类型-批量删除", notes="识别类型-批量删除")
	@DeleteMapping(value = "/expurgateRecognitionTypeBatch")
	public Result<?> expurgateRecognitionTypeBatch(@RequestParam(name="ids",required=true) String ids) {
        recognitionTypeService.dropRecognitionTypeBatch(ids);
		return Result.ok("删除成功!");
	}
	
	/**
	 * 通过id查询
	 * @param id
	 * @return
	 */
	@ApiOperation(value="识别类型-通过id查询", notes="识别类型-通过id查询")
	@GetMapping(value = "/getRecognitionTypeById")
	public Result<RecognitionType> getRecognitionTypeById(@RequestParam(name="id",required=true) String id) {
		Result<RecognitionType> result = new Result<>();
		RecognitionType recognitionType = recognitionTypeService.findRecognitionTypeById(id);
        result.setResult(recognitionType);
        result.setSuccess(true);
		return result;
	}

}
