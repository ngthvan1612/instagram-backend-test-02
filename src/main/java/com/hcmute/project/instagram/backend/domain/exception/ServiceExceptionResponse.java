package com.hcmute.project.instagram.backend.domain.exception;

import com.hcmute.project.instagram.backend.domain.base.ResponseBaseAbstract;

public class ServiceExceptionResponse extends ResponseBaseAbstract {
    public ServiceExceptionResponse() {
        super();
        this.setStatus("FAIL");
    }
}