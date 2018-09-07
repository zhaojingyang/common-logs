package com.anchor.common.logs;

import org.slf4j.Logger;

import java.util.Map;

/**
 * Created by anchor on 2015/8/16.
 */
public class LogUtil {
    public static final String INTERVAL_CHAR = ",  ";

    private final static String map2String(Map<String, Object> map) {
        StringBuffer sb = new StringBuffer("");
        int i = 0;
        sb.append("[");
        for (Map.Entry<String, Object> e : map.entrySet()) {
            sb.append(e.getKey()).append(": ");
            sb.append(String.valueOf(e.getValue()));
            i++;
            if (i < map.size()) {
                sb.append(LogUtil.INTERVAL_CHAR);
            }
        }
        sb.append("]");
        return sb.toString();
    }

    public static String getMsg(String detailMsg, String error, Object[] params, Map<String, Object> paramMap) {
        return getMsg(detailMsg, error, null, false, params, paramMap);
    }

    public static String getMsg(String detailMsg, String error, String class2Trace,
                                boolean needLineNum, Object[] params, Map<String, Object> paramMap) {
        StringBuffer logSb = new StringBuffer();
        if (needLineNum) {
            ExLocationInfo locationInfo = new ExLocationInfo(new Throwable(), class2Trace);
            logSb.append("行数: ").append(locationInfo.fullInfo).append(" | ");
        }
        logSb.append("日志: ");
        if (Utils.isNotBlank(detailMsg)) {
            logSb.append(detailMsg);
        } else if (Utils.isNotBlank(error)) {
            logSb.append(error);
        }
        if (null != params && params.length > 0) {
            logSb.append(" | ").append("参数: ");
            int i = 0;
            for (Object o : params) {
                logSb.append(String.valueOf(o));
                i++;
                if (i < params.length) {
                    logSb.append(INTERVAL_CHAR);
                }
            }
        }

        if (null != paramMap && !paramMap.isEmpty()) {
            logSb.append(" | ").append("参数: ").append(map2String(paramMap));
        }

        //最终输出的日志信息去掉空行，帮助排查问题
        return Utils.replace(logSb.toString(), "\n", "");
    }

    public static String getMsg(LogBetter logBetter) {
        return getMsg(logBetter.getMsg(), logBetter.getErrorMsg(), logBetter.getClass4Log(),
                logBetter.isNeedLineNumber(), logBetter.getParams(), logBetter.getParamMap());
    }


    public static void log(LogBetter logBetter) {
        if (logBetter.calculateLogLevel().equals(LogLevel.DEBUG)) {
            if (logBetter.getLogger().isDebugEnabled()) {
                debug(logBetter.getLogger(), getMsg(logBetter), logBetter.getException());
            }
        } else if (logBetter.calculateLogLevel().equals(LogLevel.WARN)) {
            if (logBetter.getLogger().isWarnEnabled()) {
                warn(logBetter.getLogger(), getMsg(logBetter), logBetter.getException());
            }
        } else if (logBetter.calculateLogLevel().equals(LogLevel.INFO)) {
            if (logBetter.getLogger().isInfoEnabled()) {
                info(logBetter.getLogger(), getMsg(logBetter), logBetter.getException());
            }
        } else if (logBetter.calculateLogLevel().equals(LogLevel.ERROR)) {
            if (logBetter.getLogger().isErrorEnabled()) {
                error(logBetter.getLogger(), getMsg(logBetter), logBetter.getException());
            }
        }
    }


    public static void log(Logger logger, String detailMessage,
                           LogLevel level, String error, Throwable e,
                           String class2Trace, boolean needLineNumber, Map<String, Object> paramMap, Object... params) {
        String msg = null;
        if (level.equals(LogLevel.DEBUG)) {
            if (logger.isDebugEnabled()) {
                msg = getMsg(detailMessage, error, class2Trace, needLineNumber, params, paramMap);
                debug(logger, msg, e);
            }
        } else if (level.equals(LogLevel.WARN)) {
            if (logger.isWarnEnabled()) {
                msg = getMsg(detailMessage, error, class2Trace, needLineNumber, params, paramMap);
                warn(logger, msg, e);
            }
        } else if (level.equals(LogLevel.INFO)) {
            if (logger.isInfoEnabled()) {
                msg = getMsg(detailMessage, error, class2Trace, needLineNumber, params, paramMap);
                info(logger, msg, e);
            }
        } else if (level.equals(LogLevel.ERROR)) {
            if (logger.isErrorEnabled()) {
                msg = getMsg(detailMessage, error, class2Trace, needLineNumber, params, paramMap);
                error(logger, msg, e);
            }
        }
    }

    public static void warn(Logger logger, String msg, Throwable e) {
        if (e != null) {
            logger.warn(msg, e);
        } else {
            logger.warn(msg);
        }
    }

    public static void info(Logger logger, String msg, Throwable e) {
        if (e != null) {
            logger.info(msg, e);
        } else {
            logger.info(msg);
        }
    }

    public static void error(Logger logger, String msg, Throwable e) {
        if (e != null) {
            if (logger.isErrorEnabled()) {
                logger.error(msg, e);
            }
        } else {
            logger.error(msg);
        }
    }

    public static void debug(Logger logger, String msg, Throwable e) {
        if (e != null) {
            logger.debug(msg, e);
        } else {
            logger.debug(msg);
        }
    }
}
