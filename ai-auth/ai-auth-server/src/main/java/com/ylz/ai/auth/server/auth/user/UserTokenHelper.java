package com.ylz.ai.auth.server.auth.user;

import com.ylz.ai.auth.server.configuration.KeyConfiguration;
import com.ylz.ai.common.auth.AuthHelper;
import com.ylz.ai.common.vo.AuthInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Description 用户 token
 * @Author haifeng.lv
 * @Date 2019/12/19 10:41
 */
@Component
public class UserTokenHelper {
    @Autowired
    private KeyConfiguration keyConfiguration;

    /**
     * @Description 秘钥加密token
     * @Author haifeng.lv
     * @param: authInfo 授权信息
     * @Date 2019/12/16 17:34
     * @return: java.lang.String
     */
    public String generateToken(AuthInfo authInfo) throws Exception {
        return AuthHelper.generateToken(authInfo, keyConfiguration.getUserPriKey(), Integer.MAX_VALUE);
    }

    /**
     * @Description 获取用户 token
     * @Author haifeng.lv
     * @param: token
     * @Date 2019/12/21 16:31
     * @return: com.lvhaifeng.cloud.common.vo.AuthInfo
     */
    public AuthInfo getInfoFromToken(String token) throws Exception {
        AuthInfo authInfo = AuthHelper.getInfoFromToken(token, keyConfiguration.getUserPubKey());
        return authInfo;
    }
}
