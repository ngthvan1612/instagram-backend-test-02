package com.hcmute.project.instagram.backend.domain.aggregate.productaggregate.repositories;

import com.hcmute.project.instagram.backend.*;
import com.hcmute.project.instagram.backend.controller.admin.*;
import com.hcmute.project.instagram.backend.controller.common.*;
import com.hcmute.project.instagram.backend.controller.exception.*;
import com.hcmute.project.instagram.backend.domain.aggregate.treemessage.dto.tree.*;
import com.hcmute.project.instagram.backend.domain.aggregate.treemessage.entities.*;
import com.hcmute.project.instagram.backend.domain.aggregate.treemessage.repositories.*;
import com.hcmute.project.instagram.backend.domain.aggregate.treemessage.services.*;
import com.hcmute.project.instagram.backend.domain.aggregate.treemessage.services.interfaces.*;
import com.hcmute.project.instagram.backend.domain.aggregate.useraggregate.dto.user.*;
import com.hcmute.project.instagram.backend.domain.aggregate.useraggregate.entities.*;
import com.hcmute.project.instagram.backend.domain.aggregate.useraggregate.enums.*;
import com.hcmute.project.instagram.backend.domain.aggregate.useraggregate.repositories.*;
import com.hcmute.project.instagram.backend.domain.aggregate.useraggregate.services.*;
import com.hcmute.project.instagram.backend.domain.aggregate.useraggregate.services.interfaces.*;
import com.hcmute.project.instagram.backend.domain.aggregate.productaggregate.dto.product.*;
import com.hcmute.project.instagram.backend.domain.aggregate.productaggregate.dto.productcategory.*;
import com.hcmute.project.instagram.backend.domain.aggregate.productaggregate.entities.*;
import com.hcmute.project.instagram.backend.domain.aggregate.productaggregate.repositories.*;
import com.hcmute.project.instagram.backend.domain.aggregate.productaggregate.services.*;
import com.hcmute.project.instagram.backend.domain.aggregate.productaggregate.services.interfaces.*;
import com.hcmute.project.instagram.backend.domain.aggregate.orderaggregate.dto.order.*;
import com.hcmute.project.instagram.backend.domain.aggregate.orderaggregate.dto.orderdetail.*;
import com.hcmute.project.instagram.backend.domain.aggregate.orderaggregate.entities.*;
import com.hcmute.project.instagram.backend.domain.aggregate.orderaggregate.repositories.*;
import com.hcmute.project.instagram.backend.domain.aggregate.orderaggregate.services.*;
import com.hcmute.project.instagram.backend.domain.aggregate.orderaggregate.services.interfaces.*;
import com.hcmute.project.instagram.backend.domain.base.*;
import com.hcmute.project.instagram.backend.domain.exception.*;
import com.hcmute.project.instagram.backend.infrastructure.aws.minio.*;
import com.hcmute.project.instagram.backend.jwt.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer>, ExtendProductCategoryRepository {
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