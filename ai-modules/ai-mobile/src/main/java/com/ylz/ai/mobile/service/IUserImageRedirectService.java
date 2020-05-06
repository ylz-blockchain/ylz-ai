package com.ylz.ai.mobile.service;

import com.ylz.ai.mobile.entity.UserImageRedirect;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ylz.ai.mobile.vo.request.UserImageRedirectRequest;
import com.ylz.ai.mobile.vo.response.UserImageRedirectInfo;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description: 用户照片转发
 * @Author: haifeng.lv
 * @Date: 2020-04-24 16:06
 */
public interface IUserImageRedirectService extends IService<UserImageRedirect> {
    IPage<UserImageRedirectInfo> findUserImageRedirectPageList(UserImageRedirectRequest request, Integer pageNo, Integer pageSize);
    void createUserImageRedirect(String imageId, String redirectPlace, String description, HttpServletRequest request) throws Exception;
}
