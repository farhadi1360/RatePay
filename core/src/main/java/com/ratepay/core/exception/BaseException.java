package com.ratepay.core.exception;

import java.rmi.RemoteException;

public class BaseException extends RemoteException {
    public String errorCode;
    public CoreExceptionTypes exceptionEnum;

    public BaseException(String errorCode) {
        super(errorCode);
        this.errorCode = errorCode;
    }

    public BaseException(CoreExceptionTypes exceptionEnum) {
        super(exceptionEnum.getCode());
        this.exceptionEnum = exceptionEnum;
        this.errorCode = exceptionEnum.getCode();
    }

    public BaseException() {
        super(CoreExceptionTypes.UNKNOWN_INTERNAL_ERROR.getCode());
        this.errorCode =CoreExceptionTypes.UNKNOWN_INTERNAL_ERROR.getCode();
    }

    public String getErrorCode() {
        return errorCode;
    }

    public CoreExceptionTypes getExceptionEnum() {
        return exceptionEnum;
    }
}
