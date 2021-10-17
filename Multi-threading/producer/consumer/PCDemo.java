package producer.consumer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

class Producers extends Thread {
	private BlockingQueue<Integer> sharedQ;

	public Producers(BlockingQueue<Integer> sharedQ) {
		this.sharedQ = sharedQ;
	}

	@Override
	public void run() {
		for (int i = 0; i < 10; i++) {
			System.out.println(getName() + " produced " + i);
			try {
				sharedQ.put(i);
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}
}

class Consumers extends Thread {
	private BlockingQueue<Integer> sharedQ;

	public Consumers(BlockingQueue<Integer> sharedQ) {
		this.sharedQ = sharedQ;
	}

	@Override
	public void run() {
		try {
			while (true) {
				Integer item = sharedQ.take();
				System.out.println(getName() + " consumed " + item);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
}

public class PCDemo {

	public static void main(String[] args) {

		BlockingQueue<Integer> sharedQ = new LinkedBlockingQueue<Integer>();
		Producers p = new Producers(sharedQ);
		Consumers c = new Consumers(sharedQ);
		p.start();
		c.start();

	}
}
