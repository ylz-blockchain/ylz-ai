package com.ylz.ai.auth.user.feign;

import com.ylz.ai.auth.client.feign.IAuthClientFeign;
import com.ylz.ai.common.vo.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Description 获取授权服务得公钥与私钥
 * @Author haifeng.lv
 * @Date 2019/12/16 17:31
 */
@FeignClient(value = "${auth.serviceId}")
public interface IAuthUserFeign extends IAuthClientFeign {
    @RequestMapping(value = "/authClient/userPubKey", method = RequestMethod.POST)
    Result<byte[]> getUserPublicKey(@RequestParam("clientId") String clientId, @RequestParam("secret") String secret);
}
