package com.ylz.ai.mobile.controller;

import com.ylz.ai.api.vo.user.FrontAuthenticationRequest;
import com.ylz.ai.auth.client.annotation.IgnoreClientToken;
import com.ylz.ai.auth.user.annotation.IgnoreUserToken;
import com.ylz.ai.common.context.BaseContextHandler;
import com.ylz.ai.common.vo.Result;
import com.ylz.ai.mobile.entity.FrontUser;
import com.ylz.ai.mobile.service.IFrontUserService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import com.ylz.ai.auth.client.annotation.CheckClientToken;
import com.ylz.ai.auth.user.annotation.CheckUserToken;

import javax.servlet.http.HttpServletRequest;
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

    @PostMapping(value = "/validate")
    @ApiOperation(value = "前端用户-验证创建", notes = "前端用户-验证创建")
    @IgnoreClientToken
    @IgnoreUserToken
    public Result<String> validate(@RequestBody FrontAuthenticationRequest request, HttpServletRequest httpServletRequest) {
        Result<String> result = new Result<>();
        frontUserService.validate(request.getCode(), request.getName(), httpServletRequest);
        result.setSuccess(true);
        result.setResult("validate success");
        return result;
    }

    /**
     * 分页列表查询
     *
     * @param frontUser
     * @param pageNo
     * @param pageSize
     * @param sortProp
     * @param sortType
     * @return
     */
    @ApiOperation(value = "前端用户-分页列表查询", notes = "前端用户-分页列表查询")
    @GetMapping(value = "/getFrontUserPageList")
    public Result<IPage<FrontUser>> getFrontUserPageList(FrontUser frontUser,
                                                         @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                         @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                         @RequestParam(name = "sortProp", required = false) String sortProp,
                                                         @RequestParam(name = "sortType", required = false) String sortType) {
        Result<IPage<FrontUser>> result = new Result<>();
        IPage<FrontUser> pageList = frontUserService.findFrontUserPageList(frontUser, pageNo, pageSize, sortProp, sortType);
        result.setSuccess(true);
        result.setResult(pageList);
        return result;
    }

    /**
     * 添加
     *
     * @param frontUser
     * @return
     */
    @ApiOperation(value = "前端用户-添加", notes = "前端用户-添加")
    @PostMapping(value = "/generateFrontUser")
    public Result<FrontUser> generateFrontUser(@RequestBody FrontUser frontUser) {
        Result<FrontUser> result = new Result<>();
        frontUserService.createFrontUser(frontUser);
        result.success("添加成功！");
        return result;
    }

    /**
     * 编辑
     *
     * @param frontUser
     * @return
     */
    @ApiOperation(value = "前端用户-编辑", notes = "前端用户-编辑")
    @PutMapping(value = "/changeFrontUserById")
    public Result<FrontUser> changeFrontUserById(@RequestBody FrontUser frontUser) {
        Result<FrontUser> result = new Result<>();
        frontUserService.alterFrontUserById(frontUser);
        result.success("编辑成功！");
        return result;
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "前端用户-通过id删除", notes = "前端用户-通过id删除")
    @DeleteMapping(value = "/expurgateFrontUserById")
    public Result<?> expurgateFrontUserById(@RequestParam(name = "id", required = true) String id) {
        frontUserService.dropFrontUserById(id);
        return Result.ok("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @ApiOperation(value = "前端用户-批量删除", notes = "前端用户-批量删除")
    @DeleteMapping(value = "/expurgateFrontUserBatch")
    public Result<?> expurgateFrontUserBatch(@RequestParam(name = "ids", required = true) String ids) {
        frontUserService.dropFrontUserBatch(ids);
        return Result.ok("删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "前端用户-通过id查询", notes = "前端用户-通过id查询")
    @GetMapping(value = "/getFrontUserById")
    public Result<FrontUser> getFrontUserById(@RequestParam(name = "id", required = true) String id) {
        Result<FrontUser> result = new Result<>();
        FrontUser frontUser = frontUserService.findFrontUserById(id);
        result.setResult(frontUser);
        result.setSuccess(true);
        return result;
    }

}
