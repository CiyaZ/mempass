package com.ciyaz.mempass.dao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import com.ciyaz.mempass.util.FileUtils;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.ciyaz.mempass.model.domain.Account;
import com.ciyaz.mempass.model.domain.Category;
import com.ciyaz.mempass.util.Config;

public class ExportDao {

	private CategoryDao categoryDao = CategoryDao.getInstance();
	private AccountDao accountDao = AccountDao.getInstance();

	private static ExportDao self = null;

	/**
	 * 单例模式
	 * 
	 * @return 单例自身对象
	 */
	public static ExportDao getInstance() {
		if (self == null) {
			self = new ExportDao();
		}
		return self;
	}

	/**
	 * 导出数据库
	 * 
	 * @param file 目标文件
	 */
	public void exportDb(File file) {
		String srcPath = Config.WORK_DIR + "/data/" + Config.AUTH_ID + "/mempass.mv.db";
		File srcFile = new File(srcPath);
		FileUtils.copyFile(srcFile, file);
	}

	/**
	 * 导出到Excel
	 * 
	 * @param file 目标文件
	 */
	public void exportToExcel(File file) {
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("password");
		List<Category> categories = categoryDao.queryAllCategories();
		int rowCnt = 0;
		int rowMergeStart = 0;
		for (int i = 0; i < categories.size(); i++) {
			List<Account> accounts = accountDao.queryAccountsByCategoryId(categories.get(i).getCateoryId());
			for (int j = 0; j < accounts.size(); j++) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				XSSFRow row = sheet.createRow(rowCnt);
				XSSFCell cell0 = row.createCell(0);
				cell0.setCellValue(categories.get(i).getCategoryName());
				XSSFCell cell1 = row.createCell(1);
				cell1.setCellValue(accounts.get(j).getItemName());
				XSSFCell cell2 = row.createCell(2);
				cell2.setCellValue(accounts.get(j).getUsername());
				XSSFCell cell3 = row.createCell(3);
				cell3.setCellValue(accounts.get(j).getPassword());
				XSSFCell cell4 = row.createCell(4);
				cell4.setCellValue(accounts.get(j).getDescription());
				XSSFCell cell5 = row.createCell(5);
				cell5.setCellValue(accounts.get(j).getNote());
				XSSFCell cell6 = row.createCell(6);
				cell6.setCellValue(sdf.format(accounts.get(j).getCreateTime()));
				XSSFCell cell7 = row.createCell(7);
				cell7.setCellValue(sdf.format(accounts.get(j).getLastModifiedTime()));
				XSSFCell cell8 = row.createCell(8);
				String availableStatus = "可用";
				switch (accounts.get(j).getAvailableStatus()) {
				case 1:
					availableStatus = "正常";
					break;
				case 2:
					availableStatus = "不可用";
					break;
				case 3:
					availableStatus = "废弃";
					break;
				case 4:
					availableStatus = "已注销";
					break;
				default:
					break;
				}
				cell8.setCellValue(availableStatus);
				rowCnt++;
			}
			int lenAccounts = accounts.size();
			if (lenAccounts > 1) {
				sheet.addMergedRegion(new CellRangeAddress(rowMergeStart, rowMergeStart + lenAccounts - 1, 0, 0));
			}
			rowMergeStart += lenAccounts;
		}
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			workbook.write(fos);
			fos.close();
			workbook.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
