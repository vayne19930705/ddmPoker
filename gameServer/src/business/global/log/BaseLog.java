package business.global.log;

import BaseCommon.CommLog;

public abstract class BaseLog {
	public BaseLog(){
	}
	
	
	public abstract String getLogString();
	
	//--------------log---------------------
	
	public void trace(String msg) {
		CommLog.trace(this.getLogString() + msg);
    }

    public void trace(String format, Object arg) {
    	CommLog.trace(this.getLogString() + format, arg);
    }

    public void trace(String format, Object arg1, Object arg2) {
    	CommLog.trace(this.getLogString() + format, arg1, arg2);
    }

    public void trace(String format, Object... arguments) {
    	CommLog.trace(this.getLogString() + format, arguments);
    }

    public void trace(String msg, Throwable t) {
    	CommLog.trace(this.getLogString() + msg, t);
    }
    

	public void debug(String msg) {
		CommLog.debug(this.getLogString() + msg);
    }

    public void debug(String format, Object arg) {
    	CommLog.debug(this.getLogString() + format, arg);
    }

    public void debug(String format, Object arg1, Object arg2) {
    	CommLog.debug(this.getLogString() + format, arg1, arg2);
    }

    public void debug(String format, Object... arguments) {
    	CommLog.debug(this.getLogString() + format, arguments);
    }

    public void debug(String msg, Throwable t) {
    	CommLog.debug(this.getLogString() + msg, t);
    }
    
    
	public void info(String msg) {
		CommLog.info(this.getLogString() + msg);
    }

    public void info(String format, Object arg) {
    	CommLog.info(this.getLogString() + format, arg);
    }

    public void info(String format, Object arg1, Object arg2) {
    	CommLog.info(this.getLogString() + format, arg1, arg2);
    }

    public void info(String format, Object... arguments) {
    	CommLog.info(this.getLogString() + format, arguments);
    }

    public void info(String msg, Throwable t) {
    	CommLog.info(this.getLogString() + msg, t);
    }
    
    
	public void warn(String msg) {
		CommLog.warn(this.getLogString() + msg);
    }

    public void warn(String format, Object arg) {
    	CommLog.warn(this.getLogString() + format, arg);
    }

    public void warn(String format, Object arg1, Object arg2) {
    	CommLog.warn(this.getLogString() + format, arg1, arg2);
    }

    public void warn(String format, Object... arguments) {
    	CommLog.warn(this.getLogString() + format, arguments);
    }

    public void warn(String msg, Throwable t) {
    	CommLog.warn(this.getLogString() + msg, t);
    }
    
    
	public void error(String msg) {
		CommLog.error(this.getLogString() + msg);
    }

    public void error(String format, Object arg) {
    	CommLog.error(this.getLogString() + format, arg);
    }

    public void error(String format, Object arg1, Object arg2) {
    	CommLog.error(this.getLogString() + format, arg1, arg2);
    }

    public void error(String format, Object... arguments) {
    	CommLog.error(this.getLogString() + format, arguments);
    }

    public void error(String msg, Throwable t) {
    	CommLog.error(this.getLogString() + msg, t);
    }
}
