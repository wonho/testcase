package com.base.system.eventlog;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.SmartLifecycle;

	public class ModumLogService<T> implements LogService<T>, SmartLifecycle {

		private static final Logger logger = LoggerFactory.getLogger(ModumLogService.class);
		
		private BatchWriter<T> batchWriter;
		private int thresold = 10;
		private long ms = 5000;
		private String threadName;
		private static int defaultThreadNameCounter = 0;
		private String DEFAULT_THREAD_NAME = "modumlog";
		private int phase = Integer.MAX_VALUE;
		
		public void setBatchWriter(BatchWriter<T> batchWriter) {
			this.batchWriter = batchWriter;
		}
		public void setThresold(int thresold) {
			this.thresold = thresold;
		}
		
		public void setLatency(long ms) {
			this.ms = ms;
		}
		
		public void setThreadName(String threadName) {
			this.threadName = threadName;
		}
		
		public void setPhase(int phase) {
			this.phase = phase;
		}
		
		private BlockingQueue<T> queue = new LinkedBlockingQueue<T>();
		
		private volatile boolean running;


		private Thread thread;


		public void sendEvent(T event) {
			try {
				queue.put(event);

			} catch (InterruptedException e) {
				logger.warn("interrupted while putting event to queue", e);
			}
		}
		
		private Runnable getRunnable() {
			return new Runnable() {

				private int errorCount = 0;
				private int warnCount = 1;
				
				public void run() {
					while (running) {
						try {
							Thread.sleep(ms);

						} catch (InterruptedException e) {
							logger.warn("logger interrupted, ignoring.", e);
						}
							
						flush();
					}
					
					/*
					 * on thread shutdown.
					 */
					flush();
				}
				
				private List<T> events = new ArrayList<T>();
				
				private void flush() {
					
					int transferred;
					
					do {
						transferred = queue.drainTo(events, thresold);
						
						if (transferred == 0) {
							break;
						}

						writeEmbracingThrowable(events);
						events.clear();
						
					} while (transferred == thresold);
					
				}

				private void writeEmbracingThrowable(List<T> events) {
					try {
						batchWriter.write(events);
						errorCount = 0;
						warnCount = 1;
						
					} catch (Throwable t) {
						errorCount++;
						
						if (errorCount == warnCount) {
							logger.warn("exception while writing log in batch.", t);
							warnCount *= 2;
						}

					}
				}
				
			};
		}
		public void start() {
			if (threadName == null) {
				defaultThreadNameCounter++;
				threadName = DEFAULT_THREAD_NAME + defaultThreadNameCounter;
			}
			try {
				thread = new Thread(getRunnable(), threadName);
				thread.setPriority(Thread.NORM_PRIORITY + 1);

				running = true;
				logger.info("starting eventLogService {}", threadName);
				thread.start();
				
			} catch (Exception e) {
				logger.warn("error starting eventLogService {}", threadName, e);
			}
		}
		
		public void stop() {
			try {
				running = false;
				thread.join();
				logger.info("stopped eventLogService {}", threadName);
				
			} catch (Exception e) {
				logger.warn("error stopping eventLogService {}", threadName, e);
			}
		}
		public boolean isRunning() {
			return running;
		}
		public int getPhase() {
			return phase ;
		}
		public boolean isAutoStartup() {
			return true;
		}
		public void stop(Runnable callback) {
			stop();
			callback.run();
		}
}
