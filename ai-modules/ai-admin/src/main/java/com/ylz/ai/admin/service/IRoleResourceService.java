package com.ylz.ai.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ylz.ai.admin.entity.RoleResource;
import com.ylz.ai.admin.vo.request.AddRoleResource;

import java.util.List;

/**
 * @Description: 角色资源
 * @Author: haifeng.lv
 * @Date: 2020-01-13 17:26
 */
public interface IRoleResourceService extends IService<RoleResource> {
    IPage<RoleResource> findRoleResourcePageList(RoleResource roleResource, Integer pageNo, Integer pageSize, String sortProp, String sortType);
    boolean createRoleResource(RoleResource roleResource);
    void alterRoleResourceByRoleId(AddRoleResource request);
    boolean dropRoleResourceById(String id);
    boolean dropRoleResourceBatch(String ids);
    RoleResource findRoleResourceById(String id);
    void removeByResourceId(List<String> ids);
}
