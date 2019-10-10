package com.ciyaz.mempass.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 * 运行时配置管理
 * 
 * @author CiyaZ
 */
public class PropertiesUtil {
	/**
	 * 运行时配置加载
	 */
	public static void loadRuntimeProperties() throws IOException {
		File file = new File(Config.WORK_DIR + "/config.properties");
		if (file.exists()) {
			InputStream in = new FileInputStream(file);
			Properties props = new Properties();
			props.load(in);
			in.close();
			// 加载LAST_AUTH_ID
			String s = (String) props.get("LAST_AUTH_ID");
			if (s != null) {
				Config.LAST_AUTH_ID = s;
			}
		}
	}

	/**
	 * 抽离的应用配置加载
	 */
	public static void loadAppProperties() throws IOException {
		InputStream in = PropertiesUtil.class.getClassLoader().getResourceAsStream("conf/app.properties");
		Properties props = new Properties();
		props.load(in);
		String h2Trace = (String) props.get("H2_TRACE");
		if (!StringUtils.isEmpty(h2Trace)) {
			Config.H2_TRACE = h2Trace;
		}
		in.close();
	}

	/**
	 * 运行时配置保存
	 */
	public static void saveRuntimeProperties() throws IOException {
		// 如果存在加载配置文件，不存在新建
		File file = new File(Config.WORK_DIR + "/config.properties");
		Properties props = null;
		if (file.exists()) {
			InputStream in = new FileInputStream(file);
			props = new Properties();
			props.load(in);
		} else {
			file.createNewFile();
			props = new Properties();
		}
		if (props != null) {
			// 设置LAST_AUTH_ID
			props.setProperty("LAST_AUTH_ID", Config.LAST_AUTH_ID);
			// 保存配置
			OutputStream out = new FileOutputStream(file);
			props.store(out, null);
			out.close();
		}
	}
}
