package com.duke.thread;

class Shared extends Thread {
	private String name;

	public Shared(String name) {
		this.name = name;
	}

	public void run() {
		System.out.println(Thread.currentThread().getName());
		System.out.println(name + "M1");
		System.out.println(name + "M2");
	}
}

public class JoinTest {

	public static void main(String[] args) throws InterruptedException {
		
		Shared t1 = new Shared("1->");
		Shared t2 = new Shared("2->");
		
		System.out.println("Begin "+Thread.currentThread().getName());
		t2.start();
		t2.join();
		t1.start();
		t1.join();
		System.out.println("End "+Thread.currentThread().getName());	
		
	}

}

/* Result
 
 Begin main
Thread-1
2->M1
2->M2
Thread-0
1->M1
1->M2
End main
 
 */
