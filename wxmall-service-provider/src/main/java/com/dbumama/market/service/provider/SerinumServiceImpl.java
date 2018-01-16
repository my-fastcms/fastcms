package com.dbumama.market.service.provider;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.dbumama.market.service.api.serinum.SerinumService;

@Service("serinumService")
public class SerinumServiceImpl implements SerinumService{

	private static Object lockObj = new Object();
	
	/** 
     * 序列号生成计数器 
     */  
	private static long numCount = 0L;  
	
    /** 
     * 每毫秒生成序列号数量最大值 
     */  
    private int maxPerMSECSize=1000;  
	
	@Override
	public String getOrderSn() {
		return genSerinum("E");
	}
	
	private String genSerinum(String prefix){
        synchronized (lockObj) {  
            // 取系统当前时间作为订单号变量前半部分，精确到毫秒  
            long nowLong = Long.parseLong(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));  
            // 计数器到最大值归零，可扩展更大，目前1毫秒处理峰值1000个，1秒100万  
            if (numCount > maxPerMSECSize) {  
            	numCount = 0L;  
            }  
            //组装序列号  
            String countStr=maxPerMSECSize +numCount+"";  
            numCount++;  
            return prefix + nowLong+countStr.substring(1);  
        }  
	}

	@Override
	public String getProductSn() {
		return genSerinum("P");
	}

}
