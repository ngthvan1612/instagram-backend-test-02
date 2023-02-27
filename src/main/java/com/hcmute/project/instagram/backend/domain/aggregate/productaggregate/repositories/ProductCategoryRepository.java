package com.hcmute.project.instagram.backend.domain.aggregate.productaggregate.repositories;

import com.hcmute.project.instagram.backend.domain.aggregate.productaggregate.entities.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {
  @Override
  @Query("SELECT CASE WHEN COUNT(u) > 0 THEN TRUE ELSE FALSE END FROM ProductCategory u WHERE u.id = :integer AND u.deletedAt is null")
  boolean existsById(@Param("integer") Integer integer);

  @Override
  @Query("SELECT u FROM ProductCategory u WHERE u.id = :integer AND u.deletedAt is null")
  Optional<ProductCategory> findById(@Param("integer") Integer integer);

  @Override
  @Query("SELECT u FROM ProductCategory u WHERE u.deletedAt is null")
  List<ProductCategory> findAll();
  
  @Query("SELECT u FROM ProductCategory u WHERE u.categoryName = :categoryName ")
  ProductCategory getProductCategoryByCategoryName(@Param("categoryName") String categoryName);
  
  
  @Query("SELECT CASE WHEN COUNT(u) > 0 THEN TRUE ELSE FALSE END FROM ProductCategory u WHERE u.categoryName = :categoryName ")
  boolean existsByCategoryName(@Param("categoryName") String categoryName);

  @Query("SELECT CASE WHEN COUNT(u) > 0 THEN TRUE ELSE FALSE END FROM ProductCategory u WHERE u.categoryName = :categoryName AND u.id <> :id ")
  boolean existsByCategoryNameExceptId(@Param("categoryName") String categoryName, @Param("id") Integer id);
  
}