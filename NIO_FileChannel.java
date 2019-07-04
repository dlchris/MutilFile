package net.mutilfile;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;

public class NIO_FileChannel {
	/**
	 * 普通的文件复制方法
	 *
	 * @param fromFile
	 *            源文件
	 * @param toFile
	 *            目标文件
	 * @throws FileNotFoundException
	 *             未找到文件异常
	 */
	public void fileCopyNormal(File fromFile, File toFile) throws FileNotFoundException {
		InputStream inputStream = null;
		OutputStream outputStream = null;
		try {
			inputStream = new BufferedInputStream(new FileInputStream(fromFile));
			outputStream = new BufferedOutputStream(new FileOutputStream(toFile));
			byte[] bytes = new byte[1024];
			int i;
			// 读取到输入流数据，然后写入到输出流中去，实现复制
			while ((i = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, i);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (inputStream != null)
					inputStream.close();
				if (outputStream != null)
					outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
 
	/**
	 * 用filechannel进行文件复制
	 *
	 * @param fromFile
	 *            源文件
	 * @param toFile
	 *            目标文件
	 */
	public void fileCopyWithFileChannel(File fromFile, File toFile) {
		FileInputStream fileInputStream = null;
		FileOutputStream fileOutputStream = null;
		FileChannel fileChannelInput = null;
		FileChannel fileChannelOutput = null;
		try {
			fileInputStream = new FileInputStream(fromFile);
			fileOutputStream = new FileOutputStream(toFile);
			// 得到fileInputStream的文件通道
			fileChannelInput = fileInputStream.getChannel();
			// 得到fileOutputStream的文件通道
			fileChannelOutput = fileOutputStream.getChannel();
			// 将fileChannelInput通道的数据，写入到fileChannelOutput通道
			fileChannelInput.transferTo(0, fileChannelInput.size(), fileChannelOutput);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fileInputStream != null)
					fileInputStream.close();
				if (fileChannelInput != null)
					fileChannelInput.close();
				if (fileOutputStream != null)
					fileOutputStream.close();
				if (fileChannelOutput != null)
					fileChannelOutput.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
 
	public static void main(String[] args) {
		NIO_FileChannel nf = new NIO_FileChannel();
		try {
			long t1 = System.currentTimeMillis();
			nf.fileCopyNormal(new File("F:/360Downloads/Software/网易有道词典_8.5.3.exe"), new File("F:/360Downloads/Software/网易有道词典_8.5.3111.exe"));
			long t2 = System.currentTimeMillis();
			System.out.println("IO复制时间：" + (t2 - t1));
			long t3 = System.currentTimeMillis();
			nf.fileCopyWithFileChannel(new File("F:/360Downloads/Software/网易有道词典_8.5.3.exe"), new File("F:/360Downloads/Software/网易有道词典_8.5.3222.exe"));
			long t4 = System.currentTimeMillis();
			System.out.println("NIO复制时间：" + (t4 - t3));
 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
 
}
