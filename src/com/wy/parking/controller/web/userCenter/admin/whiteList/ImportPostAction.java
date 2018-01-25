/**
 * 
 */
package com.wy.parking.controller.web.userCenter.admin.whiteList;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import com.wy.parking.model.CarInfo;
import com.wy.parking.service.ParkingService;
import com.wy.service.CommonService;
import com.wy.superClass.SuperAction;

/**
 * @author wy
 * 
 */
public class ImportPostAction extends SuperAction {

	private POIFSFileSystem fs;
	private HSSFWorkbook wb;
	private HSSFSheet sheet;
	private HSSFRow row;

	private Logger logger = Logger.getLogger(ImportPostAction.class);

	private ParkingService parkingService = null;

	private String parkCode = null;

	private String CarNo = null;

	private String parkNos = null;// 子级停车场

	private String fileFileName = null;

	private String picFileName = null;

	private File file = null;

	private String CardType = null;

	private String CardIndate = null;

	private String CardAmount = null;

	private String CarType = null;

	private String MasterName = null;

	private String MasterTel = null;

	private String MasterAddr = null;

	private String ParkPosition = null;

	private String PayAmount = null;

	private String Remark = null;

	protected Map<String, Object> addMap = null;

	public Map<String, Object> getAddMap() {
		return addMap;
	}

	public void setAddMap(Map<String, Object> addMap) {
		this.addMap = addMap;
	}

	public POIFSFileSystem getFs() {
		return fs;
	}

	public void setFs(POIFSFileSystem fs) {
		this.fs = fs;
	}

	public HSSFWorkbook getWb() {
		return wb;
	}

	public void setWb(HSSFWorkbook wb) {
		this.wb = wb;
	}

	public HSSFSheet getSheet() {
		return sheet;
	}

	public void setSheet(HSSFSheet sheet) {
		this.sheet = sheet;
	}

	public HSSFRow getRow() {
		return row;
	}

	public void setRow(HSSFRow row) {
		this.row = row;
	}

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public String getCardType() {
		return CardType;
	}

	public void setCardType(String cardType) {
		CardType = cardType;
	}

	public String getCardIndate() {
		return CardIndate;
	}

	public void setCardIndate(String cardIndate) {
		CardIndate = cardIndate;
	}

	public String getCardAmount() {
		return CardAmount;
	}

	public void setCardAmount(String cardAmount) {
		CardAmount = cardAmount;
	}

	public String getCarType() {
		return CarType;
	}

	public void setCarType(String carType) {
		CarType = carType;
	}

	public String getMasterName() {
		return MasterName;
	}

	public void setMasterName(String masterName) {
		MasterName = masterName;
	}

	public String getMasterTel() {
		return MasterTel;
	}

	public void setMasterTel(String masterTel) {
		MasterTel = masterTel;
	}

	public String getMasterAddr() {
		return MasterAddr;
	}

	public void setMasterAddr(String masterAddr) {
		MasterAddr = masterAddr;
	}

	public String getParkPosition() {
		return ParkPosition;
	}

	public void setParkPosition(String parkPosition) {
		ParkPosition = parkPosition;
	}

	public String getPayAmount() {
		return PayAmount;
	}

	public void setPayAmount(String payAmount) {
		PayAmount = payAmount;
	}

	public String getRemark() {
		return Remark;
	}

	public void setRemark(String remark) {
		Remark = remark;
	}

	public String getPicFileName() {
		return picFileName;
	}

	public void setPicFileName(String picFileName) {
		this.picFileName = picFileName;
	}

	public String getParkNos() {
		return parkNos;
	}

	public void setParkNos(String parkNos) {
		this.parkNos = parkNos;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public ParkingService getParkingService() {
		return parkingService;
	}

	public void setParkingService(ParkingService parkingService) {
		this.parkingService = parkingService;
	}

	public String getParkCode() {
		return parkCode;
	}

	public void setParkCode(String parkCode) {
		this.parkCode = parkCode;
	}

	public String getCarNo() {
		return CarNo;
	}

	public void setCarNo(String carNo) {
		CarNo = carNo;
	}

	@Override
	public String execute() {
		
		
		//西安客户

		List<CarInfo> carInfoList = null;
		
		HttpSession session = request.getSession();

		parkCode = (String) session.getAttribute("pcode");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		String fileNewName = CommonService.createFileName(fileFileName);

		String upPath = request.getSession().getServletContext()
				.getRealPath("/")
				+ CommonService.UP_ROUTE_PHOTO_PATH;
		

		String saveFilePath = CommonService.upFile(upPath, fileNewName, file);

		
		System.out.println(saveFilePath);
		
		String lastPath =upPath+"/"+fileNewName;
		
		System.out.println(lastPath);
		
		JSONObject jsonObject = null;
		Map<String, Object> rtn = null;

		int appcode = 1;
		String errmsg = "";
		
		try {
			rtn = InfoImp(lastPath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		appcode = (Integer) rtn.get("appcode");
		
		carInfoList=(List<CarInfo>) rtn.get("list");
		
		if (appcode == 1) {
			
			for (CarInfo carInfo : carInfoList) {

				//给主车场增加白名单
				
				
//				if(StringUtils.isNotBlank(parkCode) && !"1".equalsIgnoreCase(parkCode))
//				{
//				
//				
//			  String paramLevel = "parkCode=" + parkCode + "&CarNo=" + carInfo.getCardNo()
//	    				+ "&CardType=" + carInfo.getCardType() + "&CardIndate=" + carInfo.getCardIndate()
//	    				+ "&CardAmount=" + carInfo.getCardAmount() + "&CarType=" + carInfo.getCardType()
//	    				+ "&MasterName=" + carInfo.getMasterName() + "&MasterTel=" + carInfo.getMasterTel()
//	    				+ "&MasterAddr=" + carInfo.getMasterAddr() + "&ParkPosition=" + carInfo.getParkPosition()
//	    				+ "&PayAmount=" + carInfo.getPayAmount() + "&Remark=" + carInfo.getRemark();
//			  
//			     addMap = parkingService.AddCar(paramLevel);
//			     
//			     rtn.put("levelCode", addMap.get("resultCode"));
//			     rtn.put("levelMsg", addMap.get("message"));
//			     rtn.put("levelPcode", parkCode);
//			     
//				}
			     
			     addMap=new HashMap<String, Object>();
			     
			 	int j=0;
				
				if(StringUtils.isNotBlank(parkNos)){
				
				 String[] sourceStrArrays = parkNos.split(",");
				 
				 for (int i = 0; i < sourceStrArrays.length; i++) {
					 
					 String parkNos=sourceStrArrays[i].trim();
					 
					 //给其他车场增加白名单
					  String paramLevel1 = "parkCode=" + parkNos + "&CarNo=" + carInfo.getCardNo()
			    				+"&CardIndate=" + carInfo.getCardIndate()
			    				+ "&CarType=" + carInfo.getCarType()
			    				+ "&MasterName=" + carInfo.getMasterName() + "&MasterTel=" + carInfo.getMasterTel()
			    				+ "&MasterAddr=" + carInfo.getMasterAddr() + "&ParkPosition=" + carInfo.getParkPosition()
			    				+  "&Remark=" + carInfo.getRemark();
					  
					   addMap = parkingService.AddCar(paramLevel1);
					    
					    if(!"0".equalsIgnoreCase(addMap.get("resultCode").toString())){
					    	
						    rtn.put("levelCode", addMap.get("resultCode"));
						    rtn.put("levelMsg1", addMap.get("message"));
						    rtn.put("levelPcode1", parkNos);
					    	
						    break;
					    }else{
					    
					    	if(j==0)
					    	{
					        rtn.put("levelCode", addMap.get("resultCode"));
						    rtn.put("levelMsg1", addMap.get("message"));
						    rtn.put("levelPcode1", parkNos);
					    	}
						    
						    j++;
					    }
						
	
				 }
				
				}else
				{
				    rtn.put("levelCode", "");
				    rtn.put("levelMsg1","");
				    rtn.put("levelPcode1", "");
				}

			}
			rtn.remove("list");
		}
		jsonObject = JSONObject.fromObject(rtn);
		
		result = jsonObject.toString();

		System.out.println(result);
		
		return SUCCESS;

		
	}

	private Map<String, Object> InfoImp(String lastPath) throws IOException {
		
		Map<String, Object> rtn = new LinkedHashMap<String, Object>();

		int appcode = 1;
		String errmsg = "";
		List errlist = new ArrayList();
		
		List<CarInfo> list = new ArrayList<CarInfo>();
		
		// TODO Auto-generated method stub
		FileInputStream fileIn = new FileInputStream(lastPath);
		
		System.out.println(fileIn+lastPath);
		
		// 根据指定的文件输入流导入Excel从而产生Workbook对象
		Workbook wb0 = new HSSFWorkbook(fileIn);
		// 获取Excel文档中的第一个表单
		Sheet sht0 = wb0.getSheetAt(0);
		BigDecimal priority = null;
		
		BigDecimal priority1 = null;
		
		if (sht0.getLastRowNum() < 1) {
			fileIn.close();
			appcode = 0;
			errmsg = "无导入数据，导入数据从第二行开始";
			rtn.put("appcode", appcode);
			rtn.put("errmsg", errmsg);
			return rtn;
		}
		// 对Sheet中的每一行进行迭代
		for (Row r : sht0) {
			// 如果当前行的行号（从0开始）未达到2（第三行）则从新循环
			
			if (r.getRowNum() < 1) {
				continue;
			}
			
			if(isRowEmpty(r)){
				
				continue;
			}
			
			if (r.getCell(0) == null) {
				CarNo  = "";
			} else {
				CarNo  = getCellStringValue(r.getCell(0));
			}
			
			//判断有效期
			if (r.getCell(1) == null) {
				CardIndate = "";
			} else {
				CardIndate= getDateCell(r.getCell(1));
			}
			
			//判断车辆类型
			if (r.getCell(2) == null) {
				CarType = "";
			} else {
				CarType= getCellStringValue(r.getCell(2));
			}
			
			if (r.getCell(3) == null) {
				MasterName = "";
			} else {
				MasterName= getCellStringValue(r.getCell(3));
			}
			
			if (r.getCell(4) == null) {
				MasterTel = "";
			} else {
				MasterTel= getCellStringValue(r.getCell(4));
			}
			
			
			if (r.getCell(5) == null) {
				MasterAddr = "";
			} else {
				MasterAddr= getCellStringValue(r.getCell(5));
			}
			
			
			if (r.getCell(6) == null) {
				Remark = "";
			} else {
				Remark= getCellStringValue(r.getCell(6));
			}
			
			
			
			
			if (CarNo  == null || CarNo .length() <= 0) {
				errmsg = "车牌号不能为空";
				Map<String, Object> errquertion = new LinkedHashMap<String, Object>();
				errquertion.put("rownum", r.getRowNum());
				errquertion.put("errmsg", errmsg);
				errlist.add(errquertion);
				continue;
			}
					
			if (CardIndate == null || CardIndate.length() <= 0) {
				errmsg = "有效期不能为空";
				Map<String, Object> errquertion = new LinkedHashMap<String, Object>();
				errquertion.put("rownum", r.getRowNum());
				errquertion.put("errmsg", errmsg);
				errlist.add(errquertion);
				continue;
			}

			if (CarType == null || CarType.length() <= 0) {
				errmsg = "车辆类型不能为空";
				Map<String, Object> errquertion = new LinkedHashMap<String, Object>();
				errquertion.put("rownum", r.getRowNum());
				errquertion.put("errmsg", errmsg);
				errlist.add(errquertion);
				continue;
			}

			
//			CardAmount=formatAmtY2F(priority.toString());
//			
//			PayAmount=priority1.toString();
			
			// 创建实体类
			CarInfo info = new CarInfo();
			// 取出当前行第1个单元格数据，并封装在info实体stuName属性上
			
			info.setCardNo(CarNo.trim());
			info.setCardIndate(CardIndate.trim());
			info.setCarType(CarType.trim());
			info.setMasterName(MasterName.trim());
			info.setMasterTel(MasterTel.trim());
			info.setMasterAddr(MasterAddr.trim());
			info.setRemark(Remark.trim());
			
         	list.add(info);
		}	
			
		fileIn.close();
		if (!(errlist == null) && errlist.size()>0) {
			appcode = -1;
			errmsg = "数据错误！";
			rtn.put("appcode", appcode);
			rtn.put("errmsg", errmsg);
			rtn.put("list", errlist);

			return rtn;
		}

		rtn.put("appcode", appcode);
		rtn.put("list", list);
		
		return rtn;
	}
	
	public boolean isRowEmpty(Row row) {
	    for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
	        Cell cell = row.getCell(c);
	        if (cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK)
	            return false;
	    }
	    return true;
	}
	
	public String getDateCell(Cell cell) {
		DecimalFormat df = new DecimalFormat("#");
		if (cell == null)
			return "";
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_NUMERIC:
			if(HSSFDateUtil.isCellDateFormatted(cell)){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				return sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue())).toString();
			}
			return df.format(cell.getNumericCellValue());
		case HSSFCell.CELL_TYPE_STRING:
			System.out.println(cell.getStringCellValue());
			return cell.getStringCellValue();
		case HSSFCell.CELL_TYPE_FORMULA:
			return cell.getCellFormula();
		case HSSFCell.CELL_TYPE_BLANK:
			return "";
		case HSSFCell.CELL_TYPE_BOOLEAN:
			return cell.getBooleanCellValue() + "";
		case HSSFCell.CELL_TYPE_ERROR:
			return cell.getErrorCellValue() + "";
		}
		return "";
	}

	
	public static String getCellStringValue(Cell cell) {
		String cellValue = "";
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_STRING:// 字符串类型
			cellValue = cell.getStringCellValue();
			if (cellValue.trim().length() <= 0)
				cellValue = "";
			break;
		case HSSFCell.CELL_TYPE_NUMERIC: // 数值类型
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cellValue = cell.getStringCellValue();
			if (cellValue.trim().length() <= 0)
				cellValue = "";
			break;
		case HSSFCell.CELL_TYPE_FORMULA: // 公式
			cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			cellValue = String.valueOf(cell.getNumericCellValue());
			if (cellValue.trim().length() <= 0)
				cellValue = "";
			break;
		case HSSFCell.CELL_TYPE_BLANK:
			break;
		case HSSFCell.CELL_TYPE_BOOLEAN:
			break;
		case HSSFCell.CELL_TYPE_ERROR:
			break;
		default:
			break;
		}
		return cellValue;
	}

	public String formatAmtY2F(String amtY) {
		if (amtY == null || "".equals(amtY.trim()) || "0".equals(amtY))
			return "0";
		if (amtY.indexOf(",") != -1) {
			amtY = amtY.replace(",", "");
		}

		amtY = new DecimalFormat("0.00").format(new BigDecimal(amtY));
		int index = amtY.indexOf(".");
		int len = amtY.length();
		StringBuffer amtF = new StringBuffer();
		if (index == -1) {
			amtF.append(amtY).append("00");
		} else if ((len - index) == 1) {
			amtF.append(Long.parseLong(amtY.replace(".", ""))).append("00");
		} else if ((len - index) == 2) {
			amtF.append(Long.parseLong(amtY.replace(".", ""))).append("0");
		} else {
			amtF.append(Long.parseLong(amtY.replace(".", "")));
		}
		return amtF.toString();
	}

	/*
	 * 读取Excel表格表头的内容
	 * 
	 * @param InputStream
	 * 
	 * @return String 表头内容的数组
	 */
	public String[] readExcelTitle(InputStream is) {
		try {
			fs = new POIFSFileSystem(is);
			wb = new HSSFWorkbook(fs);
		} catch (IOException e) {
			e.printStackTrace();
		}
		sheet = wb.getSheetAt(0);
		row = sheet.getRow(0);
		// 标题总列数
		int colNum = row.getPhysicalNumberOfCells();
		System.out.println("colNum:" + colNum);
		String[] title = new String[colNum];
		for (int i = 0; i < colNum; i++) {
			// title[i] = getStringCellValue(row.getCell((short) i));
			title[i] = getCellFormatValue(row.getCell((short) i));
		}
		return title;
	}

	/**
	 * 读取Excel数据内容
	 * 
	 * @param InputStream
	 * @return Map 包含单元格数据内容的Map对象
	 */
	public Map<Integer, String> readExcelContent(InputStream is) {
		Map<Integer, String> content = new HashMap<Integer, String>();
		String str = "";
		try {
			fs = new POIFSFileSystem(is);
			wb = new HSSFWorkbook(fs);
		} catch (IOException e) {
			e.printStackTrace();
		}
		sheet = wb.getSheetAt(0);
		// 得到总行数
		int rowNum = sheet.getLastRowNum();
		row = sheet.getRow(0);
		int colNum = row.getPhysicalNumberOfCells();
		// 正文内容应该从第二行开始,第一行为表头的标题
		for (int i = 1; i <= rowNum; i++) {
			row = sheet.getRow(i);
			int j = 0;
			while (j < colNum) {
				// 每个单元格的数据内容用"-"分割开，以后需要时用String类的replace()方法还原数据
				// 也可以将每个单元格的数据设置到一个javabean的属性中，此时需要新建一个javabean
				// str += getStringCellValue(row.getCell((short) j)).trim() +
				// "-";
				str += getCellFormatValue(row.getCell((short) j)).trim()
						+ "    ";
				j++;
			}
			content.put(i, str);
			str = "";
		}
		return content;
	}

	/**
	 * 获取单元格数据内容为字符串类型的数据
	 * 
	 * @param cell
	 *            Excel单元格
	 * @return String 单元格数据内容
	 */
	private String getStringCellValue(HSSFCell cell) {
		String strCell = "";
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_STRING:
			strCell = cell.getStringCellValue();
			break;
		case HSSFCell.CELL_TYPE_NUMERIC:
			strCell = String.valueOf(cell.getNumericCellValue());
			break;
		case HSSFCell.CELL_TYPE_BOOLEAN:
			strCell = String.valueOf(cell.getBooleanCellValue());
			break;
		case HSSFCell.CELL_TYPE_BLANK:
			strCell = "";
			break;
		default:
			strCell = "";
			break;
		}
		if (strCell.equals("") || strCell == null) {
			return "";
		}
		if (cell == null) {
			return "";
		}
		return strCell;
	}

	/**
	 * 获取单元格数据内容为日期类型的数据
	 * 
	 * @param cell
	 *            Excel单元格
	 * @return String 单元格数据内容
	 */
	private String getDateCellValue(HSSFCell cell) {
		String result = "";
		try {
			int cellType = cell.getCellType();
			if (cellType == HSSFCell.CELL_TYPE_NUMERIC) {
				Date date = cell.getDateCellValue();
				result = (date.getYear() + 1900) + "-" + (date.getMonth() + 1)
						+ "-" + date.getDate();
			} else if (cellType == HSSFCell.CELL_TYPE_STRING) {
				String date = getStringCellValue(cell);
				result = date.replaceAll("[年月]", "-").replace("日", "").trim();
			} else if (cellType == HSSFCell.CELL_TYPE_BLANK) {
				result = "";
			}
		} catch (Exception e) {
			System.out.println("日期格式不正确!");
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 根据HSSFCell类型设置数据
	 * 
	 * @param cell
	 * @return
	 */
	private String getCellFormatValue(HSSFCell cell) {
		String cellvalue = "";
		if (cell != null) {
			// 判断当前Cell的Type
			switch (cell.getCellType()) {
			// 如果当前Cell的Type为NUMERIC
			case HSSFCell.CELL_TYPE_NUMERIC:
			case HSSFCell.CELL_TYPE_FORMULA: {
				// 判断当前的cell是否为Date
				if (HSSFDateUtil.isCellDateFormatted(cell)) {
					// 如果是Date类型则，转化为Data格式

					// 方法1：这样子的data格式是带时分秒的：2011-10-12 0:00:00
					// cellvalue = cell.getDateCellValue().toLocaleString();

					// 方法2：这样子的data格式是不带带时分秒的：2011-10-12
					Date date = cell.getDateCellValue();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					cellvalue = sdf.format(date);

				}
				// 如果是纯数字
				else {
					// 取得当前Cell的数值
					cellvalue = String.valueOf(cell.getNumericCellValue());
				}
				break;
			}
			// 如果当前Cell的Type为STRIN
			case HSSFCell.CELL_TYPE_STRING:
				// 取得当前的Cell字符串
				cellvalue = cell.getRichStringCellValue().getString();
				break;
			// 默认的Cell值
			default:
				cellvalue = " ";
			}
		} else {
			cellvalue = "";
		}
		return cellvalue;

	}

	public static void main(String[] args) {
		try {
			// 对读取Excel表格标题测试
			InputStream is = new FileInputStream("d:\\test2.xls");
			ImportPostAction excelReader = new ImportPostAction();
			String[] title = excelReader.readExcelTitle(is);
			System.out.println("获得Excel表格的标题:");
			for (String s : title) {
				System.out.print(s + " ");
			}

			// 对读取Excel表格内容测试
			InputStream is2 = new FileInputStream("d:\\test2.xls");
			Map<Integer, String> map = excelReader.readExcelContent(is2);
			System.out.println("获得Excel表格的内容:");
			for (int i = 1; i <= map.size(); i++) {
				System.out.println(map.get(i));
			}

		} catch (FileNotFoundException e) {
			System.out.println("未找到指定路径的文件!");
			e.printStackTrace();
		}
	}

}
