package com.ylz.ai.mobile.service;

import com.ylz.ai.mobile.entity.UserLike;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ylz.ai.mobile.vo.request.AddUserLike;

import java.util.List;

/**
 * @Description: 用户点赞
 * @Author: haifeng.lv
 * @Date: 2020-04-22 10:46
 */
public interface IUserLikeService extends IService<UserLike> {
    boolean createUserLike(String imageId);
    boolean dropUserLikeByImageId(String id);
    List<UserLike> findUserLikeByUserId(String userId);
}
