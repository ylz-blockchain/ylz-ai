package com.ylz.ai.mobile.service.impl;

import com.ylz.ai.common.context.BaseContextHandler;
import com.ylz.ai.mobile.entity.UserImageRedirect;
import com.ylz.ai.mobile.entity.UserLike;
import com.ylz.ai.mobile.mapper.UserImageRedirectMapper;
import com.ylz.ai.mobile.service.IImageService;
import com.ylz.ai.mobile.service.IUserImageRedirectService;
import com.ylz.ai.common.error.ErrCodeBaseConstant;
import com.ylz.ai.common.exception.BusinessException;
import com.ylz.ai.common.query.QueryGenerator;
import com.ylz.ai.common.util.EntityUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.Arrays;

/**
 * @Description: 用户照片转发
 * @Author: haifeng.lv
 * @Date: 2020-04-24 16:06
 */
@Service
public class UserImageRedirectServiceImpl extends ServiceImpl<UserImageRedirectMapper, UserImageRedirect> implements IUserImageRedirectService {
    @Autowired
    private IImageService imageService;

    @Override
    public void createUserImageRedirect(String imageId, String redirectPlace, String description) {
        String userId = BaseContextHandler.getUserId();
        UserImageRedirect userImageRedirect = new UserImageRedirect();
        EntityUtils.setDefaultValue(userImageRedirect);
        userImageRedirect.setImageId(imageId);
        userImageRedirect.setUserId(userId);
        // 描述
        userImageRedirect.setDescription(description);
        // 转发目标地
        userImageRedirect.setRedirectPlace(redirectPlace);

        imageService.alterImageRedirectById(imageId);
    }
}
