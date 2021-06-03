package com.testapplication.retrofit.errorAdapters;

import android.util.Log;

import com.testapplication.retrofit.exceptions.RetrofitException;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.HttpException;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public final class RxErrorHandlingCallAdapterFactory extends CallAdapter.Factory {

    private final RxJava2CallAdapterFactory original;

    private RxErrorHandlingCallAdapterFactory() {
        ExecutorService executorService = Executors.newCachedThreadPool();
        original = RxJava2CallAdapterFactory.createWithScheduler(Schedulers.from(executorService));//.createAsync();
    }

    public static CallAdapter.Factory create() {
        return new RxErrorHandlingCallAdapterFactory();
    }

    @Override
    public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        return new RxCallAdapterWrapper(retrofit, original.get(returnType, annotations, retrofit));
    }

    private static class RxCallAdapterWrapper<R> implements CallAdapter<R, Object> {
        private final Retrofit retrofit;
        private final CallAdapter<R, Object> wrapped;

        public RxCallAdapterWrapper(Retrofit retrofit, CallAdapter<R, Object> wrapped) {
            this.retrofit = retrofit;
            this.wrapped = wrapped;
        }

        @Override
        public Type responseType() {
            return wrapped.responseType();
        }

        @androidx.annotation.NonNull
        @NonNull
        @Override
        public Object adapt(@androidx.annotation.NonNull Call<R> call) {
            Object result = wrapped.adapt(call);

            if (result instanceof Single) {
                return ((Single) result).onErrorResumeNext(new Function<Throwable, SingleSource<R>>() {
                    @Override
                    public SingleSource<R> apply(@NonNull Throwable throwable) throws Exception {
                        return Single.error(asRetrofitException(throwable));
                    }
                });
            }
            if (result instanceof Observable) {
                Log.e("RxAdapter TAG","We usually get here");
                return ((Observable<R>) result).onErrorResumeNext((Function<Throwable, ObservableSource<R>>) throwable -> {
                    return Observable.error(asRetrofitException(throwable));
                });
            }

            if (result instanceof Completable) {
                return ((Completable) result).onErrorResumeNext(throwable -> Completable.error(asRetrofitException(throwable)));
            }

            return result;
        }

        private RetrofitException asRetrofitException(Throwable throwable) {
            // We had non-200 http error
            if (throwable instanceof HttpException) {
                HttpException httpException = (HttpException) throwable;
                Response response = httpException.response();
                return RetrofitException.httpError(httpException);//httpError(response.raw().request().url().toString(), response, retrofit);
            }

            // A network error happened
            if (throwable instanceof IOException) {
                if (throwable instanceof SocketTimeoutException){
                    throwable.printStackTrace();
                    //return RetrofitException.unexpectedError((SocketTimeoutException) throwable);
                }
                return RetrofitException.networkError((IOException) throwable);
            }

            if(throwable instanceof SocketTimeoutException){
                throwable.printStackTrace();
                return new RetrofitException(throwable.getMessage(),throwable);
            }

            // We don't know what happened. We need to simply convert to an unknown error
            Log.e("Rx E", "Converting to an unexpected error");
            return RetrofitException.unexpectedError(throwable);
        }
    }

}
