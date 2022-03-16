package com.fastcms.hello;

import com.fastcms.core.directive.BaseFunction;
import com.fastcms.plugin.Directive;
import freemarker.template.TemplateModelException;

import java.util.List;

/**
 * @author： wjun_java@163.com
 * @date： 2022/3/16
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Directive("helloFun")
public class HelloFunction extends BaseFunction {

    @Override
    public Object exec(List list) throws TemplateModelException {
        return "hello plugin function";
    }

}
