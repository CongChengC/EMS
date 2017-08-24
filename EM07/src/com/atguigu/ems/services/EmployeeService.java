package com.atguigu.ems.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.record.formula.functions.True;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atguigu.ems.daos.DepartmentDao;
import com.atguigu.ems.daos.RoleDao;
import com.atguigu.ems.entities.Department;
import com.atguigu.ems.entities.Employee;
import com.atguigu.ems.entities.Role;
import com.atguigu.ems.exceptions.EmployeeDisabledException;
import com.atguigu.ems.exceptions.EmployeeIsDeletedException;
import com.atguigu.ems.exceptions.LoginNameNotFoundException;
import com.atguigu.ems.exceptions.PasswordNotMatchException;
import com.atguigu.ems.orm.Page;
import com.sun.org.apache.bcel.internal.generic.RETURN;

@Service
public class EmployeeService extends BaseService<Employee> {
	
//	@Autowired
//	private dao dao;
	
	@Autowired 
	private DepartmentDao departmentDao;
	
	@Autowired
	private RoleDao roleDao;
	
	@Transactional
	public void updateVisitedTimes(Integer id){
		Employee employee = dao.get(id);
		employee.setVisitedTimes(employee.getVisitedTimes() + 1);
	}
	
	@Transactional
	public List<String[]> upload(File file) throws InvalidFormatException, IOException {
		List<String[]> positions = new ArrayList<>();
		// 1. 把 File 对象解析为一个 Workbook 对象
		 InputStream inp = new FileInputStream(file);
		 Workbook wb = WorkbookFactory.create(inp);
		 
		// 2. 得到 name="employees" 的 Sheet 对象
		 Sheet sheet = wb.getSheet("employees");
		// 3. 把内容解析为一个 Employee 的 List.
		List<Employee> emps = parseSheetToEmployees(sheet, positions);

		if (positions.size() > 0) {
			return positions;
		}
		
		System.out.println(emps.size());
		// 4. 完成批量的上传
		dao.batchSave(emps);
		
		return positions;
	}

	
	
	private List<Employee> parseSheetToEmployees(Sheet sheet,
			List<String[]> positions) {
		List<Employee> emps = new ArrayList<>();
		    // 1. 得到每一行
		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			// 2. 把每一行都解析为一个 Employee 对象
			Row row = sheet.getRow(i);

			// 3. 若解析的过程没有错误, 则把该 Employee 放入到 Employee 的集合中.
			Employee employee = parseRowToEmployee(row, i + 1, positions);
			if (employee != null) {
				emps.add(employee);
			}
		}
		// 4. 返回 Employee 的集合
		return emps;
	}



	private Employee parseRowToEmployee(Row row, int rowNum, List<String[]> positions) {
		// 1. 得到具体每一列的值
		// 序号 登录名 姓名 性别 登录许可 部门 E-mail 角色
		Cell cell = row.getCell(1);
		Object cellVal = getCellValue(cell);
		boolean flag = validateLoginName(cellVal, rowNum, positions);
		String loginName = null;
		if(flag){
			loginName = (String) cellVal;
		}
		
		cell = row.getCell(2);
		cellVal = getCellValue(cell);
		flag = validateEmployeeName(cellVal, rowNum, positions) && flag;
		String employeeName = null;
		if(flag){
			employeeName = (String) cellVal;
		}
		
		// 2. 对每一列的值进行解析, 校验该列的值
		//性别:
		cell = row.getCell(3);
		cellVal = getCellValue(cell);
		String gender = validateGender(cellVal, rowNum, positions);
		flag = (gender != null) && flag;
		
		
		//登陆许可: 填入的值可能是字符串, 也可能是数值. 若是数值, 则时间返回的是 1.0. 所以需要对用户
		//输入的 1.0, 0.0 进行兼容。 
		cell = row.getCell(4);
		cellVal = getCellValue(cell);
		Integer enabled = validateEnable(cellVal, rowNum, positions);
		flag = (enabled != null) && flag;
		
		//部门: 必须是已经存在的某一个部门的名字
		cell = row.getCell(5);
		cellVal = getCellValue(cell);
		Department department = validateDepartment(cellVal, rowNum, positions);
		flag = (department != null) && flag;
		
		//Email: 必须是一个合法的 Email 地址
		cell = row.getCell(6);
		cellVal = getCellValue(cell);
		String email = validateEmail(cellVal, rowNum, positions);
		flag = (email != null) && flag;
		
		//角色: 必须是已经存在的 Role 的 roleName 的列表(用逗号分隔, 若有一个不对, 都不行)
		cell = row.getCell(7);
		cellVal = getCellValue(cell);
		Set<Role> roles = validateRoles(cellVal, rowNum, positions);
		flag = (roles != null) && flag;

		// 3. 若所有的验证都可以通过, 则组装 Employee 对象返回
		if(flag){
			//创建 Employee 对象, 并返回
			Employee employee = new Employee(loginName, employeeName, roles, enabled, department, gender, email);
			return employee;
		}
		
		return null;
	}
    
	private Set<Role> validateRoles(Object cellVal, int rowNum,
			List<String[]> positions) {
		if(cellVal instanceof String){
			String roleNames = (String) cellVal;
			String [] names = roleNames.split(",");
			
			List<String> nameList = Arrays.asList(names);
			List<Role> roles = roleDao.getByIn("roleName", nameList);
			
			if(roles.size() == names.length){
				return new HashSet<>(roles);
			}
		}
		
		positions.add(new String[]{rowNum + "", "8"});
		return null;
	}

	private String validateEmail(Object cellVal, int rowNum,
			List<String[]> positions) {
		if(cellVal instanceof String){
			String email = (String) cellVal;
			Pattern pattern = Pattern.compile("^([a-z0-9_\\.-]+)@([\\da-z\\.-]+)\\.([a-z\\.]{2,6})$");
			Matcher matcher = pattern.matcher(email);
			if(matcher.matches()){
				return email;
			}
		}
		
		positions.add(new String[]{rowNum + "", "7"});
		return null;
	}

	private Department validateDepartment(Object cellVal, int rowNum,
			List<String[]> positions) {
		if(cellVal instanceof String){
			String deptName = (String) cellVal;
			Department department = departmentDao.getBy("departmentName", deptName);
			if(department != null){
				return department;
			}
		}
		
		positions.add(new String[]{rowNum + "", "6"});
		return null;
	}

	private Integer validateEnable(Object cellVal, int rowNum,
			List<String[]> positions) {
		if(cellVal instanceof Double){
			double d = (double) cellVal;
			int i = (int) d;
			if(i == 1 || i == 0){
				return i;
			}
		}
		if(cellVal instanceof String){
			String s = (String) cellVal;
			if("1".equals(s) || "0".equals(s)){
				return Integer.parseInt(s);
			}
		}
		
		positions.add(new String[]{rowNum + "", "5"});
		return null;
	}

	private String validateGender(Object cellVal, int rowNum,
			List<String[]> positions) {
		if(cellVal instanceof Double){
			double d = (double) cellVal;
			int i = (int) d;
			if(i == 1 || i == 0){
				return i + "";
			}
		}
		if(cellVal instanceof String){
			String s = (String) cellVal;
			if("1".equals(s) || "0".equals(s)){
				return s;
			}
		}
		
		positions.add(new String[]{rowNum + "", "4"});
		return null;
	}

	private boolean validateEmployeeName(Object cellVal, int rowNum,
			List<String[]> positions) {
		if(cellVal instanceof String){
			return true;
		}
		
		positions.add(new String[]{rowNum + "", "3"});
		return false;
	}

	//校验 loginName
	private boolean validateLoginName(Object cellVal, int rowNum,
			List<String[]> positions) {
		boolean flag = true;
		if(cellVal == null || !(cellVal instanceof String)){
			flag = false;
		}else{
			String loginName = (String) cellVal;
			if(loginName.length() < 6){
				flag = false;
			}else{
				Pattern p = Pattern.compile("^[a-zA-Z]\\w+\\w$");
				Matcher m = p.matcher(loginName);
				if(!m.matches()){
					flag = false;
				}
			}
		}
		
		if(!flag){
			positions.add(new String[]{rowNum + "", "2"});
		}
		return flag;
	}
	
	private Object getCellValue(Cell cell) {
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_STRING:
			return cell.getRichStringCellValue().getString();
		case Cell.CELL_TYPE_NUMERIC:
			if (DateUtil.isCellDateFormatted(cell)) {
				return cell.getDateCellValue();
			} else {
				return cell.getNumericCellValue();
			}
		case Cell.CELL_TYPE_BOOLEAN:
			return cell.getBooleanCellValue();
		case Cell.CELL_TYPE_FORMULA:
			return cell.getCellFormula();
		default:
			return null;
		}
	}


	@Transactional(readOnly=true)
	public void downloadFile(String tempFileName) throws IOException {
		Workbook wb = new HSSFWorkbook();
	    //Workbook wb = new XSSFWorkbook();
	    CreationHelper createHelper = wb.getCreationHelper();
	    Sheet sheet = wb.createSheet("employees");

		//读取所有的 Employee. 把他们添加到 Excel 文档中.
		//1. 填充 "标题" 行: 序号 登录名 姓名 性别 登录许可 部门 E-mail 角色
	    setTitles(sheet);
	    
		//2. 读取所有的 Employee. 且要读取 department 和 roles
		//3. 把所有的 Employee 填充都 Excel 文档中
	    setContent(sheet);
	    
		//4. 设置 CellStyle: 行高, 列宽, 边框
	    setCellStyle(wb);

	    // Write the output to a file
	    FileOutputStream fileOut = new FileOutputStream(tempFileName);
	    wb.write(fileOut);
	    fileOut.close();
	}

	//序号    登录名     姓名     性别     登录许可     部门        E-mail    角色
	private void setTitles(Sheet sheet) {
		String [] titles = {"序号","登录名","姓名", "性别", "登录许可", "部门", "E-mail", "角色"};
        Row row = sheet.createRow(0);
		for(int i = 0; i < titles.length; i++){
			row.createCell(i).setCellValue(titles[i]);
		}
	}
	
	private void setContent(Sheet sheet) {
		List<Employee> employees = dao.getAll();

		for (int i = 0; i < employees.size(); i++) {
			Employee employee = employees.get(i);

			Row row = sheet.createRow(i + 1);

			row.createCell(0).setCellValue("" + (i + 1));
			row.createCell(1).setCellValue("" + employee.getLoginName());

			row.createCell(2).setCellValue("" + employee.getEmployeeName());
			row.createCell(3).setCellValue("" + employee.getGender());

			row.createCell(4).setCellValue("" + employee.getEnabled());
			row.createCell(5).setCellValue("" + employee.getDepartmentName());

			row.createCell(6).setCellValue("" + employee.getEmail());
			row.createCell(7).setCellValue("" + employee.getRoleNames());
		}
	}
	
	private void setCellStyle(Workbook wb) {
		CellStyle cellStyle = getCellStyle(wb);

		Sheet sheet = wb.getSheet("employees");

		// 行高. 注意: i <= sheet.getLastRowNum()
		for (int i = 0; i <= sheet.getLastRowNum(); i++) {
			Row row = sheet.getRow(i);
			row.setHeightInPoints(30);

			// 列宽
			for (int j = 0; j < row.getLastCellNum(); j++) {
				// 自动设置列宽
				sheet.autoSizeColumn(j);
				sheet.setColumnWidth(j, (int) (sheet.getColumnWidth(j) * 1.5));

				// 设置边框
				Cell cell = row.getCell(j);
				cell.setCellStyle(cellStyle);
			}
		}
	}
	
	private CellStyle getCellStyle(Workbook wb) {
		CellStyle style = wb.createCellStyle();
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setRightBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setTopBorderColor(IndexedColors.BLACK.getIndex());

		return style;
	}
	

/*	@Transactional(readOnly=true)
	public Employee get(Integer id){
		Employee employee = dao.get(id);
		Hibernate.initialize(employee.getEditor());
		Hibernate.initialize(employee.getRoles());
		Hibernate.initialize(employee.getDepartment());
		return employee;
	}
	*/
	
	//设置子类需要初始化的属性
	@Override
	protected void initializeEntity(Employee entity) {
		Hibernate.initialize(entity.getEditor());
		Hibernate.initialize(entity.getRoles());
		Hibernate.initialize(entity.getDepartment());
	}
	

	@Transactional
	public void save(Employee entity) {
		// 添加
		if (entity.getEmployeeId() == null) {
			// 后边需要进行调整
			entity.setPassword("123456");
		}

		super.save(entity);
	}
	
/*	@Transactional(readOnly=true)
	public Employee getByLoginName(String loginName){
		return dao.getBy("loginName", loginName);
	}*/
	
	@Transactional
	public int deleteEmployee(Integer id){
		//查询 id 对应的 Employee 是否为一个Manager 
		//若是一个Manager 则返回 0;
		Employee manager = new Employee();
		//其实对于引用类型，值使用id，所以直接赋值即可
		manager.setEmployeeId(id);
		Department department = departmentDao.getBy("manager", manager);
		if(department !=null){
			return 0;
		}
		
		//若不是一个Manager 则 执行删除，返回 1;
		Employee employee = dao.get(id);
		//因为此时 Employee 是一个持久化对象，所以可以修改其属性，且能够完成更新操作
		employee.setIsDeleted(1);
		return 1;
	}
	
//	@Transactional(readOnly=true)
//	public Page<Employee> getPage(int pageNo){
//		Page<Employee> page =  dao.getPage(pageNo);
//		for(Employee employee: page.getContent()){
//			Hibernate.initialize(employee.getDepartment());
//			Hibernate.initialize(employee.getRoles());
//		}
//		return page;
//	}
	
	@Override
	protected void initializePageContent(List<Employee> content) {
		for(Employee employee: content){
			Hibernate.initialize(employee.getDepartment());
			Hibernate.initialize(employee.getRoles());
			Hibernate.initialize(employee.getResume());
			Hibernate.initialize(employee.getEditor());
		}
	}
	
	@Transactional
	public Employee login(String loginName, String password){
		Employee employee = dao.getBy("loginName", loginName);
		if(employee == null){
			throw new LoginNameNotFoundException();
		}
		if(employee.getIsDeleted() == 1){
			throw new EmployeeIsDeletedException();
		}
		if(employee.getEnabled() != 1){
			throw new EmployeeDisabledException();
		}
		if(!password.equals(employee.getPassword())){
			throw new PasswordNotMatchException();
		}
		//登陆次数 + 1
		employee.setVisitedTimes(employee.getVisitedTimes()+1);
		return employee;
		
	}







}
