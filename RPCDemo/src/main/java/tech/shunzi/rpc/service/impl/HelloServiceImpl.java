package tech.shunzi.rpc.service.impl;

import tech.shunzi.rpc.service.HelloService;

public class HelloServiceImpl implements HelloService {

    @Override
    public String sayHi(String name) {
        return "Hi, " + name;
    }
}
