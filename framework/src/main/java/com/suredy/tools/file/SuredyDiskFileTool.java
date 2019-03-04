package com.suredy.tools.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 文件存储工具类
 * 
 * @author VIVID.G
 * @since 2015-10-26
 * @version v0.1
 */
@Component
public class SuredyDiskFileTool {
	private final static Logger log = LoggerFactory.getLogger(SuredyDiskFileTool.class);

	/* 读（写）文件缓冲大小，默认64K */
	private int bufferSize = 1024 * 64;

	/**
	 * 存储文件到本地磁盘
	 * 
	 * @param source
	 * @param absoluteFilePath 文件的绝对路径
	 * @param overwrite 文件存在时，是否覆盖文件
	 * @return
	 */
	public File toDiskFile(final byte[] source, final String absoluteFilePath, final boolean overwrite) {
		if (source == null || StringUtils.isBlank(absoluteFilePath)) {
			log.error("Invalid byte array source or absolute file path.");
			return null;
		}

		File file = new File(absoluteFilePath);

		if (!file.isAbsolute()) {
			log.error("Not a absolute file path: " + absoluteFilePath);
			return null;
		}

		// do not allowed to overwrite file
		if (file.isFile() && !overwrite) {
			log.error("File exists, and can not be overwrite. " + absoluteFilePath);
			return null;
		}

		// begin to receive file data
		File temp = this.toRandomDiskFile(source, file.getParent(), ".tmp");

		if (temp == null) {
			log.error("Failed to receive file data.");
			return null;
		}

		file = this.toDiskFile(temp, absoluteFilePath, overwrite);

		if (file == null && temp.isFile()) {
			this.deleteFromDisk(temp.getAbsolutePath());
			log.error("Failed to rename temp file to target file.");
		}

		return file;
	}

	/**
	 * 存储文件到本地磁盘
	 * 
	 * @param source
	 * @param absoluteFilePath 文件存储的绝对路径
	 * @return
	 */
	public File toDiskFile(final InputStream source, final String absoluteFilePath, final boolean overwrite) {
		if (source == null || StringUtils.isBlank(absoluteFilePath)) {
			log.error("Invalid input stream source or absolute file path.");
			return null;
		}

		File file = new File(absoluteFilePath);

		if (!file.isAbsolute()) {
			log.error("Not a absolute file path: " + absoluteFilePath);
			return null;
		}

		// do not allowed to overwrite file
		if (file.isFile() && !overwrite) {
			log.error("File exists, and can not be overwrite. " + absoluteFilePath);
			return null;
		}

		// begin to receive file data
		File temp = this.toRandomDiskFile(source, file.getParent(), ".tmp");

		if (temp == null) {
			log.error("Failed to receive file data.");
			return null;
		}

		file = this.toDiskFile(temp, absoluteFilePath, overwrite);

		if (file == null && temp.isFile()) {
			this.deleteFromDisk(temp.getAbsolutePath());
			log.error("Failed to rename temp file to target file.");
		}

		return file;
	}

	/**
	 * 存储文件到本地磁盘
	 * 
	 * @param source
	 * @param absoluteFilePath 文件存储的绝对对路径
	 * @return
	 */
	public File toDiskFile(final File source, final String absoluteFilePath, boolean overwrite) {
		if (source == null || !source.isFile() || StringUtils.isBlank(absoluteFilePath)) {
			log.error("Invalid file source or absolute file path.");
			return null;
		}

		File file = new File(absoluteFilePath);

		if (!file.isAbsolute()) {
			log.error("Not a absolute file path: " + absoluteFilePath);
			return null;
		}

		if (file.isFile() && !overwrite) {
			log.error("File exists, and can not be overwrite. " + absoluteFilePath);
			return null;
		}

		// store old file, until the new file completed removed
		File oldFile = new File(file.getParent(), file.getName() + ".old");
		if (file.isFile() && file.renameTo(oldFile))
			file = new File(absoluteFilePath);

		if (source.renameTo(file)) {
			if (oldFile.isFile())
				this.deleteFromDisk(oldFile.getAbsolutePath());

			return file;
		}

		// recover the old file
		else {
			if (oldFile.isFile())
				oldFile.renameTo(file);
		}

		log.error("Failed to move template file into dir: " + absoluteFilePath);

		return null;
	}

	/**
	 * 存储文件到本地磁盘
	 * <p>
	 * 使用随机文件名
	 * 
	 * @param source
	 * @param absoluteDir 文件存储的绝对路径
	 * @return
	 */
	public File toRandomDiskFile(final byte[] source, final String absoluteDir, String suffix) {
		if (source == null || StringUtils.isBlank(absoluteDir)) {
			log.error("Invalid byte array source or absolute dir.");
			return null;
		}

		File file = this.newRandomFileIn(absoluteDir, suffix, true);

		if (file == null) {
			log.error("Failed to create a new random file.");
			return null;
		}

		BufferedOutputStream target = null;

		try {
			target = new BufferedOutputStream(new FileOutputStream(file), this.bufferSize());

			target.write(source);

			return file;
		} catch (IOException e) {
			if (file.isFile())
				this.deleteFromDisk(file.getAbsolutePath());

			log.error("Failed to write data into disk file.", e);
		} finally {
			try {
				if (target != null)
					target.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	/**
	 * 存储文件到本地磁盘
	 * <p>
	 * 使用随机文件名
	 * 
	 * @param source
	 * @param absoluteDir 文件存储的绝对路径
	 * @return
	 */
	public File toRandomDiskFile(final InputStream source, final String absoluteDir, String suffix) {
		if (source == null || StringUtils.isBlank(absoluteDir)) {
			log.error("Invalid input stream source or absolute dir.");
			return null;
		}

		File file = this.newRandomFileIn(absoluteDir, suffix, true);

		if (file == null) {
			log.error("Failed to create a new random file.");
			return null;
		}

		BufferedOutputStream target = null;

		try {
			target = new BufferedOutputStream(new FileOutputStream(file), this.bufferSize());

			byte[] buffer = new byte[this.bufferSize()];
			int read = -1;

			while ((read = source.read(buffer)) != -1) {
				target.write(buffer, 0, read);
			}

			target.flush();

			return file;
		} catch (IOException e) {
			if (file.isFile())
				this.deleteFromDisk(file.getAbsolutePath());

			log.error("Failed to write data into disk file.", e);
		} finally {
			try {
				if (target != null)
					target.close();

				if (source != null)
					source.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	/**
	 * 存储文件到本地磁盘
	 * <p>
	 * 使用随机文件名
	 * 
	 * @param source
	 * @param absoluteDir 文件存储的绝对对路径
	 * @return
	 */
	public File toRandomDiskFile(final File source, final String absoluteDir, String suffix) {
		if (source == null || !source.isFile() || StringUtils.isBlank(absoluteDir)) {
			log.error("Invalid file source or absolute dir.");
			return null;
		}

		File newFile = this.newRandomFileIn(absoluteDir, suffix, true);

		if (newFile == null) {
			log.error("Failed to create a new random file.");
			return null;
		}

		// delete the new file, and remove source file to the new file
		if (this.deleteFromDisk(newFile.getAbsolutePath()) && source.renameTo(newFile))
			return newFile;

		log.error("Failed to move template file into dir: " + absoluteDir);

		return null;
	}

	/**
	 * 从本地磁盘读取文件
	 * 
	 * @param target
	 * @param absoluteFilePath 文件绝对路径
	 * @return
	 */
	public long fromDiskFile(final OutputStream target, final String absoluteFilePath) {
		if (target == null || StringUtils.isBlank(absoluteFilePath)) {
			log.error("Invalid output source or absolute file path.");
			return -1;
		}

		File file = new File(absoluteFilePath);

		if (!file.isAbsolute()) {
			log.error("Not a absolute file path: " + absoluteFilePath);
			return -1;
		}

		// file not exists
		if (!file.isFile()) {
			log.error("Can not fount the file: " + absoluteFilePath);
			return -1;
		}

		InputStream source = null;

		try {
			source = new BufferedInputStream(new FileInputStream(file), this.bufferSize());

			byte[] buffer = new byte[this.bufferSize()];
			int readed = -1;
			long length = 0;

			while ((readed = source.read(buffer)) != -1) {
				target.write(buffer, 0, readed);
				length += readed;
			}

			target.flush();

			return length;
		} catch (IOException e) {
			log.error("Failed to read data from disk file: " + absoluteFilePath, e);
		} finally {
			try {
				if (target != null)
					target.close();

				if (source != null)
					source.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return -1;
	}

	/**
	 * 通过绝对路径删除文件（文件夹）
	 * 
	 * @param absolutePath 绝对路径
	 * @return
	 */
	public boolean deleteFromDisk(String absolutePath) {
		if (StringUtils.isBlank(absolutePath)) {
			log.error("Invalid absolute path. It is blank.");
			return false;
		}

		File exists = new File(absolutePath);

		if (!exists.isAbsolute()) {
			log.error("Not a absolue file path: " + absolutePath);
			return false;
		}

		// not exists, return true
		if (!exists.exists())
			return true;

		if (!exists.isFile() && !exists.isDirectory()) {
			log.error("The path is neither file nor directory: " + absolutePath);
			return false;
		}

		if (exists.delete())
			return true;

		log.error("Failed to delete file(or folder): " + absolutePath);

		return false;
	}

	/**
	 * 创建文件夹
	 * 
	 * @param absoluteDir
	 * @return
	 */
	public File mkDirs(final String absoluteDir) {
		if (StringUtils.isBlank(absoluteDir)) {
			log.error("Invalid absolute dir. It is blank.");
			return null;
		}

		File folder = new File(absoluteDir);

		// must be a absolute dir.
		if (!folder.isAbsolute()) {
			log.error("Not a absolute dir: " + absoluteDir);
			return null;
		}

		synchronized (absoluteDir.intern()) {
			if (folder.isDirectory())
				return folder;

			if (folder.mkdirs())
				return folder;
		}

		log.error("Failed to create folders on disk. It is: " + absoluteDir);

		return null;
	}

	/**
	 * 获取一个新的UUID
	 * 
	 * @return
	 */
	public String newId() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	/**
	 * 创建新的随机文件路径。
	 * 
	 * @return
	 */
	public String newRandomFilePathIn(final String absoluteDir, String suffix) {
		if (StringUtils.isBlank(absoluteDir)) {
			log.error("Invalid absolute dir. It is blank.");
			return null;
		}

		File folder = new File(absoluteDir);

		if (!folder.isAbsolute()) {
			log.error("Not a absolute dir: " + absoluteDir);
			return null;
		}

		if (StringUtils.isBlank(suffix))
			suffix = "";

		else if (!suffix.startsWith("."))
			suffix = "." + suffix;

		return this.appendDir(absoluteDir) + this.newId() + suffix;
	}

	/**
	 * 创建新随机文件。文件名只有一个随机UUID，没有文件后缀
	 * <p>
	 * 此方法会在磁盘中创建一个空的物理文件
	 * 
	 * 
	 * @param absoluteDir
	 * @param makeSureNotExists 确保新文件不存在
	 * @return
	 */
	public File newRandomFileIn(final String absoluteDir, boolean makeSureNotExists) {
		return this.newRandomFileIn(absoluteDir, null, makeSureNotExists);
	}

	/**
	 * 创建新随机文件。文件名只有一个随机UUID
	 * <p>
	 * 此方法会在磁盘中创建一个空的物理文件
	 * 
	 * @param absoluteDir
	 * @param makeSureNotExists 确保新文件不存在
	 * @return
	 */
	public File newRandomFileIn(final String absoluteDir, String suffix, boolean makeSureNotExists) {
		if (this.mkDirs(absoluteDir) == null)
			return null;

		int dealTimes = 0;

		while (dealTimes++ < 10) {
			File file = new File(this.newRandomFilePathIn(absoluteDir, suffix));

			// file exists and do not care about it's exists.
			if (file.isFile() && !makeSureNotExists)
				return file;

			// create a new file. when the file is not exists.
			try {
				if (file.createNewFile())
					return file;
			} catch (IOException e) {
				log.error("Can not create file: " + file.getAbsolutePath(), e);
			}
		}

		log.error("More than 10 times to new a random file in dir: " + absoluteDir);

		return null;
	}

	/**
	 * 获取磁盘根路径
	 * 
	 * @return
	 */
	public String getDiskRoot() {
		String path = this.getClass().getResource("/").getPath();

		for (File root : File.listRoots()) {
			if (path.indexOf(root.getAbsolutePath().replace(File.separator, "/")) != -1)
				return root.getAbsolutePath();
		}

		return null;
	}

	/**
	 * 获取操作系统的临时目录
	 * 
	 * @return
	 */
	public String getTempDir() {
		return System.getProperty("java.io.tmpdir");
	}

	/**
	 * 使用文件路径分隔符拼接字符串
	 * 
	 * @param dir
	 * @return
	 */
	public String appendDir(String... dir) {
		if (ArrayUtils.isEmpty(dir)) {
			log.error("Invalid dirs. Need a String[], and not empty.");
			return null;
		}

		String ret = "";

		for (String d : dir) {
			if (StringUtils.isBlank(d)) {
				log.error("A blank dir is founded. Ignore it.");
				continue;
			}

			if (ret.length() > 0 && !ret.endsWith(File.separator) && !d.startsWith(File.separator))
				ret += File.separator;

			ret += d;
		}

		if (ret.length() <= 0)
			return null;

		if (!ret.endsWith(File.separator))
			ret += File.separator;

		return ret;
	}

	/**
	 * 获取文件名称
	 * 
	 * @param filePath
	 * @return
	 */
	public String fileFullNameFrom(String filePath) {
		if (StringUtils.isBlank(filePath))
			return "";

		// replace windows's File.separator to Unix's separator
		filePath = filePath.replace('\\', '/');

		return filePath.substring(filePath.lastIndexOf('/') + 1);
	}

	/**
	 * 文件名称，不包含后缀
	 * 
	 * @param filePath
	 * @return
	 */
	public String fileShortName(String filePath) {
		if (StringUtils.isBlank(filePath))
			return "";

		String fileFullName = this.fileFullNameFrom(filePath);

		int index = fileFullName.lastIndexOf('.');

		// 无文件后缀
		if (index == -1)
			return fileFullName;

		return fileFullName.substring(0, index);
	}

	/**
	 * 获取文件后缀
	 * <p>
	 * eg: .java
	 * 
	 * @param filePath
	 * @return
	 */
	public String fileSuffix(String filePath) {
		if (StringUtils.isBlank(filePath))
			return "";

		String fileFullName = this.fileFullNameFrom(filePath);

		int index = fileFullName.lastIndexOf('.');

		// 无文件后缀
		if (index == -1)
			return "";

		return fileFullName.substring(index);
	}

	/**
	 * 读（写）文件的缓存大小
	 * 
	 * @return
	 */
	public int bufferSize() {
		return this.bufferSize;
	}

	public void setBufferSize(int bufferSize) {
		this.bufferSize = bufferSize;
	}

}
