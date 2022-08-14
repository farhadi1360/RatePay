package com.ratepay.core.exception;

import lombok.Getter;

@Getter
public enum CoreExceptionTypes {
    TIMEOUT("1012", 408),
    NOT_FOUND_EXCEPTION("1011", 404),
    UNKNOWN_INTERNAL_ERROR("1000",422)

    ;
    CoreExceptionTypes(String code, int httpStatus) {
        this.code = code;
        this.httpStatus = httpStatus;
    }

    private String code;
    private int httpStatus;
}
