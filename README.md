# MultiThreading
MultiThreading related info

- Video Link
   Best https://www.youtube.com/watch?v=J3QZ5gfCtAg&ab_channel=DefogTech

# CountDownLatch
A CountDownLatch is a construct that a thread waits on while other threads count down on the latch until it reaches zero.
We can think of this like a dish at a restaurant that is being prepared. No matter which cook prepares however many of the n items, the waiter must wait
until all the items are on the plate. If a plate takes n items, any cook will count down on the latch for each item she puts on the plate.

![image](https://user-images.githubusercontent.com/29571875/131259466-9eb701a1-633a-4c97-ab21-b001a711d2d1.png)

Main thread has to wait on method `latch.await()` to reach to zero count to continue and `latch.countdown()` will decrease the count down by one.

![image](https://user-images.githubusercontent.com/29571875/131259528-a612cf32-c903-41ed-b1a4-c965e98ff6c5.png)

from read point, now after completion of latch work main thread started.


# CyclicBarrier
A CyclicBarrier is a reusable construct where a group of threads waits together until all of the threads arrive. At that point, the barrier is broken and an action
can optionally be taken.
We can think of this like a group of friends. Every time they plan to eat at a restaurant they decide a common point where they can meet. They wait for each 
other there, and only when everyone arrives can they go to the restaurant to eat together.

![image](https://user-images.githubusercontent.com/29571875/131259569-e28a34a0-6a23-402c-b4bf-8ca41ea4c901.png)

All the the thread after execution has to come one by one and wait on `barrier.await()` method to move further if any one has not reached then
other has to come and wait. Once all reach to one point then it will start execution.

![image](https://user-images.githubusercontent.com/29571875/131259653-8b1531e0-1027-4bad-b096-3a1ff1d1db1c.png)

# Phaser 
It is combination of CyclicBarrier and CountDownLatch.
![image](https://user-images.githubusercontent.com/29571875/131259713-3ab89427-abde-4e96-844b-20636ba2ee93.png)



# Future
- A Future interface provides methods to check if the computation is complete, to wait for its completion and to retrieve the results of the computation

```
ExecutorService executor = Executors.newFixedThreadPool(1);
Future<Integer> future = executor.submit(task);

System.out.println("future done? " + future.isDone());

Integer result = future.get();

System.out.println("future done? " + future.isDone());
System.out.print("result: " + result);

```

- Synchronized locks does not offer any mechanism of waiting queue in which after the execution of one thread any thread running in parallel can acquire the lock. Due to which the thread which is there in the system and running for a longer period of time never gets chance to access the shared resource thus leading to starvation.

- Reentrant locks are very much flexible and has a fairness policy in which if a thread is waiting for a longer time and after the completion of the currently executing thread we can make sure that the longer waiting thread gets the chance of accessing the shared resource hereby decreasing the throughput of the system and making it more time consuming.

# FutureTask:

- FutureTask implementation Future interface and RunnableFuture Interface, means one can use FutureTask as Runnable and can be submitted to ExecutorService for execution.
- When one call Future.submit() Callable or Runnable objects then most of time ExecutorService creates FutureTask, and one can create it manually also.
- FutureTask acts like a latch.
- Computation represent by FutureTask is implemented with Callable interface.
- It implements Future or Callable interface.
- Behaviour of get() method depends on the state of the task. If tasks are not completed get() method waits or blocks till the task is completed. 
   Once task completed, it returns the result or throws an ExecutionException.
   
```
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;
 
class MyRunnable implements Runnable {
 
    private final long waitTime;
 
    public MyRunnable(int timeInMillis)
    {
        this.waitTime = timeInMillis;
    }
 
    @Override
    public void run()
    {
        try {
            // sleep for user given millisecond
            // before checking again
            Thread.sleep(waitTime);
 
            // return current thread name
            System.out.println(Thread
                                   .currentThread()
                                   .getName());
        }
 
        catch (InterruptedException ex) {
            Logger
                .getLogger(MyRunnable.class.getName())
                .log(Level.SEVERE, null, ex);
        }
    }
}
 
// Class FutureTaskExample execute two future task
class FutureTaskExample {
 
    public static void main(String[] args)
    {
        // create two object of MyRunnable class
        // for FutureTask and sleep 1000, 2000
        // millisecond before checking again
        MyRunnable myrunnableobject1 = new MyRunnable(1000);
        MyRunnable myrunnableobject2 = new MyRunnable(2000);
 
        FutureTask<String>
            futureTask1 = new FutureTask<>(myrunnableobject1,
                                           "FutureTask1 is complete");
        FutureTask<String>
            futureTask2 = new FutureTask<>(myrunnableobject2,
                                           "FutureTask2 is complete");
 
        // create thread pool of 2 size for ExecutorService
        ExecutorService executor = Executors.newFixedThreadPool(2);
 
        // submit futureTask1 to ExecutorService
        executor.submit(futureTask1);
 
        // submit futureTask2 to ExecutorService
        executor.submit(futureTask2);
 
        while (true) {
            try {
 
                // if both future task complete
                if (futureTask1.isDone() && futureTask2.isDone()) {
 
                    System.out.println("Both FutureTask Complete");
 
                    // shut down executor service
                    executor.shutdown();
                    return;
                }
 
                if (!futureTask1.isDone()) {
 
                    // wait indefinitely for future
                    // task to complete
                    System.out.println("FutureTask1 output = "
                                       + futureTask1.get());
                }
 
                System.out.println("Waiting for FutureTask2 to complete");
 
                // Wait if necessary for the computation to complete,
                // and then retrieves its result
                String s = futureTask2.get(250, TimeUnit.MILLISECONDS);
 
                if (s != null) {
                    System.out.println("FutureTask2 output=" + s);
                }
            }
 
            catch (Exception e) {
                Sysmtem.out.println("Exception: " + e);
            }
        }
    }
}

```

# Output

```
FutureTask1 output=FutureTask1 is complete
Waiting for FutureTask2 to complete
Waiting for FutureTask2 to complete
Waiting for FutureTask2 to complete
Waiting for FutureTask2 to complete
FutureTask2 output=FutureTask2 is complete
Both FutureTask Complete
```

# CompletableFuture in java-8
Future get() method is blocking in nature means until and unless you don't have result it will block the main thread.

https://www.javatpoint.com/completablefuture-in-java
https://www.youtube.com/watch?v=ImtZgX1nmr8&ab_channel=DefogTech


# Reentrant Lock:

```
ExecutorService executor = Executors.newFixedThreadPool(2);
ReentrantLock lock = new ReentrantLock();

executor.submit(() -> {
    lock.lock();
    try {
        sleep(1);
    } finally {
        lock.unlock();
    }
});

executor.submit(() -> {
    System.out.println("Locked: " + lock.isLocked());
    System.out.println("Held by me: " + lock.isHeldByCurrentThread());
    boolean locked = lock.tryLock();
    System.out.println("Lock acquired: " + locked);
});

stop(executor);
```
While the first task holds the lock for one second the second task obtains different information about the current state of the lock:

# Output
```
Locked: true
Held by me: false
Lock acquired: false
```

The method tryLock() as an alternative to lock() tries to acquire the lock ``without pausing the current thread``. The boolean result must be used to check 
if the lock has actually been acquired before accessing any shared mutable variables.


For reference:
https://winterbe.com/posts/2015/04/30/java8-concurrency-tutorial-synchronized-locks-examples/


# Join:

join(): It will put the current thread(Main-thread) on wait until the thread on which it is called is dead. If thread is interrupted then it will throw InterruptedException

Ex. Main thread will wait until for both t1 and t2, but t1 has to wait until the t2 to complete to begin itself.


Syntax:

`` public final void join() ``

```
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
 
 ```

- **CONCURRENCY**

![image](https://user-images.githubusercontent.com/29571875/128905958-1e8bc0e8-22b4-4e6a-bf07-66f730900ecb.png)




![image](https://user-images.githubusercontent.com/29571875/128906051-8f098a04-bc70-4c6a-a0ff-0de4d6a48173.png)


# Object level Lock and Class level Lock

https://java2blog.com/object-level-locking-vs-class-level-locking-java/

# Deadlock Example

```
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
		t1.start();		
		new Thread(()->{
			bResource.m2(aResource);	
		}).start();
	}
}
```

