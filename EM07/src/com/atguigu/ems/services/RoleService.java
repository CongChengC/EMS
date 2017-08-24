package com.atguigu.ems.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atguigu.ems.daos.RoleDao;
import com.atguigu.ems.entities.Role;


@Service 
public class RoleService extends BaseService<Role> {
       
/*	@Autowired
	private RoleDao roleDao;
	
	@Transactional(readOnly=true)
	public List<Role> getAll(){
		return roleDao.getAll();
	}*/
}
