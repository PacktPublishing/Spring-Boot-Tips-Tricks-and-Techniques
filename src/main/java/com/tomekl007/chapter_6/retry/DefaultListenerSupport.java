package com.tomekl007.chapter_6.retry;

import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.listener.RetryListenerSupport;

public class DefaultListenerSupport extends RetryListenerSupport {
    @Override
    public <T, E extends Throwable> void close(RetryContext context,
                                               RetryCallback<T, E> callback, Throwable throwable) {
        System.out.println("onClose");

        super.close(context, callback, throwable);
    }
 
    @Override
    public <T, E extends Throwable> void onError(RetryContext context,
      RetryCallback<T, E> callback, Throwable throwable) {
        System.out.println("onError");
        super.onError(context, callback, throwable);
    }
 
    @Override
    public <T, E extends Throwable> boolean open(RetryContext context,
      RetryCallback<T, E> callback) {
        System.out.println("onOpen");
        return super.open(context, callback);
    }
}