package com.ciyaz.mempass.util;

import java.io.File;

/**
 * 静态配置常量
 * 
 * @author CiyaZ
 */
public class Config {
	/**
	 * 工作路径
	 */
	public static String WORK_DIR = null;
	/**
	 * 认证ID，默认default仅供测试，应用必须设置一个AUTH_ID才能继续后续步骤
	 */
	public static String AUTH_ID = "default";

	/**
	 * 认证口令，默认admin123仅供测试，应用必须设置一个AUTH_KEY才能继续后续步骤
	 */
	public static String AUTH_KEY = "admin123";

	/**
	 * 上次登录的认证ID
	 */
	public static String LAST_AUTH_ID = null;

	static {
		// 检查/home/<user>/.mempass文件夹不存在则创建
		String userDir = System.getProperty("user.home");
		WORK_DIR = userDir + "/.mempass";
		File workDirFile = new java.io.File(WORK_DIR);
		if (!workDirFile.exists()) {
			workDirFile.mkdir();
		}
	}
}
