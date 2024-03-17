package com.dy.client;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import com.dy.utils.SignUtils;
import lombok.extern.slf4j.Slf4j;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: dy
 * @Date: 2023/12/21 17:35
 * @Description:
 */
@Slf4j
public class DyApiClient {
    
    

    private String accessKey;
    private String secretKey;
//    public static  String GATEWAY_HOST = "http://localhost:8090";
    public static  String GATEWAY_HOST = "http://8.130.9.216:8090";
    public DyApiClient() {
    }

    public DyApiClient(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    public void setHOST(String host) {
        GATEWAY_HOST = host;
    }


    public String invokeInterface(String params, String url, String method) {

        log.info("client: 网关地址 -> {}", GATEWAY_HOST);
        // TODO: 2024/3/1 防止编码异常
        HttpResponse httpResponse = HttpRequest.post(GATEWAY_HOST + url)
                .header("Accept-Charset", CharsetUtil.UTF_8)
                .addHeaders(getHeaderMap(params, method))
                .body(params)
                .execute();

        //  端点调试一下这个是个啥玩意
        String body = httpResponse.body();

        return JSONUtil.formatJsonStr(body);
    }

    private Map<String, String> getHeaderMap(String body, String method) {
        HashMap<String, String> map = new HashMap<>();
        map.put("accessKey", accessKey);
        map.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
        map.put("randomNum", RandomUtil.randomNumbers(4));
        map.put("sign", SignUtils.getSign(body, secretKey));
        //  看一下 body 变成什么样子了
        body = URLEncoder.encode(body, CharsetUtil.CHARSET_UTF_8);
        map.put("body", body);
        map.put("method", method);
        return map;
    }


}
