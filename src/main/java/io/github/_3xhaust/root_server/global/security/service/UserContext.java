package io.github._3xhaust.root_server.global.security.service;

import io.github._3xhaust.root_server.domain.user.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UserContext implements UserDetails {
    private final Long userId;     // 우리가 필요했던 PK
    private final String email;    // 로그인 아이디(username)
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;

    // 생성자
    public UserContext(Long userId, String email, String password, Collection<? extends GrantedAuthority> authorities) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    public UserContext(User user, Collection<? extends GrantedAuthority> authorities){
        this.userId = user.getId();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.authorities = authorities;
    }

    public Long getUserId() { return userId; }

    // UserDetails 필수 구현 메서드들
    @Override public String getUsername() { return email; }
    @Override public String getPassword() { return password; }
    @Override public Collection<? extends GrantedAuthority> getAuthorities() { return authorities; }

    // 나머지 계정 잠김 관련은 전부 true 반환
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}