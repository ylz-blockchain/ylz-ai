package com.ylz.ai.auth.server.modules.client.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ylz.ai.auth.server.modules.client.entity.AuthClientService;

/**
 * @Description: 认证客户端服务
 * @Author: haifeng.lv
 * @Date: 2019-12-05
 */
public interface AuthClientServiceMapper extends BaseMapper<AuthClientService> {
    void deleteByServiceId(String id);
}
