package net.mutilfile;
import java.io.File;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class DelFile {
	public static void main(String[] args) {
		long t1 = System.currentTimeMillis();
		// 第一种方式
		ExecutorService executor = Executors.newCachedThreadPool();

		for (int i = 0; i < 50000; i++) {
			Task3 task = new Task3(i);
			FutureTask<File> futureTask = new FutureTask<File>(task);
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

	}
}

class Task3 implements Callable<File> {

	private int ii;

	public Task3(int ii) {
		this.ii = ii;
	}

	@Override
	public File call() throws Exception {
		File file = new File("F:/360Downloads/Software/to/" + ii + ".txt");
		File file1 = new File("F:/360Downloads/Software/to1/" + ii + ".txt");
		file.delete();
		file1.delete();
		return file;
	}
}