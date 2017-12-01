package com.base.system.eventlog;

import java.util.List;

public interface BatchWriter<T> {
	void write(List<T> logEvents);
}
