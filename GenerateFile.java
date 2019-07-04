package net.mutilfile;
import java.io.File;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class GenerateFile {
	public static void main(String[] args) {
		long t1 = System.currentTimeMillis();
		
//		List<FutureTask<File>> taskList = new ArrayList<FutureTask<File>>();
		// 第一种方式
		ExecutorService executor = Executors.newFixedThreadPool(1000);

		for (int i = 0; i < 50000; i++) {
			Task task = new Task(i);
			FutureTask<File> futureTask = new FutureTask<File>(task);
//			taskList.add(futureTask);
			executor.execute(futureTask);
		}

		executor.shutdown();
		
		while (true) {
			if (executor.isTerminated()) {
				long t2 = System.currentTimeMillis();
				System.out.println("所有任务执行完毕" + (t2 - t1));
				break;
			}
		}



		// try {
		// Thread.sleep(1000);
		// } catch (InterruptedException e1) {
		// e1.printStackTrace();
		// }
		//
		// System.out.println("主线程在执行任务");
		//
		// try {
		// System.out.println("task运行结果" + futureTask.get());
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// } catch (ExecutionException e) {
		// e.printStackTrace();
		// }

		
	}
}

class Task implements Callable<File> {

	private int ii;

	public Task(int ii) {
		this.ii = ii;
	}

	@Override
	public File call() throws Exception {
		File file = new File("F:/360Downloads/Software/from/" + ii + ".txt");
		file.createNewFile();
		return file;
	}
}