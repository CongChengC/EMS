package com.atguigu.ems.actions;

import java.io.File;
import java.io.InputStream;
import java.util.Map;

import org.apache.struts2.interceptor.ParameterAware;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;

import com.atguigu.ems.entities.Employee;
import com.atguigu.ems.orm.Page;
import com.atguigu.ems.services.BaseService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

public class BaseAction<T> extends ActionSupport implements ModelDriven<T>,
		Preparable, SessionAware, RequestAware, ParameterAware {

	@Autowired
	protected BaseService<T> service;

	// 输入流
	protected InputStream inputStream;

	// 文件下载的类型
	protected String contentType;
	// 下载的文件的长度
	protected long contentLength;
	// 下载的文件的文件名
	protected String fileName;

	public InputStream getInputStream() {
		return inputStream;
	}

	public String getContentType() {
		return contentType;
	}

	public long getContentLength() {
		return contentLength;
	}

	public String getContentDisposition() {
		return "attachment;filename=" + fileName;
	}

	protected File file;

	public void setFile(File file) {
		this.file = file;
	}

	// 几乎所有的 Action 都需要来显示分页消息, 所以可以把 Page 作为成员变量.
	protected Page<Employee> page;

	public Page<Employee> getPage() {
		return page;
	}

	// 获取当前第几页, 也是一个 Action 级别的要求。
	protected int pageNo;

	public void setPageNo(String pageNo) {
		this.pageNo = 1;
		try {
			this.pageNo = Integer.parseInt(pageNo);
		} catch (NumberFormatException e) {
		}
	}

	// 每一个Action 实际上都会有接收id 的任务，所以id作为成员变量。
	protected Integer id;

	public void setId(Integer id) {
		this.id = id;
	}

	protected T model;

	@Override
	public void prepare() throws Exception {
	}

	@Override
	public T getModel() {
		return model;
	}

	protected Map<String, Object> session = null;

	@Override
	public void setSession(Map<String, Object> arg0) {
		this.session = arg0;
	}

	protected Map<String, Object> request;

	@Override
	public void setRequest(Map<String, Object> arg0) {
		this.request = arg0;
	}

	protected Map<String, String[]> params = null;

	@Override
	public void setParameters(Map<String, String[]> arg0) {
		this.params = arg0;
	}

}
