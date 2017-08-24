package com.atguigu.ems.actions;

import java.util.Map;

import org.apache.struts2.interceptor.RequestAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.atguigu.ems.entities.Role;
import com.atguigu.ems.services.AuthorityService;
import com.atguigu.ems.services.EmployeeService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

@Scope("prototype")
@Controller
public class RoleAction extends BaseAction<Role> {
      
	@Autowired
	private AuthorityService authorityService;
	
	
	public void prepareSave(){
		System.out.println("save:" + model);
		model = new Role();
	}
	
	public String save(){
		return input();
	}
	
	public String input(){
		//如果 parentAuthority是null的话就是父权限；如果parentAuthority不是null的话就是子权限
		this.request.put("parentAuthorities", authorityService.getByIsNull("parentAuthority"));
		this.request.put("subAuthorities", authorityService.getByIsNotNull("parentAuthority"));
	    return "input";
	}
	
//	private Map<String, Object> request;
//
//	@Override
//	public void setRequest(Map<String, Object> request) {
//        this.request = request;
//	}
//
//	@Override
//	public void prepare() throws Exception {}
//
//	private Role model;
//	
//	@Override
//	public Role getModel() {
//		return model;
//	}
	
}
