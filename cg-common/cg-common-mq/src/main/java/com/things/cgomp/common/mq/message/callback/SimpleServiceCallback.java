package com.things.cgomp.common.mq.message.callback;


public interface SimpleServiceCallback<T> extends CallBack {


    SimpleServiceCallback<Void> EMPTY = new SimpleServiceCallback<Void>() {
        @Override
        public void onSuccess(Void msg) {

        }

        @Override
        public void onError(Throwable e) {

        }
    };

    void onSuccess(T msg);

    void onError(Throwable e);

}
