package com.ciyaz.mempass.controller;

import com.ciyaz.mempass.WindowInitializr;
import com.ciyaz.mempass.dao.AuthDao;
import com.ciyaz.mempass.util.Config;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class LoginController {

	@FXML
	private TextField tfAuthId;
	@FXML
	private PasswordField pfAuthKey;

	private WindowInitializr windowInitializr = WindowInitializr.getInstance();
	private AuthDao authDao = AuthDao.getInstance();

	public void handleLoginButton() {
		String authId = tfAuthId.getText();
		String authKey = pfAuthKey.getText();

		// 表单校验
		if ("".equals(authId) || "".equals(authKey)) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("警告");
			alert.setContentText("字段值不能为空！");
			alert.show();
			return;
		}

		Config.AUTH_ID = authId;
		Config.AUTH_KEY = authKey;
		boolean checked = authDao.checkAuthInfo(authId, authKey);
		if (checked) {
			// 登陆成功
			windowInitializr.STAGE_LOGIN.hide();
			try {
				windowInitializr.initStageMain();
				windowInitializr.STAGE_MAIN.show();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			// 登录失败
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("警告");
			alert.setContentText("认证失败！该行为会报告给管理员！");
			alert.show();
		}
	}

	public void handleResetButton() {
		tfAuthId.setText("");
		pfAuthKey.setText("");
	}

	public void handleNewButton() {
		windowInitializr.STAGE_LOGIN.hide();
		windowInitializr.STAGE_AUTH_INIT.show();
	}
}
