package com.duke.thread;

class Testing implements Runnable
{
	int x;
	Testing(int x)
	{
		this.x=x;
	}
	public void run() {
		System.out.println("Running....");
	}	
}

public class MyThread {

	public static void main(String[] args) {
		Thread thread = new Thread(new Testing(10));
		thread.start();
	}
}
