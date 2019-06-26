package cn.zsy.thermos.controller;

import cn.zsy.thermos.domain.User;
import cn.zsy.thermos.service.FeignRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.alibaba.dubbo.annotation.DubboTransported;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
public class DemoController {

    @Autowired
    @Lazy
    private FeignRestService feignRestService;

    @Value("${provider.application.name}")
    private String providerApplicationName;

    @Autowired
    @LoadBalanced
    private RestTemplate restTemplate;


    @Bean
    @LoadBalanced
    @DubboTransported
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }


    @RequestMapping(value = "/alibaba", method = {RequestMethod.POST, RequestMethod.GET})
    public String devBaseInfoInit() {
        String str = feignRestService.param("hello word");
        System.out.println("str: " + str);
        String strA = feignRestService.pathVariables("b", "a", "c");
        System.out.println("strA: " + strA);
        String strB = feignRestService.headers("b", "a", 10);
        System.out.println("strB: " + strB);
        String strC = feignRestService.params("1", 1);
        System.out.println("strc: " + strC);
        Map<String, Object> data = new HashMap<>();
        data.put("id", 1);
        data.put("name", "小马哥");
        data.put("age", 33);
        System.out.println("data: " + data.toString());

        User user = feignRestService.requestBody("Hello,World", data);
        System.out.println("user:---> " + user);

        Map<String, Object> map = feignRestService.requestBodyUser(user);
        map.get("id");
        map.get("name");
        map.get("age");
        System.out.println("map: " + map.toString());
        return user.toString();
    }

    @RequestMapping(value = "/", method = {RequestMethod.POST, RequestMethod.GET})
    public String tessss() {

        return "asdfasdfsfd";
    }


}



