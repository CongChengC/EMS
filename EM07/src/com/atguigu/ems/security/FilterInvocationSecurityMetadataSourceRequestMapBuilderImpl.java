package com.atguigu.ems.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.util.AntPathRequestMatcher;
import org.springframework.security.web.util.RequestMatcher;
import org.springframework.stereotype.Component;

import com.atguigu.ems.entities.Authority;
import com.atguigu.ems.entities.Resource;
import com.atguigu.ems.services.ResourceService;

@Component("filterInvocationSecurityMetadataSourceRequestMapBuilder")
public class FilterInvocationSecurityMetadataSourceRequestMapBuilderImpl
		implements FilterInvocationSecurityMetadataSourceRequestMapBuilder {

	@Autowired
	private ResourceService resourceService;
	
	@Override
	public LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> buildRequestMap() {
		LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> requestMap = 
				new LinkedHashMap<>();
		
		AntPathRequestMatcher key = null;
		Collection<ConfigAttribute> val = null;
		
		for(Resource resource: resourceService.getAll()){
			key = new AntPathRequestMatcher(resource.getUrl());
			val = new HashSet<>();
			
			for(Authority authority: resource.getAuthorities()){
				String name = authority.getName();
				val.add(new SecurityConfig(name));
			}
			
			requestMap.put(key, val);
		}
		
		return requestMap;
	}

}
