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
	 * ��ͨ���ļ����Ʒ���
	 *
	 * @param fromFile
	 *            Դ�ļ�
	 * @param toFile
	 *            Ŀ���ļ�
	 * @throws FileNotFoundException
	 *             δ�ҵ��ļ��쳣
	 */
	public void fileCopyNormal(File fromFile, File toFile) throws FileNotFoundException {
		InputStream inputStream = null;
		OutputStream outputStream = null;
		try {
			inputStream = new BufferedInputStream(new FileInputStream(fromFile));
			outputStream = new BufferedOutputStream(new FileOutputStream(toFile));
			byte[] bytes = new byte[1024];
			int i;
			// ��ȡ�����������ݣ�Ȼ��д�뵽�������ȥ��ʵ�ָ���
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
	 * ��filechannel�����ļ�����
	 *
	 * @param fromFile
	 *            Դ�ļ�
	 * @param toFile
	 *            Ŀ���ļ�
	 */
	public void fileCopyWithFileChannel(File fromFile, File toFile) {
		FileInputStream fileInputStream = null;
		FileOutputStream fileOutputStream = null;
		FileChannel fileChannelInput = null;
		FileChannel fileChannelOutput = null;
		try {
			fileInputStream = new FileInputStream(fromFile);
			fileOutputStream = new FileOutputStream(toFile);
			// �õ�fileInputStream���ļ�ͨ��
			fileChannelInput = fileInputStream.getChannel();
			// �õ�fileOutputStream���ļ�ͨ��
			fileChannelOutput = fileOutputStream.getChannel();
			// ��fileChannelInputͨ�������ݣ�д�뵽fileChannelOutputͨ��
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
			nf.fileCopyNormal(new File("F:/360Downloads/Software/�����е��ʵ�_8.5.3.exe"), new File("F:/360Downloads/Software/�����е��ʵ�_8.5.3111.exe"));
			long t2 = System.currentTimeMillis();
			System.out.println("IO����ʱ�䣺" + (t2 - t1));
			long t3 = System.currentTimeMillis();
			nf.fileCopyWithFileChannel(new File("F:/360Downloads/Software/�����е��ʵ�_8.5.3.exe"), new File("F:/360Downloads/Software/�����е��ʵ�_8.5.3222.exe"));
			long t4 = System.currentTimeMillis();
			System.out.println("NIO����ʱ�䣺" + (t4 - t3));
 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
 
}
