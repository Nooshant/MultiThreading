package producer.consumer;

import java.util.Queue;

public class Consumer implements Runnable {

	private final Queue<Integer> queue;

	Consumer(Queue<Integer> list2) {
		this.queue = list2;
	}

	@Override
	public void run() {
		while (true) {
			synchronized (queue) {
				while (queue.isEmpty()) {
					try {
						System.out.println("Queue is empty. wait....");
						queue.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				Integer valInteger = queue.remove();
				System.out.println("Consumed: " + valInteger);
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				queue.notify();
			}
		}
	}
}
