package com.ratepay.core.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class CreatingCoreException {
    private static MessageSource messageSource;
    @Autowired
    public CreatingCoreException(MessageSource messageSource){

        CreatingCoreException.messageSource = messageSource;
    }

    public static Result getExceptionSpecification(CoreExceptionTypes coreExceptionTypes){

        String code = coreExceptionTypes.getCode();
        Locale locale = LocaleContextHolder.getLocale();
        String message = messageSource.getMessage(code, null, locale);
        int statusCode = coreExceptionTypes.getHttpStatus();

        return new Result(code, message, statusCode);
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class Result {
        public String code;
        public String message;
        public int httpStatusCode;
    }
}
