package com.atguigu.ems.test;

import static org.junit.Assert.*;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Test;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTCacheField;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.atguigu.ems.entities.Employee;
import com.atguigu.ems.orm.Page;
import com.atguigu.ems.services.DepartmentService;
import com.atguigu.ems.services.EmployeeService;
import com.atguigu.ems.services.RoleService;


public class ApplicationContextTest {
	
	private ApplicationContext ctx = null;
	private EmployeeService employeeService = null;
	private DepartmentService departmentService = null;
	private RoleService roleService = null;
	
	{
		ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		employeeService = ctx.getBean(EmployeeService.class);
		departmentService = ctx.getBean(DepartmentService.class);
		roleService = ctx.getBean(RoleService.class);
	}
	
	@Test
	public void testGetPage2(){
		Page<Employee> page = employeeService.getPage(2);
		
		for(Employee emp: page.getContent()){
//			System.out.println(emp.getLoginName());
//			System.out.println(emp.getEmployeeName());
//			System.out.println(emp.getEnabled());
			
			System.out.println(emp.getDepartmentName());
			System.out.println(emp.getDisplayBirth());
//			System.out.println(emp.getGender());
			
//			System.out.println(emp.getEmail());
//			System.out.println(emp.getMobilePhone());
//			System.out.println(emp.getVisitedTimes());
			
			System.out.println("--" + emp.getRoleNames() + "--");
			System.out.println();
		}
		
		System.out.println(page.getContent().size());
	}
	
	
	@Test
	public void testGet(){
		Employee employee = employeeService.get(2);
		System.out.println(employee.getLoginName());//AABBCC
		System.out.println("\n\n");
		//因为oa_employee表中有一个 Department_ID外键，输出3
		System.out.println(employee.getDepartment().getDepartmentId());
		//下面一行会报异常：org.hibernate.LazyInitializationException: could not initialize proxy - no Session
		//System.out.println(employee.getDepartment().getDepartmentName());
		System.out.println(employee.getEditor().getEmployeeId());//3
		//下一行抱异常org.hibernate.LazyInitializationException: failed to lazily initialize a collection of role: com.atguigu.ems.entities.Employee.roles, could not initialize proxy - no Session
		//System.out.println(employee.getRoles().size());
		
	}
	
	@Test
	public void testGetbyLoginName(){
		Employee employee = employeeService.getByLoginName("FM12345");
		System.out.println("size:"+employee.getRoles().size());
	}
	
	
	@Test
	public void testSecondLevelCache(){
		departmentService.getAll();
		departmentService.getAll();
	}
	
	@Test
	public void testDelete(){
		int result = employeeService.deleteEmployee(12);
		System.out.println(result);
	}
	
	@Test
	public void testGetPage(){
		Page<Employee> page = employeeService.getPage(4);
		System.out.println(page.getTotalElements());
		System.out.println(page.getTotalPages());
		System.out.println(page.getContent());
	}
	
	
	@Test
	public void testLogin(){
		Employee employee = employeeService.login("FM12345", "123456");
		System.out.println(employee.getVisitedTimes());;
	}

	@Test
	public void testDataSource() throws SQLException {

		DataSource dataSource = (DataSource)ctx.getBean("dataSource");
		System.out.println(dataSource.getConnection());
	}

}
