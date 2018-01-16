package com.weixin.sdk.pay;

import java.io.IOException;
import java.io.Serializable;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Element;

/**
 * wjun_java@163.com
 * 2015年12月9日
 */
public class RefundApi extends PaycertApi{

	private static final String url = "https://api.mch.weixin.qq.com/secapi/pay/refund";
	
	/* (non-Javadoc)
	 * @see com.weixin.sdk.pay.PayApi#getApiUrl()
	 */
	@Override
	protected String getApiUrl() {
		return url;
	}

	/* (non-Javadoc)
	 * @see com.weixin.sdk.pay.PayApi#getRespone(org.dom4j.Element)
	 */
	@Override
	protected BaseResData getRespone(Element root) {
		RefundResData refundResData = new RefundResData();
		refundResData.setReturn_code(root.elementText("return_code"));
		refundResData.setReturn_msg(root.elementText("return_msg"));
		refundResData.setResult_code(root.elementText("result_code"));
		refundResData.setErr_code(root.elementText("err_code"));
		refundResData.setErr_code_des(root.elementText("err_code_des"));
		refundResData.setRefund_fee(root.elementText("refund_fee"));
		refundResData.setTotal_fee(root.elementText("total_fee"));		
		return refundResData;
	}
	
	public void testRefund(){
		List<RefundItem> items = new ArrayList<RefundItem>();
		
		RefundItem refundItem = new RefundItem();
		refundItem.setFee("200");
		refundItem.setTotalFee("200");
		refundItem.setNickname("左耳情话i");
		refundItem.setOutRefundNo("1000");
		refundItem.setTransactionId("1000380406201512262325606142");
		items.add(refundItem);
		
		refundItem = new RefundItem();
		refundItem.setFee("200");
		refundItem.setTotalFee("200");
		refundItem.setNickname("罗密欧");
		refundItem.setOutRefundNo("1001");
		refundItem.setTransactionId("1006900406201512262325331407");
		items.add(refundItem);
		
		refundItem = new RefundItem();
		refundItem.setFee("200");
		refundItem.setTotalFee("200");
		refundItem.setNickname("志轩");
		refundItem.setOutRefundNo("1002");
		refundItem.setTransactionId("1002140406201512262324706863");
		items.add(refundItem);
		
		refundItem = new RefundItem();
		refundItem.setFee("200");
		refundItem.setTotalFee("200");
		refundItem.setNickname("稻草人");
		refundItem.setOutRefundNo("1003");
		refundItem.setTransactionId("1007050406201512262332216625");
		items.add(refundItem);
		
		refundItem = new RefundItem();
		refundItem.setFee("300");
		refundItem.setTotalFee("300");
		refundItem.setNickname("左耳情话i");
		refundItem.setOutRefundNo("1004");
		refundItem.setTransactionId("1000380406201512262325606142");
		items.add(refundItem);
		
		refundItem = new RefundItem();
		refundItem.setFee("300");
		refundItem.setTotalFee("300");
		refundItem.setNickname("°初");
		refundItem.setOutRefundNo("1005");
		refundItem.setTransactionId("1002350406201512262322183782");
		items.add(refundItem);
		
		refundItem = new RefundItem();
		refundItem.setFee("100");
		refundItem.setTotalFee("100");
		refundItem.setNickname("王军");
		refundItem.setOutRefundNo("1006");
		refundItem.setTransactionId("1002690406201512262333143074");
		items.add(refundItem);
		
		refundItem = new RefundItem();
		refundItem.setFee("100");
		refundItem.setTotalFee("100");
		refundItem.setNickname("A0广州企业qq办理-华金花18024533394");
		refundItem.setOutRefundNo("1007");
		refundItem.setTransactionId("1001940406201512282370987752");
		items.add(refundItem);
		
		refundItem = new RefundItem();
		refundItem.setFee("100");
		refundItem.setTotalFee("100");
		refundItem.setNickname("左耳情话i");
		refundItem.setOutRefundNo("1008");
		refundItem.setTransactionId("1000380406201512282371918392");
		items.add(refundItem);
		
		refundItem = new RefundItem();
		refundItem.setFee("100");
		refundItem.setTotalFee("100");
		refundItem.setNickname("爱屋及乌");
		refundItem.setOutRefundNo("1009");
		refundItem.setTransactionId("1000300406201512292373956502");
		items.add(refundItem);
		
		for(RefundItem rfi : items){
			RefundReqData refundReqData = new RefundReqData(
					 rfi.getOutRefundNo(), "", rfi.getFee(), rfi.getTotalFee(), rfi.getTransactionId());
			RefundApi refundApi = new RefundApi();
			try {
				RefundResData refundResData = (RefundResData) refundApi.post(refundReqData);
				if("SUCCESS".equals(refundResData.getResult_code())){
					System.out.println("totalFee:" + refundResData.getTotal_fee() + "已退款给：" + rfi.getNickname());
				}else{
					System.out.println(rfi.getNickname() + "err_code_msg:" + refundResData.getErr_code_des() + ",return_msg:" + refundResData.getReturn_msg());
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public class RefundItem implements Serializable{

		private static final long serialVersionUID = 1L;
		
		private String outRefundNo;
		private String transactionId;
		private String fee;
		private String totalFee;
		private String nickname;
		
		public String getOutRefundNo() {
			return outRefundNo;
		}
		public void setOutRefundNo(String outRefundNo) {
			this.outRefundNo = outRefundNo;
		}
		public String getTransactionId() {
			return transactionId;
		}
		public void setTransactionId(String transactionId) {
			this.transactionId = transactionId;
		}
		public String getFee() {
			return fee;
		}
		public void setFee(String fee) {
			this.fee = fee;
		}
		public String getTotalFee() {
			return totalFee;
		}
		public void setTotalFee(String totalFee) {
			this.totalFee = totalFee;
		}
		public String getNickname() {
			return nickname;
		}
		public void setNickname(String nickname) {
			this.nickname = nickname;
		}
		
	}
	
	public static void main(String[] args) throws UnrecoverableKeyException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException, IOException {
		
		/*RefundReqData refundReqData = new RefundReqData(
				 "4", "", "5", "5", "refid");
		RefundApi refundApi = new RefundApi();
		try {
			RefundResData refundResData = (RefundResData) refundApi.post(refundReqData);
			if("SUCCESS".equals(refundResData.getResult_code())){
				System.out.println("totalFee:" + refundResData.getTotal_fee() + "");
			}else{
				System.out.println("err_code_msg:" + refundResData.getErr_code_des() + ",return_msg:" + refundResData.getReturn_msg());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		RefundApi refundApi = new RefundApi();
		refundApi.testRefund();
	}

}
