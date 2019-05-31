package com.ciyaz.mempass.controller;

import com.ciyaz.mempass.WindowInitializr;
import com.ciyaz.mempass.dao.AuthDao;
import com.ciyaz.mempass.dao.SchemaInitDao;
import com.ciyaz.mempass.util.Config;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class AuthInitController {
	@FXML
	private TextField tfAuthId;
	@FXML
	private PasswordField pfAuthKey;
	@FXML
	private PasswordField pfReAuthKey;

	private WindowInitializr windowInitializr = WindowInitializr.getInstance();
	private AuthDao authDao = AuthDao.getInstance();
	private SchemaInitDao schemaInitDao = SchemaInitDao.getInstance();

	public void handleButton() {
		String authId = tfAuthId.getText();
		String authKey = pfAuthKey.getText();
		String reAuthKey = pfReAuthKey.getText();

		// 表单校验
		if ("".equals(authId) || "".equals(authKey) || "".equals(reAuthKey)) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("警告");
			alert.setContentText("字段值不能为空！");
			alert.show();
			return;
		}
		if (!authKey.equals(reAuthKey)) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("警告");
			alert.setContentText("两次输入加密秘钥不一致！");
			alert.show();
			return;
		}

		// 完成数据初始化
		Config.AUTH_ID = authId;
		Config.AUTH_KEY = authKey;
		schemaInitDao.initSchema();
		authDao.initAuthInfo(authId, authKey);
		schemaInitDao.completeInitData();

		// 隐藏初始化窗口，显示登录窗口
		windowInitializr.STAGE_AUTH_INIT.hide();
		windowInitializr.STAGE_LOGIN.show();
	}
}
