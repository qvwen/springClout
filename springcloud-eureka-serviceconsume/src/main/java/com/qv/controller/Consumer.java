package com.qv.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
@RequestMapping("/Hello")
class Consumer {
    @Resource
    private LoadBalancerClient loadBalancerClient;
    @Resource
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "hiError")
    @RequestMapping("/Consumer")
    public String helloWorld(String s){
        System.out.println("传入的值为："+s);
        //第一种调用方式
        //String forObject = new RestTemplate().getForObject("http://localhost:8071/Hello/World?s=" + s, String.class);

        //第二种调用方式
        //根据服务名 获取服务列表 根据算法选取某个服务 并访问某个服务的网络位置。
        //ServiceInstance serviceInstance = loadBalancerClient.choose("EUREKA-SERVICE");
        //String forObject = new RestTemplate().getForObject("http://"+serviceInstance.getHost()+":"+serviceInstance.getPort()+"/Hello/World?s="+s,String.class);

        //第三种调用方式 需要restTemplate注入的方式
        String forObject = restTemplate.getForObject("http://STUDY-MS/Hello/World?s=" + s, String.class);
        return forObject;
    }
    public String hiError(String name) {
        return "hi,"+name+",sorry,error!";
    }
}
