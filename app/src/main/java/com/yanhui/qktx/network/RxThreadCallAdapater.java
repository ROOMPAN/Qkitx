package com.yanhui.qktx.network;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import retrofit.Call;
import retrofit.CallAdapter;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.Scheduler;

/**
 * RxThreadCallAdapater简单封装
 */

public class RxThreadCallAdapater implements CallAdapter.Factory {
    RxJavaCallAdapterFactory rxFactory = RxJavaCallAdapterFactory.create();
    private Scheduler subscribeScheduler;
    private Scheduler observerScheduler;

    public RxThreadCallAdapater(Scheduler subscribeScheduler, Scheduler observerScheduler) {
        this.subscribeScheduler = subscribeScheduler;
        this.observerScheduler = observerScheduler;
    }


    @Override
    public CallAdapter<?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        CallAdapter<Observable<?>> callAdapter = (CallAdapter<Observable<?>>)
                rxFactory.get(returnType, annotations, retrofit);
        return callAdapter != null ? new ThreadCallAdapter(callAdapter) : null;
    }

    final class ThreadCallAdapter implements CallAdapter<Observable<?>> {
        CallAdapter<Observable<?>> delegateAdapter;

        ThreadCallAdapter(CallAdapter<Observable<?>> delegateAdapter) {
            this.delegateAdapter = delegateAdapter;
        }

        @Override
        public Type responseType() {
            return delegateAdapter.responseType();
        }

        @Override
        public <T> Observable<?> adapt(Call<T> call) {
            return delegateAdapter.adapt(call).subscribeOn(subscribeScheduler)
                    .observeOn(observerScheduler);
        }
    }
}