package com.fastcms.web;

import com.fastcms.core.template.Template;
import com.fastcms.core.template.TemplateService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * wjun_java@163.com
 */
@SpringBootTest
public class AttachTest {

    @Autowired
    private TemplateService templateService;

    @Test
    public void test() {
        Template currTemplate = templateService.getCurrTemplate();
    }

}
