package com.duke.thread.synchronize;

class Display
{
	public static synchronized void displaySachin()
	{
		for (int i = 0; i < 10; i++) {
			System.out.println(Thread.currentThread().getName());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
		}
	}
}

class MyThread extends Thread
{
	Display d1;
	
	public MyThread(Display d1) {
		super();
		this.d1 = d1;
	}

	@Override
	public void run() {
		d1.displaySachin();
	}
}

public class StaticSyncDemo {

	public static void main(String[] args) {
		
		Display d1 = new Display();
		Display d2 = new Display();
		
		MyThread t1 = new MyThread(d1);
		t1.setName("Sachin");
		MyThread t2 = new MyThread(d2);
		t2.setName("sehwag");
		t1.start();
		t2.start();
		System.out.println("Main thread completed..");
		
	}
}
