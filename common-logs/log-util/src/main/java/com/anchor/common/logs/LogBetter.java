package com.anchor.common.logs;

import org.slf4j.Logger;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by anchor on 2015/8/16.
 */
public class LogBetter {

    private Logger logger;
    /**
     * log级别
     */
    private LogLevel level;
    /**
     * 信息
     */
    private String msg;
    /**
     * 错误信息
     */
    private String errorMsg;
    /**
     * 异常
     */
    private Throwable e;
    /**
     * 参数列表
     */
    private Object[] params;

    /**
     * 为无聊用户设置。想要把参数名和值都一直打印出来的
     */
    private Map<String, Object> paramMap = new LinkedHashMap<String, Object>();

    private boolean needLineNumber = true;
    /**
     * 设置了这个参数，会打印到class的上一级堆栈对应的类
     */
    private Class class2Trace;


    public static LogBetter instance(Logger logger) {
        LogBetter logBetter = new LogBetter();
        logBetter.logger = logger;
        return logBetter;
    }


    public LogBetter setLevel(LogLevel level) {
        this.level = level;
        return this;
    }

    public LogBetter setMsg(String msg) {
        this.msg = msg;
        return this;

    }

    public LogBetter notNeedLineNumber() {
        this.needLineNumber = false;
        return this;
    }

    public LogBetter needLineNumber() {
        this.needLineNumber = true;
        return this;
    }

    public LogBetter setException(Throwable e) {
        this.e = e;
        return this;
    }

    public LogBetter setParams(Object... params) {
        this.params = params;
        return this;
    }

    public LogBetter setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
        return this;
    }

    public LogBetter setTraceClass(Class clasz) {
        this.class2Trace = clasz;
        return this;
    }

    public LogBetter setParamsMap(Map<String, Object> map) {
        this.paramMap = map;
        return this;
    }

    public LogBetter addParam(String key, Object value) {
        this.paramMap.put(key, value);
        return this;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public LogBetter setNeedLineNumber(boolean needLineNumber) {
        this.needLineNumber = needLineNumber;
        return this;
    }

    public LogBetter setClass2Trace(Class class2Trace) {
        this.class2Trace = class2Trace;
        return this;
    }

    public String getClass4Log() {
        return this.class2Trace == null ? LogBetter.class.getName() : class2Trace.getName();
    }

    public LogLevel calculateLogLevel() {
        if (this.level == null
                && Utils.isNotBlank(this.errorMsg)) {
            return LogLevel.ERROR;
        } else if (this.level == null) {
            return LogLevel.WARN;
        }
        return level;
    }

    public void log() {
        if (this.level == null && Utils.isNotBlank(this.errorMsg)) {
            this.level = LogLevel.ERROR;
        } else if (this.level == null) {
            this.level = LogLevel.WARN;
        }

        LogUtil.log(this);
    }

    public Logger getLogger() {
        return logger;
    }

    public String getMsg() {
        return msg;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public Throwable getException() {
        return e;
    }

    public Object[] getParams() {
        return params;
    }

    public Map<String, Object> getParamMap() {
        return paramMap;
    }

    public boolean isNeedLineNumber() {
        return needLineNumber;
    }

    public Class getClass2Trace() {
        return class2Trace;
    }
}