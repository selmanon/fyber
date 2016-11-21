package io.abdellah.fyber.data.source.remote;

import io.abdellah.fyber.data.model.FyberOffers;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface FyberOfferAPIService {

  @GET("/feed/v1/offers.json?") Observable<Response<ResponseBody>> getFyberOffers(
      @Query("appid") String appid, @Query("format") String format, @Query("pub0") String pub0,
      @Query("timestamp") String timestamp, @Query("uid") String uid,
      @Query("hashkey") String hashkey);

  class Factory {
    public static FyberOfferAPIService create() {
      Retrofit retrofit = new Retrofit.Builder().baseUrl("http://api.fyber.com/")
          .addConverterFactory(GsonConverterFactory.create())
          .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
          .build();
      return retrofit.create(FyberOfferAPIService.class);
    }
  }
}
