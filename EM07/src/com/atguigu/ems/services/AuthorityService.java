package com.atguigu.ems.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atguigu.ems.daos.AuthorityDao;
import com.atguigu.ems.entities.Authority;

@Service
public class AuthorityService extends BaseService<Authority>  {
    
/*	@Autowired
	private AuthorityDao authorityDao;
	
	
	@Transactional(readOnly=true)
	public List<Authority> getByIsNotNull(String propertyName){
		return authorityDao.getByNotNull(propertyName);
	}
	
	@Transactional(readOnly=true)
	public List<Authority> getByIsNull(String propertyName){
		return authorityDao.getByNull(propertyName);
	}*/
}
