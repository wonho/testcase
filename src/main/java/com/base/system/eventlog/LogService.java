package com.base.system.eventlog;

public interface LogService<T> {
	void sendEvent(T logEvent);
}
