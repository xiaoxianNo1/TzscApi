package com.xiaoxian.service;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

public class HelloImpl {
    @RequestMapping("/HelloImpl")
    @ResponseBody
    public String HelloImpl(){
        return "Hello";
    }
}
