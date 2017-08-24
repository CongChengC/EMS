package com.atguigu.ems.daos;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.metadata.ClassMetadata;
import org.springframework.beans.factory.annotation.Autowired;

import com.atguigu.ems.entities.Authority;
import com.atguigu.ems.entities.Role;
import com.atguigu.ems.orm.Page;
import com.atguigu.ems.orm.PropertyFilter;
import com.atguigu.ems.orm.PropertyFilter.MatchType;
import com.atguigu.ems.util.ReflectionUtils;

public class BaseDao<T> {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private Class<T> entityClass;
	
	public BaseDao() {
		entityClass = ReflectionUtils.getSuperGenericType(getClass());
	}
	
	public Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	
	public Page<T> getPage(int pageNo, List<PropertyFilter> filters) {
		//1. 把 PropertyFilter 的集合转为 Criterion 的集合. 
		//即一个 PropertyFilter 对应着一个查询条件.
		List<Criterion> criterions = parsePropertyFiltersToCriterions(filters);
		//2. 查询总的记录数
		Page<T> page = new Page<>();
		page.setPageNo(pageNo);
		
		long totalElements = getTotalElements(criterions);
		page.setTotalElements(totalElements);
		
		//3. 查询当前页面的 Content
		List<T> content = getContent(page, criterions);
		page.setContent(content);
		
		//4. 返回
		return page;
	}
	
	private List<T> getContent(Page<T> page, List<Criterion> criterions) {
		int firstResult = (page.getPageNo() - 1) * page.getPageSize();
		int maxResults = page.getPageSize();
		
		Criteria criteria = getCriteria();
		
		//3. 添加查询条件
		for(Criterion criterion: criterions){
			criteria.add(criterion);
		}
		
		return criteria.setFirstResult(firstResult).setMaxResults(maxResults).list();
	}

	private long getTotalElements(List<Criterion> criterions) {
		//1. 获取实体类的 id 的属性名
		String idName = getIdName();
		
		//2. 查询 id 的 count
		Projection projection = Projections.count(idName);
		Criteria criteria = getCriteria();
		
		//3. 添加查询条件
		for(Criterion criterion: criterions){
			criteria.add(criterion);
		}
		
		criteria.setProjection(projection);
		return (long) criteria.uniqueResult();
	}
	
	
	private List<Criterion> parsePropertyFiltersToCriterions(
			List<PropertyFilter> filters) {
		List<Criterion> criterions = new ArrayList<>();
		
		Criterion criterion = null;
		for(PropertyFilter filter: filters){
			String propertyName = filter.getPropertyName();
			Object propertyVal = filter.getPropertyVal();
			MatchType matchType = filter.getMatchType();
			Class propertyType = filter.getPropertyType();
			
			//把页面传入的查询条件的值转为目标类型
			propertyVal = ReflectionUtils.convertValue(propertyVal, propertyType);
			
			switch(matchType){
			case EQ:
				criterion = Restrictions.eq(propertyName, propertyVal);
				break;
			case GE:
				criterion = Restrictions.ge(propertyName, propertyVal);
				break;
			case GT:
				criterion = Restrictions.gt(propertyName, propertyVal);
				break;
			case LE:
				criterion = Restrictions.le(propertyName, propertyVal);
				break;
			case LIKE:
				criterion = Restrictions.like(propertyName, (String)propertyVal, MatchMode.ANYWHERE);
				break;
			case LT:
				criterion = Restrictions.lt(propertyName, propertyVal);
				break;
			}
			
			if(criterion != null){
				criterions.add(criterion);
			}
		}
		
		return criterions;
	}
	
	public void delete(Integer id){
		T entity = get(id);
		getSession().delete(entity);
	}
	
	public List<Role> getByIn(String propertyName, List<? extends Object> propertyVals){
		Criterion criterion = Restrictions.in(propertyName, propertyVals);
		Criteria criteria = getCriteria();
		criteria.add(criterion);
		
		return criteria.list();
	}
	
	public List<T> getByNotNull(String propertyName){
		Criterion criterion = Restrictions.isNotNull(propertyName);
		Criteria criteria = getCriteria();
		criteria.add(criterion);
		
		return criteria.setCacheable(true).list();
	}
	
	public List<T> getByNull(String propertyName){
		Criterion criterion = Restrictions.isNull(propertyName);
		Criteria criteria = getCriteria();
		criteria.add(criterion);
		
		return criteria.setCacheable(true).list();
	}
	
	
	public void saveOrUpdate(T entity){
		getSession().saveOrUpdate(entity);
	}
	
	public Criteria getCriteria(){
		return getSession().createCriteria(entityClass);
	}
	
	public void batchSave(List<T> entities) {
		for(int i = 0; i < entities.size(); i++){
			getSession().save(entities.get(i));
			
			if((i + 1) % 50 == 0){
				getSession().flush();
				getSession().clear();
			}
		}
	}
	
	public List<T> getAll(){
		return getCriteria().list();
	}
	
	public T get(Integer id){
		return (T) getSession().get(entityClass, id);
	}
	
	public Page getPage(int pageNo){
		Page<T> page = new Page<>();
		page.setPageNo(pageNo);
		
		long totalElements = getTotalElements();
		page.setTotalElements(totalElements);
		
		List<T> content = getContent(page);
		page.setContent(content);
		
		return page;
	}
	
	public List<T> getContent(Page<T> page){
		int firstResult = (page.getPageNo() - 1) * page.getPageSize();
		int maxResults = page.getPageSize();
		
		return getCriteria().setFirstResult(firstResult).setMaxResults(maxResults).list();
	}
	
	public long getTotalElements(){
		//1. 获取实体类的 id 的属性名
		String idName = getIdName();
		
		//2. 查询 id 的 count
		Projection projection = Projections.count(idName);
		Criteria criteria = getCriteria().setProjection(projection);
		
		return (long) criteria.uniqueResult();
	}
	
	//Hibernate 的 .cfg.xml 文件中包含了所有的 .hbm.xml 文件
	//即在 SessionFactory 对象创建的时候, 实际上可以得到所有的实体类的映射信息
	//所以应该可以得到实体类在映射文件中的所有映射细节. 包括 id 的 name 值
	private String getIdName() {
		//根据实体类的类型来获取 hibernate 中类的元数据
		ClassMetadata cmd = sessionFactory.getClassMetadata(entityClass);
		//再根据 ClassMetadata 得到映射文件中的细节. 例如 id 的名字
		return cmd.getIdentifierPropertyName();
	}
	
	public T getBy(String propertyName, Object propertyVal){
		Criteria criteria = getCriteria();
		
		Criterion criterion = Restrictions.eq(propertyName, propertyVal);
		criteria.add(criterion);
		
		return (T) criteria.uniqueResult();
	}
	
	

}
