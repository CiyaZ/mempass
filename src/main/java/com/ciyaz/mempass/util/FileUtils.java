package com.ciyaz.mempass.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * @author CiyaZ
 */
public class FileUtils {

	private static final long FILE_COPY_BUFFER_SIZE = 1024 * 1024;

	/**
	 * 参考apache commons-io的拷贝文件，即使源文件被操作系统锁定也执行拷贝，java.nio.file.Files.copy()做不到这点
	 * 
	 * @param srcFile  源文件
	 * @param destFile 目标文件
	 */
	public static void copyFile(File srcFile, File destFile) {

		FileInputStream fis = null;
		FileChannel input = null;
		FileOutputStream fos = null;
		FileChannel output = null;

		try {

			fis = new FileInputStream(srcFile);
			input = fis.getChannel();
			fos = new FileOutputStream(destFile);
			output = fos.getChannel();

			final long size = input.size();
			long pos = 0;
			long count = 0;
			while (pos < size) {
				final long remain = size - pos;
				count = remain > FILE_COPY_BUFFER_SIZE ? FILE_COPY_BUFFER_SIZE : remain;
				final long bytesCopied = output.transferFrom(input, pos, count);
				if (bytesCopied == 0) {
					break;
				}
				pos += bytesCopied;
			}
			destFile.setLastModified(srcFile.lastModified());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}
}
