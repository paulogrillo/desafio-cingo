package com.cingo.logstore;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

import com.cingo.logstore.entity.factory.LogFactory;
import com.cingo.logstore.entity.factory.impl.DefaultLogFactory;
import com.cingo.logstore.job.Job;
import com.cingo.logstore.job.JobException;
import com.cingo.logstore.job.impl.LogStoreJob;
import com.cingo.logstore.logfile.LogWrapperFactory;
import com.cingo.logstore.mapper.ObjectMapperProvider;
import com.cingo.logstore.repostory.LogRepository;

public class Application extends ResourceConfig {

	public Application() {
		this.register(ObjectMapperProvider.class);
		this.register(JacksonFeature.class);
		this.packages("com.cingo.logstore");

		LogWrapperFactory logWrapperFactory = new LogWrapperFactory();
		LogRepository logRepository = new LogRepository();
		LogFactory logFactory = new DefaultLogFactory();

		Job logStoreJob = new LogStoreJob(logWrapperFactory, logRepository, logFactory);
		try {
			logStoreJob.run();
		} catch (JobException e) {
			System.out.println(e);
		}
	}
}
