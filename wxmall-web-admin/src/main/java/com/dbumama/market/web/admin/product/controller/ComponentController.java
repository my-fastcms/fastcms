package com.dbumama.market.web.admin.product.controller;

import com.dbumama.market.web.core.annotation.RouteBind;
import com.dbumama.market.web.core.controller.BaseController;
@RouteBind(path = "component", viewPath = "component")
public class ComponentController extends BaseController{
     public void index(){
    	 render("/component/component_set.html");
     }
}
