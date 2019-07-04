package net.mutilfile;
import java.io.File;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class Test2 {
	public static void main(String[] args) {
		long t1 = System.currentTimeMillis();
		// 第一种方式
		ExecutorService executor = Executors.newCachedThreadPool();
		NIO_FileChannel nf = new NIO_FileChannel();
		for (int i = 0; i < 50000; i++) {
			Task2 task = new Task2(i, nf);
			FutureTask<File> futureTask = new FutureTask<File>(task);
			executor.submit(futureTask);
		}
		
		executor.shutdown();
		
		while (true) {
			if (executor.isTerminated()) {
				long t2 = System.currentTimeMillis();
				System.out.println("IO复制时间：" + (t2 - t1));
				break;
			}
		}

	}
}

class Task2 implements Callable<File> {

	private int ii;
	private NIO_FileChannel nf;

	public Task2(int ii, NIO_FileChannel nf) {
		this.ii = ii;
		this.nf = nf;
	}

	@Override
	public File call() throws Exception {
		nf.fileCopyWithFileChannel(new File("F:/360Downloads/Software/from/" + ii + ".txt"), new File("F:/360Downloads/Software/to1/" + ii + ".txt"));
		return null;
	}
}