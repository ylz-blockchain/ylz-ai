package com.ylz.ai.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ylz.ai.admin.entity.Role;

/**
 * @Description: 角色
 * @Author: haifeng.lv
 * @Date: 2020-01-13 17:26
 */
public interface IRoleService extends IService<Role> {
    IPage<Role> findRolePageList(Role role, Integer pageNo, Integer pageSize, String sortProp, String sortType);
    boolean createRole(Role role);
    boolean alterRoleById(Role role);
    boolean dropRoleById(String id);
    boolean dropRoleBatch(String ids);
    Role findRoleById(String id);
}
