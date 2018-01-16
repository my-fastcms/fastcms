package com.dbumama.market.service.provider;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dbumama.market.model.AuthUserConfig;
import com.dbumama.market.model.BuyerUser;
import com.dbumama.market.model.Lottery;
import com.dbumama.market.model.Prize;
import com.dbumama.market.model.PrizeSendRecord;
import com.dbumama.market.model.PrizeType;
import com.dbumama.market.model.Qiandao;
import com.dbumama.market.service.api.exception.MarketBaseException;
import com.dbumama.market.service.api.prize.PrizeResultDto;
import com.dbumama.market.service.api.prize.PrizeSendParamDto;
import com.dbumama.market.service.api.prize.PrizeSendResultDto;
import com.dbumama.market.service.api.prize.PrizeService;
import com.dbumama.market.service.base.AbstractServiceImpl;
import com.dbumama.market.service.sql.QueryHelper;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.ehcache.CacheKit;
import com.weixin.sdk.pay.SendredpackApi;
import com.weixin.sdk.pay.SendredpackReqData;
import com.weixin.sdk.pay.SendredpackResData;
import com.weixin.sdk.utils.DateTimeUtil;

/**
 * wjun_java@163.com
 * 2016年7月20日
 */
@Service("prizeService")
public class PrizeServiceImpl extends AbstractServiceImpl implements PrizeService{

	private static final String CACHENAME = "/prize/list";
	private static final String CACHEKEY = "prize_list_date";
	
	private static final AuthUserConfig authConfigDao = new AuthUserConfig().dao();
	private static final Lottery lotteryDao = new Lottery().dao();
	private static final Prize prizeDao = new Prize().dao();
	private static final PrizeType prizeTypedao = new PrizeType().dao();
	private static final Qiandao qiandaodao = new Qiandao().dao();
	
	/* (non-Javadoc)
	 * @see com.dbumama.market.service.api.prize.PrizeService#sendPrize(java.lang.Long, java.lang.String)
	 */
	@Override
	@Transactional(rollbackFor = MarketBaseException.class)
	public PrizeSendResultDto sendPrize(PrizeSendParamDto prizeSendParamDto) throws MarketBaseException{
		if(prizeSendParamDto == null
				|| prizeSendParamDto.getPrizeId() == null
				|| prizeSendParamDto.getActivityId() == null
				|| prizeSendParamDto.getBuyer() == null
				|| prizeSendParamDto.getSeller() == null)
			throw new MarketBaseException("调用发放奖品接口参数错误，请检查参数");
		PrizeSendResultDto prizeSendResultDto = new PrizeSendResultDto();
		Prize prize = prizeDao.findById(prizeSendParamDto.getPrizeId());
		if(prize == null) throw new MarketBaseException("奖品不存在");
		if(prize.getActive() != 1) throw new MarketBaseException("掌柜说这个奖品不发了！");
		if(prize.getPublishCount() == prize.getHadOutCount()) throw new MarketBaseException("客官，奖品["+prize.getPrizeName()+"]已全部发放完了，下次再来哦");
		
		//查找prizeType
		PrizeType pt = prizeTypedao.findById(prize.getPrizeTypeId());
		if(pt == null) throw new MarketBaseException("奖品类型不存在");

		if(!pt.getTypeCode().equals("item") && prize.getPrizeSingleCash() == null)
			throw new MarketBaseException("除实物外，其他类型的奖品都需要设置prize_single_cash值");
		
		//微信红包
		if(pt.getTypeCode().equals("redpack")){
			AuthUserConfig authConfig = authConfigDao.findFirst(" select * from " + AuthUserConfig.table);
			if(authConfig == null){
				throw new MarketBaseException("授权公众号不存在或已取消授权");
			}
			
			String actName = "";//活动名称
			if(prizeSendParamDto.getActivityType() == 1){
				//表示签到
				Qiandao qiandao = qiandaodao.findById(prizeSendParamDto.getActivityId());
				actName = qiandao.getQiandaoName();
			}else if(prizeSendParamDto.getActivityType() == 2){
				//表示抽奖
				Lottery lottery = lotteryDao.findById(prizeSendParamDto.getActivityId());
				actName = lottery.getLotteryName();
			}
			
			if(authConfig.getPayCertFile() == null){
				throw new MarketBaseException("未设置证书文件");
			}
			
			SendredpackReqData sendredpackReqData = new SendredpackReqData(authConfig.getPayMchId(),
					authConfig.getPaySecretKey(),
					authConfig.getPayMchId() + DateTimeUtil.getDateTime8String() + String.valueOf(System.currentTimeMillis()).substring(3, 13), 
					"", authConfig.getAppId(), "", 
					authConfig.getAppId(), prizeSendParamDto.getBuyer().getOpenId(), String.valueOf(prize.getPrizeSingleCash() * 100), "1", prize.getPrizeMemo(), 
					"127.0.0.1", actName, authConfig.getAppId(), "");
			
			try {
				SendredpackApi sendredpackApi = new SendredpackApi();
				SendredpackResData sendredpackResData = (SendredpackResData) sendredpackApi.post(sendredpackReqData, authConfig.getPayCertFile());
				if(!"SUCCESS".equals(sendredpackResData.getResult_code())){
					throw new MarketBaseException(sendredpackResData.getErr_code_des());
				}else{
					System.out.println("totalMoney:" + sendredpackResData.getTotal_amount() + ",listid:" + sendredpackResData.getSend_listid());
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new MarketBaseException(e.getMessage());
			}
		}
		//积分，软件内积分
		else if(pt.getTypeCode().equals("jifen")){
			//直接更新会员积分数
			BuyerUser buyer = prizeSendParamDto.getBuyer();
			if(buyer.getActive()!=1){
				throw new MarketBaseException("用户不存在或被拉黑，发放奖品失败");
			}
			int newscore = buyer.getScore() + Double.valueOf(prize.getPrizeSingleCash()).intValue();
			buyer.setScore(newscore);
			buyer.setUpdated(new Date());
			try {
				buyer.update();	
			} catch (Exception e) {
				throw new MarketBaseException(e.getMessage());
			}
		}
		//淘金币
		else if(pt.getTypeCode().equals("taojinbi")){
			/*CoinBuyerAddRequest req = new CoinBuyerAddRequest();
			req.setBuyerNick(prizeSendParamDto.getBuyer().getMixNick());
			req.setAmount(Long.valueOf(prize.getPrizeSingleCash()));
			req.setComments(prizeSendParamDto.getReason());
			//req.setFeatureString("{bizId:1212}");
			req.setOutId(123L);
			req.setOutParentId(123L);
			req.setToken(Constants.TAOJINBI_API_TOKEN);
			try {
				CoinBuyerAddResponse rsp = taobaoClientFactory.get().execute(req);
				if(rsp == null || !rsp.isSuccess()) throw new MarketBaseException("调用淘金币发放接口失败");
			} catch (ApiException e) {
				e.printStackTrace();
				throw new MarketBaseException(e.getMessage());
			}*/
		}
		//实物
		else if(pt.getTypeCode().equals("item")){
			//需要卖家手动发放奖品
		}else {
			throw new MarketBaseException("找不到奖品类型typecode属性值");
		}
		//更新奖品数量
		prize.setHadOutCount(prize.getHadOutCount() + 1);
		prize.setUpdated(new Date());
		prize.update();
		//记录中奖信息
		PrizeSendRecord prizeSendRecord = new PrizeSendRecord();
		prizeSendRecord.setBuyerId(prizeSendParamDto.getBuyer().getId());
		prizeSendRecord.setPrizeId(prize.getId());
		prizeSendRecord.setActive(1);
		prizeSendRecord.setActivityId(prizeSendParamDto.getActivityId());
		prizeSendRecord.setActivityType(prizeSendParamDto.getActivityType());
		prizeSendRecord.setCreated(new Date());
		prizeSendRecord.setUpdated(new Date());
		if(pt.getTypeCode().equals("item")) 
			prizeSendRecord.setStatus(3);
		else prizeSendRecord.setStatus(1);
		try {
			prizeSendRecord.save();
		} catch (Exception e) {
			throw new MarketBaseException(e.getMessage());
		}
		prizeSendResultDto.setPrize(prize);
		return prizeSendResultDto;
	}

	@Override
	public Prize findById(Long prizeId) {
		return prizeDao.findById(prizeId);
	}

	@Override
	public List<Prize> getVaildPrizes(Long sellerId) {
		String currDate = DateTimeUtil.getDateTimeString();
		//查询卖家定义的奖品列表 在有效时间范围内可见的 并且是没有删除的奖品 并且是兑换进行中的奖品
		return prizeDao.find("select * from " + Prize.table 
				+ " where active=1 and seller_id =? "
				+ " and start_date<=str_to_date('"+currDate+"', '%Y-%m-%d %H:%i:%s') and end_date >=str_to_date('"+currDate+"', '%Y-%m-%d %H:%i:%s')"
				+ " order by created desc ", 
				sellerId);
	}

	@Override
	public Page<PrizeResultDto> list(Long sellerId, Integer pageNo, Integer pageSize, Integer type, String keyword) {
		
		QueryHelper helper = new QueryHelper("select p.*, pt.id as type_id, pt.type_name, pt.type_code ", " from "+Prize.table + " p " 
				+ " left join " + PrizeType.table + " pt on p.prize_type_id = pt.id ");
		helper.addWhere("p.seller_id", sellerId)
		.addWhere("p.active", 1)
		.addWhere("pt.active", 1)
		.addWhere("p.prize_type_id", type)
		.addWhereLike("p.prize_name", keyword)
		.addOrderBy("desc", "updated");
		
		Page<Record> prizePage = Db.paginateByCache(CACHENAME, CACHEKEY + keyword + type + "_" + pageNo, pageNo, pageSize, 
				helper.getSelect(), helper.getSqlExceptSelect(), helper.getParams());
		
		List<PrizeResultDto> prizeVos = new ArrayList<PrizeResultDto>();
		for(Record prize : prizePage.getList()){
			PrizeResultDto prizeVo = new PrizeResultDto();
			prizeVo.setId(prize.getLong("id"));
			prizeVo.setPrizeImg(prize.getStr("prize_img"));
			prizeVo.setPrizeName(prize.getStr("prize_name"));
			prizeVo.setPrizePrice(prize.getStr("prize_price"));
			prizeVo.setPublishCount(prize.getInt("publish_count").toString());
			prizeVo.setHadOutCount(prize.getInt("had_out_count").toString());
			prizeVo.setPrizeTypeId(prize.getLong("type_id"));
			prizeVo.setPrizeType(prize.getStr("type_name"));
			prizeVo.setTypeCode(prize.getStr("type_code"));
			if(prize.getInt("active") == 1){
				prizeVo.setStatus("有效");
			}else{
				prizeVo.setStatus("已删除");
			}
			if(prize.getDate("start_date") != null){
				prizeVo.setStartDate(DateTimeUtil.toDateString(prize.getDate("start_date")));
			}
			if(prize.getDate("end_date") != null){
				prizeVo.setEndDate(DateTimeUtil.toDateString(prize.getDate("end_date")));
			}
			prizeVos.add(prizeVo);
		}
		
		return new Page<PrizeResultDto>(prizeVos, prizePage.getPageNumber(), prizePage.getPageSize(), prizePage.getTotalPage(), prizePage.getTotalRow());
	}

	@Override
	public Prize save(Prize prize) throws MarketBaseException {
		CacheKit.removeAll(CACHENAME);
		try {
			PrizeType prizeType = prizeTypedao.findById(prize.getPrizeTypeId());
			if("redpack".equals(prizeType.getTypeCode())){
				if(prize.getSellerId() == 1){
					throw new MarketBaseException("体验账号不可以创建红包");
				}
			}
			
			if(prize.getLong("id") != null)
				prize.update();
			else {
				prize.setActive(1);
				prize.save();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return prize;
	}

	@Override
	public List<PrizeType> getPrizeTypes() {
		return prizeTypedao.find(" select * from " + PrizeType.table + " where active=1 ");
	}
}
