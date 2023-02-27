package com.hcmute.project.instagram.backend.domain.aggregate.orderaggregate.repositories;

import com.hcmute.project.instagram.backend.domain.aggregate.orderaggregate.entities.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {
  @Override
  @Query("SELECT CASE WHEN COUNT(u) > 0 THEN TRUE ELSE FALSE END FROM OrderDetail u WHERE u.id = :integer AND u.deletedAt is null")
  boolean existsById(@Param("integer") Integer integer);

  @Override
  @Query("SELECT u FROM OrderDetail u WHERE u.id = :integer AND u.deletedAt is null")
  Optional<OrderDetail> findById(@Param("integer") Integer integer);

  @Override
  @Query("SELECT u FROM OrderDetail u WHERE u.deletedAt is null")
  List<OrderDetail> findAll();
  
  
}