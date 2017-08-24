package com.atguigu.ems.daos;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.atguigu.ems.entities.Authority;
import com.atguigu.ems.entities.Department;


@Repository
public class AuthorityDao extends BaseDao<Authority> {
	
	/*@Autowired
	private SessionFactory sessionFactory;
	
	public Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	
	public Criteria getCriteria(){
		return getSession().createCriteria(Authority.class);
	}
	
	public List<Authority> getByNotNull(String propertyName){
		Criterion criterion = Restrictions.isNotNull(propertyName);
		Criteria criteria = getCriteria();
		criteria.add(criterion);
		
		return criteria.setCacheable(true).list();
	}
	
	public List<Authority> getByNull(String propertyName){
		Criterion criterion = Restrictions.isNull(propertyName);
		Criteria criteria = getCriteria();
		criteria.add(criterion);
		
		return criteria.setCacheable(true).list();
	}*/

}
