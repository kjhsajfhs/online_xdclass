package xyz.kyjef.online_xdclass.utils.excel.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author zengzr
 *2017年6月16日
 */
public class ExcelText implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<String> text;
	private int startRow;
	private int endRow;
	private int startCol;
	private Integer hiddenCol;
	private Integer relation;
	private Map<String,List<String>> map;

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	public Integer getRelation() {
		return relation;
	}

	public void setRelation(Integer relation) {
		this.relation = relation;
	}

	public Integer getHiddenCol() {
		return hiddenCol;
	}

	public void setHiddenCol(Integer hiddenCol) {
		this.hiddenCol = hiddenCol;
	}


	public int getStartRow() {
		return startRow;
	}
	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}
	public int getEndRow() {
		return endRow;
	}
	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}
	public int getStartCol() {
		return startCol;
	}
	public void setStartCol(int startCol) {
		this.startCol = startCol;
	}
	public List<String> getText() {
		return text;
	}
	public void setText(List<String> text) {
		this.text = text;
	}
}
