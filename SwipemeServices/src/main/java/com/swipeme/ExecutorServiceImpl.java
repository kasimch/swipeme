package com.swipeme;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.swipeme.dao.TimeSheetDAO;
import com.swipeme.domain.SwipeDetailResult;
import com.swipeme.executor.WorkerThread;

@Component
public class ExecutorServiceImpl extends Thread {

	public static ConcurrentLinkedQueue<List<SwipeDetailResult>> queue = new ConcurrentLinkedQueue<List<SwipeDetailResult>>();
	ExecutorService executor = null;
	// ExecutorServiceImpl serviceImpl = new ExecutorServiceImpl();

	@Autowired
	private TimeSheetDAO timeSheetDAO;

	public TimeSheetDAO getTimeSheetDAO() {
		return timeSheetDAO;
	}

	public void setTimeSheetDAO(TimeSheetDAO timeSheetDAO) {
		this.timeSheetDAO = timeSheetDAO;
	}

	@PostConstruct
	public void Sample() {
		executor = Executors.newFixedThreadPool(10);
		this.setName("MainThread");
		this.start();
		System.out.println("Thread Pool Excutor Start ::::::::::::::::::::::::");
	}

	@PreDestroy
	public void cleanUp() throws Exception {
		System.out.println("Spring Container is destroy! ");
		if (executor != null) {
			executor.shutdown();
			if (!executor.isTerminated()) {
				executor.shutdownNow();
			}
		}
		System.out.println("Thread Pool Excutor End ::::::::::::::::::");
	}

	@Override
	public void run() {

		try {
			while (!executor.isTerminated()) {
				if (queue != null && queue.size() > 0) {
					Runnable worker = new WorkerThread(timeSheetDAO, queue.poll());
					executor.execute(worker);
				//	System.out.println("REcords::::::::::::::::::");
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
