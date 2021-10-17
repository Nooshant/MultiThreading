package com.duke.thread;

class A {
	
	public void m2(A a) {
		synchronized (a) {//acquire the lock on a1 then acquired the lock a
			System.out.println(Thread.currentThread().getName() + " acquire lock");
			synchronized (this) { //trying to acquire lock on this object
				System.out.println("trying to acquire");
			}
		}
	}
}

public class CreateDeadlockDemo {

	public static void main(String[] args) {

		A a = new A();
		A a1 = new A();

		Runnable runnable1 = new Runnable() {
			
			@Override
			public void run() {
				//System.out.println("a..........");
				a.m2(a1);
			}
		};
		Runnable runnable2 = new Runnable() {
			
			@Override
			public void run() {
				//System.out.println("a1..........");
				a1.m2(a);
			}
		};
		
		Thread t1 = new Thread(runnable1);
		t1.setName("rooh");
		t1.start();
		Thread t2 = new Thread(runnable2);
		t2.setName("deep");
		t2.start();
	}
}
