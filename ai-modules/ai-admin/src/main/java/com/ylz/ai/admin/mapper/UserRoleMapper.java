package com.ylz.ai.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ylz.ai.admin.entity.UserRole;

import java.util.List;

/**
 * @Description: 用户角色
 * @Author: haifeng.lv
 * @Date: 2020-01-13 17:26
 */
public interface UserRoleMapper extends BaseMapper<UserRole> {
    /**
     * @Description 批量插入
     * @Author haifeng.lv
     * @param: userRoles
     * @Date 2020/1/17 11:05
     * @return: boolean
     */
    void insertBatch(List<UserRole> userRoles);
}
