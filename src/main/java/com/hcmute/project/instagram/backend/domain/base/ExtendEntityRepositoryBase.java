package com.hcmute.project.instagram.backend.domain.base;

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
import jakarta.persistence.criteria.*;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ExtendEntityRepositoryBase<E> {
    private static final String ERROR_INVALID_PARAMETER = "Tham số không hợp lệ";

    private static final Set<Class<?>> primitiveNumbers = Stream
            .of(int.class, long.class, float.class,
                    double.class, byte.class, short.class)
            .collect(Collectors.toSet());

    private static final Set<Class<?>> primitiveStrings = Stream
            .of(String.class, CharSequence.class)
            .collect(Collectors.toSet());

    private static final Set<Class<?>> primitiveDates = Stream
            .of(java.util.Date.class)
            .collect(Collectors.toSet());

    private static boolean isNumericType(Class<?> cls) {
        if (cls.isPrimitive()) {
            return primitiveNumbers.contains(cls);
        } else {
            return Number.class.isAssignableFrom(cls);
        }
    }

    private static boolean isStringType(Class<?> cls) {
        if (cls.isPrimitive()) {
            return primitiveStrings.contains(cls);
        } else {
            return CharSequence.class.isAssignableFrom(cls);
        }
    }

    private static boolean isDateType(Class<?> cls) {
        if (cls.isPrimitive()) {
            return primitiveDates.contains(cls);
        } else {
            return Date.class.isAssignableFrom(cls);
        }
    }

    private static Path retrievePath(Path root, String[] queryTokens, int length) {
        for (int i = 0; i < length; ++i) {
            root = root.get(queryTokens[i]);
        }
        return root;
    }

    protected List<E> dynamicSearchEntity(
            EntityManager entityManager,
            Map<String, String> queries,
            CriteriaBuilder criteriaBuilder,
            CriteriaQuery<E> query,
            Root<E> root,
            Field[] fields
    ) {
        List<Predicate> predicates = new ArrayList<>();

        for (Map.Entry<String, String> pair : queries.entrySet()) {
            String key = pair.getKey();
            String value = pair.getValue();

            System.out.println("value = " + value);

            String[] tokens = key.split("\\.");

            if (tokens.length < 2) {
                throw ServiceExceptionFactory.badRequest()
                        .addMessage(ERROR_INVALID_PARAMETER);
            }

            String columnName = tokens[0];
            String columnFilter = tokens[tokens.length - 1];

            Optional<Field> optionalField = Arrays.stream(fields).filter(f -> f.getName().equals(columnName)).findFirst();

            if (optionalField.isEmpty()) {
                System.out.println("not found field with name = " + columnName);
                throw ServiceExceptionFactory.badRequest()
                        .addMessage(ERROR_INVALID_PARAMETER);
            }

            Field field = optionalField.get();

            switch (columnFilter) {
                case "startsWith" -> {
                    if (!isStringType(field.getType()))
                        throw ServiceExceptionFactory.badRequest()
                                .addMessage(ERROR_INVALID_PARAMETER);

                    Expression<String> expressionQueryValue = criteriaBuilder.literal(value + "%");
                    predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(columnName)), criteriaBuilder.lower(expressionQueryValue)));
                }
                case "endsWith" -> {
                    if (!isStringType(field.getType()))
                        throw ServiceExceptionFactory.badRequest()
                                .addMessage(ERROR_INVALID_PARAMETER);

                    Expression<String> expressionQueryValue = criteriaBuilder.literal("%" + value);
                    predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(columnName)), criteriaBuilder.lower(expressionQueryValue)));
                }
                case "equal" -> {
                    if (isStringType(field.getType())) {
                        Expression<String> expressionQueryValue = criteriaBuilder.literal(value);
                        predicates.add(criteriaBuilder.equal(criteriaBuilder.lower(root.get(columnName)), criteriaBuilder.lower(expressionQueryValue)));
                    }
                    else if (isNumericType(field.getType())) {
                        predicates.add(criteriaBuilder.equal(root.get(columnName), value));
                    }
                    else if (isDateType(field.getType())) {
                        predicates.add(criteriaBuilder.equal(root.get(columnName), Date.parse(value)));
                    }
                    else if (field.getType().isEnum()) {
                        predicates.add(criteriaBuilder.equal(criteriaBuilder.lower(root.get(columnName)), value));
                    }
                    else {
                        Path path = retrievePath(root, tokens, tokens.length - 1);
                        predicates.add(criteriaBuilder.equal(path, value));
                    }
                }
                case "contains" -> {
                    if (!isStringType(field.getType()))
                        throw ServiceExceptionFactory.badRequest()
                                .addMessage(ERROR_INVALID_PARAMETER);

                    Expression<String> expressionQueryValue = criteriaBuilder.literal("%" + value + "%");
                    predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(columnName)), criteriaBuilder.lower(expressionQueryValue)));
                }
                case "min" -> {
                    if (isStringType(field.getType()))
                        throw ServiceExceptionFactory.badRequest()
                                .addMessage(ERROR_INVALID_PARAMETER);

                    if (isNumericType(field.getType())) {
                        predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(columnName), Double.parseDouble(value)));
                    }
                    else {
                        predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(columnName), new Date(value)));
                    }
                }
                case "max" -> {
                    if (isStringType(field.getType()))
                        throw ServiceExceptionFactory.badRequest()
                                .addMessage(ERROR_INVALID_PARAMETER);

                    if (isNumericType(field.getType())) {
                        predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(columnName), Double.parseDouble(value)));
                    }
                    else {
                        predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(columnName), new Date(value)));
                    }
                }
            }
        }

        predicates.add(root.get("deletedAt").isNull());
        query.where(criteriaBuilder.and(predicates.toArray(Predicate[]::new)));

        return entityManager.createQuery(query).getResultList();
    }
}