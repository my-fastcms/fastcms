package com.dbumama.market.web.mobile.user.controller;

import java.util.List;

import com.dbumama.market.model.Agent;
import com.dbumama.market.service.api.agent.AgentService;
import com.dbumama.market.web.core.annotation.RouteBind;
import com.dbumama.market.web.core.controller.BaseMobileController;
import com.dbumama.market.web.core.plugin.spring.Inject.BY_NAME;

@RouteBind(path = "user")
public class UserController extends BaseMobileController{

	@BY_NAME
	private AgentService agentService;
	
	public void index(){
		Agent agent = Agent.dao.findFirst(" select * from " + Agent.table + " where buyer_id=? ", getBuyerId());
		if(agent !=null && agent.getStatus() == 1){
			//有审核通过的分销代理
			List<Agent> agents = agentService.getSelfAndParent(agent.getId());
			if(agents.size() != 3){
				//说明不是第三级代理，可以发展下级代理
				setAttr("agent", agent);
				List<Agent> childrenAgents = Agent.dao.find("select * from " + Agent.table + " where parent_id=? ", agent.getId());
				setAttr("childrenAgents", childrenAgents);
			}
		}
		render("/user/index.html");
	}

}
