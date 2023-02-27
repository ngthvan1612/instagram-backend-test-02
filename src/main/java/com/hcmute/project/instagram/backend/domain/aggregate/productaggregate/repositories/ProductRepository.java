package com.hcmute.project.instagram.backend.domain.aggregate.productaggregate.repositories;

import com.hcmute.project.instagram.backend.domain.aggregate.productaggregate.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {
  @Override
  @Query("SELECT CASE WHEN COUNT(u) > 0 THEN TRUE ELSE FALSE END FROM Product u WHERE u.id = :integer AND u.deletedAt is null")
  boolean existsById(@Param("integer") Integer integer);

  @Override
  @Query("SELECT u FROM Product u WHERE u.id = :integer AND u.deletedAt is null")
  Optional<Product> findById(@Param("integer") Integer integer);

  @Override
  @Query("SELECT u FROM Product u WHERE u.deletedAt is null")
  List<Product> findAll();
  
  
}