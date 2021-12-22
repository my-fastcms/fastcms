package com.fastcms.hello;

import com.fastcms.common.model.RestResultUtils;
import org.pf4j.Extension;
import org.pf4j.ExtensionPoint;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Extension
@Controller
@RequestMapping("hello")
public class HelloController implements ExtensionPoint {

    @GetMapping("say")
    public Object say() {
        return RestResultUtils.success("hello, fastcms");
    }

}
