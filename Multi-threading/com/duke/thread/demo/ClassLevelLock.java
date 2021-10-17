package com.duke.thread.demo;


public class ClassLevelLock implements Runnable{
	
	@Override
	public void run() {
		class_lock();
	}

	private static synchronized void class_lock() {
		System.out.println(Thread.currentThread().getName());
		
		synchronized (ClassLevelLock.class) {
			System.out.println("inside static block thread-> "+Thread.currentThread().getName());
			System.out.println("End of thread-> "+Thread.currentThread().getName());
		}
		
	}

	public static void main(String[] args) {
		
		ClassLevelLock c1 = new ClassLevelLock();
		Thread t1= new Thread(c1);
		t1.setName("T1");
		Thread t2= new Thread(c1);
		t2.setName("T2");
		
		
		ClassLevelLock c3 = new ClassLevelLock();
		Thread t3= new Thread(c3);
		t3.setName("T3");
		t1.start();
		t2.start();
		t3.start();

	}

}
