package com.atguigu.ems.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atguigu.ems.daos.DepartmentDao;
import com.atguigu.ems.daos.RoleDao;
import com.atguigu.ems.entities.Department;
import com.atguigu.ems.entities.Role;


@Service 
public class DepartmentService extends BaseService<Department>{
       
/*	@Autowired
	private DepartmentDao departmentDao;
	
	@Transactional(readOnly=true)
	public List<Department> getAll(){
		return departmentDao.getAll();
	}*/
}
