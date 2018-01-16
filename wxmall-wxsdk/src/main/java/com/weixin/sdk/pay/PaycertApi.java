package com.weixin.sdk.pay;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketTimeoutException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.TreeMap;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.weixin.sdk.kit.WxSdkPropKit;

/**
 * 使用证书支付api 基类
 * wjun_java@163.com
 * 2015年12月11日
 */
public abstract class PaycertApi extends PayApi{

	private boolean hasInit = false;
	
	//连接超时时间，默认10秒
    private int socketTimeout = 10000;

    //传输超时时间，默认30秒
    private int connectTimeout = 30000;

    //请求器的配置
    private RequestConfig requestConfig;

    //HTTP请求器
    private CloseableHttpClient httpClient;
	
	@Override
	public BaseResData post(BaseReq reqData, byte [] certs) throws Exception {
		TreeMap<String, Object> map = reqData.toMap();
		String mchId = map.get("mch_id") != null ? map.get("mch_id").toString() : WxSdkPropKit.get("wx_app_id");
		if(!hasInit){
			init(certs, mchId);
		}
		String result = null;
        HttpPost httpPost = new HttpPost(getApiUrl());
        //将要提交给API的数据对象转换成XML格式数据Post给API
        String postDataXML = getRequestXml(map);
        //得指明使用UTF-8编码，否则到API服务器XML的中文不能被成功识别
        StringEntity postEntity = new StringEntity(postDataXML, "UTF-8");
        httpPost.addHeader("Content-Type", "text/xml");
        httpPost.setEntity(postEntity);
        //设置请求器的配置
        httpPost.setConfig(requestConfig);
        try {
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity, "UTF-8");
        } catch (ConnectionPoolTimeoutException e) {
        	e.printStackTrace();
        } catch (ConnectTimeoutException e) {
        	e.printStackTrace();
        } catch (SocketTimeoutException e) {
        	e.printStackTrace();
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
            httpPost.abort();
        }
		return getRespone(getResponseRoot(result));
	}
	
	private void init(byte [] certs, String mchId) throws IOException, KeyStoreException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyManagementException {

        KeyStore keyStore = KeyStore.getInstance("PKCS12");
//        String filePath = getClass().getClassLoader().getResource(Constants.certLocalPath).getPath();
//        FileInputStream instream = new FileInputStream(new File(filePath));//加载本地的证书进行https加密传输
//        InputStream instream = getClass().getClassLoader().getResourceAsStream(Constants.certLocalPath);
        
//        InputStream instream = getClass().getClassLoader().getResourceAsStream(WxSdkPropKit.get("cert_local_path"));
        
        InputStream instream = new ByteArrayInputStream(certs); 
        
        try {
            keyStore.load(instream, mchId.toCharArray());//设置证书密码
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } finally {
            instream.close();
        }

        // Trust own CA and all self-signed certs
        SSLContext sslcontext = SSLContexts.custom()
                .loadKeyMaterial(keyStore, mchId.toCharArray())
                .build();
        // Allow TLSv1 protocol only
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslcontext,
                new String[]{"TLSv1"},
                null,
                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);

        httpClient = HttpClients.custom().setSSLSocketFactory(sslsf).build();

        //根据默认超时限制初始化requestConfig
        requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();

//        hasInit = true;
    }

}
