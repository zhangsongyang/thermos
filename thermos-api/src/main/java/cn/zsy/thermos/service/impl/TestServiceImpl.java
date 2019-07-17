package cn.zsy.thermos.service.impl;

import cn.zsy.thermos.service.TestService;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class TestServiceImpl implements TestService {

    // 限流与阻塞处理
    @Override
    @SentinelResource(value = "doSomeThing", blockHandler = "exceptionHandler")
    public String doSomeThing(String str) {
        log.info(str);
        return str;
    }

    public String exceptionHandler(String str, BlockException ex) {
        log.error("blockHandler：" + str, ex);
        return str;
    }

    // 熔断与降级处理
    @Override
    @SentinelResource(value = "doSomeThing2", fallback = "fallbackHandler")
    public void doSomeThing2(String str) {
        log.info(str);
        throw new RuntimeException("发生异常");
    }

    public void fallbackHandler(String str) {
        log.error("fallbackHandler：" + str);
    }

}