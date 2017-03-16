package com.dbumama.market.web.admin.agent.controller;

import java.util.List;

import com.dbumama.market.model.Agent;
import com.dbumama.market.service.api.agent.AgentAduitParamDto;
import com.dbumama.market.service.api.agent.AgentException;
import com.dbumama.market.service.api.agent.AgentParamDto;
import com.dbumama.market.service.api.agent.AgentService;
import com.dbumama.market.web.core.annotation.RouteBind;
import com.dbumama.market.web.core.controller.AdminBaseController;
import com.dbumama.market.web.core.plugin.spring.Inject.BY_NAME;

/**
 * 分销商
 * @author wangjun
 *
 */
@RouteBind(path="agent")
public class AgentController extends AdminBaseController<Agent>{
	
	@BY_NAME
	private AgentService agentService;

	/**
	 * 分销商
	 */
	public void index(){
		Long agentId = getParaToLong("agentId");
		if(agentId != null){
			List<Agent> agents = agentService.getSelfAndParent(agentId);
			setAttr("agents", agents);
			setAttr("parentAgentId", agentId);
		}
		render("/agent/agent_index.html");
	}

	/**
	 * 已审核分销商列表
	 */
	public void list(){
		AgentParamDto agentParam = new AgentParamDto(getSellerId(), getPageNo());
		agentParam.setAgentName(getPara("agentName"));
		agentParam.setAgentPhone(getPara("agentPhone"));
		agentParam.setWxNick(getPara("wxNick"));
		if(getParaToLong("parentId") != null){
			agentParam.setParentId(getParaToLong("parentId"));
		}
		try {
			rendSuccessJson(agentService.list(agentParam));
		} catch (AgentException e) {
			rendFailedJson(e.getMessage());
		}
	}
	
	/**
	 * 待审核分销商
	 */
	public void aduit(){
		render("/agent/agent_aduit_index.html");
	}
	
	/**
	 * 待审核分销商列表
	 */
	public void aduitList(){
		AgentAduitParamDto aduitParamDto = new AgentAduitParamDto(getSellerId(), getPageNo());
		aduitParamDto.setAgentName(getPara("agentName"));
		aduitParamDto.setAgentPhone(getPara("agentPhone"));
		aduitParamDto.setWxNick(getPara("wxNick"));
		aduitParamDto.setStatus(getParaToInt("status"));
		try {
			rendSuccessJson(agentService.getAduitList(aduitParamDto));			
		} catch (AgentException e) {
			rendFailedJson(e.getMessage());
		}
	}
	
	/**
	 * 审核不通过
	 */
	public void nopass(){
		final Long agentId = getParaToLong("ids", -100L);
		final String content = getPara("content");
		
		try {
			agentService.nopass(agentId, getSellerId(), content);
			rendSuccessJson();
		} catch (AgentException e) {
			rendFailedJson(e.getMessage());
		}
	}
	
	/**
	 * 审核通过
	 */
	public void pass(){
		final Long agentId = getParaToLong("ids", -100L);
		try {
			agentService.pass(agentId, getSellerId());
			rendSuccessJson();
		} catch (AgentException e) {
			rendFailedJson(e.getMessage());
		}
	}
	
	@Override
	protected Class<Agent> getModelClass() {
		return Agent.class;
	}

}
