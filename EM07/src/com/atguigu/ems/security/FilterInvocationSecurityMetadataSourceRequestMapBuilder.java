package com.atguigu.ems.security;

import java.util.Collection;
import java.util.LinkedHashMap;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.util.RequestMatcher;

public interface FilterInvocationSecurityMetadataSourceRequestMapBuilder {
	
	public LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> buildRequestMap();
	
}
