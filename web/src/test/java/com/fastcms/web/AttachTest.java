package com.fastcms.web;

import com.fastcms.core.utils.AttachUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;

/**
 * wjun_java@163.com
 */
@SpringBootTest
public class AttachTest {

    @Test
    public void testTextWaterMark() throws IOException {
        String fastcms = AttachUtils.addTextWaterMark(new File("F:\\wangjun\\images\\E69276A0-9DCB-4d61-8965-404BAE45BCD3.png"), "fastcms");
        System.out.println(fastcms);
    }

    @Test
    public void testFileWaterMark() throws IOException {
        String fastcms = AttachUtils.addFileWaterMark(new File("F:\\wangjun\\images\\E69276A0-9DCB-4d61-8965-404BAE45BCD3.png"), new File("F:\\wangjun\\images\\warter.png"));
        System.out.println(fastcms);
    }

}
