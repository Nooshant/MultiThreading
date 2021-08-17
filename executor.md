# Executor

`Executor` declares a solitary `void execute(Runnable runnable)` method that executes the runnable task named runnable at some point in the future. `execute()` throws `java.lang.NullPointerException` when runnable is `null` and `java.util.concurrent.RejectedExecutionException` when it cannot execute runnable.


Unlike `Runnable`, whose `void run()` method cannot return a value and `throw checked exceptions`, `Callable<V>’s V call()` method returns a value and can throw checked exceptions because it’s declared with a `throws Exception` clause.


# Future

`Future` interface, which represents the result of an `asynchronous` computation. The result is known as a future because it typically will not be available until some moment in the future. `Future`, whose generic type is `Future<V>`, provides methods for canceling a task, for returning a task’s value, and for determining whether or not the task has finished.

Eg.
Suppose you intend to write an application whose graphical user interface lets the user enter a word. After the user enters the word, the
application presents this word to several online dictionaries and obtains each dictionary’s entry. These entries are subsequently displayed to the user.

Because online access can be slow, and because the user interface should remain responsive (perhaps the user might want to end the application),
you offload the “obtain word entries” task to an executor that runs this task on a separate thread. The following example uses ExecutorService,
Callable, and Future to accomplish this objective:

```
ExecutorService executor = ...; // ... represents some executor creation
Future<String[]> taskFuture =
   executor.submit(new Callable<String[]>()
                   {
                      @Override
                      public String[] call()
                      {
                         String[] entries = ...;
                         // Access online dictionaries
                         // with search word and populate
                         // entries with their resulting
                         // entries.
                         return entries;
                      }
                   });
// Do stuff.
String entries = taskFuture.get();

```
**Note**  Thread pools are used to eliminate the overhead from having to create a new thread for each submitted task. Thread creation isn’t cheap, and having to create many threads could severely impact an application’s performance


**Calculating Euler’s Number**

```
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CalculateE
{
   final static int LASTITER = 17;

   public static void main(String[] args)
   {
      ExecutorService executor = Executors.newFixedThreadPool(1);
      Callable<BigDecimal> callable;
      callable = new Callable<BigDecimal>()
                 {
                    @Override
                    public BigDecimal call()
                    {
                       MathContext mc =
                         new MathContext(100, RoundingMode.HALF_UP);
                       BigDecimal result = BigDecimal.ZERO;
                       for (int i = 0; i <= LASTITER; i++)
                       {
                          BigDecimal factorial =
                             factorial(new BigDecimal(i));
                          BigDecimal res = BigDecimal.ONE.divide(factorial,
                                                                 mc);
                          result = result.add(res);
                       }
                       return result;
                    }

                    public BigDecimal factorial(BigDecimal n)
                    {
                       if (n.equals(BigDecimal.ZERO))
                          return BigDecimal.ONE;
                       else
                          return n.multiply(factorial(n.
                                   subtract(BigDecimal.ONE)));
                    }
                 };
      Future<BigDecimal> taskFuture = executor.submit(callable);
      try
      {
         while (!taskFuture.isDone())
            System.out.println("waiting");
         System.out.println(taskFuture.get());
      }
      catch(ExecutionException ee)
      {
         System.err.println("task threw an exception");
         System.err.println(ee);
      }
      catch(InterruptedException ie)
      {
         System.err.println("interrupted while waiting");
      }
      executor.shutdownNow();
   }
}
```

Explanation:

The callable’s call() method calculates e by evaluating the mathematical power series e = 1 / 0! + 1 / 1! + 1 / 2! + . . . . This series can 
be evaluated by summing 1 / n!, where n ranges from 0 to infinity (and ! stands for factorial).

call() first instantiates java.math.MathContext to encapsulate a precision (number of digits) and a rounding mode. I chose 100 as an upper limit 
on e’s precision, and I also chose HALF_UP as the rounding mode.

call() next initializes a java.math.BigDecimal local variable named result to BigDecimal.ZERO. It then enters a loop that calculates a factorial, 
divides BigDecimal.ONE by the factorial, and adds the division result to result.

The divide() method takes the MathContext instance as its second argument to provide rounding information. (If I specified 0 as the precision for 
the math context and a nonterminating decimal expansion [the quotient result of the division cannot be represented exactly—0.3333333..., for example]
occurred, java.lang.ArithmeticException would be thrown to alert the caller to the fact that the quotient cannot be represented exactly. The executor 
would rethrow this exception as ExecutionException.)

Output:
```
waiting
waiting
waiting
waiting
waiting
2.718281828459045070516047795848605061178979635251032698900735004065225042504843314055887974344245741730039454062711
```

 
