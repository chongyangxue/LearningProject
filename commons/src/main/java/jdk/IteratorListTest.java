package jdk;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 每个任务有两个字段ID和Pre_ID，pre_ID代表任务的前提任务，要求对一个任务列表按流程排序
 * 
 * @author Sachiel
 *
 */
public class IteratorListTest {
	
	public static void main(String[] args) {
		List<Task> task1 = new ArrayList<Task>();
		task1.add(new Task(1, 0));
		task1.add(new Task(2, 1));
		task1.add(new Task(6, 5));
		task1.add(new Task(4, 3));
		task1.add(new Task(3, 2));
		task1.add(new Task(5, 4));
		task1.add(new Task(9, 8));
		task1.add(new Task(7, 6));
		task1.add(new Task(8, 7));
		task1.add(new Task(10, 9));
		List<Task> task2 = new ArrayList<Task>(task1);
		TestThread t1 = new TestThread(task1);
		TestThread t2 = new TestThread(task2);
		t1.start();
		t2.start();
	}
	
	/**
	 * 如果你不需要内部类对象与其外围类对象之间有联系，那你可以将内部类声明为static。
	 * 这通常称为嵌套类（nested class）。Static Nested Class是被声明为静态（static）的内部类，
	 * 它可以不依赖于外部类实例被实例化。而通常的内部类需要在外部类实例化后才能实例化。想要理解static应用于内部类时的含义，
	 * 你就必须记住，普通的内部类对象隐含地保存了一个引用，指向创建它的外围类对象。然而，当内部类是static的时，就不是这样了。
	 * 嵌套类意味着： 
		1. 嵌套类的对象，并不需要其外围类的对象。 
		2. 不能从嵌套类的对象中访问非静态的外围类对象。 
	 * @author Sachiel
	 *
	 */
	static class Task {
		private int id;
		private int pre_id;
		
		public Task(int id, int pre_id) {
			this.id = id;
			this.pre_id = pre_id;
		}
		
		public int getId() {
			return this.id;
		}
		
		public int getPreId(){
			return this.pre_id;
		}
	}
	
	static class TestThread extends Thread{
		private List<Task> tasks;
		private List<Task> results = new ArrayList<Task>();
		
		private TestThread(List<Task> tasks){
			this.tasks = tasks;
		}
		
		public List<Task> getResults(){
			return results;
		}
		
		@Override
		public void run(){
			this.results = getSort(tasks);
			for(Task task : results) {
				System.out.print(" " + task.getId());
			}
		}
		
		/**
		 * 如果getSort()方法不加synchronized关键字，排序结果会错误
		 * @param tasks
		 * @return
		 */
		private List<Task> getSort(List<Task> tasks) {
			
			int taskCount = tasks.size();
			List<Task> taskQueue = new ArrayList<Task>();
			taskQueue.add(tasks.get(0));
			tasks.remove(0);
			int queueCount = taskQueue.size();
			
			/*
			 * 完全可以用for(int i = 0; i < tasks.size();i++)来实现
			 */
			while(queueCount < taskCount) {
				for(Iterator<Task> iter = tasks.iterator(); iter.hasNext();) {
					Task task = iter.next();
					for(Task queue : taskQueue) {
						if(queue.getId() == task.getPreId()){
							taskQueue.add(task);
							iter.remove();
							queueCount++;
							break;
						}
					}
				}
			}
			
			return taskQueue;
		}
	}
}
