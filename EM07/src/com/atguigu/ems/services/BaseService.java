package com.atguigu.ems.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.atguigu.ems.daos.BaseDao;
import com.atguigu.ems.orm.Page;
import com.atguigu.ems.orm.PropertyFilter;

public class BaseService<T> {
	
	//Spring4 提供的泛型注入.
	@Autowired
	protected BaseDao<T> dao;
	
	@Transactional(readOnly=true)
	public Page<T> getPage(int pageNo, Map<String, Object> params) {
		//1. 把 params 转为 PropertyFilter 的集合
		List<PropertyFilter> filters = PropertyFilter.parseParamsToPropertyFilters(params);
		
		//2. 调用 Dao 方法得到 Page 对象
		Page<T> page = dao.getPage(pageNo, filters);
		initializePageContent(page.getContent());
		
		//3. 返回 Page
		return page;
	}
	
	
	@Transactional
	public List<T> getAll(){
		List<T> list = dao.getAll();
		initializeList(list);
		return list;
	}
	
	protected void initializeList(List<T> list) {}
	
	@Transactional(readOnly = true)
	public Page<T> getPage(int pageNo) {
		Page<T> page = dao.getPage(pageNo);
		initializePageContent(page.getContent());
		return page;
	}
	
	protected void initializePageContent(List<T> content) {}
	
	@Transactional
	public void delete(Integer id){
		dao.delete(id);
	}
	
	
	@Transactional(readOnly = true)
	public T getByLoginName(String loginName) {
		return dao.getBy("loginName",loginName);
	}	
	
	@Transactional
	public void save(T entity){
		dao.saveOrUpdate(entity);
	}
	
	@Transactional(readOnly = true)
	public T get(Integer id) {
		T entity = dao.get(id);
		
		//在父类中是空的方法. 子类可以根据自己的情况来初始化当前实体类的属性
		initializeEntity(entity);
		
		return entity;
	}
	
	protected void initializeEntity(T entity) {}
	
	@Transactional(readOnly=true)
	public List<T> getByIsNotNull(String propertyName){
		return dao.getByNotNull(propertyName);
	}
	
	@Transactional(readOnly=true)
	public List<T> getByIsNull(String propertyName){
		return dao.getByNull(propertyName);
	}
	
	
	
	

}
