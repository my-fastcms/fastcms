package com.fastcms.common.model;

public class Captcha {

    public Captcha(String verCode, String codeUuid, String image) {
        this.verCode = verCode;
        this.codeUuid = codeUuid;
        this.image = image;
    }

    /**
     * 验证码
     */
    private String verCode;
    /**
     * 验证码cache key
     */
    private String codeUuid;
    /**
     * 验证码图片
     */
    private String image;

    public String getVerCode() {
        return verCode;
    }

    public void setVerCode(String verCode) {
        this.verCode = verCode;
    }

    public String getCodeUuid() {
        return codeUuid;
    }

    public void setCodeUuid(String codeUuid) {
        this.codeUuid = codeUuid;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
