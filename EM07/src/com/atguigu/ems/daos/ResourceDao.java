package com.atguigu.ems.daos;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.atguigu.ems.entities.Resource;

@Repository
public class ResourceDao extends BaseDao<Resource> {

/*	@Autowired
	private SessionFactory sessionFactory;
	
	public Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	
	public Criteria getCriteria(){
		return getSession().createCriteria(Resource.class);
	}
	
	public List<Resource> getAll(){
		return getCriteria().list();
	}*/
}
