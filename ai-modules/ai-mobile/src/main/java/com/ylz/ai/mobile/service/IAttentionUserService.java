package com.ylz.ai.mobile.service;

import com.ylz.ai.mobile.entity.AttentionUser;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ylz.ai.mobile.vo.response.UserInfo;

import java.util.List;

/**
 * @Description: 关注用户
 * @Author: haifeng.lv
 * @Date: 2020-04-20 14:27
 */
public interface IAttentionUserService extends IService<AttentionUser> {
    List<UserInfo> findAttentionUsers();
    List<UserInfo> findBeAttentionUsers(String id);
    boolean attentionUsers(String id);
    boolean cleanAttentionUsers(String id);
    Integer findUserAttentionCountByUserId(String userId, boolean flag);
}
