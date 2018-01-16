package com.dbumama.market.web.core.security;

/**
 * 一个请求的参数
 * wjun.java@gmail.com
 */
public class Parameter implements Comparable<Parameter>{

    private String key;
    private String value;

    public Parameter(String key, String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String toString() {
        return "Parameter{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    @Override
    public int compareTo(Parameter o) {
        return this.key.compareTo(o.getKey());
    }
}
