package com.atguigu.ems.daos;


import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.atguigu.ems.entities.Employee;
import com.atguigu.ems.orm.Page;


@Repository
public class EmployeeDao extends BaseDao<Employee> {
	
/*	public List<Employee> getContent(Page<Employee> page){
		int firstResult = (page.getPageNo() - 1) * page.getPageSize();
		int maxResults = page.getPageSize();
		
		String hql = "FROM Employee e "
				+ "LEFT OUTER JOIN FETCH e.department "
				+ "LEFT OUTER JOIN FETCH e.roles ";
		return getSession().createQuery(hql).setFirstResult(firstResult).setMaxResults(maxResults).list();
	}*/
	
	
	
	/*@Autowired
	private SessionFactory sessionFactory;
	
	
	public Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	
	
	public void updateVisitedTimes(Integer id){
		String hql = "UPDATE Employee e "
				+ "SET e.visitedTimes = e.visitedTimes + 1 "
				+ "WHERE e.employeeId = :id";
		getSession().createQuery(hql).setParameter("id", id).executeUpdate();
	}
	
	public void batchSave(List<Employee> emps) {
		for(int i = 0; i < emps.size(); i++){
			getSession().save(emps.get(i));
			
			if((i + 1) % 50 == 0){
				getSession().flush();
				getSession().clear();
			}
		}
	}
	
	
	public List<Employee> getAll(){
		String hql = "FROM Employee e "
				+ "LEFT OUTER JOIN FETCH e.department "
				+ "LEFT OUTER JOIN FETCH e.roles ";
		return getSession().createQuery(hql).list();
	}
	
	
	
	public void save(Employee employee){
		getSession().saveOrUpdate(employee);
	}
	
	public Employee get(Integer id){
		return (Employee) getSession().get(Employee.class, id);
	}
	
	public Page getPage(int pageNo){
		Page<Employee> page = new Page<>();
		page.setPageNo(pageNo);
		
		long totalElements = getTotalElements();
		page.setTotalElements(totalElements);
		
		List<Employee> content = getContent(page);
		page.setContent(content);
		
		return page;
	}
	
	
	public List<Employee> getContent(Page<Employee> page){
		int firstResult = (page.getPageNo() - 1) * page.getPageSize();
		int maxResults = page.getPageSize();
		
		String hql = "FROM Employee e "
				+ "LEFT OUTER JOIN FETCH e.department "
				+ "LEFT OUTER JOIN FETCH e.roles ";
		return getSession().createQuery(hql).setFirstResult(firstResult).setMaxResults(maxResults).list();
	}
	

	
	public long getTotalElements(){
		String hql = "SELECT count(e.id) "
				     +"FROM Employee e";
		return (long) getSession().createQuery(hql).uniqueResult();
	}
	
	
	public Employee getByLoginName(String loginName){
		String hql = "FROM Employee e WHERE e.loginName = :loginName";
//		String hql = "FROM Employee e "
//				+ "LEFT OUTER JOIN FETCH e.roles r "
//				+ "LEFT OUTER JOIN FETCH r.authorities "
//				+ "WHERE e.loginName = :loginName ";
		Query query = getSession().createQuery(hql).setParameter("loginName", loginName);
		return (Employee) query.uniqueResult();
	}*/





	
}
