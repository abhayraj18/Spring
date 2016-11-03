package com.spring.test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.google.gson.Gson;
import com.spring.model.User;

public class ThreadPoolTest implements Callable<User> {

	String name;
	int i;

	public ThreadPoolTest(String name, int i) {
		this.name = name;
		this.i = i;
	}

	public static ThreadPoolExecutor threadPoolExecutor = null;
	
	public static void initThreadpool(){
		/*
		 * If the number of threads is less than the corePoolSize, create a new Thread to run a new task.
		   If the number of threads is equal (or greater than) the corePoolSize, put the task into the queue.
		   If the queue is full, and the number of threads is less than the maxPoolSize, create a new thread to run tasks in.
		   If the queue is full, and the number of threads is greater than or equal to maxPoolSize, reject the task.
		 */
		BlockingQueue<Runnable> q = new LinkedBlockingQueue<Runnable>(100);
		threadPoolExecutor = new ThreadPoolExecutor(10, 50, 20, TimeUnit.SECONDS, q);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void main(String[] args) {
		String s1 = "";
		if(s1.equals(null))
			System.out.println("hello");
		else
			System.out.println("Hi");
		
		initThreadpool();
		//ExecutorService executorService = Executors.newFixedThreadPool(10);
		List<Future<User>> futureList = new ArrayList<Future<User>>();
		for(int i = 0; i < 100; i++){
			Callable t = new ThreadPoolTest("Hello "+i, i);
			futureList.add(threadPoolExecutor.submit(t));
			//futureList.add(executorService.submit(t));
		}
		
		for(Future<?> future : futureList){
			try {
				User s = null;
				try {
					s = (User) future.get(1, TimeUnit.SECONDS);
				} catch (TimeoutException e) {
					e.printStackTrace();
				}
				System.out.println(new Gson().toJson(s));
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("Done");
		threadPoolExecutor.shutdown();
		//executorService.shutdown();
	}
	
	public void run() {
		System.out.println(this.name);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public User call() throws Exception {
		try {
			System.out.println(this.name);
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return new User(this.name);
	}

}