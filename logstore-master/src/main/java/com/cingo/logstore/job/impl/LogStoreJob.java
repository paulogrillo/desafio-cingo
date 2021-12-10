package com.cingo.logstore.job.impl;

import com.cingo.logstore.entity.factory.LogFactory;
import com.cingo.logstore.job.Job;
import com.cingo.logstore.job.JobException;
import com.cingo.logstore.logfile.LogWrapperBuildException;
import com.cingo.logstore.logfile.LogWrapperFactory;
import com.cingo.logstore.repostory.LogRepository;

public class LogStoreJob implements Job {

	private static final int DEFAULT_INITIAL_OCCURRENCES = 1;
	private LogWrapperFactory logWrapperFactory;
	private LogFactory logFactory;
	private LogRepository logRepository;
	
	public LogStoreJob(LogWrapperFactory logWrapperFactory, LogRepository logRepository, LogFactory logFactory) {
		this.logWrapperFactory = logWrapperFactory;
		this.logRepository = logRepository;
		this.logFactory = logFactory;
	}

	@Override
	public void run() throws JobException {
		try {
			this.logWrapperFactory.build().getLineContents().forEach(logLineContent -> {
				this.logRepository.add(this.logFactory.build(logLineContent, DEFAULT_INITIAL_OCCURRENCES));
			});
		} catch (LogWrapperBuildException e) {
			throw new JobException(this.getClass().getName(), e);
		}
		
	}

}
