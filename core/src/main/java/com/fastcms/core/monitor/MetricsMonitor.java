package com.fastcms.core.monitor;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.Timer;

/**
 * wjun_java@163.com
 */
public class MetricsMonitor {

    public static Timer getNotifyRtTimer() {
        return Metrics.timer("fastcms_timer", "name", "notifyRt");
    }

    public static Counter getIllegalArgumentException() {
        return Metrics.counter("fastcms_exception", "name", "illegalArgument");
    }

    public static Counter getFastcmsException() {
        return Metrics.counter("fastcms_exception", "name", "fastcms");
    }

    public static Counter getDbException() {
        return Metrics.counter("fastcms_exception", "name", "db");
    }

    public static Counter getConfigNotifyException() {
        return Metrics.counter("fastcms_exception", "name", "configNotify");
    }

    public static Counter getUnhealthException() {
        return Metrics.counter("fastcms_exception", "name", "unhealth");
    }

}
