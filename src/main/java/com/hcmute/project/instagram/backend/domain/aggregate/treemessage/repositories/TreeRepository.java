package com.hcmute.project.instagram.backend.domain.aggregate.treemessage.repositories;

import com.hcmute.project.instagram.backend.domain.aggregate.treemessage.entities.Tree;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TreeRepository extends JpaRepository<Tree, Integer> {
  @Override
  @Query("SELECT CASE WHEN COUNT(u) > 0 THEN TRUE ELSE FALSE END FROM Tree u WHERE u.id = :integer AND u.deletedAt is null")
  boolean existsById(@Param("integer") Integer integer);

  @Override
  @Query("SELECT u FROM Tree u WHERE u.id = :integer AND u.deletedAt is null")
  Optional<Tree> findById(@Param("integer") Integer integer);

  @Override
  @Query("SELECT u FROM Tree u WHERE u.deletedAt is null")
  List<Tree> findAll();
  
  
}