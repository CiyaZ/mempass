package com.ciyaz.mempass;

import com.ciyaz.mempass.dao.SchemaInitDao;
import com.ciyaz.mempass.util.PropertiesUtil;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * JFX应用程序入口
 * 
 * @author CiyaZ
 *
 */
public class App extends Application {

	private WindowInitializr windowInitializr;
	private SchemaInitDao schemaInitDao;

	@Override
	public void start(Stage primaryStage) throws Exception {

		SystemTrayInitializr.getInstance();
		windowInitializr = WindowInitializr.getInstance();
		schemaInitDao = SchemaInitDao.getInstance();

		// 运行时配置初始化
		PropertiesUtil.loadRuntimeProperties();
		PropertiesUtil.loadAppProperties();

		// 初始化起始窗口
		windowInitializr.initStageLogin(primaryStage);
		windowInitializr.initStageAuthInit();

		// 显示入口窗口
		boolean checked = schemaInitDao.initCheck();
		if (checked) {
			// 已初始化，显示登录
			windowInitializr.STAGE_LOGIN.show();
			SystemTrayInitializr.getInstance().setCurrentStage(windowInitializr.STAGE_LOGIN);
		} else {
			// 未初始化，显示初始化窗口
			windowInitializr.STAGE_AUTH_INIT.show();
			SystemTrayInitializr.getInstance().setCurrentStage(windowInitializr.STAGE_AUTH_INIT);
		}

	}

	public static void appMain(String[] args) {
		launch();
	}
}
