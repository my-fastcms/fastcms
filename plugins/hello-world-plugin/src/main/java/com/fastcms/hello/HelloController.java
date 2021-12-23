package com.fastcms.hello;

import com.fastcms.common.model.RestResultUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * wjun_java@163.com
 */
@Controller
@RequestMapping("hello")
public class HelloController {

    @GetMapping
    public String index() {
        return "hello";
    }

    @GetMapping("say")
    @ResponseBody
    public Object say() {
        return RestResultUtils.success("hello fastcms");
    }

}
