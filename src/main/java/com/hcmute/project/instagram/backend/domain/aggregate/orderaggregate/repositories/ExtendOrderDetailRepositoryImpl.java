package com.hcmute.project.instagram.backend.domain.aggregate.orderaggregate.repositories;

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
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class ExtendOrderDetailRepositoryImpl extends ExtendEntityRepositoryBase<OrderDetail> implements ExtendOrderDetailRepository {
    @PersistenceContext
    private EntityManager entityManager;

    private final String ERROR_INVALID_PARAMETER = "Tham s??? kh??ng h???p l???";

    @Override
    public List<OrderDetail> searchOrderDetail(Map<String, String> queries) {
        CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<OrderDetail> criteriaQuery = criteriaBuilder.createQuery(OrderDetail.class);
        Root<OrderDetail> orderDetailRoot = criteriaQuery.from(OrderDetail.class);

        return this.dynamicSearchEntity(
                this.entityManager,
                queries,
                criteriaBuilder,
                criteriaQuery,
                orderDetailRoot,
                OrderDetail.class.getDeclaredFields()
        );
    }
}