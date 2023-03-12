package com.fastcms.web.controller.api;

import com.fastcms.utils.I18nUtils;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.fastcms.common.constants.FastcmsConstants.FASTCMS_SYSTEM_REQUEST_ERROR;
import static com.fastcms.common.constants.FastcmsConstants.FASTCMS_SYSTEM_REQUEST_PARAMS_ERROR;

/**
 * 接收微信消息
 * @author： wjun_java@163.com
 * @date： 2022/03/02
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@RestController
@RequestMapping("/wechat/message/{appId}")
public class WechatMessageCallbackController {

    private static final Logger logger = LoggerFactory.getLogger(WechatMessageCallbackController.class);

    @Autowired
    private WxMpService wxService;

    @Autowired
    private WxMpMessageRouter messageRouter;

    /**
     * 公众号服务器校验
     * @param appId
     * @param signature
     * @param timestamp
     * @param nonce
     * @param echostr
     * @return
     */
    @GetMapping(value = "mp", produces = "text/plain;charset=utf-8")
    public String auth(@PathVariable String appId,
                          @RequestParam(name = "signature", required = false) String signature,
                          @RequestParam(name = "timestamp", required = false) String timestamp,
                          @RequestParam(name = "nonce", required = false) String nonce,
                          @RequestParam(name = "echostr", required = false) String echostr) {

        logger.info("\n接收到来自微信服务器的认证消息：[{}, {}, {}, {}]", signature, timestamp, nonce, echostr);
        if (StringUtils.isAnyBlank(signature, timestamp, nonce, echostr)) {
            throw new IllegalArgumentException(I18nUtils.getMessage(FASTCMS_SYSTEM_REQUEST_PARAMS_ERROR));
        }
        this.wxService.switchoverTo(appId);

        if (wxService.checkSignature(timestamp, nonce, signature)) {
            return echostr;
        }

        return "非法请求";
    }

    /**
     * 公众号消息接收
     * @param appId
     * @param requestBody
     * @param signature
     * @param timestamp
     * @param nonce
     * @param encType
     * @param msgSignature
     * @return
     */
    @PostMapping(value = "mp", produces = "application/xml; charset=UTF-8")
    public String post(@PathVariable String appId,
                       @RequestBody String requestBody,
                       @RequestParam("signature") String signature,
                       @RequestParam("timestamp") String timestamp,
                       @RequestParam("nonce") String nonce,
                       @RequestParam(name = "encrypt_type", required = false) String encType,
                       @RequestParam(name = "msg_signature", required = false) String msgSignature) {

        this.wxService.switchoverTo(appId);
        if (!wxService.checkSignature(timestamp, nonce, signature)) {
            throw new IllegalArgumentException(I18nUtils.getMessage(FASTCMS_SYSTEM_REQUEST_ERROR));
        }

        String out = null;
        if (encType == null) {
            // 明文传输的消息
            WxMpXmlMessage inMessage = WxMpXmlMessage.fromXml(requestBody);
            WxMpXmlOutMessage outMessage;
            try {
                outMessage = messageRouter.route(appId, inMessage);
            } catch (Exception e) {
                e.printStackTrace();
                outMessage = null;
            }
            if (outMessage == null) {
                return "";
            }

            out = outMessage.toXml();
        } else if ("aes".equalsIgnoreCase(encType)) {
            // aes加密的消息
            WxMpXmlMessage inMessage = WxMpXmlMessage.fromEncryptedXml(requestBody, wxService.getWxMpConfigStorage(),
                    timestamp, nonce, msgSignature);
            logger.debug("\n消息解密后内容为：\n{} ", inMessage.toString());
            WxMpXmlOutMessage outMessage;
            try {
                outMessage = messageRouter.route(appId, inMessage);
            } catch (Exception e) {
                e.printStackTrace();
                outMessage = null;
            }
            if (outMessage == null) {
                return "";
            }
            String s = outMessage.toString();
            System.out.println("=========>out:" + s);
            out = outMessage.toEncryptedXml(wxService.getWxMpConfigStorage());
        }

        System.out.println("===================组装回复信息：" + out);
        logger.debug("\n组装回复信息：{}", out);
        return out;
    }

}
