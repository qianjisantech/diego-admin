package com.qianjisan.common.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "邮件管理", description = "邮件相关接口")
@RestController
@RequestMapping("/common-api")
@RequiredArgsConstructor
@Slf4j
public class EmailController {
}
