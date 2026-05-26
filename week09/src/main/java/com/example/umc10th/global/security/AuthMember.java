package com.example.umc10th.global.security;

import com.example.umc10th.member.entity.Member;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class AuthMember implements UserDetails {

    private final Member member;
    private final List<GrantedAuthority> authorities;

    public AuthMember(Member member) {
        this.member = member;
        String role = (member.getRole() == null || member.getRole().isBlank())
                ? "ROLE_USER"
                : member.getRole();
        this.authorities = List.of(new SimpleGrantedAuthority(role));
    }

    public Member getMember() {
        return member;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return member.getEmail();
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
        return true;
    }
}

