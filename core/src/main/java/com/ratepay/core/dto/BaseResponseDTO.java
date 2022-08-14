package com.ratepay.core.dto;

public class BaseResponseDTO<T> {
    private int code;
    private String message;
    private T data;

    public BaseResponseDTO() {}

    public static <T> BaseResponseDTO<T> ok(T data) {
        BaseResponseDTO<T> response = new BaseResponseDTO<>();
        response.data = data;
        response.code = 1;
        response.message = "OK";
        return response;
    }
    public static  BaseResponseDTO error(int code, String message) {
        BaseResponseDTO response = new BaseResponseDTO<>();
        response.data = null;
        response.code = code;
        response.message = message;
        return response;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}
