package com.ylz.ai.auth.client.runner;

import com.ylz.ai.auth.client.config.AuthClientConfig;
import com.ylz.ai.auth.client.feign.IAuthClientFeign;
import com.ylz.ai.common.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @Description runner 初始化
 * @Author haifeng.lv
 * @Date 2019/12/16 17:32
 */
@Configuration
@Slf4j
public class AuthClientRunner implements CommandLineRunner {
    @Autowired
    private AuthClientConfig serviceAuthConfig;
    @Autowired
    private IAuthClientFeign authClientFeign;

    @Override
    public void run(String... args) {
        log.info("初始化加载客户pubKey");
        try {
            log.info("refreshServicePubKey,clientId={},clientSecret={}",serviceAuthConfig.getClientId(),serviceAuthConfig.getClientSecret());
            refreshServicePubKey();
        } catch (Exception ex) {
            log.error("初始化加载客户pubKey失败,1分钟后自动重试!", ex);
        }
    }

    @Scheduled(cron = "0 0/1 * * * ?")
    public void refreshServicePubKey() {
        log.info("refreshServicePubKey,clientId={},clientSecret={}",serviceAuthConfig.getClientId(), serviceAuthConfig.getClientSecret());
        Result<byte[]> clientPubKey = authClientFeign.getClientPubKey(serviceAuthConfig.getClientId(), serviceAuthConfig.getClientSecret());
        if (clientPubKey.isSuccess()) {
            this.serviceAuthConfig.setPubKeyByte(clientPubKey.getResult());
        }
    }
}
