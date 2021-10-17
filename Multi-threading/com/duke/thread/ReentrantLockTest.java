package com.duke.thread;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.locks.ReentrantLock;

class Worker implements Runnable {
	String name;
	ReentrantLock re;

	public Worker(String name, ReentrantLock re) {
		super();
		this.name = name;
		this.re = re;
	}

	@Override
	public void run() {
		boolean done = false;
		while (!done) {
			if (re.tryLock()) {
				Date d = new Date();
				SimpleDateFormat ft = new SimpleDateFormat("hh:mm:ss");
				System.out.println("lock is free");
				try {

					System.out.println(
							"task name - " + name + " outer lock acquired at " + ft.format(d) + " Doing outer work");
					Thread.sleep(1500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				re.lock();
				try {
					d = new Date();
					ft = new SimpleDateFormat("hh:mm:ss");
					System.out.println(
							"task name - " + name + " inner lock acquired at " + ft.format(d) + " Doing inner work");
					System.out.println("Lock Hold Count - " + re.getHoldCount());
					Thread.sleep(1500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					// Inner lock release
					System.out.println("task name - " + name + " releasing inner lock");
					re.unlock();
				}
				System.out.println("Lock Hold Count - " + re.getHoldCount());
				System.out.println("task name - " + name + " work done");
				re.unlock();
				done = true;
			}
		}
	}
}

public class ReentrantLockTest {

	public static void main(String[] args) {

		ReentrantLock reentrantLock = new ReentrantLock();
		Worker w1 = new Worker("t-1", reentrantLock);
		Worker w2 = new Worker("t-2", reentrantLock);

		new Thread(w1).start();
		new Thread(w2).start();
	}
}
