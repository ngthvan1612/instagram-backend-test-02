package com.hcmute.project.instagram.backend.domain.aggregate.useraggregate.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hcmute.project.instagram.backend.domain.aggregate.orderaggregate.entities.Order;
import com.hcmute.project.instagram.backend.domain.aggregate.useraggregate.enums.UserRole;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "tbl_user")
public class User implements UserDetails {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  
  @Column(name = "createdAt")
  private Date createdAt = new Date();
  
  @Column(name = "lastUpdatedAt")
  private Date lastUpdatedAt = new Date();
  
  @Column(name = "deletedAt")
  private Date deletedAt;
  
  @Column(name = "username")
  private String username;
  
  @Column(name = "password")
  private String password;
  
  @Column(name = "age")
  private Integer age = 5;
  
  @Column(name = "address")
  private String address;
  
  @Column(name = "dateOfBirth")
  private Date dateOfBirth;
  
  @Column(name = "avatar")
  private String avatar;
  
  @Enumerated(EnumType.STRING)
  private UserRole role;
  
  @OneToMany(fetch = FetchType.EAGER)
  @JsonIgnore
  private Collection<Order> orders;
  
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(role.name()));
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
