package com.dbumama.market.web.core.route;

import java.util.List;
import java.util.Set;

import com.dbumama.market.web.core.annotation.ClassUtils;
import com.dbumama.market.web.core.annotation.RouteBind;
import com.dbumama.market.web.core.install.InstallController;
import com.dbumama.market.web.core.ueditor.controller.UeditorApiController;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.jfinal.config.Routes;
import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;
import com.jfinal.log.Log;

public class AutoBindRoutes extends Routes {

	private List<Class<? extends Controller>> excludeClasses = Lists.newArrayList();

	protected final Log logger = Log.getLog(getClass());

	private String suffix = "Controller";

	@SuppressWarnings("unchecked")
	public AutoBindRoutes addExcludeClasses(Class<? extends Controller>... clazzes) {
		if (clazzes != null) {
			for (Class<? extends Controller> clazz : clazzes) {
				excludeClasses.add(clazz);
			}
		}
		return this;
	}

	public AutoBindRoutes addExcludeClasses(List<Class<? extends Controller>> clazzes) {
		excludeClasses.addAll(clazzes);
		return this;
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void config() {
		excludeClasses.add(InstallController.class);
		excludeClasses.add(UeditorApiController.class);
		
		Set<Class<Controller>> controllerClassList = ClassUtils.scanSubClass(Controller.class);
		RouteBind controllerBind = null;
		for (Class controller : controllerClassList) {
			if (excludeClasses.contains(controller)) {
				continue;
			}
			controllerBind = (RouteBind) controller.getAnnotation(RouteBind.class);
			if (controllerBind == null) {
				this.add(controllerKey(controller), controller);
			} else if (StrKit.isBlank(controllerBind.viewPath())) {
				this.add(controllerBind.path(), controller);
			} else {
				this.add(controllerBind.path(), controller, controllerBind.viewPath());
			}
		}
	}

	private String controllerKey(Class<Controller> clazz) {
		Preconditions.checkArgument(clazz.getSimpleName().endsWith(suffix),
				clazz.getName() + " is not annotated with @ControllerBind and not end with " + suffix);
		String controllerKey = "/" + StrKit.firstCharToLowerCase(clazz.getSimpleName());
		controllerKey = controllerKey.substring(0, controllerKey.indexOf(suffix));
		return controllerKey;
	}

	public AutoBindRoutes suffix(String suffix) {
		this.suffix = suffix;
		return this;
	}

}
