package cn.zsy.thermos.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
public class DemoConfigController {

    @Value("${didispace.title:}")
    private String title;

    @Value("${xxxprovider.application:}")
    private String application;

    @Value("${didispace.sex:}")
    private String sex;

    @Value("${didispace.name:}")
    private String name;

    @GetMapping("/config/test")
    public String testConfig() {
        return "222" + title + "ccc" + application + "------" + sex + "++++" + name;
    }
}
