package com.dbumama.market.service.api.customer;

import java.util.List;

import com.dbumama.market.model.MemberRank;

/**
 * 会员等级
 * @author wangjun
 *
 */
public interface MemberRankService {

	/**
	 * 会员等级列表
	 * @param sellerId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws CustomerException
	 */
	public List<MemberRank> list(Long sellerId) throws CustomerException;
	
	/**
	 * 保存或修改
	 * @param memberRank
	 * @return
	 * @throws CustomerException
	 */
	public Long save(MemberRank memberRank) throws CustomerException;
	
	MemberRank findById(Long rankId);
	
	List<MemberRank> getSellerRanks(Long sellerId);
	
}
