package com.hcmute.project.instagram.backend.domain.aggregate.useraggregate.repositories;

import com.hcmute.project.instagram.backend.domain.aggregate.useraggregate.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
  @Override
  @Query("SELECT CASE WHEN COUNT(u) > 0 THEN TRUE ELSE FALSE END FROM User u WHERE u.id = :integer AND u.deletedAt is null")
  boolean existsById(@Param("integer") Integer integer);

  @Override
  @Query("SELECT u FROM User u WHERE u.id = :integer AND u.deletedAt is null")
  Optional<User> findById(@Param("integer") Integer integer);

  @Override
  @Query("SELECT u FROM User u WHERE u.deletedAt is null")
  List<User> findAll();
  
  @Query("SELECT u FROM User u WHERE u.username = :username ")
  User getUserByUsername(@Param("username") String username);
  
  
  @Query("SELECT CASE WHEN COUNT(u) > 0 THEN TRUE ELSE FALSE END FROM User u WHERE u.username = :username ")
  boolean existsByUsername(@Param("username") String username);

  @Query("SELECT CASE WHEN COUNT(u) > 0 THEN TRUE ELSE FALSE END FROM User u WHERE u.username = :username AND u.id <> :id ")
  boolean existsByUsernameExceptId(@Param("username") String username, @Param("id") Integer id);
  
}