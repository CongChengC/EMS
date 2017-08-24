package com.atguigu.ems.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atguigu.ems.daos.ResourceDao;
import com.atguigu.ems.entities.Resource;

@Service
public class ResourceService extends BaseService<Resource> {

/*	@Autowired
	private ResourceDao resourceDao;
	
	@Transactional(readOnly=true)
	public List<Resource> getAll(){
		return resourceDao.getAll(); 
	}*/
	
}
