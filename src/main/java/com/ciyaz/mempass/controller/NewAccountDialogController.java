package com.ciyaz.mempass.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ciyaz.mempass.WindowInitializr;
import com.ciyaz.mempass.dao.AccountDao;
import com.ciyaz.mempass.dao.CategoryDao;
import com.ciyaz.mempass.model.domain.Account;
import com.ciyaz.mempass.model.domain.Category;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

/**
 * 新建账户
 * 
 * @author CiyaZ
 */
public class NewAccountDialogController {

	@FXML
	private TextField tfItemName;
	@FXML
	private ChoiceBox<String> cbCategory;
	private List<Long> cbCategoryList = new ArrayList<>();
	@FXML
	private TextField tfUsername;
	@FXML
	private PasswordField pfPassword;
	@FXML
	private PasswordField pfRePassword;
	@FXML
	private TextField tfDescription;
	@FXML
	private TextField tfNote;
	@FXML
	private ChoiceBox<String> cbAvailableStatus;
	private List<Integer> cbAvailableStatusList = new ArrayList<>();

	/**
	 * 0插入 1更新
	 */
	private int upsertFlag = 0;
	private Long updateId = null;
	private Account toUpdateAccount = null;

	private static NewAccountDialogController self = null;

	private CategoryDao categoryDao = CategoryDao.getInstance();
	private AccountDao accountDao = AccountDao.getInstance();

	WindowInitializr windowInitializr = WindowInitializr.getInstance();

	/**
	 * 单例模式获取控制器引用
	 * 
	 * @return NewAccountDialogController引用
	 */
	public static NewAccountDialogController getInstance() {
		return NewAccountDialogController.self;
	}

	@FXML
	public void initialize() {
		NewAccountDialogController.self = this;
		// 初始化分类下拉选框
		List<Category> categories = categoryDao.queryAllCategories();
		for (Category category : categories) {
			cbCategory.getItems().add(category.getCategoryName());
			cbCategoryList.add(category.getCateoryId());
		}
		cbCategory.getSelectionModel().selectFirst();
		// 初始化状态下拉选框
		cbAvailableStatus.getItems().add("正常");
		cbAvailableStatus.getItems().add("不可用");
		cbAvailableStatus.getItems().add("废弃");
		cbAvailableStatus.getItems().add("已注销");
		cbAvailableStatusList.add(1);
		cbAvailableStatusList.add(2);
		cbAvailableStatusList.add(3);
		cbAvailableStatusList.add(4);
		cbAvailableStatus.getSelectionModel().selectFirst();
	}

	/**
	 * 初始化用来更新操作的表单
	 */
	public void initUpdateData() {
		if (upsertFlag == 1 && updateId != null) {
			Account account = accountDao.queryAccountById(updateId);
			Category category = categoryDao.queryCategoryByAccountId(updateId);
			tfItemName.setText(account.getItemName());
			cbCategory.getSelectionModel().select(category.getCategoryName());
			tfUsername.setText(account.getUsername());
			pfPassword.setText(account.getPassword());
			pfRePassword.setText(account.getPassword());
			tfDescription.setText(account.getDescription());
			tfNote.setText(account.getNote());
			cbAvailableStatus.getSelectionModel().select(account.getAvailableStatus() - 1);
			toUpdateAccount = account;
		}
	}

	public int getUpsertFlag() {
		return upsertFlag;
	}

	public void setUpsertFlag(int upsertFlag) {
		this.upsertFlag = upsertFlag;
	}

	public Long getUpdateId() {
		return updateId;
	}

	public void setUpdateId(Long updateId) {
		this.updateId = updateId;
	}

	public void handleCancelButton() {
		windowInitializr.STAGE_NEW_ACCOUNT_DIALOG.hide();
	}

	public void handleConfirmButton() {
		String itemName = tfItemName.getText();
		long categoryId = cbCategoryList.get(cbCategory.getSelectionModel().getSelectedIndex());
		String username = tfUsername.getText();
		String password = pfPassword.getText();
		String rePassword = pfRePassword.getText();
		String description = tfDescription.getText();
		String note = tfNote.getText();
		int availableStatus = cbAvailableStatusList.get(cbAvailableStatus.getSelectionModel().getSelectedIndex());

		// 表单校验
		if ("".equals(itemName) || "".equals(username) || "".equals(password) || "".equals(rePassword)) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("警告");
			alert.setContentText("账户项，账户名，密码字段值不能为空！");
			alert.show();
			return;
		}

		if (!password.equals(rePassword)) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("警告");
			alert.setContentText("两次输入密码不一致！");
			alert.show();
			return;
		}

		// 插入账户信息
		Account account = Account.builder().accountId(null).itemName(itemName).username(username).password(password)
				.description(description).note(note).createTime(new Date()).lastModifiedTime(new Date())
				.availableStatus(availableStatus).build();
		if (upsertFlag == 1 && updateId != null) {
			account.setAccountId(updateId);
			account.setCreateTime(toUpdateAccount.getCreateTime());
			accountDao.updateAccount(account, categoryId);
		} else {
			accountDao.addAccount(account, categoryId);
		}

		// 刷新主界面
		MainController.getInstance().reloadTreeView();
		MainController.getInstance().clearTable();
		windowInitializr.STAGE_NEW_ACCOUNT_DIALOG.hide();
	}
}
