package com.dbumama.market.service.provider;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.dbumama.market.model.MemberRank;
import com.dbumama.market.service.api.customer.CustomerException;
import com.dbumama.market.service.api.customer.MemberRankService;

@Service("memberRankService")
public class MemberRankServiceImpl implements MemberRankService {

	private static final MemberRank mbRankdao = new MemberRank().dao();
	
	@Override
	public List<MemberRank> list(Long sellerId) throws CustomerException {
		if(sellerId == null) throw new CustomerException("获取会员等级列表缺少sellerId");
		return mbRankdao.find("select * from " + MemberRank.table + " where seller_id=? and active=1", sellerId);
	}

	@Override
	public Long save(MemberRank memberRank) throws CustomerException {
		if(memberRank == null) throw new CustomerException("memberRank is null");
		if(memberRank.getRankDiscount() == null) throw new CustomerException("等级折扣不能为空");
		if(memberRank.getFirstCharge() == null) throw new CustomerException("等级购买金额不能为空");
		if(memberRank.getRankCashFull() == null || memberRank.getRankCashRward() == null) throw new CustomerException("等级消费满减不能为空");
		if(memberRank.getRankDiscount().compareTo(new BigDecimal(10)) == 1) throw new CustomerException("等级折扣不能大于10");
		if(memberRank.getId() == null){
			memberRank.save();
		}else{
			memberRank.update();
		}
		return memberRank.getId();
	}

	@Override
	public MemberRank findById(Long rankId) {
		return mbRankdao.findById(rankId);
	}

	@Override
	public List<MemberRank> getSellerRanks(Long sellerId) {
		return mbRankdao.find("select * from " + MemberRank.table + " where seller_id=?", sellerId);
	}

}
