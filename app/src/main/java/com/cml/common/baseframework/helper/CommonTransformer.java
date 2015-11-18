package com.cml.common.baseframework.helper;

import rx.Observable;

/**
 * Created by cmlBeliever on 2015/11/18.
 */
public class CommonTransformer<T> implements Observable.Transformer<T, T> {

    private Observable.Transformer sourceTransformer;

    public CommonTransformer(Observable.Transformer sourceTransformer) {
        this.sourceTransformer = sourceTransformer;
    }

    @Override
    public Observable<T> call(Observable<T> source) {
        return source.compose(sourceTransformer);
    }
}
