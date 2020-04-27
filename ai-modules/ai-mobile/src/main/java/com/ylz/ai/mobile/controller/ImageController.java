package com.ylz.ai.mobile.controller;

import com.ylz.ai.auth.client.annotation.IgnoreClientToken;
import com.ylz.ai.auth.user.annotation.IgnoreUserToken;
import com.ylz.ai.common.vo.Result;
import com.ylz.ai.mobile.entity.Image;
import com.ylz.ai.mobile.service.IImageService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ylz.ai.mobile.vo.response.ImageInfo;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import com.ylz.ai.auth.client.annotation.CheckClientToken;
import com.ylz.ai.auth.user.annotation.CheckUserToken;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description: 照片
 * @Author: haifeng.lv
 * @Date: 2020-04-20 14:25
 */
@Slf4j
@Api(tags = "照片")
@RestController
@RequestMapping("/image")
@CheckClientToken
@CheckUserToken
public class ImageController {
    @Autowired
    private IImageService imageService;

    /**
     * 首页分页列表查询
     *
     * @param pageNo
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "照片-首页分页列表查询", notes = "照片-首页分页列表查询")
    @GetMapping(value = "/getIndexImagePageList")
    @IgnoreUserToken
    @IgnoreClientToken
    public Result<IPage<ImageInfo>> getIndexImagePageList(@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                          @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                          HttpServletRequest request) throws Exception {
        Result<IPage<ImageInfo>> result = new Result<>();
        IPage<ImageInfo> pageList = imageService.findIndexImagePageList(pageNo, pageSize, request);
        result.setSuccess(true);
        result.setResult(pageList);
        return result;
    }

    /**
     * 发现分页列表查询
     *
     * @param pageNo
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "照片-发现分页列表查询", notes = "照片-发现分页列表查询")
    @GetMapping(value = "/getDiscoverImagePageList")
    @IgnoreUserToken
    @IgnoreClientToken
    public Result<IPage<ImageInfo>> getDiscoverImagePageList(@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                     @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        Result<IPage<ImageInfo>> result = new Result<>();
        IPage<ImageInfo> pageList = imageService.findDiscoverImagePageList(pageNo, pageSize);
        result.setSuccess(true);
        result.setResult(pageList);
        return result;
    }

    /**
     * 我的相册分页列表查询
     *
     * @param pageNo
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "照片-我的相册分页列表查询", notes = "照片-我的相册分页列表查询")
    @GetMapping(value = "/getMyImagePageList")
    @IgnoreUserToken
    @IgnoreClientToken
    public Result<IPage<ImageInfo>> getMyImagePageList(@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                     @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        Result<IPage<ImageInfo>> result = new Result<>();
        IPage<ImageInfo> pageList = imageService.findMyImagePageList(pageNo, pageSize);
        result.setSuccess(true);
        result.setResult(pageList);
        return result;
    }

    /**
     * 添加
     *
     * @param image
     * @return
     */
    @ApiOperation(value = "照片-添加", notes = "照片-添加")
    @PostMapping(value = "/generateImage")
    public Result<Image> generateImage(@RequestBody Image image) {
        Result<Image> result = new Result<>();
        imageService.createImage(image);
        result.success("添加成功！");
        return result;
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "照片-通过id删除", notes = "照片-通过id删除")
    @DeleteMapping(value = "/expurgateImageById")
    public Result<?> expurgateImageById(@RequestParam(name = "id", required = true) String id) {
        imageService.dropImageById(id);
        return Result.ok("删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "照片-通过id查询", notes = "照片-通过id查询")
    @GetMapping(value = "/getImageById")
    public Result<ImageInfo> getImageById(@RequestParam(name = "id", required = true) String id) {
        Result<ImageInfo> result = new Result<>();
        ImageInfo imageInfo = imageService.findImageById(id);
        result.setResult(imageInfo);
        result.setSuccess(true);
        return result;
    }

}
