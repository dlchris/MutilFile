package net.mutilfile;
import java.io.File;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class Test1 {
	public static void main(String[] args) {
		long t1 = System.currentTimeMillis();
		// ��һ�ַ�ʽ
		ExecutorService executor = Executors.newCachedThreadPool();
		NIO_FileChannel nf = new NIO_FileChannel();
		for (int i = 0; i < 50000; i++) {
			Task1 task = new Task1(i, nf);
			FutureTask<File> futureTask = new FutureTask<File>(task);
			executor.submit(futureTask);
		}
		
		executor.shutdown();
		
		while (true) {
			if (executor.isTerminated()) {
				long t2 = System.currentTimeMillis();
				//IO����ʱ�䣺21545
				//IO����ʱ�䣺18907
				
				// IO����ʱ�䣺29094
				//IO����ʱ�䣺25600
				System.out.println("IO����ʱ�䣺" + (t2 - t1));
				break;
			}
		}

	}
}

class Task1 implements Callable<File> {

	private int ii;
	private NIO_FileChannel nf;

	public Task1(int ii, NIO_FileChannel nf) {
		this.ii = ii;
		this.nf = nf;
	}

	@Override
	public File call() throws Exception {
		nf.fileCopyNormal(new File("F:/360Downloads/Software/from/" + ii + ".txt"), new File("F:/360Downloads/Software/to/" + ii + ".txt"));
		return null;
	}
}