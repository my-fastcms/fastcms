package com.fastcms.hello;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * wjun_java@163.com
 */
@Controller
@RequestMapping("hello")
public class HelloController {

    @GetMapping("say")
    public Object say() {
        return "hello";
    }

}
