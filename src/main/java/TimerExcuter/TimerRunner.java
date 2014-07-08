package TimerExcuter;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class TimerRunner {
	public static void main(String[] args){
		 final Runnable stuffToDo = new Thread() {
		        @Override public void run() {
		        	int i = 0;
		        	while(true){
		        		System.out.println("printing msg:" + i);
		        		i++;
		        	}
		        }
		    };
		 final ExecutorService executor = Executors.newSingleThreadExecutor();
		 final Future future = executor.submit(stuffToDo);
		 executor.shutdown(); // This does not cancel the already-scheduled task.
		 try { future.get(3, TimeUnit.SECONDS); 
		 }catch (InterruptedException ie) {
		 }catch (ExecutionException ee) {
		 }catch (TimeoutException te) {
			 System.err.println("-------------------TIME OUT!!!---------------");
			 System.exit(0);
		 }
		 if (!executor.isTerminated())
		    executor.shutdownNow(); // If you want to stop the code that hasn't finished.
		    
	}
}
