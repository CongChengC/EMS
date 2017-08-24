package com.atguigu.ems.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.atguigu.ems.entities.Authority;
import com.atguigu.ems.entities.Employee;
import com.atguigu.ems.entities.Role;
import com.atguigu.ems.services.EmployeeService;

@Component
public class EmsUserDetailsService implements UserDetailsService {

	@Autowired
	private EmployeeService employeeService;
	
	@Override
	@Transactional(readOnly=true)
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		Employee employee = employeeService.getByLoginName(username);
		
		if(employee == null){
			throw new UsernameNotFoundException(username);
		}
		
		//初始化 Employee 的 roles, 以及 roles 的 authorities
		for(Role role: employee.getRoles()){
			for(Authority authority: role.getAuthorities()){
				Hibernate.initialize(authority.getMainResource());
				Hibernate.initialize(authority.getParentAuthority());
			}
		}
		
		String password = employee.getPassword();
		boolean enabled = employee.getEnabled() == 1;
		
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;
		
		Collection<GrantedAuthority> authorities = new HashSet<>();
		for(Role role: employee.getRoles()){
			for(Authority authority: role.getAuthorities()){
				String name = authority.getName();
				GrantedAuthorityImpl impl = new GrantedAuthorityImpl(name);
				authorities.add(impl);
			}
		}
		
		User user = new SecurityUser(username, password, enabled, 
				accountNonExpired, credentialsNonExpired, accountNonLocked, authorities,employee);
		return user;
	}
	
	public class SecurityUser extends User{
		
		private Employee employee;
		
		public SecurityUser(String username, String password, boolean enabled,
				boolean accountNonExpired, boolean credentialsNonExpired,
				boolean accountNonLocked,
				Collection<? extends GrantedAuthority> authorities,Employee employee) {
			super(username, password, enabled, accountNonExpired, credentialsNonExpired,
					accountNonLocked, authorities);
			this.employee = employee;
		}
	
		public Employee getEmployee() {
			return employee;
		}
		
	}
	

}
