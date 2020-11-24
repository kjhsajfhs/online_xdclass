package xyz.kyjef.online_xdclass.utils.excel;


import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Name;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.springframework.web.multipart.MultipartFile;
import xyz.kyjef.online_xdclass.utils.DateUtils;
import xyz.kyjef.online_xdclass.utils.StringUtit;
import xyz.kyjef.online_xdclass.utils.excel.bean.ExcelCol;
import xyz.kyjef.online_xdclass.utils.excel.bean.ExcelRow;
import xyz.kyjef.online_xdclass.utils.excel.bean.ExcelText;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.*;

/**
 * excel文件操作
 * 
 * @author Xiangzhu_fu
 * @version 2015-04-25
 * 
 */
public class ExcelUtil {
	public static String dateFormate = "yyyy-MM-dd";

	/**
	 * 读取excel数据 作者：Xiangzhu_fu 时间：2015-08-24
	 * 
	 * @param file
	 *            要读取的文件
	 * @param startRow
	 *            开始行
	 * @param startCol
	 *            开始列
	 * @param startRow
	 *            每个excel下多个sheet，对其重命名，如果没有传递该值，则默认使用sheet1,sheet2.....
	 * @return Map<String, List<Map<String, Object>> 返回每个sheet对应的行列集合
	 * @throws Exception
	 */
	public static Map<String, List<List<String>>> readDataFromExcel(MultipartFile file,
																	int startRow, int startCol, String... sheetNames) throws Exception {
		// 读取xls文件
		InputStream ins =  file.getInputStream();
		// 设置读文件编码
		WorkbookSettings setEncode = new WorkbookSettings();
		setEncode.setEncoding("ISO-8859-1");
		Workbook rwb = Workbook.getWorkbook(ins, setEncode);
		Sheet[] sheets = rwb.getSheets();
		if (sheets == null || sheets.length == 0)
			new HashMap<String, List<List<String>>>();
		Map<String, List<List<String>>> map = new HashMap<String, List<List<String>>>();
		List<String> sheetNameLs = new ArrayList<String>();
		for (int j = 0; j < sheets.length; j++) {
			Sheet sheet = sheets[j];
			// 获取该sheet下数据总行数
			int rows = sheet.getRows();
			// 获取该sheet下数据总列数
			int cols = sheet.getColumns();
			// 行循环,Excel的行列是从（0，0）开始
			List<List<String>> list = new ArrayList<List<String>>();
			for (int r = startRow; r < rows; r++) {
				List<String> cellDatas = new ArrayList<String>();
				for (int i = 0; i < cols; i++) {
					cellDatas.add(StringUtit.trimChar(sheet.getCell(i, r).getContents()));
					//cellDatas.add(sheet.getCell(i, r).getContents());
				}
				//if (!"".equals(cellDatas.get(0))) {
					list.add(cellDatas);
				//}
			}
			String sheetName = sheet.getName();
			if (sheetNames != null && sheetNames[j] != null )
				sheetName = sheetNames[j];
			if (sheetName.indexOf("hidden")<0){
			map.put(sheetName, list);
			sheetNameLs.add(sheetName);}
		}
		ins.close();
		// 回写sheetName
		sheetNames = (String[]) sheetNameLs.toArray(new String[sheetNameLs.size()]);
		return map;
	}


	public static Map<String, List<List<String>>> readData(String file,int startRow, int startCol, String... sheetNames) throws Exception {
		// 读取xls文件
		InputStream ins =  new FileInputStream(file);
		// 设置读文件编码
		WorkbookSettings setEncode = new WorkbookSettings();
		setEncode.setEncoding("ISO-8859-1");
		Workbook rwb = Workbook.getWorkbook(ins, setEncode);
		Sheet[] sheets = rwb.getSheets();
		if (sheets == null || sheets.length == 0)
			new HashMap<String, List<List<String>>>();
		Map<String, List<List<String>>> map = new HashMap<String, List<List<String>>>();
		List<String> sheetNameLs = new ArrayList<String>();
		for (int j = 0; j < sheets.length; j++) {
			Sheet sheet = sheets[j];
			// 获取该sheet下数据总行数
			int rows = sheet.getRows();
			// 获取该sheet下数据总列数
			int cols = sheet.getColumns();
			// 行循环,Excel的行列是从（0，0）开始
			List<List<String>> list = new ArrayList<List<String>>();
			for (int r = startRow; r < rows; r++) {
				List<String> cellDatas = new ArrayList<String>();
				for (int i = 0; i < cols; i++) {
					cellDatas.add(StringUtit.trimChar(sheet.getCell(i, r).getContents()));
					//cellDatas.add(sheet.getCell(i, r).getContents());
				}
				//if (!"".equals(cellDatas.get(0))) {
				list.add(cellDatas);
				//}
			}
			String sheetName = sheet.getName();
			if (sheetNames != null && sheetNames[j] != null )
				sheetName = sheetNames[j];
			if (sheetName.indexOf("hidden")<0){
				map.put(sheetName, list);
				sheetNameLs.add(sheetName);}
		}
		ins.close();
		// 回写sheetName
		sheetNames = (String[]) sheetNameLs.toArray(new String[sheetNameLs.size()]);
		return map;
	}
	/**
	 * 导出报表 作者：Xiangzhu_fu 时间：2015-07-31
	 * 
	 * @param title
	 *            表格标题名
	 * @param header
	 *            表格属性列名数组
	 * @param dataLs
	 *            需要显示的数据集合
	 * @param userName
	 *            操作者名称
	 * @param response
	 *            响应到web端页面的响应对象
	 */
	public static void exportExcel(String title, List<ExcelCol> header,
								   List<ExcelRow> dataLs, String userName, List<ExcelText> list, HttpServletResponse response) {
		List<List<ExcelCol>> headLs = new ArrayList<List<ExcelCol>>();
		headLs.add(header);
		List<List<ExcelRow>> dataRowLs = new ArrayList<List<ExcelRow>>();
		dataRowLs.add(dataLs);
		List<String> sheetNameLs = new ArrayList<String>();
		sheetNameLs.add(title);
		exportExcelOfSheetLs(title, headLs, dataRowLs, sheetNameLs, userName,list,
				response);
	}

	/**
	 * 导出报表 作者：Xiangzhu_fu 时间：2015-07-31
	 * 
	 * @param title
	 *            表格标题名
	 * @param headerColLs
	 *            表格属性列名数组
	 * @param dataRowLs
	 *            需要显示的数据集合
	 * @param sheetNameLs
	 *            对应excel的sheet
	 * @param userName
	 *            操作者名称
	 * @param response
	 *            响应到web端页面的响应对象
	 */
	@SuppressWarnings("deprecation")
	public static void exportExcelOfSheetLs(String title,
			List<List<ExcelCol>> headerColLs, List<List<ExcelRow>> dataRowLs,
			List<String> sheetNameLs, String userName,List<ExcelText> list,
			HttpServletResponse response) {
		OutputStream out = null;
		try {
			String fileName = "attachment; filename="
					+ new String(title.getBytes("GBK"), "ISO-8859-1");
			out = response.getOutputStream();
			if (dataRowLs == null || headerColLs == null
					|| headerColLs.size() == 0) {
				response.setContentType("text/plain;charset=utf-8");
				response.setHeader("Content-Disposition", fileName + "-log.txt");
				String msg = "导出出错!";
				byte[] b = msg.getBytes();
				out.write(b, 0, b.length);
				out.flush();
				return;
			}
			// 声明一个工作薄
			HSSFWorkbook workbook = new HSSFWorkbook();
			for (int k = sheetNameLs.size() - 1; k >= 0; k--) {
				List<ExcelCol> header = headerColLs.get(k);
				// 生成一个表格
				String sheetName = sheetNameLs.get(k);
				HSSFSheet sheet = workbook.createSheet(sheetName);
				if (list!=null) {
					for (int i = 0; i < list.size(); i++) {
						List<String> textList=list.get(i).getText();
						Map<String,List<String>> map=list.get(i).getMap();
						Integer relation=list.get(i).getRelation();
						String [] aliasText=null;
						if (textList!=null)aliasText=textList.toArray(new String[textList.size()]);
						int startCol=list.get(i).getStartCol();
						sheet=setHSSFValidation(sheet,aliasText,1, -1,startCol,startCol,workbook,map,relation);
						Integer hiddenCol = list.get(i).getHiddenCol();
						if(hiddenCol!=null){
							sheet.setColumnHidden(hiddenCol,true);
						}
					}
				}
				
				// 设置表格默认列宽度为18个字节
				sheet.setDefaultColumnWidth(18);
				// 生成一个字体
				HSSFFont font = workbook.createFont();
				font.setColor(HSSFColor.BLACK.index);
				font.setFontHeightInPoints((short) 15);
				//必填样式
				HSSFFont font1 = workbook.createFont();
				font1.setColor(HSSFColor.RED.index);
				font1.setFontHeightInPoints((short) 15);
				//font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
				// 生成一个样式
				HSSFCellStyle headerStyle = workbook.createCellStyle();
				headerStyle.setWrapText(true);//设置自动换行
				//headerStyle
				//		.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
				//headerStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
				headerStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直
				headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平
				// 把字体应用到当前的样式
				headerStyle.setFont(font);
				DataFormat format = workbook.createDataFormat();
				headerStyle.setDataFormat(format.getFormat("@"));

				//必填标头
				HSSFCellStyle headerStyle1 = workbook.createCellStyle();
				headerStyle1.setWrapText(true);//设置自动换行
				headerStyle1.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
				headerStyle1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直
				headerStyle1.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平
				// 把字体应用到当前的样式
				headerStyle1.setFont(font1);
				DataFormat  format1 = workbook.createDataFormat();
				headerStyle1.setDataFormat(format1.getFormat("@"));

				// 生成并设置另一个样式
				HSSFCellStyle rowDataStyle = workbook.createCellStyle();
//				rowDataStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
//				rowDataStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
//				rowDataStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
//				rowDataStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
				rowDataStyle.setDataFormat(format.getFormat("@"));
				rowDataStyle.setWrapText(true);//设置自动换行
				rowDataStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直
				rowDataStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平
			/*	// 声明一个画图的顶级管理器
				HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
				// 定义注释的大小和位置,详见文档
				HSSFComment comment = patriarch
						.createComment(new HSSFClientAnchor(0, 0, 0, 0,
								(short) 4, 2, (short) 6, 5));
				// 设置注释内容
				comment.setString(new HSSFRichTextString(DateUtils
						.dataFormatToString(new Date(), "yyyy/MM/DD HH:mm:ss")
						+ "由" + userName + "生成!"));
				// 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.
				comment.setAuthor(userName);*/

				// 产生表格标题行
				List<List<Map<String, Object>>> headerLs = new ArrayList<List<Map<String, Object>>>();
				constructHeader(header, headerLs);
				// 实例化一个二维数组用于标记已经填充的单元格
				List<List<String>> flagCellFull = new ArrayList<List<String>>(
						headerLs.size());
				// 定义开始行，开始列，结束行，结束列
				int beginC = 0, beginR = 0, endC = 0, endR = 0;
				for (int hRowIndex = 0; hRowIndex < headerLs.size(); hRowIndex++) {
					// 从第hRowIndex行开始
					beginR = hRowIndex;
					// 定义一个excel行
					HSSFRow row = sheet.createRow(hRowIndex);
					// 得到要写入的第hRowIndex行表头信息
					List<Map<String, Object>> hCols = headerLs.get(hRowIndex);
					// 遍历第hRowIndex行表头信息
					for (int hColIndex = 0; hColIndex < hCols.size(); hColIndex++) {
						Map<String, Object> c = hCols.get(hColIndex);
						// 起始列
						beginC = getBeginC(flagCellFull, hRowIndex);
						// 获取该列表头值
						String val = (String) c.get("val");
						// 获取该表头是否有下级表头标记，subNum
						Long subNum = (Long) c.get("subNum");
						// 如果没有子表头，则跨headerLs.size()-hRowIndex行，1列,否则跨1行，subNum列
						if (subNum == null || subNum == 0) {
							endR = (headerLs.size() - hRowIndex) + beginR - 1;
							endC = beginC;
						} else {
							endC = subNum.intValue() + beginC - 1;
							endR = hRowIndex;
						}
						// 创建该单元格
						sheet.addMergedRegion(new Region((short) beginR,
								(short) beginC, (short) endR, (short) endC));
						HSSFCell cell = row.createCell(beginC);
						if(val.indexOf("必填")>-1){
							cell.setCellStyle(headerStyle1);

						}else{
							cell.setCellStyle(headerStyle);
						}
						sheet.setDefaultColumnStyle(hColIndex,rowDataStyle);//设置每一列的样式
						cell.setCellType(HSSFCell.CELL_TYPE_STRING); // 设置单元格内容为字符串型
						row.setHeight((short) (25 * 25));//设置第一行单元格高度
						sheet.setColumnWidth(hColIndex, val.getBytes().length*2*256);//动态设置单元格宽度
						cell.setCellValue(val);

						// 标记已经填充的单元格
						flagCell(beginC, endC, beginR, endR, flagCellFull);
					}
				}

				// 遍历集合数据，产生数据行
				int rowIndex = headerLs.size();
				List<ExcelRow> dataLs = dataRowLs.get(k);
				if(dataLs.size()>0)
				{
					for (int i = 0; i < dataLs.size(); i++, rowIndex++) {
						HSSFRow row = sheet.createRow(rowIndex);
						List<ExcelCol> colLs = dataLs.get(i).getCols();
						for (int j = 0; j < colLs.size(); j++) {
							sheet.setDefaultColumnStyle(j,rowDataStyle);
							HSSFCell cell = row.createCell(j);
							cell.setCellStyle(rowDataStyle);
							cell.setCellType(HSSFCell.CELL_TYPE_STRING); // 设置单元格内容为字符串型
							cell.setCellValue(colLs.get(j).getVal());
							row.setHeight((short) (25 * 12));//设置第一行以外的单元格高度
						}
					}
				}
			}
			
			// 设置文件名称
			response.setContentType("application/vnd.ms-excel;charset=UTF-8");
			response.setHeader("Content-Disposition", fileName + ".xls");
			workbook.write(out);
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 标记已经填充的单元格 作者：Xiangzhu_fu 时间：2015-12-30
	 * 
	 * @param beginC
	 * @param endC
	 * @param beginR
	 * @param endR
	 * @param flagCellFull
	 */
	private static void flagCell(int beginC, int endC, int beginR, int endR,
			List<List<String>> flagCellFull) {
		String flag = "flag", unFlag = "unFlag";
		for (int j = beginR; j <= endR; j++) {
			if (flagCellFull.size() <= j)
				flagCellFull.add(j, new ArrayList<String>());
			List<String> flagR = flagCellFull.get(j);
			for (int i = 0; i <= endC; i++) {
				if (i >= beginC && i <= endC) {
					if (i < flagR.size() && unFlag.equals(flagR.get(i)))
						flagR.set(i, flag);
					else
						flagR.add(i, flag);
					continue;
				}
				if (i < flagR.size() && flagR.get(i) != null)
					continue;
				flagR.add(i, unFlag);
			}
		}
	}

	/**
	 * 获取开始列 作者：Xiangzhu_fu 时间：2015-12-30
	 * 
	 * @param flagHasFull
	 * @param rowIndex
	 * @return
	 */
	private static int getBeginC(List<List<String>> flagHasFull, int rowIndex) {
		if (rowIndex >= flagHasFull.size())
			return 0;
		List<String> flagR = flagHasFull.get(rowIndex);
		String unFlag = "unFlag";
		for (int i = 0; flagR != null && i < flagR.size(); i++) {
			if (flagR.get(i).equals(unFlag))
				return i;
		}
		return flagR == null ? 0 : flagR.size();
	}

	/**
	 * 构造一个表头开始 作者：Xiangzhu_fu 时间：2015-12-30
	 */
	private static void constructHeader(List<ExcelCol> header,
			List<List<Map<String, Object>>> rsHeaderLs) {
		List<Map<String, Object>> newHeaderLs = new ArrayList<Map<String, Object>>();
		if (rsHeaderLs.size() == 0) {
			doConstructHeader(header, newHeaderLs, null);
		} else {
			List<Map<String, Object>> pHeaderLs = rsHeaderLs.get(rsHeaderLs
					.size() - 1);
			for (Map<String, Object> hM : pHeaderLs) {
				doConstructHeader(header, newHeaderLs, (Long) hM.get("id"));
			}
		}
		if (newHeaderLs.size() == 0)
			return;
		rsHeaderLs.add(newHeaderLs);
		constructHeader(header, rsHeaderLs);
	}

	/**
	 * 执行构造表头 作者：Xiangzhu_fu 时间：2015-12-30
	 * 
	 * @param header
	 * @param newHeaderLs
	 * @param pid
	 */
	private static void doConstructHeader(List<ExcelCol> header,
			List<Map<String, Object>> newHeaderLs, Long pid) {
		for (ExcelCol c : header) {
			Map<String, Object> headerMap = new HashMap<String, Object>();
			if (pid != null) {
				if (c.getPid().longValue() != pid.longValue())
					continue;
			} else {
				// 获取根类表头
				if (c.getPid() != null && c.getPid() != 0)
					continue;
				if (c.getId() == null || c.getId() == 0)
					continue;
			}
			Long subId = c.getId();
			Long subNum = 0L;
			for (ExcelCol ssc : header) {
				if (ssc.getPid() == null || ssc.getPid() == 0L
						|| ssc.getPid().longValue() != subId.longValue())
					continue;
				subNum++;
			}
			// 表头id
			headerMap.put("id", subId);
			// 表头值
			headerMap.put("val", c.getVal());
			// 表头下子表头个数
			headerMap.put("subNum", subNum);
			newHeaderLs.add(headerMap);
		}
	}

	/**
	 * 导出 作者：Xiangzhu_fu 时间：2015-12-30
	 * 
	 * @param
	 *
	 * @param headerStr
	 *            表头
	 * @param headerKey
	 *            要取得的值
	 * @param ls
	 *            数值列表
	 * @param personName
	 *            操作者
	 * @param response
	 * @param cls
	 *            字节码
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void exportExcel(String fileName, String headerStr,
                                   String headerKey, List ls, String personName,
                                   HttpServletResponse response, Class cls, Map<String, Object> dict, List<ExcelText> list) {
		try {
			String[] headerLs = headerStr.split(",");
			List<ExcelCol> header = new ArrayList<ExcelCol>();
			for (int i = 0; i < headerLs.length; i++) {
				String h = headerLs[i];
				if (StringUtils.isEmpty(h))
					continue;
				ExcelCol c = new ExcelCol();
				c.setId(Long.valueOf(i + 1));
				c.setVal(h);
				c.setPid(0L);
				header.add(c);
			}
			List<ExcelRow> dataLs = new ArrayList<ExcelRow>();
			for (Object t : ls) {
				List<ExcelCol> cols = new ArrayList<ExcelCol>();
				int i = 0;
				for (String key : headerKey.split(",")) {
					if (StringUtils.isEmpty(key))
						continue;
					Object obj = null;
					if (t instanceof Map) {
						obj = ((Map<String, Object>) t).get(key);
					} else {
						String f = "get" + key.substring(0, 1).toUpperCase();
						if (key.length() > 1){
						if (key.indexOf("dictVal")>-1) {
							String[] ff=key.split("\\.");
							Method m = cls.getMethod("getDictVal");
							obj = m.invoke(t);
							obj = ((Map<String, Object>) obj).get(ff[1]);
						}else{
						f = f + key.substring(1);
						Method m = cls.getMethod(f);
						obj = m.invoke(t);}}
					}
					if (obj == null)
						obj = "";
					ExcelCol c = new ExcelCol();
					c.setId(Long.valueOf(i + 1));
					c.setPid(0L);
					if (obj instanceof Date)
						c.setVal(DateUtils.dataFormatToString((Date) obj,
								dateFormate));
					else {
						if (null != dict) {
							Object dictMap = dict.get(key);
							if (dictMap != null && obj != null) {
								obj = ((Map<String, String>) dictMap).get(obj
										.toString());
							}
						}
						if (obj == null)
							obj = "";
						c.setVal(obj.toString());
					}
					cols.add(c);
					i++;
				}
				ExcelRow r = new ExcelRow();
				r.setCols(cols);
				dataLs.add(r);
			}
			ExcelUtil.exportExcel(fileName, header, dataLs, personName,list,
					response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * 设置某些列的值只能输入预制的数据,显示下拉框.
	 * @param sheet 要设置的sheet.
	 * @param textlist 下拉框显示的内容
	 * @param firstRow 开始行
	 * @param endRow 结束行
	 * @param firstCol   开始列
	 * @param endCol  结束列
	 * @param map
	 * @param relation
	 * @return 设置好的sheet.
	 */
	public static HSSFSheet setHSSFValidation(HSSFSheet sheet,
											  String[] textlist, int firstRow, int endRow, int firstCol,
											  int endCol, HSSFWorkbook workbook, Map<String,List<String>> map, Integer relation) {
		if(relation!=null){
			HSSFSheet hidden = workbook.createSheet("hidden"+firstCol);
			workbook.setSheetHidden(workbook.getSheetIndex("hidden"+firstCol), true);
			Iterator<String> keyIterator = map.keySet().iterator();
			int rowId = 0;
			while (keyIterator.hasNext()) {
				String key = keyIterator.next();
				List<String> son = map.get(key);

				Row row = hidden.createRow(rowId++);
				row.createCell(0).setCellValue(key);
				for (int i = 0; i < son.size(); i++) {
					Cell cell = row.createCell(i + 1);
					cell.setCellValue(son.get(i));
				}

				// 添加名称管理器
				String range = getRange(1, rowId, son.size());
				Name name = workbook.createName();
				name.setNameName(key);
				String formula = "hidden"+firstCol+"!" + range;
				name.setRefersToFormula(formula);

			}
			String o=numToABC(relation);
			//限制10000行记录校验
			for (int i=0;i<2000;i++){
				DVConstraint constraint=DVConstraint.createFormulaListConstraint("INDIRECT($"+o+"$"+(i+2)+")");
				CellRangeAddressList addressList = new CellRangeAddressList(i+1, i+1, firstCol, endCol);
				HSSFDataValidation validation = new HSSFDataValidation(addressList, constraint);
				validation.setErrorStyle(HSSFDataValidation.ErrorStyle.STOP);
				validation.createErrorBox("error", "请选择正确的选项");
				validation.setShowErrorBox(true);
				validation.setSuppressDropDownArrow(false);
				sheet.addValidationData(validation);
			}
			return sheet;
//		}else if(Arrays.toString(textlist).length()>200){
//			HSSFSheet hidden = workbook.createSheet("hidden"+firstCol);
//			HSSFCell cell = null;
//			for (int i = 0, length= textlist.length; i < length; i++) {
//				String name = textlist[i];
//				HSSFRow row = hidden.createRow(i);
//				cell = row.createCell(firstCol);
//				cell.setCellValue(name);
//			}
//			Name namedCell = workbook.createName();
//			namedCell.setNameName("hidden"+firstCol);
//			namedCell.setRefersToFormula("hidden"+firstCol+"!A1:A" + textlist.length);
//			//加载数据,将名称为hidden的
//			DVConstraint constraint = DVConstraint.createFormulaListConstraint("hidden"+firstCol);
//			// 设置数据有效性加载在哪个单元格上,四个参数分别是：起始行、终止行、起始列、终止列
//			CellRangeAddressList addressList = new CellRangeAddressList(firstRow, endRow, firstCol, endCol);
//			HSSFDataValidation validation = new HSSFDataValidation(addressList, constraint);
//			validation.createErrorBox("Error", "Error");
//			validation.createPromptBox("下拉选择提示", "请使用下拉方式选择合适的值！");
////			workbook.setSheetHidden(workbook.getSheetIndex("hidden"+firstCol), true);
//			sheet.addValidationData(validation);
//			return sheet;
		}else{
			// 加载下拉列表内容
			DVConstraint constraint = DVConstraint.createExplicitListConstraint(textlist);
			// 设置数据有效性加载在哪个单元格上,四个参数分别是：起始行、终止行、起始列、终止列
			CellRangeAddressList regions = new CellRangeAddressList(firstRow,endRow, firstCol, endCol);
			// 数据有效性对象
			HSSFDataValidation data_validation_list = new HSSFDataValidation(regions, constraint);
			sheet.addValidationData(data_validation_list);
			return sheet;
		}

	}
	public static String getRange(int offset, int rowId, int colCount) {
		char start = (char)('A' + offset);
		if (colCount <= 25) {
			char end = (char)(start + colCount - 1);
			return "$" + start + "$" + rowId + ":$" + end + "$" + rowId;
		} else {
			char endPrefix = 'A';
			char endSuffix = 'A';
			if ((colCount - 25) / 26 == 0 || colCount == 51) {// 26-51之间，包括边界（仅两次字母表计算）
				if ((colCount - 25) % 26 == 0) {// 边界值
					endSuffix = (char)('A' + 25);
				} else {
					endSuffix = (char)('A' + (colCount - 25) % 26 - 1);
				}
			} else {// 51以上
				if ((colCount - 25) % 26 == 0) {
					endSuffix = (char)('A' + 25);
					endPrefix = (char)(endPrefix + (colCount - 25) / 26 - 1);
				} else {
					endSuffix = (char)('A' + (colCount - 25) % 26 - 1);
					endPrefix = (char)(endPrefix + (colCount - 25) / 26);
				}
			}
			return "$" + start + "$" + rowId + ":$" + endPrefix + endSuffix + "$" + rowId;
		}
	}
	public static String numToABC(Integer relation){
		String[] ABC={ "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
				"K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
				"W", "X", "Y", "Z" };
		return ABC[relation].toString();

	}
	public static HSSFSheet setHSSFPrompt(HSSFSheet sheet, String promptTitle,
			String promptContent, int firstRow, int endRow ,int firstCol,int endCol) {
		// 构造constraint对象
		DVConstraint constraint = DVConstraint.createCustomFormulaConstraint("BB1");
		// 四个参数分别是：起始行、终止行、起始列、终止列
		CellRangeAddressList regions = new CellRangeAddressList(firstRow,endRow,firstCol, endCol);
		// 数据有效性对象
		HSSFDataValidation data_validation_view = new HSSFDataValidation(regions,constraint);
		data_validation_view.createPromptBox(promptTitle, promptContent);
		sheet.addValidationData(data_validation_view);
		return sheet;
	}

	public static void exportExcel(HSSFWorkbook workbook, String[] fieldName, String sheetName, HttpServletResponse response) throws IOException {
		OutputStream output=null;
   try {
		String filename = "attachment; filename="
				+ new String(sheetName.getBytes("GBK"), "ISO-8859-1");
	    output=response.getOutputStream();  
		HSSFCellStyle headerStyle = workbook.createCellStyle(); //标题样式
		headerStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		headerStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		headerStyle.setFillForegroundColor(HSSFColor.YELLOW.index);
		headerStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND); 
		headerStyle.setFillBackgroundColor(HSSFColor.YELLOW.index);
		headerStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);// 下边框
		headerStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
		headerStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
		headerStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
		HSSFFont headerFont = workbook.createFont();	//标题字体
		headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		headerFont.setFontHeightInPoints((short)13);
		headerStyle.setFont(headerFont);
			HSSFSheet sheet = workbook.getSheet(sheetName);// 产生工作表对象
			HSSFRow row = sheet.createRow(0);// 产生一行
			HSSFCell cell;// 产生单元格
			
			// 写入各个字段的名称
			for (int i = 0; i < fieldName.length; i++) {
				cell = row.createCell(i); // 创建第一行各个字段名称的单元格
				cell.setCellType(HSSFCell.CELL_TYPE_STRING); // 设置单元格内容为字符串型
				cell.setCellStyle(headerStyle);
	            // cell.setEncoding(HSSFCell.ENCODING_UTF_16);
				// //为了能在单元格中输入中文,设置字符集为UTF_16
				cell.setCellValue(fieldName[i]); // 给单元格内容赋值
			}
			response.setContentType("application/vnd.ms-excel;charset=UTF-8");
			response.setHeader("Content-Disposition", filename + ".xls");
			workbook.write(output);
			output.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}
	/**
	 * 设置EXCEL下拉内容 作者：张良辉 时间：2017-08-09
	 * -2表示不设置该参数，行号和列号从0开始计算
	 * @param text------下拉内容
	 * @param startRow------设置EXCEL下拉功能的开始行
	 * @param endRow------设置EXCEL下拉功能的结束行
	 * @param startCol------设置EXCEL下拉功能的列
	 * @param hiddenCol------设置EXCEL隐藏的列
	 * @return ExcelText
	 */
	public static ExcelText getExceltext(List<String> text, int startRow ,int endRow,int startCol,Integer hiddenCol) {
		ExcelText exceltext = new  ExcelText();
		exceltext.setText(text);
		if(startRow!=-2&&startRow>=-1)
		{
			exceltext.setStartRow(startRow);
		}
		if(endRow!=-2&&endRow>=-1)
		{
			exceltext.setEndRow(endRow);
		}
		if(startCol!=-2&&startCol>=-1)
		{
			exceltext.setStartCol(startCol);
		}
		if(hiddenCol!=-2&&hiddenCol>=-1)
		{
			exceltext.setHiddenCol(hiddenCol);
		}			
		return exceltext;
	}
	
	    /**
	        * @Description: 判断单元格类型
	        * @author zengzr
	        * @date 2017/11/17
	        * @version V1.0
	        */
	    public static String getCellValue(HSSFCell cell){
	    	String cellValue="";
			if (null != cell) {
				// 以下是判断数据的类型
				switch (cell.getCellType()) {
					case HSSFCell.CELL_TYPE_NUMERIC: // 数字
						DecimalFormat df = new DecimalFormat("0");
						cellValue = df.format(cell.getNumericCellValue());
						break;

					case HSSFCell.CELL_TYPE_STRING: // 字符串
						cellValue = cell.getStringCellValue();
						break;

					case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean
						cellValue = cell.getBooleanCellValue() + "";
						break;

					case HSSFCell.CELL_TYPE_FORMULA: // 公式
						cellValue = cell.getCellFormula() + "";
						break;

					case HSSFCell.CELL_TYPE_BLANK: // 空值
						cellValue = "";
						break;

					case HSSFCell.CELL_TYPE_ERROR: // 故障
						cellValue = "非法字符";
						break;

					default:
						cellValue = "未知类型";
						break;
				}
			}
			return cellValue;
		}

	public static void main(String[] args) throws Exception {
		Map map=readData("C:\\Users\\zengzr\\Desktop\\150812.xls",0,10,null);
	  System.out.println(map);
	    }


}
