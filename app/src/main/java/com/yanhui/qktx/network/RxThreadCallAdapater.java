package com.yanhui.qktx.network;

import android.support.annotation.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.Scheduler;

/**
 * RxThreadCallAdapater简单封装
 */

public class RxThreadCallAdapater extends CallAdapter.Factory {


    RxJavaCallAdapterFactory rxFactory = RxJavaCallAdapterFactory.create();
    private Scheduler subscribeScheduler;
    private Scheduler observerScheduler;

    public RxThreadCallAdapater(Scheduler subscribeScheduler, Scheduler observerScheduler) {
        this.subscribeScheduler = subscribeScheduler;
        this.observerScheduler = observerScheduler;
    }


    @Nullable
    @Override
    public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        CallAdapter<Observable<?>,Observable<?>> callAdapter = (CallAdapter<Observable<?>, Observable<?>>) rxFactory.get(returnType, annotations, retrofit);
        return callAdapter != null ? new ThreadCallAdapter(callAdapter) : null;
    }


    final class ThreadCallAdapter implements CallAdapter {
        CallAdapter<?, Observable<?>> delegateAdapter;

        ThreadCallAdapter(CallAdapter<Observable<?>, Observable<?>> delegateAdapter) {
            this.delegateAdapter = delegateAdapter;
        }


        @Override
        public Type responseType() {
            return delegateAdapter.responseType();
        }

        @Override
        public Object adapt(Call call) {
            return delegateAdapter.adapt(call)
                    .subscribeOn(subscribeScheduler)
                    .observeOn(observerScheduler);
        }

    }
}