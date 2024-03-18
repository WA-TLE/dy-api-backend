package com.dy.controller;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.dy.utils.JsonUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


/**
 * 网易云音乐接口
 *
 * @author csw
 */
@RestController
@Slf4j
public class NetEaseController {

    private static final ObjectMapper mapper = new ObjectMapper();

    @PostMapping("/api/comments.163")
    public String hotComments(HttpServletRequest request) {
        String url = "https://api.uomg.com/api/comments.163";
        String body = URLUtil.decode(request.getHeader("body"), CharsetUtil.CHARSET_UTF_8);
        HttpResponse httpResponse = HttpRequest.post(url)
                .body(body)
                .execute();

        String response = httpResponse.body();
        log.info("得到的数据: " + response);
        return extractData(response);
    }

    /**
     * 网易云随机音乐
     *
     * @param request
     * @return
     */
    @PostMapping("/api/rand.music")
    public String randMusic(HttpServletRequest request) {
        String url = "https://api.uomg.com/api/rand.music";
        String body = URLUtil.decode(request.getHeader("body"), CharsetUtil.CHARSET_UTF_8);
        HttpResponse httpResponse = HttpRequest.get(url + "?" + body)
                .execute();
        String response = httpResponse.body();
        log.info("得到的数据: " + response);
        return extractData(response);
    }


    /**
     * 从给定的JSON字符串中提取"data"部分。
     *
     * @param jsonStr JSON字符串
     * @return "data"部分的JSON字符串
     */
    public static String extractData(String jsonStr) {
        try {
            JsonNode rootNode = mapper.readTree(jsonStr);
            JsonNode dataNode = rootNode.path("data");
            return mapper.writeValueAsString(dataNode);
        } catch (IOException e) {
            e.printStackTrace();
            return null; // 根据需要处理异常
        }
    }


}
