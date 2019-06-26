package cn.zsy.thermos.service;

import cn.zsy.thermos.domain.User;
import org.springframework.cloud.alibaba.dubbo.annotation.DubboTransported;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@FeignClient("${provider.application.name}")
@DubboTransported(protocol = "dubbo")
public interface DubboFeignRestService {

    @GetMapping(value = "/param")
    String param(@RequestParam("param") String param);

    @PostMapping("/params")
    String params(@RequestParam("b") String paramB, @RequestParam("a") int paramA);

    @PostMapping(value = "/request/body/map", produces = APPLICATION_JSON_UTF8_VALUE)
    User requestBody(@RequestParam("param") String param, @RequestBody Map<String, Object> data);

    @GetMapping("/headers")
    String headers(@RequestHeader("h2") String header2,
                   @RequestParam("v") Integer value,
                   @RequestHeader("h") String header);

    @GetMapping("/path-variables/{p1}/{p2}")
    String pathVariables(@RequestParam("v") String param,
                         @PathVariable("p2") String path2,
                         @PathVariable("p1") String path1);

    @PostMapping(value = "/request/body/user", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Map<String, Object> requestBodyUser(@RequestBody User user);
}
