package com.weixin.sdk.msg.in.event.card;

import com.weixin.sdk.msg.in.event.EventInMsg;

/**
 * 
 * @author wangjun
 * 用户领取卡券事件
 *<xml> 
  <ToUserName> <![CDATA[gh_fc0a06a20993]]> </ToUserName>  
  <FromUserName> <![CDATA[oZI8Fj040-be6rlDohc6gkoPOQTQ]]> </FromUserName>  
  <CreateTime>1472551036</CreateTime>  
  <MsgType> <![CDATA[event]]> </MsgType>  
  <Event> <![CDATA[user_get_card]]> </Event>  
  <CardId> <![CDATA[pZI8Fjwsy5fVPRBeD78J4RmqVvBc]]> </CardId>  
  <IsGiveByFriend>0</IsGiveByFriend>  
  <UserCardCode> <![CDATA[226009850808]]> </UserCardCode>  
  <FriendUserName> <![CDATA[]]> </FriendUserName>  
  <OuterId>0</OuterId>  
  <OldUserCardCode> <![CDATA[]]> </OldUserCardCode>  
  <OuterStr> <![CDATA[12b]]> </OuterStr>  
  <IsRestoreMemberCard>0</IsRestoreMemberCard>  
  <IsRecommendByFriend>0</IsRecommendByFriend> 
</xml>
 */
public class InUserGetcardEvent extends EventInMsg{

	private String cardId;
	private String userCardCode;
	private String outerId;
	private String outerStr;
	private String isGiveByFriend;
	private String friendUserName;
	private String oldUserCardCode;
	private String isRestoreMemberCard;
	private String isRecommendByFriend;
	
	public InUserGetcardEvent(String toUserName, String fromUserName, Integer createTime, String msgType,
			String event) {
		super(toUserName, fromUserName, createTime, msgType, event);
	}
	
	public String getCardId() {
		return cardId;
	}
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	public String getUserCardCode() {
		return userCardCode;
	}
	public void setUserCardCode(String userCardCode) {
		this.userCardCode = userCardCode;
	}
	public String getOuterId() {
		return outerId;
	}
	public void setOuterId(String outerId) {
		this.outerId = outerId;
	}
	public String getIsGiveByFriend() {
		return isGiveByFriend;
	}
	public void setIsGiveByFriend(String isGiveByFriend) {
		this.isGiveByFriend = isGiveByFriend;
	}
	public String getOuterStr() {
		return outerStr;
	}
	public void setOuterStr(String outerStr) {
		this.outerStr = outerStr;
	}
	public String getFriendUserName() {
		return friendUserName;
	}
	public void setFriendUserName(String friendUserName) {
		this.friendUserName = friendUserName;
	}
	public String getOldUserCardCode() {
		return oldUserCardCode;
	}
	public void setOldUserCardCode(String oldUserCardCode) {
		this.oldUserCardCode = oldUserCardCode;
	}
	public String getIsRestoreMemberCard() {
		return isRestoreMemberCard;
	}
	public void setIsRestoreMemberCard(String isRestoreMemberCard) {
		this.isRestoreMemberCard = isRestoreMemberCard;
	}
	public String getIsRecommendByFriend() {
		return isRecommendByFriend;
	}
	public void setIsRecommendByFriend(String isRecommendByFriend) {
		this.isRecommendByFriend = isRecommendByFriend;
	}

}
