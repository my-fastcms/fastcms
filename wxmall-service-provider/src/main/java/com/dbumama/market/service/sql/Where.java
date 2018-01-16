package com.dbumama.market.service.sql;

import java.io.Serializable;

/**
 * 
 * @author wangjun
 *
 */
@SuppressWarnings("serial")
class Where implements Serializable{
	private String field;
    private Object value;
    private int type;		//相等，或者<= 或 其他
    
    public Where(String field, Object value, int type) {
        this.field = field;
        this.value = value;
        this.type = type;
    }
    
    public String getField() {
        return field;
    }

    public Object getValue() {
        return value;
    }
    
    public int getType() {
        return type;
    }

    //以下为包访问权限
    void setValue(Object value) {
        this.value = value;
    }
}
