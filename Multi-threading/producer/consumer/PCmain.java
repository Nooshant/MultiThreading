package producer.consumer;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class PCmain {

	public static void main(String[] args) {

		BlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(2);
		Producer p = new Producer(queue, 2);
		Consumer con = new Consumer(queue);
		
		Thread pthread =new Thread(p);
		Thread cThread =new Thread(con);
		pthread.start();
		cThread.start();
	}

}
