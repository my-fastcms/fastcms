package com.dbumama.market.service.api.agent;

import java.math.BigDecimal;
import java.util.List;

import com.dbumama.market.model.Agent;
import com.dbumama.market.model.Order;
import com.jfinal.plugin.activerecord.Page;

/**
 * 分销代理服务
 * @author wangjun
 *
 */
public interface AgentService {

	/**
	 * 获取代理的自身以及父对象
	 * @param agentId
	 * @return
	 */
	public List<Agent> getSelfAndParent(Long agentId);
	
	/**
	 * 查询审核通过的分销商列表
	 * @param agentParam
	 * @return
	 * @throws AgentException
	 */
	Page<AgentResultDto> list(AgentParamDto agentParam) throws AgentException;

	/**
	 * 申请成为分销商代理
	 * @param applyParam
	 * @throws AgentException
	 */
	public void apply(AgentApplyParamDto applyParam) throws AgentException;
	
	/**
	 * 获取待审核分销商列表
	 * @param aduitParam
	 * @return
	 * @throws AgentException
	 */
	public Page<AgentAduitResultDto> getAduitList(AgentAduitParamDto aduitParam) throws AgentException;
	
	/**
	 * 通过分销商审核
	 * @param agentId
	 * @param opterId 操作人
	 * @throws AgentException
	 */
	public void pass(Long agentId, Long opterId) throws AgentException;
	
	/**
	 * 不通过审核
	 * @param agentId
	 * @param opterId 操作人
	 * @param content 不通过审核的理由
	 * @throws AgentException
	 */
	public void nopass(Long agentId, Long opterId, String content) throws AgentException;
	
	/**
	 * 获取代理商佣金
	 * @param agent
	 * @return
	 * @throws AgentException
	 */
	public BigDecimal getAgentCommission(Agent agent, Order order) throws AgentException;
	public Page<AgentRankResultDto> getRanktList(AgentRankParamDto rankParam) throws AgentException;
	
}
