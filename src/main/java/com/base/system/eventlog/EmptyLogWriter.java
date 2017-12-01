package com.base.system.eventlog;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmptyLogWriter implements BatchWriter<LogSession> {

	Logger logger = LoggerFactory.getLogger(EmptyLogWriter.class);

	public void write(final List<LogSession> logEvents) {
		logger.debug("{}",logEvents);
	}
}
