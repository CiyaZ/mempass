package com.ciyaz.mempass.controller;

import com.ciyaz.mempass.WindowInitializr;
import com.ciyaz.mempass.dao.AccountDao;
import com.ciyaz.mempass.dao.AuthDao;
import com.ciyaz.mempass.util.Config;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Alert.AlertType;

public class ChangePasswordDialogController {

	@FXML
	private PasswordField pfOldPassword;
	@FXML
	private PasswordField pfPassword;
	@FXML
	private PasswordField pfRePassword;

	private WindowInitializr windowInitializr = WindowInitializr.getInstance();

	private AuthDao authDao = AuthDao.getInstance();
	private AccountDao accountDao = AccountDao.getInstance();

	/**
	 * 确认按钮
	 */
	public void handleConfirmButton() {

		String oldPassword = pfOldPassword.getText();
		String password = pfPassword.getText();
		String rePassword = pfPassword.getText();

		// 表单校验
		if ("".equals(oldPassword) || "".equals(password) || "".equals(rePassword)) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("警告");
			alert.setContentText("原密码错误！");
			alert.show();
			return;
		}
		if (!oldPassword.equals(Config.AUTH_KEY)) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("警告");
			alert.setContentText("字段值不能为空！");
			alert.show();
			return;
		}
		if (!password.equals(rePassword)) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("警告");
			alert.setContentText("两次新密码输入不同！");
			alert.show();
			return;
		}
		// 修改密码
		authDao.changePassword(password);
		accountDao.updateEncKey(oldPassword, password);
		Config.AUTH_KEY = password;

		windowInitializr.STAGE_CHANGE_PASSWORD_DIALOG.hide();
		windowInitializr.STAGE_MAIN.show();
	}

	/**
	 * 取消按钮
	 */
	public void handleCancelButton() {
		windowInitializr.STAGE_CHANGE_PASSWORD_DIALOG.hide();
		windowInitializr.STAGE_MAIN.show();
	}
}
