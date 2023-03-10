package com.hcmute.project.instagram.backend.domain.exception;

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
import org.springframework.http.HttpStatus;

public class ServiceExceptionFactory {
    public ServiceExceptionFactory() {

    }

    /**
     * L???i x???y ra khi kh??ng t??m th???y t??i nguy??n y??u c???u
     * @return ServiceExceptionBase
     */
    public static ServiceExceptionBase notFound() {
      return new ServiceExceptionBase(HttpStatus.NOT_FOUND);
    }

    /**
     * L???i x???y ra khi tr??ng d??? li???u
     * @return ServiceExceptionBase
     */
    public static ServiceExceptionBase duplicate() {
      return new ServiceExceptionBase(HttpStatus.CONFLICT);
    }

    /**
     * L???i kh??ng x??c ?????nh (l???i thu???c v??? ph??a user)
     * @return ServiceExceptionBase
     */
    public static ServiceExceptionBase badRequest() {
      return new ServiceExceptionBase(HttpStatus.BAD_REQUEST);
    }

    /**
     * Ch??a ????ng nh???p
     * @return ServiceExceptionBase
     */
    public static ServiceExceptionBase unauthorized() {
      return new ServiceExceptionBase(HttpStatus.UNAUTHORIZED);
    }

    /**
     * ???? ????ng nh???p nh??ng KH??NG ????? quy???n truy c???p v??o t??i nguy??n
     * @return ServiceExceptionBase
     */
    public static ServiceExceptionBase forbidden() {
      return new ServiceExceptionBase(HttpStatus.FORBIDDEN);
    }
}