package com.atguigu.ems.security;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.security.web.access.intercept.DefaultFilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.stereotype.Component;

@Component
public class EmsSecurityBeanPostProcessor implements BeanPostProcessor {

	private FilterSecurityInterceptor interceptor;
	private DefaultFilterInvocationSecurityMetadataSource metadataSource;
	private boolean isSetter = false;
	
	@Override
	public Object postProcessAfterInitialization(Object arg0, String arg1)
			throws BeansException {
		// 1. 获取 FilterSecurityInterceptor 对象
		if(arg0 instanceof FilterSecurityInterceptor){
			this.interceptor = (FilterSecurityInterceptor) arg0;
		}
		// 2. 获取 DefaultFilterInvocationSecurityMetadataSource 对象
		if(arg1.equals("filterInvocationSecurityMetadataSource")){
			this.metadataSource = (DefaultFilterInvocationSecurityMetadataSource) arg0;
		}
		// 3. 若上述的两个对象都已经被初始化完成, 则完成属性的替换
		if(this.interceptor != null
				&& this.metadataSource != null
				&& !isSetter){
			this.interceptor.setSecurityMetadataSource(metadataSource);
			isSetter = true;
		}
		
		
		return arg0;
	}

	@Override
	public Object postProcessBeforeInitialization(Object arg0, String arg1)
			throws BeansException {
		return arg0;
	}

}
