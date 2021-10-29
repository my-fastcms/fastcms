package com.fastcms.core.permission;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class MenuNode implements Serializable {

    private String name;
    private String path;
    private String component;
    private String isLink = "";
    private Integer menuSort;
    private Meta meta;
    private List<MenuNode> children;

    @Data
    public static class Meta implements Serializable {
        private String title;
        private String icon;
        private Boolean isHide = false;
        private Boolean isKeepAlive = true;
        private Boolean isAffix = false;
        private Boolean isIframe = false;
        private List<String> auth;
    }

}
