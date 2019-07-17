package cn.zsy.thermos.service;

import cn.zsy.thermos.domain.User;
import cn.zsy.thermos.dubbo.service.RestService;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.apache.dubbo.rpc.RpcContext;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


@Slf4j
@Service(version = "1.0.0")
@RestController
public class SpringRestService implements RestService {

    @Override
    @GetMapping(value = "/param")
    @SentinelResource(value = "doSomeThingaA", blockHandler = "exceptionHandler")
    public String param(@RequestParam String param) {
        log("/param", param);
        return param;
    }

    public String exceptionHandler(String param, BlockException ex) {
        log.error("blockHandler：" + param, ex);
        return "blockHandler";
    }

    @Override
    @PostMapping("/params")
    public String params(@RequestParam int a, @RequestParam String b) {
        log("/params", a + b);
        return a + b;
    }

    @Override
    @GetMapping("/headers")
    public String headers(@RequestHeader("h") String header,
                          @RequestHeader("h2") String header2,
                          @RequestParam("v") Integer param) {
        String result = header + " , " + header2 + " , " + param;
        log("/headers", result);
        return result;
    }

    @Override
    @GetMapping("/path-variables/{p1}/{p2}")
    public String pathVariables(@PathVariable("p1") String path1,
                                @PathVariable("p2") String path2,
                                @RequestParam("v") String param) {
        String result = path1 + " , " + path2 + " , " + param;
        log("/path-variables", result);
        return result;
    }

    @Override
    @PostMapping("/form")
    public String form(@RequestParam("f") String form) {
        return String.valueOf(form);
    }

    @Override
    @PostMapping(value = "/request/body/map", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public User requestBodyMap(@RequestBody Map<String, Object> data, @RequestParam("param") String param) {
        User user = new User();
        user.setId(((Integer) data.get("id")).longValue());
        user.setName((String) data.get("name"));
        user.setAge((Integer) data.get("age"));
        log("/request/body/map", param);
        return user;
    }

    @PostMapping(value = "/request/body/user", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @Override
    public Map<String, Object> requestBodyUser(@RequestBody User user) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", user.getId());
        map.put("name", user.getName());
        map.put("age", user.getAge());
        log("/request/body/user", map);
        return map;
    }

    public static void log(String url, Object result) {
        String message = String.format("The client[%s] uses '%s' protocol to call %s : %s",
                RpcContext.getContext().getRemoteHostName(),
                RpcContext.getContext().getUrl() == null ? "N/A" : RpcContext.getContext().getUrl().getProtocol(),
                url,
                result
        );
        System.out.println(message);
    }

}

