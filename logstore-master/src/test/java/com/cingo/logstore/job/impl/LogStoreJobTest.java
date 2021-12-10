package com.cingo.logstore.job.impl;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.times;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.cingo.logstore.entity.Log;
import com.cingo.logstore.entity.factory.LogFactory;
import com.cingo.logstore.job.JobException;
import com.cingo.logstore.logfile.LogWrapper;
import com.cingo.logstore.logfile.LogWrapperBuildException;
import com.cingo.logstore.logfile.LogWrapperFactory;
import com.cingo.logstore.repostory.LogRepository;

class LogStoreJobTest {

	private static final String LINE_CONTENT_1 = "linecontent1";
	private static final String LINE_CONTENT_2 = "linecontent2";
	private static final String LINE_CONTENT_3 = "linecontent3";
	private static final String LINE_CONTENT_4 = "linecontent4";

	@Mock
	private LogRepository repositoryMock;
	
	@Mock
	private LogWrapperFactory logWrapperFactoryMock;
	
	@Mock
	private LogFactory logFactoryMock;
	
	@InjectMocks
	private LogStoreJob logStoreJob;
	
	@Mock
	private LogWrapper logWrapperMock;
	
	@Mock
	private Log firstLogLineMock;
	
	@Mock
	private Log secondLogLineMock;
	
	@Mock
	private Log thirdLogLineMock;
	
	@Mock
	private Log fourthLogLineMock;
	
	@BeforeEach
	void setUp() throws LogWrapperBuildException {
		MockitoAnnotations.initMocks(this);
		when(this.logWrapperFactoryMock.build()).thenReturn(this.logWrapperMock);
		List<String> logLineContents = new ArrayList<String>();
		logLineContents.add(LINE_CONTENT_1);
		when(this.logWrapperMock.getLineContents()).thenReturn(logLineContents);
		when(this.logFactoryMock.build(LINE_CONTENT_1, 1)).thenReturn(this.firstLogLineMock);
		when(this.logFactoryMock.build(LINE_CONTENT_2, 1)).thenReturn(this.secondLogLineMock);
		when(this.logFactoryMock.build(LINE_CONTENT_3, 1)).thenReturn(this.thirdLogLineMock);
		when(this.logFactoryMock.build(LINE_CONTENT_4, 1)).thenReturn(this.fourthLogLineMock);
	}
	
	@Test
	void testLogWrapperBuild() throws JobException, LogWrapperBuildException {
		this.logStoreJob.run();
		
		verify(this.logWrapperFactoryMock, times(1)).build();
	}
	
	@Test
	void testLogWrapperBuildException() throws LogWrapperBuildException {
		when(this.logWrapperFactoryMock.build()).thenThrow(LogWrapperBuildException.class);
		assertThrows(
				JobException.class,
	           () -> this.logStoreJob.run()
	    );
	}
	
	@Test
	void testGetLogLineContents() throws JobException {
		this.logStoreJob.run();
		
		verify(this.logWrapperMock, times(1)).getLineContents();
	}
	
	@Test
	void testCreateLogEntity() throws JobException {
		this.logStoreJob.run();
		
		verify(this.logFactoryMock, times(1)).build(LINE_CONTENT_1, 1);
	}
	
	@Test
	void testPersistLogEntity() throws JobException {
		this.logStoreJob.run();
		
		verify(this.repositoryMock, times(1)).add(this.firstLogLineMock);
	}
	
	@Test
	void testPersistFourLogEntities() throws JobException {
		List<String> logLineContents = new ArrayList<String>();
		logLineContents.add(LINE_CONTENT_1);
		logLineContents.add(LINE_CONTENT_2);
		logLineContents.add(LINE_CONTENT_3);
		logLineContents.add(LINE_CONTENT_4);
		when(this.logWrapperMock.getLineContents()).thenReturn(logLineContents);
		
		this.logStoreJob.run();
		
		verify(this.repositoryMock, times(4)).add(any());
		verify(this.repositoryMock, times(1)).add(this.firstLogLineMock);
		verify(this.repositoryMock, times(1)).add(this.firstLogLineMock);
		verify(this.repositoryMock, times(1)).add(this.firstLogLineMock);
		verify(this.repositoryMock, times(1)).add(this.firstLogLineMock);
	}
	
	

}
