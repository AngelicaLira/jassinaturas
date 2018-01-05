package br.com.moip.jassinaturas.exceptions;

import br.com.moip.jassinaturas.communicators.ApiResponseError;

public class ApiResponseErrorException extends RuntimeException {

    private static final long serialVersionUID = -5037934610970014201L;

    private ApiResponseError apiResponseError;

    public ApiResponseErrorException() {
        super();
    }

    public ApiResponseErrorException(final ApiResponseError responseError) {
        this.apiResponseError = responseError;
    }

    public ApiResponseErrorException(final String message) {
        super(message);
    }

    public ApiResponseErrorException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ApiResponseErrorException(final String message, final Throwable cause, final boolean enableSuppression,
            final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ApiResponseErrorException(final Throwable cause) {
        super(cause);
    }

    public ApiResponseError getApiResponseError() {
        return apiResponseError;
    }

}
