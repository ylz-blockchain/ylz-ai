package com.ylz.ai.mobile.factory;

import com.ylz.ai.mobile.service.ILoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description 登录方式工厂
 * @Author haifeng.lv
 * @Date 2020/4/27 14:19
 */
@Component
public class LoginFactory {
    @Autowired
    private List<ILoginService> loginServices;

    /**
     * @Description 创建登录方式
     * @Author haifeng.lv
     * @param: type 登录类型
     * @Date 2020/4/27 14:21
     * @return: com.ylz.ai.mobile.service.ILoginService
     */
    public ILoginService createLoginService(int type) {
        for (ILoginService loginService : loginServices) {
            if (loginService.isSupport(type)) {
                return loginService;
            }
        }
        return null;
    }
}
