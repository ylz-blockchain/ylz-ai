package com.ylz.ai.mobile.weixin;

import com.alibaba.fastjson.JSONObject;
import com.ylz.ai.common.exception.BusinessException;
import com.ylz.ai.common.util.UrlUtils;
import com.ylz.ai.mobile.constant.ErrCodeConstant;
import com.ylz.ai.mobile.constant.WeiXinConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description 微信操作模板
 * @Author haifeng.lv
 * @Date 2020/4/26 15:26
 */
@Component
@Slf4j
public class WeixinTemplate {
    @Autowired
    private WeixinConfig weixinConfig;
    @Autowired
    private RestTemplate restTemplate;

    /**
     * @Description 微信登录
     * @Author haifeng.lv
     * @param: code 登录时获取的 code
     * @Date 2020/4/26 15:31
     * @return: com.alibaba.fastjson.JSONObject
     */
    public JSONObject login(String code) {
        // https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + weixinConfig.getAppId() +
                "&secret=" + weixinConfig.getSecret() +
                "&js_code=" + code +
                "&grant_type=" + weixinConfig.getGrantType();
        log.info("微信登录请求地址: " + url);
        return restTemplate.getForEntity(url, JSONObject.class).getBody();
    }

    /**
     * @Description 获取小程序的 access_token
     * @Author haifeng.lv
     * @Date 2020/5/19 11:34
     * @return: java.lang.String
     */
    public String getAccessToken() {
        try {
            // 请求地址 https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=appid&secret=secret
            String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&" +
                    "appid=" + weixinConfig.getAppId() + "&" +
                    "secret=" + weixinConfig.getSecret();
            log.info("access_token: " + url);

            JSONObject result = restTemplate.getForEntity(url, JSONObject.class).getBody();
            return (String) result.get(WeiXinConstant.TOKEN_CODE);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(ErrCodeConstant.GET_WEIXIN_TOKEN_ERR);
        }
    }

    /**
     * @Description 检查内容
     * @Author haifeng.lv
     * @param: content
     * @Date 2020/5/19 11:38
     * @return: boolean
     */
    public boolean checkContent(String content) {
        try {
            // 获取 token
            String accessToken = getAccessToken();

            CloseableHttpClient httpclient = HttpClients.createDefault();
            // 请求地址 https://api.weixin.qq.com/wxa/msg_sec_check?access_token=accessToken
            String url = "https://api.weixin.qq.com/wxa/msg_sec_check?access_token=" + accessToken;
            log.info("验证地址: " + url);

            HttpPost request = new HttpPost(url);
            request.addHeader("Content-Type", "application/json");
            Map<String, String> map = new HashMap<>();
            map.put("content", content);
            String body = JSONObject.toJSONString(map);
            request.setEntity(new StringEntity(body, ContentType.create("text/json", "UTF-8")));
            CloseableHttpResponse response = httpclient.execute(request);
            HttpEntity httpEntity = response.getEntity();
            // 转成string
            String result = EntityUtils.toString(httpEntity, "UTF-8");
            JSONObject jsonObject = JSONObject.parseObject(result);
            return getResult(jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    /**
     * @Description 校验图片是否有敏感信息
     * @Author haifeng.lv
     * @param: path 路径
     * @Date 2020/5/19 14:01
     * @return: java.lang.Boolean
     */
    public Boolean checkImage(String path) {
        try {
            // 获取 token
            String accessToken = getAccessToken();
            UrlUtils.FileVo fileVo = UrlUtils.returnFileInfo(path);

            CloseableHttpClient httpclient = HttpClients.createDefault();
            CloseableHttpResponse response;

            // https://api.weixin.qq.com/wxa/img_sec_check?access_token=accessToken
            String url = "https://api.weixin.qq.com/wxa/img_sec_check?access_token=" + accessToken;
            log.info("验证地址: " + url);

            HttpPost request = new HttpPost(url);
            request.addHeader("Content-Type", "application/octet-stream");
            InputStream inputStream = fileVo.getInputStream();
            byte[] byt = new byte[inputStream.available()];
            inputStream.read(byt);
            request.setEntity(new ByteArrayEntity(byt, ContentType.create(fileVo.getContentType())));
            response = httpclient.execute(request);
            HttpEntity httpEntity = response.getEntity();
            String result = EntityUtils.toString(httpEntity, "UTF-8");
            JSONObject jsonObject = JSONObject.parseObject(result);
            return getResult(jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    /**
     * 返回状态信息,可以修改为自己的逻辑
     * @param jsonObject
     * @return
     */
    private Boolean getResult(JSONObject jsonObject) {
        int errCode = (int) jsonObject.get(WeiXinConstant.CHECK_CODE);
        if (errCode == WeiXinConstant.CHECK_SUCCESS) {
            return true;
        } else if (errCode == WeiXinConstant.CHECK_ERROR) {
            return false;
        } else {
            return false;
        }
    }

}
