package com.fastcms.common.exception;

/**
 * @author： wjun_java@163.com
 * @date： 2021/5/25
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public class FastcmsRuntimeException extends RuntimeException  {

    public static final String ERROR_MESSAGE_FORMAT = "errCode: %d, errMsg: %s ";

    private int errCode;

    public FastcmsRuntimeException(int errCode) {
        super();
        this.errCode = errCode;
    }

    public FastcmsRuntimeException(int errCode, String errMsg) {
        super(String.format(ERROR_MESSAGE_FORMAT, errCode, errMsg));
        this.errCode = errCode;
    }

    public FastcmsRuntimeException(int errCode, Throwable throwable) {
        super(throwable);
        this.errCode = errCode;
    }

    public FastcmsRuntimeException(int errCode, String errMsg, Throwable throwable) {
        super(String.format(ERROR_MESSAGE_FORMAT, errCode, errMsg), throwable);
        this.errCode = errCode;
    }

    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }

}
