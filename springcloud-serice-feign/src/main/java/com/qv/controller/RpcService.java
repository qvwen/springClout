package com.qv.controller;

import com.qv.util.RpcServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;


@FeignClient(name = "STUDY-MS", fallback = RpcServiceImpl.class)
public interface RpcService {
    @RequestMapping("/Hello/World")
    String helloWorld(String helloWorld);
}
