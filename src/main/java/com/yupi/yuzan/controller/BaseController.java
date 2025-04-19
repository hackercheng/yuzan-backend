package com.yupi.yuzan.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/base")
@Tag(name = "基础接口")
public class BaseController {

    @Operation(summary = "测试接口")
    @PostMapping("/test")
    public String test() {
        return "hello world";
    }
}
