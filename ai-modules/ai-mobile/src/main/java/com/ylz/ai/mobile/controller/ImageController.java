package com.ylz.ai.mobile.controller;

import com.ylz.ai.auth.user.annotation.IgnoreUserToken;
import com.ylz.ai.common.vo.Result;
import com.ylz.ai.mobile.service.IImageService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ylz.ai.mobile.vo.request.AddImage;
import com.ylz.ai.mobile.vo.request.ImageRequest;
import com.ylz.ai.mobile.vo.request.UpdateImageEnable;
import com.ylz.ai.mobile.vo.response.ImageInfo;
import com.ylz.ai.mobile.vo.response.ImageMinInfo;
import com.ylz.ai.mobile.vo.response.ImageStatusInfo;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import com.ylz.ai.auth.client.annotation.CheckClientToken;
import com.ylz.ai.auth.user.annotation.CheckUserToken;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
     * 照片-分页列表查询(管理端使用)
     *
     * @param request
     * @param pageNo
     * @param pageSize
     * @param sortProp
     * @param sortType
     * @return
     */
    @ApiOperation(value = "照片-分页列表查询(管理端使用)", notes = "照片-分页列表查询(管理端使用)")
    @GetMapping(value = "/getImagePageList")
    public Result<IPage<ImageMinInfo>> getImagePageList(ImageRequest request,
                                                        @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                        @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                        @RequestParam(name = "sortProp", required = false) String sortProp,
                                                        @RequestParam(name = "sortType", required = false) String sortType) {
        Result<IPage<ImageMinInfo>> result = new Result<>();
        IPage<ImageMinInfo> pageList = imageService.findImagePageList(request, pageNo, pageSize, sortProp, sortType);
        result.setSuccess(true);
        result.setResult(pageList);
        return result;
    }

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
    public Result<IPage<ImageInfo>> getDiscoverImagePageList(@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                             @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                             HttpServletRequest request) throws Exception {
        Result<IPage<ImageInfo>> result = new Result<>();
        IPage<ImageInfo> pageList = imageService.findDiscoverImagePageList(pageNo, pageSize, request);
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
     * @param addImage
     * @return
     */
    @ApiOperation(value = "照片-添加", notes = "照片-添加")
    @PostMapping(value = "/generateImage")
    public Result<String> generateImage(@RequestBody AddImage addImage) {
        Result<String> result = new Result<>();
        String imageId = imageService.createImage(addImage);
        result.success("添加成功！");
        result.setResult(imageId);
        return result;
    }

    /**
     * @Description 查询照片状态
     * @Author haifeng.lv
     * @param: id 照片 id
     * @Date 2020/4/30 17:50
     * @return: com.ylz.ai.common.vo.Result<java.lang.Integer>
     */
    @ApiOperation(value = "照片-查询照片状态", notes = "照片-查询照片状态")
    @GetMapping(value = "/getImageStatus")
    public Result<ImageStatusInfo> getImageStatus(String id) {
        Result<ImageStatusInfo> result = new Result<>();
        ImageStatusInfo response = imageService.findImageStatus(id);
        result.setResult(response);
        return result;
    }

    @ApiOperation(value = "照片是否启用-修改", notes = "照片是否启用-修改")
    @PutMapping(value = "/changeImageEnable")
    public Result changeImageEnable(@Valid @RequestBody UpdateImageEnable request) {
        Result<String> result = new Result<>();
        imageService.alterImageEnable(request.getId(), request.getIsEnable());
        result.success("修改成功！");
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
     * 批量删除
     * @param ids
     * @return
     */
    @ApiOperation(value="照片-批量删除", notes="照片-批量删除")
    @DeleteMapping(value = "/expurgateImageBatch")
    public Result<?> expurgateImageBatch(@RequestParam(name="ids",required=true) String ids) {
        imageService.dropImageBatch(ids);
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
    @IgnoreUserToken
    public Result<ImageInfo> getImageById(@RequestParam(name = "id", required = true) String id, HttpServletRequest request) throws Exception {
        Result<ImageInfo> result = new Result<>();
        ImageInfo imageInfo = imageService.findImageById(id, request);
        result.setResult(imageInfo);
        result.setSuccess(true);
        return result;
    }

    /**
     * @Description 访问照片
     * @Author haifeng.lv
     * @param: fileName
     * @Date 2020/4/28 15:52
     * @return: org.springframework.http.ResponseEntity<org.springframework.core.io.Resource>
     */
    @GetMapping("/{fileName:.+}")
    @ApiIgnore
    @IgnoreUserToken
    public ResponseEntity<Resource> showImage(@PathVariable String fileName) {
        return imageService.findImage(fileName);
    }

}
