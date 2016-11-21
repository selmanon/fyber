package io.abdellah.fyber;

import android.app.Application;
import android.content.Context;
import io.abdellah.fyber.data.source.remote.FyberOfferAPIService;
import rx.Scheduler;
import rx.schedulers.Schedulers;

/**
 * Created by abdellahselassi on 10/28/16.
 */
public class FyberOfferAPIApplication extends Application {
  private FyberOfferAPIService fyberOfferAPIService;
  private Scheduler defaultSubscribeScheduler;

  public static FyberOfferAPIApplication get(Context context) {
    return (FyberOfferAPIApplication) context.getApplicationContext();
  }

  public FyberOfferAPIService getFyberOfferAPIService() {
    if (fyberOfferAPIService == null) {
      fyberOfferAPIService = FyberOfferAPIService.Factory.create();
    }
    return fyberOfferAPIService;
  }

  //For setting mocks during testing
  public void setFyberOfferAPIService(FyberOfferAPIService FyberOfferAPIService) {
    this.fyberOfferAPIService = FyberOfferAPIService;
  }

  public Scheduler defaultSubscribeScheduler() {
    if (defaultSubscribeScheduler == null) {
      defaultSubscribeScheduler = Schedulers.io();
    }
    return defaultSubscribeScheduler;
  }

  //Offer to change scheduler from tests
  public void setDefaultSubscribeScheduler(Scheduler scheduler) {
    this.defaultSubscribeScheduler = scheduler;
  }
}
