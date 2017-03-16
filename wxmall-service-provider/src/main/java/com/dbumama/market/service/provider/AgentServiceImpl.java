package com.dbumama.market.service.provider;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dbumama.market.model.Agent;
import com.dbumama.market.model.AgentAduitLog;
import com.dbumama.market.model.AgentRank;
import com.dbumama.market.model.Area;
import com.dbumama.market.model.CommissionRate;
import com.dbumama.market.model.Order;
import com.dbumama.market.model.UserCode;
import com.dbumama.market.service.api.agent.AgentAduitParamDto;
import com.dbumama.market.service.api.agent.AgentAduitResultDto;
import com.dbumama.market.service.api.agent.AgentApplyParamDto;
import com.dbumama.market.service.api.agent.AgentException;
import com.dbumama.market.service.api.agent.AgentParamDto;
import com.dbumama.market.service.api.agent.AgentRankParamDto;
import com.dbumama.market.service.api.agent.AgentRankResultDto;
import com.dbumama.market.service.api.agent.AgentResultDto;
import com.dbumama.market.service.api.agent.AgentService;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

import cn.dreampie.encription.EncriptionKit;

@Service("agentService")
public class AgentServiceImpl implements AgentService{

	@Override
	public Page<AgentResultDto> list(AgentParamDto agentParam) throws AgentException {
		if(agentParam == null || agentParam.getSellerId() == null)
			throw new AgentException("调用分销商列表接口缺少必要参数");
		final StringBuffer where = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		params.add(agentParam.getSellerId());
		if(StrKit.notBlank(agentParam.getAgentName())){
			where.append(" and a.agent_name like ? ");
			params.add("%"+agentParam.getAgentName()+"%");
		}
		
		if(StrKit.notBlank(agentParam.getAgentPhone())){
			where.append(" and a.agent_phone = ? ");
			params.add(agentParam.getAgentPhone());
		}
		
		if(agentParam.getParentId() != null){
			where.append(" and a.parent_id = ? ");
			params.add(agentParam.getParentId());
		}else {
			//默认查询出一级分销商
			where.append(" and a.parent_id is null ");
		}

		final String select = "select bu.headimgurl, bu.nickname, a.id, a.agent_name, a.agent_phone, a.created, a.audit_date, q.member_count, b.total_price ";
		String sqlExceptSelect = "from t_agent a "
		+ " LEFT JOIN t_buyer_user bu on a.buyer_id = bu.id "
		+ " LEFT JOIN (select count(parent_id) as member_count, parent_id as pid from t_agent GROUP BY parent_id) q on a.id = q.pid "
		+ " LEFT JOIN (select SUM(total_price) as total_price, buyer_id from t_order where payment_status=2) b on a.buyer_id = b.buyer_id "
		+ " where a.seller_id = ? and a.status = 1 and bu.active=1 "; 
		
		Page<Record> records = Db.paginate(agentParam.getPageNo(), agentParam.getPageSize(), select, sqlExceptSelect + where.toString(), params.toArray());
		List<AgentResultDto> agentResultDtos = new ArrayList<AgentResultDto>();
		for(Record record : records.getList()){
			AgentResultDto resultDto = new AgentResultDto();
			resultDto.setAgentId(record.getLong("id"));
			resultDto.setAgentName(record.getStr("agent_name"));
			resultDto.setAgentPhone(record.getStr("agent_phone"));
			resultDto.setWxNick(record.getStr("nickname"));
			resultDto.setWxHeadimg(record.getStr("headimgurl"));
			resultDto.setAduitDate(record.getDate("audit_date"));
			resultDto.setCreated(record.getDate("created"));
			if(record.getLong("member_count") != null){
				resultDto.setMemberCount(record.getLong("member_count").toString());
			}
			if(record.getBigDecimal("total_price") != null){
				resultDto.setTotalMoney(record.getBigDecimal("total_price").toString());
			}
			agentResultDtos.add(resultDto);
		}
		return new Page<AgentResultDto>(agentResultDtos, records.getPageNumber(), records.getPageSize(), records.getTotalPage(), records.getTotalRow());
	}
	
	@Override
	public void apply(AgentApplyParamDto applyParam) throws AgentException {
		if(applyParam == null || applyParam.getBuyerId() == null 
				|| StrKit.isBlank(applyParam.getCode()) || StrKit.isBlank(applyParam.getCodeInSession()) || StrKit.isBlank(applyParam.getPhoneCode())
				|| applyParam.getSellerId() == null || StrKit.isBlank(applyParam.getAgentPhone()))
			throw new AgentException("调用申请分销商接口缺少必要参数");
		
		//检查图片验证码
		if(StrKit.notBlank(applyParam.getCode()) 
				&& StrKit.notBlank(applyParam.getCodeInSession())
				&& !EncriptionKit.encrypt(applyParam.getCode().toLowerCase()).equals(applyParam.getCodeInSession())) 
			throw new AgentException("验证码错误");
		
		//check 手机验证码
		UserCode userCode = UserCode.dao.findFirst("select * from " + UserCode.table + " where vcode_phone=? and vcode_code=? ", 
				applyParam.getAgentPhone(), applyParam.getPhoneCode());
		if(userCode == null) throw new AgentException("短信验证码错误");
		//检查验证码是否过期 30分钟后过期
		Integer expires_in = 1800;
		Long expiredTime = userCode.getUpdated().getTime() + ((expires_in -5) * 1000);
		if(expiredTime == null || expiredTime < System.currentTimeMillis())
			throw new AgentException("短信验证码已过期");
		
		Agent agent = Agent.dao.findFirst(" select * from " + Agent.table + " where agent_phone=? ", applyParam.getAgentPhone());
		if(agent != null) throw new AgentException("该手机号已存在");
		
		//查询地址
		Area area = Area.dao.findById(applyParam.getAreaId());
		if(area == null) throw new AgentException("地址库不存在该地址");
		
		//插入到分销商表
		agent = new Agent();
		agent.setAgentName(applyParam.getAgentName());
		agent.setAgentPhone(applyParam.getAgentPhone());
		agent.setBuyerId(applyParam.getBuyerId());
		agent.setSellerId(applyParam.getSellerId());
		agent.setAgentAddr(applyParam.getAddr());
		agent.setAreaId(applyParam.getAreaId());
		agent.setAreaTreePath(area.getTreePath());
		agent.setParentId(applyParam.getParentId());
		if(agent.getParentId() == null)
			agent.setStatus(2); 	//2表示待审核，0表示审核不通过，1表示审核通过，成为分销商
		else
			agent.setStatus(1);  	//如果有上级代理的话，表示是主动邀请成为下级，无需审核
		agent.setActive(true);
		agent.setCreated(new Date());
		agent.setUpdated(new Date());
		try {
			agent.save();			
		} catch (Exception e) {
			throw new AgentException(e.getMessage());
		}
	}

	@Override
	public Page<AgentAduitResultDto> getAduitList(AgentAduitParamDto aduitParam) throws AgentException {
		if(aduitParam == null || aduitParam.getSellerId() == null || aduitParam.getStatus() == null)
			throw new AgentException("获取待审核分销商列表缺少参数");
		/*select bu.headimgurl, bu.nickname, a.id, a.agent_name, a.agent_phone, a.agent_addr, b.total_price, a.created, a.`status` from t_agent a 
		LEFT JOIN t_buyer_user bu on a.buyer_id = bu.id
		LEFT JOIN (select SUM(total_price) as total_price, buyer_id from t_order where payment_status=2) b on a.buyer_id = b.buyer_id 
		where a.seller_id = 1*/
		final StringBuffer where = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		params.add(aduitParam.getSellerId());
		params.add(aduitParam.getStatus());
		if(StrKit.notBlank(aduitParam.getAgentName())){
			where.append(" and a.agent_name like ? ");
			params.add("%"+aduitParam.getAgentName()+"%");
		}
		
		if(StrKit.notBlank(aduitParam.getAgentPhone())){
			where.append(" and a.agent_phone = ? ");
			params.add(aduitParam.getAgentPhone());
		}
		
		if(StrKit.notBlank(aduitParam.getWxNick())){
			where.append(" and bu.nickname like ? ");
			params.add("%"+aduitParam.getWxNick()+"%");
		}
		
		final String select = "select bu.headimgurl, bu.nickname, a.id, a.agent_name, a.agent_phone, a.agent_addr, b.total_price, a.created, a.`status` ";
		String sqlExceptSelect = "from t_agent a LEFT JOIN t_buyer_user bu on a.buyer_id = bu.id "
		+ "LEFT JOIN (select SUM(total_price) as total_price, buyer_id from t_order where payment_status=2) b on a.buyer_id = b.buyer_id " 
		+ "where a.seller_id = ? and a.status =? ";
		
		Page<Record> records = Db.paginate(aduitParam.getPageNo(), aduitParam.getPageSize(), select, sqlExceptSelect + where.toString(), params.toArray());
		List<AgentAduitResultDto> resultDtos = new ArrayList<AgentAduitResultDto>();

		for(Record record : records.getList()){
			AgentAduitResultDto resultDto = new AgentAduitResultDto();
			resultDtos.add(resultDto);
			resultDto.setWxNick(record.getStr("nickname"));
			resultDto.setWxHeadimg(record.getStr("headimgurl"));
			resultDto.setAgentId(record.getLong("id"));
			resultDto.setAgentName(record.getStr("agent_name"));
			resultDto.setAgentPhone(record.getStr("agent_phone"));
			resultDto.setAgentAddr(record.getStr("agent_addr"));
			if(record.getBigDecimal("total_price") != null){
				resultDto.setTotalMoney(record.getBigDecimal("total_price").toString());
			}
			resultDto.setApplyDate(record.getDate("created"));
			resultDto.setStatus(record.getInt("status"));
			if(record.getInt("status") == 0){
				resultDto.setStatusCn("不通过");
			}else if(record.getInt("status") == 2){
				resultDto.setStatusCn("待审核");
			}
			//获取审核日志
			List<AgentAduitLog> aduitLogs = AgentAduitLog.dao.find(" select * from " + AgentAduitLog.table + " where agent_id=? ", resultDto.getAgentId());
			resultDto.setAduitLogs(aduitLogs);
		}
		return new Page<AgentAduitResultDto>(resultDtos, records.getPageNumber(), records.getPageSize(), records.getTotalPage(), records.getTotalRow());
	}

	@Override
	@Transactional(rollbackFor = AgentException.class)
	public void pass(Long agentId, Long opterId) throws AgentException {
		if(agentId == null || opterId == null ) throw new AgentException("审核分销商缺少参数");
		Agent agent = Agent.dao.findById(agentId);
		if(agent == null || agent.getStatus() == 1) throw new AgentException("该分销商不存在或不能审核");
		
		agent.setStatus(1);//设置状态为审核通过
		agent.setAuditDate(new Date());
		agent.update();
		
		//插入审核日志
		AgentAduitLog aduitLog = new AgentAduitLog();
		aduitLog.setAgentId(agentId);
		aduitLog.setAduitOpter(opterId);
		aduitLog.setContent("");
		aduitLog.setStatus("通过");
		aduitLog.setActive(true);
		aduitLog.setCreated(new Date());
		aduitLog.setUpdated(new Date());
		
		try {
			aduitLog.save();	
		} catch (Exception e) {
			throw new AgentException(e.getMessage());
		}
		//发送审核通知
	}

	@Override
	@Transactional(rollbackFor = AgentException.class)
	public void nopass(Long agentId, Long opterId, String content) throws AgentException {
		if(agentId == null || opterId == null || StrKit.isBlank(content)) throw new AgentException("审核分销商缺少参数");
		
		Agent agent = Agent.dao.findById(agentId);
		if(agent == null || agent.getStatus() == 1) throw new AgentException("该分销商不存在或不能审核");
		
		agent.setStatus(0);//设置状态为审核不通过
		agent.setAuditDate(new Date());
		agent.update();
		
		//插入审核日志
		AgentAduitLog aduitLog = new AgentAduitLog();
		aduitLog.setAgentId(agentId);
		aduitLog.setAduitOpter(opterId);
		aduitLog.setContent(content);
		aduitLog.setStatus("不通过");
		aduitLog.setActive(true);
		aduitLog.setCreated(new Date());
		aduitLog.setUpdated(new Date());
		
		try {
			aduitLog.save();	
		} catch (Exception e) {
			throw new AgentException(e.getMessage());
		}
		//发送审核通知
	}

	@Override
	public List<Agent> getSelfAndParent(Long agentId) {
		return null;
	}
	
	@Override
	public BigDecimal getAgentCommission(Agent agent, Order order) throws AgentException {
		if(agent == null || agent.getStatus()!=1) throw new AgentException("获取佣金失败");

		CommissionRate rate = CommissionRate.dao.findFirst("select * from " + CommissionRate.table + " where seller_id=?", agent.getSellerId());
		if(rate == null || rate.getSelfUpRate() == null || rate.getSecondUpRate() == null || rate.getThirdUpRate() == null)
			throw new AgentException("未设置佣金比率");
		
		if(agent.getParentId() == null){
			//说明是一级代理
			return rate.getThirdUpRate().divide(new BigDecimal(100)).subtract(order.getPayFee()).setScale(2, BigDecimal.ROUND_HALF_UP);
		}else {
			List<Agent> childAgent = Agent.dao.find("select * from " + Agent.table + " where parent_id=? ", agent.getId());
			if(childAgent != null && childAgent.size()>0){
				//说明是二级代理
				return rate.getSecondUpRate().divide(new BigDecimal(100)).subtract(order.getPayFee()).setScale(2, BigDecimal.ROUND_HALF_UP);
			}else{
				//说明是三级代理
				return rate.getSelfUpRate().divide(new BigDecimal(100)).subtract(order.getPayFee()).setScale(2, BigDecimal.ROUND_HALF_UP);
			}
		}
	}
	
	public Page<AgentRankResultDto> getRanktList(AgentRankParamDto rankParam) throws AgentException {
		final StringBuffer where = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		params.add(rankParam.getSellerId());
		if(StrKit.notBlank(rankParam.getRankName())){
			where.append(" and a.rank_name like ? ");
			params.add("%"+rankParam.getRankName()+"%");
		}
		
		final String select = "select a.*";
		String sqlExceptSelect = "from "+AgentRank.table+" a"
		+ "  where a.seller_id = ?";
		
		Page<Record> records = Db.paginate(rankParam.getPageNo(), rankParam.getPageSize(), select, sqlExceptSelect + where.toString(), params.toArray());
		List<AgentRankResultDto> resultDtos = new ArrayList<AgentRankResultDto>();
		for(Record record : records.getList()){
			AgentRankResultDto resultDto=new AgentRankResultDto();
			resultDto.setId(record.getLong("id"));
			resultDto.setRankName(record.getStr("rank_name"));
			resultDto.setRankWeight(record.getInt("rank_weight"));
			resultDto.setFirstRate(record.getBigDecimal("first_rate")+"%");
			resultDto.setSecondRate(record.getBigDecimal("second_rate")+"%");
			resultDto.setThirdRate(record.getBigDecimal("third_rate")+"%");
			resultDto.setRewardValue(record.getInt("reward_value"));
			resultDtos.add(resultDto);
		}
		return new Page<AgentRankResultDto>(resultDtos, records.getPageNumber(), records.getPageSize(), records.getTotalPage(), records.getTotalRow());
	}

}
