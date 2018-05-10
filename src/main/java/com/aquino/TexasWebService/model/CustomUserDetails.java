/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aquino.TexasWebService.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author alex
 */
public class CustomUserDetails implements UserDetails {

	private static final long serialVersionUID = 1L;
	private Collection<? extends GrantedAuthority> authorities;
	private String password;
	private String username;
        private long userId;
        private boolean enabled;

	public CustomUserDetails(User user) {
                this.userId = user.getId();
		this.username = user.getUsername();
		this.password = user.getPassword();
		this.authorities = translate(user.getRoles());
                this.enabled = user.isEnabled();
	}

	private Collection<? extends GrantedAuthority> translate(List<Role> roles) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		for (Role role : roles) {
			String name = role.getName().toUpperCase();
			if (!name.startsWith("ROLE_")) {
				name = "ROLE_" + name;
			}
			authorities.add(new SimpleGrantedAuthority(name));
		}
		return authorities;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

    /**
     * @return the userId
     */
    public long getUserId() {
        return userId;
    }
	
}