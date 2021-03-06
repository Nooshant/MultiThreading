# MultiThreading
MultiThreading related info

- Video Link
   Best https://www.youtube.com/watch?v=J3QZ5gfCtAg&ab_channel=DefogTech

# 
Java 8 new feature **StampedLock** https://stackoverflow.com/questions/26094200/what-is-stampedlock-in-java

# CountDownLatch

- CountDownLatch is a concurrency construct that allows one or more threads to wait for a given set of operations to complete. A CountDownLatch
 is initialized with a given count. This count is decremented by calls to the countDown() method. ... Calling await() blocks the thread until the count reaches zero.


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
other has to come and wait. Once all reach to one point then it will start execution..

![image](https://user-images.githubusercontent.com/29571875/131259653-8b1531e0-1027-4bad-b096-3a1ff1d1db1c.png)

# Phaser 
It is combination of CyclicBarrier and CountDownLatch and it can act as CyclicBarrier or CountDownLatch.

As similar to CountDownLatch:-

![image](https://user-images.githubusercontent.com/29571875/140633361-744dfb70-f4f9-4ffe-966f-3d23e02a0220.png)

As similar to CyclicBarrier:-

![image](https://user-images.githubusercontent.com/29571875/140633442-3d36632c-a529-481e-a9b3-4a29bdf703a6.png)


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

1. Non-blocking
2. Ability to Programmatically completing a future(using complete("") method)
3. Perform Error handling
4. Ability to Chain several futures
5. Ability to combine results of multiple futures (that run in parallel)

https://www.javatpoint.com/completablefuture-in-java
https://www.youtube.com/watch?v=ImtZgX1nmr8&ab_channel=DefogTech

![image](https://user-images.githubusercontent.com/29571875/135745468-3e7cbbd1-2ca2-4a17-9d0d-461246920825.png)
![image](https://user-images.githubusercontent.com/29571875/135745534-03413a6f-a852-4f18-aaf3-3cde59946869.png)

The thread will be blocked for next option until and unless it is not done.
Suppose next thread is waiting for this result to process further so here get() method will block until it is not completed.
means it will block the other thread(main-thread) to process.

![image](https://user-images.githubusercontent.com/29571875/135747439-ad13301d-37f9-4971-9ce1-8461a8711312.png)



![image](https://user-images.githubusercontent.com/29571875/135747513-4c330732-7026-4900-b91a-fa808de7827a.png)
Exception can be handle for one complete flow Async operation.




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

# ReentrantReadWriteLock

![image](https://user-images.githubusercontent.com/29571875/141689663-7761a2cb-a0bc-4494-a6e9-13d4ce0d59f7.png)
- Create separate thread for read and write operation
- Both the read thread will be allowed to acquire the lock and accedd the readResource at a time.


# Semaphore


When we wanted to restrict the no. of call at a time to any Resource we can use semaphore and has overloaded method like.

![image](https://user-images.githubusercontent.com/29571875/136698441-2dc98b69-5ee5-4f23-93c4-7d1470280c1c.png)

![image](https://user-images.githubusercontent.com/29571875/136698041-879fc6fc-ce3f-4120-8a32-7e0593b2989e.png)

When we want restrict the call from application to Slow service(eg. example) since Slow service which is the shared resource can take only three request at a time.

It has acquire() and release() method. Acquire method is used to acquire the lock.
``
 // acquiring the lock
                sem.acquire();
``
> Semaphore sem = new Semaphore(1);
This is the way to provide the no. of permits at creation time.

![image](https://user-images.githubusercontent.com/29571875/136698299-dbaef03d-bdb0-4d4d-9ee4-d107fd8422c1.png)

![image](https://user-images.githubusercontent.com/29571875/136698337-e93958e5-a781-4a7a-80a7-e4d9684ea1b3.png)

![image](https://user-images.githubusercontent.com/29571875/136698496-8d9c1d79-f386-441f-a54a-8ac504b4c058.png)


#

**volatile:**

volatile is a keyword. volatile forces all threads to get latest value of the variable from main memory instead of cache. No locking is required to access volatile variables. All threads can access volatile variable value at same time.

Using volatile variables reduces the risk of memory consistency errors, because any write to a volatile variable establishes a happens-before relationship with subsequent reads of that same variable.

This means that changes to a volatile variable are always visible to other threads. What's more, it also means that when a thread reads a volatile variable, it sees not just the latest change to the volatile, but also the side effects of the code that led up the change.

When to use: One thread modifies the data and other threads have to read latest value of data. Other threads will take some action but they won't update data.

**AtomicXXX:**

AtomicXXX classes support lock-free thread-safe programming on single variables. These AtomicXXX classes (like AtomicInteger) resolves memory inconsistency errors / side effects of modification of volatile variables, which have been accessed in multiple threads.

When to use: Multiple threads can read and modify data.
