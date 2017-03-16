/**
 * 文件名:AbstractActivityService.java
 * 版本信息:1.0
 * 日期:2015-7-19
 * Copyright 广州点步信息科技
 * 版权所有
 */
package com.dbumama.market.service.provider;

import org.springframework.stereotype.Component;

import com.dbumama.market.service.api.activity.ActivityService;
import com.dbumama.market.service.base.AbstractServiceImpl;

/**
 * 支持把活动广播到微淘
 * @author: wjun.java@gmail.com
 * @date:2015-7-19
 */
@Component
public abstract class AbstractActivityService extends AbstractServiceImpl implements ActivityService{

}
