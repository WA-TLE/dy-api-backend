package com.dy.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: dy
 * @Date: 2024/3/18 11:18
 * @Description:
 */
@Slf4j
public class JsonUtils {

    /**
     * 获取 json 中的 data 数据
     *
     * @param decodedBody
     * @return
     */
    public static String getData(String decodedBody) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode rootNode = mapper.readTree(decodedBody);
            JsonNode dataNode = rootNode.path("data").path("data");
            String dataJson = mapper.writeValueAsString(dataNode);
            log.info("提取的数据: {}", dataJson);
            return dataJson;
        } catch (Exception e) {
            log.error("处理JSON时发生错误: {}", e.getMessage());
            return "{额... 似乎遇到了一下未知错误, 错误码: 50070}";
        }
    }
}
