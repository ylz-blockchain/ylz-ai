package com.ylz.ai.mobile.service;

import com.ylz.ai.mobile.entity.Image;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ylz.ai.mobile.vo.request.AddImage;
import com.ylz.ai.mobile.vo.request.ImageRequest;
import com.ylz.ai.mobile.vo.response.ImageInfo;
import com.ylz.ai.mobile.vo.response.ImageMinInfo;
import com.ylz.ai.mobile.vo.response.ImageStatusInfo;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Description: 照片
 * @Author: haifeng.lv
 * @Date: 2020-04-20 14:25
 */
public interface IImageService extends IService<Image> {
    IPage<ImageMinInfo> findImagePageList(ImageRequest request, Integer pageNo, Integer pageSize, String sortProp, String sortType);
    IPage<ImageInfo> findIndexImagePageList(Integer pageNo, Integer pageSize, HttpServletRequest request) throws Exception;
    IPage<ImageInfo> findDiscoverImagePageList(Integer pageNo, Integer pageSize, HttpServletRequest request) throws Exception;
    IPage<ImageInfo> findMyImagePageList(Integer pageNo, Integer pageSize);
    boolean alterImageLikeById(String id, Integer number);
    ImageStatusInfo findImageStatus(String id);
    ImageInfo findImageById(String id, HttpServletRequest request) throws Exception;
    String createImage(AddImage addImage);
    boolean dropImageById(String id);
    boolean dropImageBatch(String ids);
    List<Image> findImagesByIds(List<String> ids);
    void alterImageRedirectById(String imageId);
    ResponseEntity<Resource> findImage(String fileName);
}
