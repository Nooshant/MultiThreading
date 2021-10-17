package producer.consumer;

import java.util.Queue;

public class Producer implements Runnable {

	private final Queue<Integer> queue;
	private final int size;

	Producer(Queue<Integer> list2, int size) {
		this.queue = list2;
		this.size = size;
	}

	@Override
	public void run() {
		for (int i = 0; i <= 10; i++) {
			try {
				produce(i);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		// Thread.currentThread().stop();//
	}

	private void produce(int x) throws InterruptedException {

		while (queue.size() == size) {
			synchronized (queue) {
				System.out.println("Queue is full. wait....");
				queue.wait();
			}
		}
		synchronized (queue) {
			queue.add(x);
			System.out.println("Produced: " + x);
			Thread.sleep(100);
			queue.notify();
		}
	}
}
