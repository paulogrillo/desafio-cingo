package com.cingo.logstore.logfile;

public class LogWrapperBuildException extends Exception {

	private static final long serialVersionUID = -7324415638870967550L;
	private static final String MESSAGE = "System cannot get log file from project resourcers.";

	public LogWrapperBuildException(Throwable rootException) {
		super(MESSAGE, rootException);
	}

	
}
