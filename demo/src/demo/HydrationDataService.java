package ht.financial.management.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ht.financial.management.dao.EconomicQuotaDailyDao;
import ht.financial.management.model.EconomicQuotaDaily;
import ht.util.UuidGenerator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi2.hssf.usermodel.HSSFCell;
import org.apache.poi2.hssf.usermodel.HSSFRow;
import org.apache.poi2.hssf.usermodel.HSSFSheet;
import org.apache.poi2.hssf.usermodel.HSSFWorkbook;

import net.sf.json.JSONObject;

public class HydrationDataService {
	
	private EconomicQuotaDailyDao economicQuotaDailyDao;
	String msg = "success";	
	public void upload(HttpServletRequest request,
			HttpServletResponse response, String filepath){
		// TODO Auto-generated method stub
		String msg = "success";	
		try {
			FileInputStream stream = new FileInputStream(filepath);
			HSSFWorkbook workbook = new HSSFWorkbook(stream);//根据指定的文件输入流导入Excel从而产生Workbook对象
			HSSFSheet sheet = workbook.getSheetAt(0);//获取excel的第一个表单
			int rowNum = sheet.getLastRowNum();//获得总行数
			SimpleDateFormat sdf =   new SimpleDateFormat("yyyy-MM-dd");//格式化日期
			//循环从第4行开始获取数据
			for (int i = 3; i <= rowNum; i++) {
				HSSFRow row = sheet.getRow(i);
				EconomicQuotaDaily economicQuotaDaily = new EconomicQuotaDaily();
				economicQuotaDaily.setTable_id(UuidGenerator.createId());//自动生成Table_id
				System.out.println(row.getCell((short) 0));
				String date = sdf.format(row.getCell((short) 0).getDateCellValue());//getCellType()获取类型，再根据不同类型调用不同的方法来获取内容
				
				economicQuotaDaily.setFinancial_date(sdf.parse(date));
				economicQuotaDaily.setFinancial_type(date);
				economicQuotaDaily.setZ_17(getExcelValue(1,row));
				economicQuotaDaily.setZ_18(getExcelValue(2,row));
				economicQuotaDaily.setF_17(getExcelValue(3,row));
				economicQuotaDaily.setZ_10(getExcelValue(4,row));
						
				economicQuotaDaily.setR_17(getExcelValue(5,row));
				economicQuotaDaily.setU_17(getExcelValue(6,row));
				economicQuotaDaily.setU_14(getExcelValue(7,row));
				economicQuotaDaily.setZ_02(getExcelValue(8,row));
				economicQuotaDaily.setZ_03(getExcelValue(9,row));
				economicQuotaDaily.setZ_04(getExcelValue(10,row));
				economicQuotaDaily.setK_18(getExcelValue(11,row));
				economicQuotaDaily.setHydration_data_dutyname(replaceBlank(parseExcel(row.getCell((short) 12))));
				
				
				List<EconomicQuotaDaily> economicQuotaList = economicQuotaDailyDao.findByProperty("financial_date",sdf.parse(date) );
				
				if (economicQuotaList.size()<1) {
								
					economicQuotaDaily.setTable_id(UuidGenerator.createId());
					economicQuotaDaily.setFinancial_date(sdf.parse(date));
					economicQuotaDaily.setFinancial_type(date);
								
					economicQuotaDailyDao.save(economicQuotaDaily);
				}else{
					
					updateBySql(economicQuotaDaily,sdf.parse(date));
				}
			
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			msg = "err";
			e.printStackTrace();
		}
		request.setAttribute("msg", msg);
		
	}
	public String parseExcel(HSSFCell cell) {
		String value = null;
		try {
			if (null != cell) {
				switch (cell.getCellType()) {
				case HSSFCell.CELL_TYPE_STRING:
					value = cell.getStringCellValue();
					break;
				case HSSFCell.CELL_TYPE_FORMULA:
					value = cell.getCellFormula();
					break;
				case HSSFCell.CELL_TYPE_NUMERIC:
					value = cell.getNumericCellValue() + "";
					break;
				case HSSFCell.CELL_TYPE_BOOLEAN:
					value = cell.getBooleanCellValue() + "";
					break;
				default:
					value = "";
					break;
				}
				return value;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			msg = "err";
		}

		return null;
	}
	/**
	 * 去除从Excel表中的换行符和回车符号
	 * 
	 * @author xkb 2018-12-27
	 * @param str
	 *            要转的的值
	 * @return 返回转换完的值
	 */
	public static String replaceBlank(String str) {
		String dest = "";
		if (str != null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
			dest = dest.replace(".0", "");
		}
		return dest;
	}
	private Double getExcelValue( int i, HSSFRow row ) {
		// TODO Auto-generated method stub
		return row.getCell((short) i).getCellType() != HSSFCell.CELL_TYPE_NUMERIC ? null : row.getCell((short) i).getNumericCellValue();
	}	
	public void setEconomicQuotaDailyDao(EconomicQuotaDailyDao economicQuotaDailyDao) {
		this.economicQuotaDailyDao = economicQuotaDailyDao;
	}
	public void save(HttpServletRequest request, HttpServletResponse response){
		response.setContentType("text/html; charset=gb2312");
		
	    try {
		    String obj=request.getParameter("obj");
		    JSONObject jsonobj=JSONObject.fromObject(obj);
		    EconomicQuotaDaily economicQuotaDaily = (EconomicQuotaDaily) JSONObject.toBean(jsonobj,EconomicQuotaDaily.class);
		    
		    String date = request.getParameter("date");
		    SimpleDateFormat sdf =   new SimpleDateFormat("yyyy-MM-dd");
				
			List<EconomicQuotaDaily> economicQuotaList = economicQuotaDailyDao.findByProperty("financial_date",sdf.parse(date) );
			
			if (economicQuotaList.size()<1) {
							
				economicQuotaDaily.setTable_id(UuidGenerator.createId());
				economicQuotaDaily.setFinancial_date(sdf.parse(date));
				economicQuotaDaily.setFinancial_type(date);
							
				economicQuotaDailyDao.save(economicQuotaDaily);
			}else{
				
				updateBySql(economicQuotaDaily,sdf.parse(date));

			}

			} catch (Exception e) {			
				msg = "err";	
				e.printStackTrace();
			}	
			JSONObject json=new JSONObject();
			json.put("msg", msg);
			PrintWriter printWriter = null;
			try {
				printWriter = response.getWriter();
				printWriter.print(json);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				printWriter.flush();
				printWriter.close();
			}
	}
	private void updateBySql(EconomicQuotaDaily economicQuotaDaily, Date parse) {
		// TODO Auto-generated method stub
		Object[] params ={
				economicQuotaDaily.getZ_17(),
				economicQuotaDaily.getF_17(),
				economicQuotaDaily.getR_17(),
				economicQuotaDaily.getU_17(),
				economicQuotaDaily.getZ_18(),
				economicQuotaDaily.getU_14(),
				economicQuotaDaily.getHydration_data_dutyname(),
				economicQuotaDaily.getZ_10(),
				economicQuotaDaily.getZ_02(),
				economicQuotaDaily.getZ_03(),
				economicQuotaDaily.getZ_04(),
				economicQuotaDaily.getK_18(),
				parse
          };
		StringBuffer updateSql = new StringBuffer();
		updateSql.append("update ECONOMIC_QUOTA_DAILY eqd set ");
		updateSql.append(" eqd.Z_17=? ,");
		updateSql.append(" eqd.F_17=? ,");
		updateSql.append(" eqd.R_17=? ,");
		updateSql.append(" eqd.U_17=? ,");
		updateSql.append(" eqd.Z_18=? ,");	
		updateSql.append(" eqd.U_14=? ,");
		updateSql.append(" eqd.Hydration_data_dutyname=? ,");
		updateSql.append(" eqd.Z_10=? ,");
		updateSql.append(" eqd.Z_02=? ,");
		updateSql.append(" eqd.Z_03=? ,");
		updateSql.append(" eqd.Z_04=? ,");
		updateSql.append(" eqd.K_18=? ");
		updateSql.append(" where eqd.Financial_date=?  ");
		
		economicQuotaDailyDao.updateBySql(updateSql.toString(), params);
	}
}
