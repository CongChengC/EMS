package com.atguigu.ems.actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.jasper.tagplugins.jstl.core.If;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ParameterAware;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.WebUtils;

import com.atguigu.ems.entities.Authority;
import com.atguigu.ems.entities.Employee;
import com.atguigu.ems.entities.Resource;
import com.atguigu.ems.entities.Role;
import com.atguigu.ems.orm.Page;
import com.atguigu.ems.security.EmsUserDetailsService.SecurityUser;
import com.atguigu.ems.services.DepartmentService;
import com.atguigu.ems.services.EmployeeService;
import com.atguigu.ems.services.RoleService;
import com.atguigu.ems.util.EmployeeCrieriaFormBean;
import com.atguigu.ems.util.Navigation;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.Result;


@Scope("prototype")
@Controller
public class EmployeeAction extends BaseAction<Employee>{

//	@Autowired
//	private EmployeeService employeeService;
	
	public EmployeeService getEmployeeService(){
		return (EmployeeService) service;
	}
	
	@Autowired
	private DepartmentService departmentService;
	
	@Autowired 
	private RoleService roleService;
	
	
	public String list3(){
		//获取查询条件对应的请求参数: 他们都是以 filter_ 开头的.
		//Spring 的 WebUtils 的 getParametersStartingWith 方法可以获取以指定前缀开头的请求参数
		//结果的 key 为去了前缀的请求参数的参数名, value 为参数值
		HttpServletRequest req = ServletActionContext.getRequest();
		Map<String, Object> params = WebUtils.getParametersStartingWith(req, "filter_");
		//把请求参数再反序列化为一个查询字符串
		String queryString = encodeParamsToQueryString(params);
		//把查询字符串再传回到页面上
		request.put("queryString", queryString);
		//调用 Service 方法完成分页操作
		page = getEmployeeService().getPage(pageNo, params);
		return "list3";
	}
	
	//请求参数再反序列化为一个查询字符串的方法
	private String encodeParamsToQueryString(Map<String, Object> params) {
		StringBuilder result = new StringBuilder();
		
		for(Map.Entry<String, Object> param: params.entrySet()){
			String key = param.getKey();
			Object val = param.getValue();
			
			if("".equals(val)){
				continue;
			}
			
			result.append("filter_").append(key).append("=").append(val).append("&");
		}
		
		if(result.length() > 0){
			result.replace(result.length() - 1, result.length(), "");
		}
		return result.toString();
	}
	
	
	public void prepareCriteriaInput(){
		EmployeeCrieriaFormBean bean = new EmployeeCrieriaFormBean();
		ActionContext.getContext().getValueStack().push(bean);
	}
	
	public String criteriaInput(){
		request.put("roles", roleService.getAll());
		request.put("departments", departmentService.getAll());
		return "criteriaInput";
	}
	
	/*//几乎所有的 Action 都需要来显示分页消息, 所以可以把 Page 作为成员变量.
	private Page<Employee> page;
	
	public Page<Employee> getPage(){
		return page;
	}
	
	//获取当前第几页, 也是一个 Action 级别的要求。 
	private int pageNo;
	
	
	public void setPageNo(String pageNo){
		this.pageNo = 1;
		try {
			this.pageNo = Integer.parseInt(pageNo);
		} catch (NumberFormatException e) {}
	}
	
	//每一个Action 实际上都会有接收id 的任务，所以id作为成员变量。
	private Integer id;
	
	public void setId(Integer id) {
		this.id = id;
	}*/
	
	
	
	    //导航菜单相关的属性
		private List<Navigation> navigations = null;
		
		public List<Navigation> getNavigations() {
			return navigations;
		}
		
		public String navigate(){
			String contextPath = ServletActionContext.getServletContext().getContextPath();
			navigations = new ArrayList<>();
			
			Navigation top = new Navigation();
			top.setId(Integer.MAX_VALUE);
			top.setText("尚硅谷智能网络办公");
			navigations.add(top);
			
			//根据当前用户的权限情况来构建动态的导航菜单.
			Employee employee = (Employee) session.get("employee");
			
			Map<Integer, Navigation> parentNavigatoins = new HashMap<>();
			//1. 获取用户的所有的 Role
			for(Role role: employee.getRoles()){
				//2. 再获取用户的所有的 Authority. 注意: mainResource 属性不为 null 的, 才需要作为导航菜单
				for(Authority authority: role.getAuthorities()){
					Resource resource = authority.getMainResource();
					if(resource == null){
						continue;
					}
					
					Navigation navigation = new Navigation();
					navigation.setId(authority.getId());
					navigation.setText(authority.getDisplayName());
					navigation.setUrl(contextPath + resource.getUrl());
					
					//3. 还需要获取权限的父权限, 来生成 Navigation. 再把具体的子权限作为父权限的 children
					Authority parentAuthority = authority.getParentAuthority();
					Navigation parentNavigation = parentNavigatoins.get(parentAuthority.getId());
					if(parentNavigation == null){
						parentNavigation = new Navigation();
						parentNavigation.setId(parentAuthority.getId());
						parentNavigation.setText(parentAuthority.getDisplayName());
						
						top.getChildren().add(parentNavigation);
						parentNavigatoins.put(parentAuthority.getId(), parentNavigation);
					}
					
					parentNavigation.getChildren().add(navigation);
				}
			}
			
			Navigation logout = new Navigation();
			logout.setId(Integer.MAX_VALUE - 1);
			logout.setText("登出");
			logout.setUrl(contextPath + "/security-logout");
			top.getChildren().add(logout);
			
			return "navigation-success";
		}
	
	   /* //输入流
		private InputStream inputStream;
		
		//文件下载的类型
		private String contentType;
		//下载的文件的长度
		private long contentLength;
		//下载的文件的文件名
		private String fileName;
		
		public InputStream getInputStream() {
			return inputStream;
		}
		public String getContentType() {
			return contentType;
		}
		public long getContentLength() {
			return contentLength;
		}
		public String getContentDisposition () {
			return "attachment;filename=" + fileName;
		}
		
		private File file;
		
		public void setFile(File file) {
			this.file = file;
		}*/
		
		public String upload() throws InvalidFormatException, IOException{
			//返回的结果类似于如下: [{"2","4"},{"5","3"}]
			List<String[]> positions = getEmployeeService().upload(file);
			//所有有错误. 
			if(positions != null && positions.size() > 0){
				for(String [] args: positions){
					String error = getText("errors.emp.upload", args);
					addActionError(error);
				}
			}else{
				addActionError(getText("message.emp.upload"));
			}
			return "input";
		}
		
		
		//下载模板
		public String downloadUploadExcelTemplate() throws IOException{
			inputStream = ServletActionContext.getServletContext().getResourceAsStream("/files/employees.xls"); 
			contentType = "application/vnd.ms-excel";
			contentLength = inputStream.available();
			fileName = "employees.xls";
			return "download-success";
		}
		
		public String download() throws IOException{
			//确定文件下载相关的成员变量的值
			String tempFileName = System.currentTimeMillis() + ".xls";
			tempFileName = ServletActionContext.getServletContext().getRealPath("/files/" + tempFileName);
			
			//把文件放在请求域中
			request.put("tempFileName", tempFileName);
			
			getEmployeeService().downloadFile(tempFileName);
			inputStream = new FileInputStream(tempFileName); 
			contentType = "application/vnd.ms-excel";
			contentLength = inputStream.available();
			fileName = "employees.xls";
			return "download-success";
		}
	
	
	public void validateLoginName() throws IOException{
		String result = "1";
		String loginName = this.params.get("loginName")[0];
		Employee employee = getEmployeeService().getByLoginName(loginName);
		if(employee !=null){
			result = "0";
		}
		ServletActionContext.getResponse().getWriter().print(result);
	}
	
	//一定要写这个，要不然验证过不了，因为在值栈里面没有loginName属性。 
	public void prepareSave(){
		if(id == null){
			model = new Employee();
		}else{
			model = getEmployeeService().get(id);
			model.getRoles().clear();
		}
	}
	
	public String save(){
		getEmployeeService().save(model);
		return "success";
	}
	
	public void prepareInput(){
		if(id !=null ){
			model = getEmployeeService().get(id);
		}
	}
	
	public String input(){
		request.put("roles", roleService.getAll());
		request.put("departments", departmentService.getAll());
		return "emp-input";
	}
	
	
	public String delete(){
		int result = getEmployeeService().deleteEmployee(id);
		if(result == 0){
			//不成功返回一个结果，现在先不管它，让它返回一个null吧
			return null;
		}else{
			//成功就返回一个page
			return list2();
		}
	}
	
	
	public String list2(){
		page = getEmployeeService().getPage(pageNo);
		return "list2";
	}
	
	public String list(){
		page = getEmployeeService().getPage(pageNo);
		return "list";
	}
	
	public void prepareLogin(){
		model = new Employee();
	}
	
	public String login() throws Exception{
		Object obj = this.request.get("exception");
		if(obj != null && obj instanceof Exception){
			throw (Exception)obj;
		}
		
		//获取登录信息 ? 
		Object object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		SecurityUser su = (SecurityUser) object;
		
		Employee employee = su.getEmployee();
		this.session.put("employee", employee);
		
		getEmployeeService().updateVisitedTimes(employee.getEmployeeId());
		
		return "success";
	}
	
/*	public String login(){
		String loginName = model.getLoginName();
		String password = model.getPassword();
		Employee employee = employeeService.login(loginName, password);
		this.session.put("employee", employee);
		return "success";
	}*/
	
	private Employee model;

	@Override
	public void prepare() throws Exception {}

	@Override
	public Employee getModel() {
		return model;
	}

	private Map<String, Object> session = null;
	
	@Override
	public void setSession(Map<String, Object> arg0) {
		this.session = arg0;
	}

	private Map<String,Object> request;
	
	@Override
	public void setRequest(Map<String, Object> arg0) {
        this.request = arg0;		
	}

	private Map<String, String[]> params = null;
		
	@Override
	public void setParameters(Map<String, String[]> arg0) {
         this.params = arg0;		
	}
	
}
