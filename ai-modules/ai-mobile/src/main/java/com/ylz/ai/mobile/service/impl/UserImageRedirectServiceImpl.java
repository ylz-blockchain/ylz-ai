package com.ylz.ai.mobile.service.impl;

import com.ylz.ai.auth.user.service.AuthUserService;
import com.ylz.ai.common.context.BaseContextHandler;
import com.ylz.ai.mobile.entity.*;
import com.ylz.ai.mobile.mapper.UserImageRedirectMapper;
import com.ylz.ai.mobile.service.IFrontUserService;
import com.ylz.ai.mobile.service.IImageLinkedService;
import com.ylz.ai.mobile.service.IImageService;
import com.ylz.ai.mobile.service.IUserImageRedirectService;
import com.ylz.ai.common.util.EntityUtils;
import com.ylz.ai.mobile.vo.request.UserImageRedirectRequest;
import com.ylz.ai.mobile.vo.response.UserImageRedirectInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Description: 用户照片转发
 * @Author: haifeng.lv
 * @Date: 2020-04-24 16:06
 */
@Service
public class UserImageRedirectServiceImpl extends ServiceImpl<UserImageRedirectMapper, UserImageRedirect> implements IUserImageRedirectService, IImageLinkedService {
    @Autowired
    private IImageService imageService;
    @Autowired
    private AuthUserService authUserService;
    @Autowired
    private IFrontUserService frontUserService;

    @Override
    public IPage<UserImageRedirectInfo> findUserImageRedirectPageList(UserImageRedirectRequest request, Integer pageNo, Integer pageSize) {
        Page<UserImageRedirectInfo> page = new Page<>(pageNo, pageSize);
        IPage<UserImageRedirectInfo> pageList = baseMapper.selectUserImageRedirectPageList(page, request);
        return pageList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createUserImageRedirect(String imageId, String redirectPlace, String description, HttpServletRequest request) throws Exception {
        UserImageRedirect userImageRedirect = new UserImageRedirect();
        EntityUtils.setDefaultValue(userImageRedirect);
        userImageRedirect.setImageId(imageId);
        // 获取用户 token
        if (authUserService.setCurrentUserInfo(request)) {
            userImageRedirect.setUserId(BaseContextHandler.getUserId());
        } else {
            // 默认匿名用户
            userImageRedirect.setUserId("1");
        }
        // 描述
        userImageRedirect.setDescription(description);
        // 转发目标地
        userImageRedirect.setRedirectPlace(redirectPlace);

        // 修改转发数
        imageService.alterImageRedirectById(imageId);
        // 添加转发记录
        this.baseMapper.insert(userImageRedirect);
    }

    /**
     * @Description 级联删除
     * @Author haifeng.lv
     * @param: id 照片 id
     * @Date 2020/4/29 16:15
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteLink(String id) {
        QueryWrapper<UserImageRedirect> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("image_id", id);
        super.remove(queryWrapper);
    }

    @Override
    public void deleteBatchLink(List<String> ids) {
        QueryWrapper<UserImageRedirect> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("image_id", ids);
        super.remove(queryWrapper);
    }
}
