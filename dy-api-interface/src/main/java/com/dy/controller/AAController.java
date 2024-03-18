package com.dy.controller;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.dy.utils.JsonUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.Random;

/**
 * @Author: dy
 * @Date: 2024/3/17 20:24
 * @Description:
 */
@RestController
@Slf4j
public class AAController {
    // TODO: 2024/3/17 目前遇到的问题: 中午乱码处理
    //  api 接口 key 失效问题

    /**
     * 历史上的今天
     *
     * @param request
     * @return
     */
    @PostMapping("/external/v1/today_history")
    public String getTodayHistory(HttpServletRequest request) {
        String url = "https://tools.mgtv100.com/external/v1/today_history";
        //  获取请求参数
        String body = URLUtil.decode(request.getHeader("body"), CharsetUtil.CHARSET_UTF_8);

        String sendUrl = url + "?" + body;
        HttpResponse httpResponse = HttpRequest.get(sendUrl).execute();
        String decodedBody = StringEscapeUtils.unescapeJava(httpResponse.body());
        log.info("解码后的数据: {}", decodedBody);

        return JsonUtils.getData(decodedBody);
    }

    /**
     * 身份证前六位查询归属地
     *
     * @param request
     * @return
     */

    @PostMapping("/API/qf.php")
    public String getSigningAndIssuingOrganization(HttpServletRequest request) {

        String url = "https://cqapi.site/API/qf.php";

        //  获取请求参数
        String body = URLUtil.decode(request.getHeader("body"), CharsetUtil.CHARSET_UTF_8);

        //  判断是否是纯数字
        if (!body.chars().allMatch(Character::isDigit)) {
            return "宝, 只能输入数字哦!";
        }
        //  校验请求参数
        if (body.length() != 6) {
            return "只能输入身份证号前六位哦!";
        }

        //  发送请求
        String sendUrl = url + "?msg=" + body;
        HttpResponse httpResponse = HttpRequest.get(sendUrl).execute();
        String response = httpResponse.body();
        log.info(response);
        return response;
    }

    /**
     * 表白情话
     *
     * @param request
     * @return
     */
    @PostMapping("/api/love")
    public String getConfessionOfLove(HttpServletRequest request) {

        String url = "https://api.vvhan.com/api/love";

        //  获取请求参数
        String body = URLUtil.decode(request.getHeader("body"), CharsetUtil.CHARSET_UTF_8);


        //  发送请求
        String sendUrl = url + "?" + body;
        HttpResponse httpResponse = HttpRequest.get(sendUrl).execute();
        String response = httpResponse.body();
        log.info(response);
        return response;
    }

    /**
     * 获取手机号归属地
     *
     * @param request
     * @return
     */
    @PostMapping("/external/v1/phone_location")
    public String getLocationOfMobilePhoneNumber(HttpServletRequest request) {

        String url = "https://tools.mgtv100.com/external/v1/phone_location";

        //  获取请求参数
        String body = URLUtil.decode(request.getHeader("body"), CharsetUtil.CHARSET_UTF_8);

        //  判断是否是纯数字
        if (!body.chars().allMatch(Character::isDigit)) {
            return "宝,手机号只能是数字哦!";
        }
        //  校验请求参数
        if (body.length() != 11) {
            return "手机号长度难道不是 11 位吗?!";
        }
        //  发送请求
        String sendUrl = url + "?phone=" + body;
        HttpResponse httpResponse = HttpRequest.get(sendUrl).execute();
        String decodedBody = StringEscapeUtils.unescapeJava(httpResponse.body());
        log.info("手机号归属地: " + decodedBody);
        return decodedBody;
    }


    /**
     * 小姐姐视频
     *
     * @param request
     * @return
     */
    @PostMapping("/Api/ScSp")
    public String getVideo(HttpServletRequest request) {
        String[] replies = {
                "呀, 同学，不好意思哈, 小姐姐们都去图书馆占座了ԅ(≖‿≖ԅ)",
                "书中自有什么玉来着(´･ω･`)?",
                "停下! 这是开放接口平台, 你想干嘛呢??",
                "如果你正在试图寻找那些令人心动的画面，不妨试着望窗外吧",
                "图片加载失败。但别灰心，生活中处处是惊喜，何不亲自去发现呢？😉",
                "你自己照下镜子不就好了? 还来这看啥",
                "视频已被未来的你拦截，他说有更重要的事情要做。",
                "你所寻找的内容已超出当前维度，建议回到现实维度尝试。"
        };
        // 使用Random类来随机选择一个回复
        Random random = new Random();
        int index = random.nextInt(replies.length); // 生成一个介于0（含）和replies.length（不含）之间的随机数
        return replies[index];
    }

    /**
     * 朋友圈文案
     *
     * @param request
     * @return
     */
    @PostMapping("/api/wr.php")
    public String getCopyWritingOnMoments(HttpServletRequest request) {
        String url = "https://api.8uid.cn/api/wr.php";
        //  解析 body
        String body = URLUtil.decode(request.getHeader("body"), CharsetUtil.CHARSET_UTF_8);
        HttpResponse httpResponse = HttpRequest.get(url + "?" + body)
                .execute();

        //  解码 httpResponse
        String decodedBody = StringEscapeUtils.unescapeJava(httpResponse.body());
        log.info("朋友圈文案: " + decodedBody);
        return decodedBody;

    }

    /**
     * 俏皮话
     *
     * @param request
     * @return
     */
    @PostMapping("/api/wenan-pp/")
    public String getWittyRemarks(HttpServletRequest request) {
        String url = "https://zj.v.api.aa1.cn/api/wenan-pp/";
        //  解析 body
        String body = URLUtil.decode(request.getHeader("body"), CharsetUtil.CHARSET_UTF_8);
        HttpResponse httpResponse = HttpRequest.get(url + "?" + body)
                .execute();

        //  解码 httpResponse
        String decodedBody = StringEscapeUtils.unescapeJava(httpResponse.body());
        log.info("俏皮话: " + decodedBody);
        return decodedBody;

    }

    /**
     * 随机摘抄文案
     *
     * @param request
     * @return
     */
    @PostMapping("/api/wenan-mj/")
    public String getBeautifulSentences(HttpServletRequest request) {
        String url = "https://zj.v.api.aa1.cn/api/wenan-mj/";
        //  解析 body
        String body = URLUtil.decode(request.getHeader("body"), CharsetUtil.CHARSET_UTF_8);
        HttpResponse httpResponse = HttpRequest.get(url + "?" + body)
                .execute();

        //  解码 httpResponse
        String decodedBody = StringEscapeUtils.unescapeJava(httpResponse.body());
        log.info("俏皮话: " + decodedBody);
        return decodedBody;

    }


}
