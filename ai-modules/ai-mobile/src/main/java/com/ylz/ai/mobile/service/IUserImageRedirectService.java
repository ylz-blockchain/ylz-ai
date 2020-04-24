package com.ylz.ai.mobile.service;

import com.ylz.ai.mobile.entity.UserImageRedirect;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 用户照片转发
 * @Author: haifeng.lv
 * @Date: 2020-04-24 16:06
 */
public interface IUserImageRedirectService extends IService<UserImageRedirect> {
    void createUserImageRedirect(String imageId, String redirectPlace, String description);
}
