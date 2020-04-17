package com.ylz.ai.mobile.service;

import com.ylz.ai.mobile.entity.FrontUser;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description: 前端用户
 * @Author: haifeng.lv
 * @Date: 2020-04-17 14:22
 */
public interface IFrontUserService extends IService<FrontUser> {
    IPage<FrontUser> findFrontUserPageList(FrontUser frontUser, Integer pageNo, Integer pageSize, String sortProp, String sortType);
    boolean createFrontUser(FrontUser frontUser);
    boolean alterFrontUserById(FrontUser frontUser);
    boolean dropFrontUserById(String id);
    boolean dropFrontUserBatch(String ids);
    FrontUser findFrontUserById(String id);
    void validate(String id, String name, HttpServletRequest httpServletRequest);
}
