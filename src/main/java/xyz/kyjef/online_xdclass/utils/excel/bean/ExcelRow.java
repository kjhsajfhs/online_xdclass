package xyz.kyjef.online_xdclass.utils.excel.bean;

import java.util.List;

/**
 * excel对应的行
 * 
 * @author Xiangzhu_fu
 * @version 2015-12-30
 * 
 */
public class ExcelRow {
	// 一行由多列组成
	private List<ExcelCol> cols;

	public List<ExcelCol> getCols() {
		return cols;
	}

	public void setCols(List<ExcelCol> cols) {
		this.cols = cols;
	}
}
