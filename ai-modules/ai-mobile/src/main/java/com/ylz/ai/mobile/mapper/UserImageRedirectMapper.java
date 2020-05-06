package com.ylz.ai.mobile.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ylz.ai.mobile.entity.UserImageRedirect;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ylz.ai.mobile.vo.request.UserImageRedirectRequest;
import com.ylz.ai.mobile.vo.response.UserImageRedirectInfo;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 用户照片转发
 * @Author: haifeng.lv
 * @Date: 2020-04-24 16:06
 */
public interface UserImageRedirectMapper extends BaseMapper<UserImageRedirect> {
    /**
     * @Description 用户转发查询列表
     * @Author haifeng.lv
     * @param: page
     * @param: request
     * @Date 2020/4/30 10:52
     * @return: com.baomidou.mybatisplus.core.metadata.IPage<com.ylz.ai.mobile.vo.response.UserImageRedirectInfo>
     */
    IPage<UserImageRedirectInfo> selectUserImageRedirectPageList(@Param("page") Page<UserImageRedirectInfo> page, @Param("userImageRedirect") UserImageRedirectRequest request);
}
