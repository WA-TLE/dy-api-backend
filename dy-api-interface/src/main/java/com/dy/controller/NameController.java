package com.dy.controller;

import com.dy.model.User;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: dy
 * @Date: 2023/12/21 17:17
 * @Description: 获取姓名接口
 */
@RestController
@RequestMapping("/api/name")
@Slf4j
public class NameController {


    @PostMapping("/user")
    public String postJsonName(@RequestBody User user, HttpServletRequest request) {

        log.info("request: {}", request);

        log.info("user: {}", user);

        System.out.println("json post 你的名字为: " + user.getUsername());
        String result = "json post 你的名字为: " + user.getUsername();


        return result;
    }


}
