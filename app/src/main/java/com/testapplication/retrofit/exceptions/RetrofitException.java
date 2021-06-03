package com.testapplication.retrofit.exceptions;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.testapplication.model.baseModel.BaseModel;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.SocketTimeoutException;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.HttpException;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RetrofitException extends RuntimeException {

    private static Gson gson = new GsonBuilder()
            .setLenient()
            .serializeSpecialFloatingPointValues()
            .setPrettyPrinting()
            .create();

    public static RetrofitException httpError(String url, Response response, Retrofit retrofit) {
        String message = response.code() + " " + response.message();
        return new RetrofitException(message, url, response, Kind.HTTP, null, retrofit);
    }

    public static String prepareErrorMessage(Throwable throwable){
        try {
            RetrofitException httpException = (RetrofitException) throwable;
            String errorBody = (String) Objects.requireNonNull(httpException.getResponse().errorBody()).string();
            BaseModel errorModel = gson.fromJson(errorBody, BaseModel.class);
            return errorModel.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
            return throwable.getMessage();
        }
    }

    public static RetrofitException httpError(HttpException httpException) {
        String message = httpException.code() + " " + httpException.message();
        Response response = httpException.response();

        Log.e("","Converted to a HTTP Exception");
        return new RetrofitException(message, httpException,"",response,response.code());
        //return new RetrofitException(message, "", response, Kind.HTTP, httpException, null);
    }

    public static RetrofitException networkError(IOException exception) {
        return new RetrofitException(exception.getMessage(), null, null, Kind.NETWORK, exception, null);
    }

    public static RetrofitException unexpectedError(Throwable exception) {
        return new RetrofitException(exception.getMessage(), null, null, Kind.UNEXPECTED, exception, null);
    }

    /** Identifies the event kind which triggered a {@link RetrofitException}. */
    public enum Kind {
        /** An {@link IOException} occurred while communicating to the server. */
        NETWORK,
        /** A non-200 HTTP status code was received from the server. */
        HTTP,
        /**
         * An internal error occurred while attempting to execute a request. It is best practice to
         * re-throw this exception so your application crashes.
         */
        UNEXPECTED,
        /**
         * */
        SOCKET_TIMEOUT
    }

    private String url;
    private Response response;
    private Kind kind;
    private static Retrofit retrofit = null;
    private int code;

    /**
     * Should we be creating construcotrs for every exception?
     * I created this one, especially for {@link SocketTimeoutException}
     * */
    public RetrofitException(String message, Throwable cause){
        super(message, cause);
    }

    public RetrofitException(String message, Throwable cause, String url, Response response, int code) {
        super(message, cause);
        this.url = url;
        this.response = response;
        this.code = code;
    }

    RetrofitException(String message, String url, Response response, Kind kind, Throwable exception, Retrofit retrofit) {
        super(message, exception);
        this.url = url;
        this.response = response;
        this.kind = kind;
        this.retrofit = retrofit;
    }

    /** The request URL which produced the error. */
    public String getUrl() {
        return url;
    }

    /** Response object containing status code, headers, body, etc. */
    public Response getResponse() {
        return response;
    }

    /** The event kind which triggered this error. */
    public Kind getKind() {
        return kind;
    }

    /** The Retrofit this request was executed on */
    public Retrofit getRetrofit() {
        return retrofit;
    }

    public int getCode() {
        return code;
    }

    /**
     * HTTP response body converted to specified {@code type}. {@code null} if there is no
     * response.
     *
     * @throws IOException if unable to convert the body to the specified {@code type}.
     */
    public <T> T getErrorBodyAs(Class<T> type) {
        try{
            if (response == null /*|| response.errorBody() == null*/ || response.body() == null) {
                return null;
            }
            Converter<ResponseBody, T> converter = retrofit.responseBodyConverter(type, new Annotation[0]);
            if (response.errorBody() == null){
                Log.e("R E", "The error body is null");
                return converter.convert((ResponseBody) response.body());
            }
            return converter.convert(response.errorBody());
        }catch (Exception e){
            e.printStackTrace();
            return null;
            //throw new IllegalArgumentException(e.getMessage());
        }
    }

    public static RetrofitException asRetrofitException(Throwable throwable) {
        // We had non-200 http error
        if (throwable instanceof HttpException) {
            System.out.println("Instance of HttpException");
            //HttpException httpException = (HttpException) throwable;
            //Response response = httpException.response();
            return RetrofitException.httpError((HttpException) throwable);
            //return RetrofitException.httpError(response.raw().request().url().toString(), response, retrofit);
        }
        // A network error happened
        if (throwable instanceof IOException) {
            return RetrofitException.networkError((IOException) throwable);
        }

        if(throwable instanceof SocketTimeoutException){
            Log.e("Retrofit Exception", "Its a socket time out exception");
            SocketTimeoutException s = (SocketTimeoutException) throwable;

            return new RetrofitException(s.getMessage(), s);
        }

        // We don't know what happened. We need to simply convert to an unknown error

        return RetrofitException.unexpectedError(throwable);
    }

}
