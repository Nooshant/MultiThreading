package com.duke.thread.OddEven;

public class OddEvenNumThread {
	
	static int number = 100;
	static int counter = 1;
	
	public void printEven() throws InterruptedException
	{
		while (counter <= number) {
			System.out.println(Thread.currentThread().getName()+" acquiring the lock on sync block");
		synchronized (this) {
			System.out.println(Thread.currentThread().getName()+" acquired the even lock.");
				while (counter % 2 != 0) {
					wait();
				}
				System.out.println("Even: " + counter);
				counter++;
				notifyAll();
			}
		}
	}
	public void printOdd() throws InterruptedException
	{
		while (counter <= number) {
			System.out.println(Thread.currentThread().getName()+" acquiring the lock on sync block");
			synchronized (this) {
				System.out.println(Thread.currentThread().getName()+" acquired the odd lock.");
				while (counter % 2 == 0) {
					wait();
				}
				System.out.println("Odd: " + counter);
				counter++;
				notifyAll();
			}
		}
	}

	public static void main(String[] args) {
		OddEvenNumThread obj = new OddEvenNumThread();
		Thread thread1 = new Thread(() -> {
			try {
				obj.printEven();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});// start();
		thread1.setName("even");
		thread1.start();

		Thread thread = new Thread(() -> {
			try {
				obj.printOdd();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		thread.setName("odd");
		thread.start();

	}
}
