package com.duke.thread;


class SharedResource
{
	public void m1(SharedResource obj) {
		System.out.println("m1-> method Waiting to acquire lock:"+Thread.currentThread().getName());
		synchronized (obj) {
		System.out.println("m1-> method Acquired lock by "+Thread.currentThread().getName());
		this.test();
		}
	}
	
	public void m2(SharedResource obj) {
		System.out.println("m2-> method Waiting to acquire lock:"+Thread.currentThread().getName());
		synchronized (obj) {
			System.out.println("m2-> method Acquired lock by "+Thread.currentThread().getName());
			this.test();
		}
	}
	
	public void test() {
		System.out.println("Waiting to acquire lock:"+Thread.currentThread().getName());
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		synchronized (this) {
			System.out.println("Locked by "+Thread.currentThread().getName());
		}
	}
}

public class CreateDeadLoackDemo2 {

	public static void main(String[] args) throws InterruptedException {
		
		SharedResource aResource = new SharedResource(); 
		SharedResource bResource = new SharedResource(); 
		
		Thread t1 = new Thread(()->{
			aResource.m1(bResource);	
		});
		//Thread.yield();
		t1.start();
		
		new Thread(()->{
			bResource.m2(aResource);	
		}).start();
	}

}
