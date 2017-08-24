package com.atguigu.ems.daos;

import java.util.List;






import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.atguigu.ems.entities.Department;
import com.atguigu.ems.entities.Role;


@Repository
public class RoleDao extends BaseDao<Role>{
       
/*	@Autowired
	private SessionFactory sesionFactory;
	
	public Session getSession(){
		return sesionFactory.getCurrentSession();
	}
	
	public Criteria getCriteria(){
		return getSession().createCriteria(Role.class);
	}
	
	public List<Role> getByIn(String propertyName, List<? extends Object> propertyVals){
		Criterion criterion = Restrictions.in(propertyName, propertyVals);
		Criteria criteria = getCriteria();
		criteria.add(criterion);
		
		return criteria.list();
	}
	
	public List<Role> getAll(){
		return getCriteria().setCacheable(true).list();
//		String hql = "FROM Role r";
//		return getSession().createQuery(hql).list();
	}*/
	
}
