package com.dy.client;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.dy.model.User;
import com.dy.utils.SignUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: dy
 * @Date: 2023/12/21 17:35
 * @Description:
 */
public class DyApiClient {

    private String accessKey;
    private String secretKey;
    public static final String REQUEST_ADDRESS = "http://localhost:8090";
    public DyApiClient() {
    }

    public DyApiClient(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    public String getName(String name) {
        //可以单独传入http参数，这样参数会自动做URL编码，拼接在URL中
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", "dy");

        String result = HttpUtil.get(REQUEST_ADDRESS + "/api/name", paramMap);
        System.out.println(result);
        return result;
    }

    public String postName(String name) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", "dy");

        String result = HttpUtil.post(REQUEST_ADDRESS + "/api/name", paramMap);
        System.out.println(result);
        return result;
    }

    public String postJsonName(User user) {

        String json = JSONUtil.toJsonStr(user);

        HttpResponse httpResponse = HttpRequest.post(REQUEST_ADDRESS + "/api/name/json")
                .addHeaders(getHeaderMap(json))
                .body(json)
                .execute();

        System.out.println(httpResponse.getStatus());

        String result = httpResponse.body();
        System.out.println(result);

        return result;
    }

    private Map<String, String> getHeaderMap(String body) {
        HashMap<String, String> header = new HashMap<>();
        header.put("accessKey", accessKey);
//        header.put("secretKey", secretKey);

        //  增加参数
        header.put("nonce", RandomUtil.randomNumbers(4));
        header.put("body", body);

        header.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
        header.put("sign", SignUtils.getSign(body, secretKey));



        return header;
    }


}
