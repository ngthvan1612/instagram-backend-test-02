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
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.http.HttpStatus;
import java.util.*;

public abstract class ResponseBaseAbstract {
  @JsonIgnore
  private HttpStatus statusCode;
  
  private String status;
  private Object data;
  private ArrayList<String> messages;

  public ResponseBaseAbstract() {
    this.statusCode = HttpStatus.OK;
    this.status = "OK";
    this.data = null;
    this.messages = new ArrayList<>();
  }

  public ResponseBaseAbstract(HttpStatus statusCode) {
    this.statusCode = statusCode;
  }

  public void setStatusCode(HttpStatus statusCode) {
    this.statusCode = statusCode;
  }

  public HttpStatus getStatusCode() {
    return this.statusCode;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getStatus() {
    return this.status;
  }

  public void setData(Object data) {
    this.data = data;
  }

  public Object getData() {
    return this.data;
  }

  public void setMessages(ArrayList<String> messages) {
    this.messages = messages;
  }

  public ArrayList<String> getMessages() {
    return this.messages;
  }

  public void addMessage(String message) {
    this.messages.add(message);
  }
}
