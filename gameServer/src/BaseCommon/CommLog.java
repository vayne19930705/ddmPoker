/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BaseCommon;

import java.io.File;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.util.StatusPrinter;




/**
 * ͨ����־�ļ������ �����ÿɱ�����ӿ�, �����ĸ�ʽ������Ϊ"{}", ע���������������, ��: logger.debug("test output
 * something {} , and others {}", "abc", "1234");
 * 
 */
public class CommLog {

    private static Logger LOG = null;
    private static CommLog instance = new CommLog();

    public static CommLog getInstance() {
        return instance;
    }

    public static void initLog() {
        LOG = LoggerFactory.getLogger("CommLog");
    }

    public static void reloadConfig() {

        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();

        String sConfigFile = System.getProperty("logback.configurationFile_bak");
        File externalConfigFile = new File(sConfigFile);
        if (!externalConfigFile.exists()) {
            error("Logback External Config File {} does nott exists", sConfigFile);
            return;
        } else {
            if (!externalConfigFile.isFile()) {
                error("Logback External Config File {} exists, but does not reference a file", sConfigFile);
                return;
            } else {
                if (!externalConfigFile.canRead()) {
                    error("Logback External Config File { }exists and is a file, but cannot be read.", sConfigFile);
                    return;
                } else {
                    JoranConfigurator configurator = new JoranConfigurator();
                    configurator.setContext(lc);
                    lc.reset();
                    try {
                        configurator.doConfigure(sConfigFile);
                    } catch (JoranException e) {
                        e.printStackTrace();
                    }
                    StatusPrinter.printInCaseOfErrorsOrWarnings(lc);
                }
            }
        }
    }

    public static void trace(String msg) {
        LOG.trace(msg);
    }

    public static void trace(String format, Object arg) {
        LOG.trace(format, arg);
    }

    public static void trace(String format, Object arg1, Object arg2) {
        LOG.trace(format, arg1, arg2);
    }

    public static void trace(String format, Object... arguments) {
        LOG.trace(format, arguments);
    }

    public static void trace(String msg, Throwable t) {
        LOG.trace(msg, t);
    }

    public static boolean isTraceEnabled(Marker marker) {
        return LOG.isTraceEnabled(marker);
    }

    public static void trace(Marker marker, String msg) {
        LOG.trace(marker, msg);
    }

    public static void trace(Marker marker, String format, Object arg) {
        LOG.trace(marker, format, arg);
    }

    public static void trace(Marker marker, String format, Object arg1, Object arg2) {
        LOG.trace(marker, format, arg1, arg2);
    }

    public static void trace(Marker marker, String format, Object... argArray) {
        LOG.trace(marker, format, argArray);
    }

    public static void trace(Marker marker, String msg, Throwable t) {
        LOG.trace(marker, msg, t);
    }

    public static void debug(String msg) {
        LOG.debug(msg);
    }

    public static void debug(String format, Object arg) {
        LOG.debug(format, arg);
    }

    public static void debug(String format, Object arg1, Object arg2) {
        LOG.debug(format, arg1, arg2);
    }

    public static void debug(String format, Object... arguments) {
        LOG.debug(format, arguments);
    }

    public static void debug(String msg, Throwable t) {
        LOG.debug(msg, t);
    }

    public static boolean isDebugEnabled(Marker marker) {
        return LOG.isDebugEnabled(marker);
    }

    public static void debug(Marker marker, String msg) {
        LOG.debug(marker, msg);
    }

    public static void debug(Marker marker, String format, Object arg) {
        LOG.debug(marker, format, arg);
    }

    public static void debug(Marker marker, String format, Object arg1, Object arg2) {
        LOG.debug(marker, format, arg1, arg2);
    }

    public static void debug(Marker marker, String format, Object... arguments) {
        LOG.debug(marker, format, arguments);
    }

    public static void debug(Marker marker, String msg, Throwable t) {
        LOG.debug(marker, msg, t);
    }

    public static void info(String msg) {
        LOG.info(msg);
    }

    public static void info(String format, Object arg) {
        LOG.info(format, arg);
    }

    public static void info(String format, Object arg1, Object arg2) {
        LOG.info(format, arg1, arg2);
    }

    public static void info(String format, Object... arguments) {
        LOG.info(format, arguments);
    }

    public static void info(String msg, Throwable t) {
        LOG.info(msg, t);
    }

    public static boolean isInfoEnabled(Marker marker) {
        return LOG.isInfoEnabled(marker);
    }

    public static void info(Marker marker, String msg) {
        LOG.info(marker, msg);
    }

    public static void info(Marker marker, String format, Object arg) {
        LOG.info(marker, format, arg);
    }

    public static void info(Marker marker, String format, Object arg1, Object arg2) {
        LOG.info(marker, format, arg1, arg2);
    }

    public static void info(Marker marker, String format, Object... arguments) {
        LOG.info(marker, format, arguments);
    }

    public static void info(Marker marker, String msg, Throwable t) {
        LOG.info(marker, msg, t);
    }

    public static void warn(String msg) {
        LOG.warn(msg);
    }

    public static void warn(String format, Object arg) {
        LOG.warn(format, arg);
    }

    public static void warn(String format, Object... arguments) {
        LOG.warn(format, arguments);
    }

    public static void warn(String format, Object arg1, Object arg2) {
        LOG.warn(format, arg1, arg2);
    }

    public static void warn(String msg, Throwable t) {
        LOG.warn(msg, t);
    }

    public static boolean isWarnEnabled(Marker marker) {
        return LOG.isWarnEnabled(marker);
    }

    public static void warn(Marker marker, String msg) {
        LOG.warn(marker, msg);
    }

    public static void warn(Marker marker, String format, Object arg) {
        LOG.warn(marker, format, arg);
    }

    public static void warn(Marker marker, String format, Object arg1, Object arg2) {
        LOG.warn(marker, format, arg1, arg2);
    }

    public static void warn(Marker marker, String format, Object... arguments) {
        LOG.warn(marker, format, arguments);
    }

    public static void warn(Marker marker, String msg, Throwable t) {
        LOG.warn(marker, msg, t);
    }

    public static void error(String msg) {
        if (null == msg) {
            LOG.error("meaningless err msg found:null, check caller :{}", Arrays.toString(Thread.currentThread().getStackTrace()));
        }
        LOG.error(msg);
    }

    public static void error(String format, Object arg) {
        if (null == format) {
            LOG.error("meaningless err msg found:null, check caller :{}", Arrays.toString(Thread.currentThread().getStackTrace()));
        }
        LOG.error(format, arg);
    }

    public static void error(String format, Object arg1, Object arg2) {
        if (null == format) {
            LOG.error("meaningless err msg found:null, check caller :{}", Arrays.toString(Thread.currentThread().getStackTrace()));
        }
        LOG.error(format, arg1, arg2);
    }

    public static void error(String format, Object... arguments) {
        if (null == format) {
            LOG.error("meaningless err msg found:null, check caller :{}", Arrays.toString(Thread.currentThread().getStackTrace()));
        }
        LOG.error(format, arguments);
    }

    public static void error(String msg, Throwable t) {
        LOG.error(msg, t);
    }

    public static boolean isErrorEnabled(Marker marker) {
        return LOG.isErrorEnabled(marker);
    }

    public static void error(Marker marker, String msg) {
        LOG.error(marker, msg);
    }

    public static void error(Marker marker, String format, Object arg) {
        LOG.error(marker, format, arg);
    }

    public static void error(Marker marker, String format, Object arg1, Object arg2) {
        LOG.error(marker, format, arg1, arg2);
    }

    public static void error(Marker marker, String format, Object... arguments) {
        LOG.error(marker, format, arguments);
    }

    public static void error(Marker marker, String msg, Throwable t) {
        LOG.error(marker, msg, t);
    }
}
