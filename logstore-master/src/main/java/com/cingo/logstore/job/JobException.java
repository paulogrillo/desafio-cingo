package com.cingo.logstore.job;

public class JobException extends Exception {

	private static final long serialVersionUID = 2317713321918541482L;
	private static final String MESSAGE = "System cannot run the job called ";

	public JobException(String jobName, Throwable cause) {
		super(new StringBuilder().append(MESSAGE).append(jobName).toString(), cause);
	}
}
