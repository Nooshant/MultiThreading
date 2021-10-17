package com.duke.thread.demo;

class First
{
	public synchronized void firstMethod(Second t)
	{
		System.out.println("Execution of firstMethod ->"+Thread.currentThread().getName());
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//System.out.println("firstMethod End of->"+Thread.currentThread().getName());
		System.out.println("Calling syncMethod from firstMethod");
		t.callingSyncMethod();
	}

	public synchronized void callingSyncMethod() {
		System.out.println("Called sync method from secondThread");
	}
}

class Second
{
	public synchronized void secondThread(First t)
	{
		System.out.println("Execution of secondThread->"+Thread.currentThread().getName());
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//System.out.println("secondThread End of->"+Thread.currentThread().getName());
		System.out.println("Calling syncMethod from secondThread");
		t.callingSyncMethod();
	}

	public synchronized void callingSyncMethod() {
		System.out.println("Called sync method from firstMethod");
	}
}

public class CreateDeadlock extends Thread
{
	First obj1 = new First();
	Second obj2 = new Second();
	
	public void m1()
	{
		this.start();
		obj1.firstMethod(obj2);
	}
	
	@Override
	public void run() {
		obj2.secondThread(obj1);
	}
	
	public static void main(String[] args) {
		CreateDeadlock obj = new CreateDeadlock();
		obj.m1();
	}

}
