package io.abdellah.fyber.base;

public interface BasePresenter<V> {

  void attachView(V view);

  void detachView();
}
