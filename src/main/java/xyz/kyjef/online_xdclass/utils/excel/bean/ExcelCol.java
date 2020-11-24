package xyz.kyjef.online_xdclass.utils.excel.bean;

/**
 * excel对应的列
 * 
 * @author Xiangzhu_fu
 * @version 2015-12-30
 * 
 */
public class ExcelCol {
	// 列值
	private String val;
	// 列id
	private Long id;
	// 列父id
	private Long pid;

	public String getVal() {
		return val;
	}

	public void setVal(String val) {
		this.val = val;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}
}
