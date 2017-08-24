package com.atguigu.ems.daos;


import java.util.List;






import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.atguigu.ems.entities.Department;
import com.atguigu.ems.entities.Employee;


@Repository
public class DepartmentDao extends BaseDao<Department>{
	
	/*@Autowired
	private SessionFactory sessionFactory;
	
	public Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	
	public Criteria getCriteria(){
		return getSession().createCriteria(Department.class);
	}
	
	public Department getBy(String propertyName, Object propertyVal){
		//1. 构造基于 QBC 的查询条件
		Criterion criterion = Restrictions.eq(propertyName, propertyVal);
		//2. 添加查询条件
		Criteria criteria = getCriteria();
		criteria.add(criterion);
		
		//3. 执行查询, 返回结果
		return (Department) criteria.uniqueResult();
	}
	
	public List<Department> getAll(){
		return getCriteria().setCacheable(true).list();
//		String hql = "FROM Department d";
//		return getSession().createQuery(hql).list();
	}
	
	public Department getByManager(Employee manager){
		String hql = "FROM Department d "
				     +"WHERE d.manager = :manager";
		Query query = getSession().createQuery(hql).setParameter("manager", manager);
		return (Department) query.uniqueResult();
	}*/
	
	

}
