package org.choongang.member.service;

import lombok.Builder;
import lombok.Data;
import org.choongang.member.entities.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import java.util.Collection;

@Data
@Builder
public class MemberInfo implements UserDetails {
    private String email;
    private String userId;
    private String password;
    private Member member;

    private Collection<? extends GrantedAuthority> authorities;

    //특정페이지 권한 인가
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override //아이디 - 이메일 or ID
    public String getUsername() {
        return StringUtils.hasText(email)? email : userId;
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

    //탈퇴시
    @Override
    public boolean isEnabled() {
        return true;
    }
}
