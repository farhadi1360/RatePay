package com.ratepay.core.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The custom exception to throw all the general exceptions into the exception handling interceptor
 */
@Getter
@AllArgsConstructor
public class GeneralException extends RuntimeException{
    /**
     * error code
     */
    private final String code;

    /**
     * cause error
     */
    private final String message;

    /**
     * Is fielded if there is more details for error
     */
    private final String details;

    /**
     * HTTP status code
     */
    private final int httpStatusCode;
}
