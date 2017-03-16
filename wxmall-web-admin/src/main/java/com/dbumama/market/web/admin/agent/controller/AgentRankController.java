package com.dbumama.market.web.admin.agent.controller;

import com.dbumama.market.model.AgentRank;
import com.dbumama.market.service.api.agent.AgentRankParamDto;
import com.dbumama.market.service.api.agent.AgentRankResultDto;
import com.dbumama.market.service.api.agent.AgentService;
import com.dbumama.market.web.core.annotation.RouteBind;
import com.dbumama.market.web.core.controller.AdminBaseController;
import com.dbumama.market.web.core.plugin.spring.Inject.BY_NAME;
import com.jfinal.plugin.activerecord.Page;

/**
 * 分销商等级
 * @author wangjun
 *
 */
@RouteBind(path = "agent/rank")
public class AgentRankController extends AdminBaseController<AgentRank>{

	@BY_NAME
	private AgentService agentService;
	public void index(){
		render("/agent/agent_rank_index.html");
	}
	
	
	public void list(){
		AgentRankParamDto  agentRankParamDto=new AgentRankParamDto(getSellerId(), getPageNo());
		agentRankParamDto.setRankName(getPara("rank_name"));
		try {
			Page<AgentRankResultDto> pages=agentService.getRanktList(agentRankParamDto);
			rendSuccessJson(pages);
		} catch (Exception e) {
			rendFailedJson(e.getMessage());
		}
	}
	
	public void addRank(){
		Long id=getParaToLong("id");
		AgentRank rank=AgentRank.dao.findById(id);
		setAttr("rand", rank);
		render("/agent/agent_rank_set.html");
	}
	
	public void saveRank(){
		try {
			AgentRank agentRank=getModel();
			if(agentRank.getId() == null){
				agentRank.setSellerId(getSellerId());
				agentRank.setActive(true);
				agentRank.save();
			}else{
				agentRank.update();	
			}
			
			rendSuccessJson();
		} catch (Exception e) {
			rendFailedJson(e.getMessage());
		}
	}
	
	@Override
	protected Class<AgentRank> getModelClass() {
		return AgentRank.class;
	}

}
