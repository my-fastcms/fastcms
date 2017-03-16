package com.dbumama.market.service.provider;

import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dbumama.market.model.AuthCert;
import com.dbumama.market.model.AuthUser;
import com.dbumama.market.model.BuyerUser;
import com.dbumama.market.model.Lottery;
import com.dbumama.market.model.Prize;
import com.dbumama.market.model.PrizeSendRecord;
import com.dbumama.market.model.PrizeType;
import com.dbumama.market.model.Qiandao;
import com.dbumama.market.service.api.exception.MarketBaseException;
import com.dbumama.market.service.api.prize.PrizeSendParamDto;
import com.dbumama.market.service.api.prize.PrizeSendResultDto;
import com.dbumama.market.service.api.prize.PrizeService;

/**
 * wjun_java@163.com
 * 2016年7月20日
 */
@Service("prizeService")
public class PrizeServiceImpl implements PrizeService{

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
		Prize prize = Prize.dao.findById(prizeSendParamDto.getPrizeId());
		if(prize == null) throw new MarketBaseException("奖品不存在");
		if(prize.getActive() != 1) throw new MarketBaseException("掌柜说这个奖品不发了！");
		if(prize.getPublishCount() == prize.getHadOutCount()) throw new MarketBaseException("客官，奖品["+prize.getPrizeName()+"]已全部发放完了，下次再来哦");
		
		//查找prizeType
		PrizeType pt = PrizeType.dao.findById(prize.getPrizeTypeId());
		if(pt == null) throw new MarketBaseException("奖品类型不存在");

		if(!pt.getTypeCode().equals("item") && prize.getPrizeSingleCash() == null)
			throw new MarketBaseException("除实物外，其他类型的奖品都需要设置prize_single_cash值");
		
		//微信红包
		if(pt.getTypeCode().equals("redpack")){
			AuthUser authUser = AuthUser.dao.findFirst(" select * from " + AuthUser.table + " where app_id=? and active=1 ", prizeSendParamDto.getBuyer().getAuthAppId());
			if(authUser == null){
				throw new MarketBaseException("授权公众号不存在或已取消授权");
			}
			
			String actName = "";//活动名称
			if(prizeSendParamDto.getActivityType() == 1){
				//表示签到
				Qiandao qiandao = Qiandao.dao.findById(prizeSendParamDto.getActivityId());
				actName = qiandao.getQiandaoName();
			}else if(prizeSendParamDto.getActivityType() == 2){
				//表示抽奖
				Lottery lottery = Lottery.dao.findById(prizeSendParamDto.getActivityId());
				actName = lottery.getLotteryName();
			}
			
			AuthCert authCert = AuthCert.dao.findFirst("select * from " + AuthCert.table + " where app_id=? ", authUser.getAppId());
			if(authCert == null || authCert.getCertFile() == null){
				throw new MarketBaseException("未设置证书文件");
			}
			
			/*SendredpackReqData sendredpackReqData = new SendredpackReqData(authUser.getPayMchId(),
					authUser.getPaySecretKey(),
					authUser.getPayMchId() + DateTimeUtil.getDateTime8String() + String.valueOf(System.currentTimeMillis()).substring(3, 13), 
					"", authUser.getAppId(), "", 
					authUser.getNickName(), prizeSendParamDto.getBuyer().getOpenId(), String.valueOf(prize.getPrizeSingleCash() * 100), "1", prize.getPrizeMemo(), 
					"127.0.0.1", actName, authUser.getPrincipalName(), "");
			
			try {
				SendredpackApi sendredpackApi = new SendredpackApi();
				SendredpackResData sendredpackResData = (SendredpackResData) sendredpackApi.post(sendredpackReqData, authCert.getCertFile());
				if(!"SUCCESS".equals(sendredpackResData.getResult_code())){
					throw new MarketBaseException(sendredpackResData.getErr_code_des());
				}else{
					System.out.println("totalMoney:" + sendredpackResData.getTotal_amount() + ",listid:" + sendredpackResData.getSend_listid());
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new MarketBaseException(e.getMessage());
			}*/
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
}
