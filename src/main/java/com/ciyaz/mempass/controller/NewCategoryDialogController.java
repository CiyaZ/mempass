package com.ciyaz.mempass.controller;

import com.ciyaz.mempass.WindowInitializr;
import com.ciyaz.mempass.dao.CategoryDao;
import com.ciyaz.mempass.model.domain.Category;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

/**
 * 新建分类
 * 
 * @author CiyaZ
 */
public class NewCategoryDialogController {
	@FXML
	private TextField tfCategory;

	/**
	 * 0插入 1更新
	 */
	private int upsertFlag = 0;
	private Long updateId = null;

	private static NewCategoryDialogController self = null;

	private CategoryDao categoryDao = CategoryDao.getInstance();
	private WindowInitializr windowInitializr = WindowInitializr.getInstance();

	@FXML
	private void initialize() {
		NewCategoryDialogController.self = this;
	}

	/**
	 * 初始化用来更新操作的表单
	 */
	public void initUpdateData() {
		if (upsertFlag == 1 && updateId != null) {
			Category category = categoryDao.queryCategoryById(updateId);
			tfCategory.setText(category.getCategoryName());
		}
	}

	/**
	 * 单例模式获取NewCategoryDialogController
	 * 
	 * @return NewCategoryDialogController引用
	 */
	public static NewCategoryDialogController getInstance() {
		return NewCategoryDialogController.self;
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
		windowInitializr.STAGE_NEW_CATEGORY_DIALOG.hide();
	}

	public void handleConfirmButton() {

		String categoryName = tfCategory.getText();

		// 表单校验
		if ("".equals(categoryName)) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("警告");
			alert.setContentText("字段值不能为空！");
			alert.show();
			return;
		}

		// 向数据库写入数据
		Category category = Category.builder().categoryName(categoryName).build();
		if (upsertFlag == 1 && updateId != null) {
			category.setCateoryId(updateId);
			categoryDao.updateCategory(category);
		} else {
			categoryDao.addCategory(category);
		}
		// 刷新主界面
		MainController.getInstance().reloadTreeView();
		// 隐藏当前窗口
		windowInitializr.STAGE_NEW_CATEGORY_DIALOG.hide();
	}
}
