package io.abdellah.fyber.fyberform.presenter;

import com.google.gson.GsonBuilder;
import io.abdellah.fyber.FyberOfferAPIApplication;
import io.abdellah.fyber.R;
import io.abdellah.fyber.base.BasePresenter;
import io.abdellah.fyber.data.model.FyberOffers;
import io.abdellah.fyber.data.model.Offers;
import io.abdellah.fyber.data.source.remote.FyberOfferAPIService;
import io.abdellah.fyber.fyberform.view.FyberOfferAPIMvpView;
import io.abdellah.fyber.utilities.ApiParamsUtility;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import okhttp3.ResponseBody;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

public class FybeOfferAPIFormPresenter implements BasePresenter<FyberOfferAPIMvpView> {

  private static final String TAG = "FybeOfferAPIFormPresenter";

  // Signature header key
  private static final String SIGNATURE_HEADER_FIELD = "X-Sponsorpay-Response-Signature";

  private FyberOfferAPIMvpView fyberOfferAPIMvpView;
  private Subscription subscription;

  @Override public void attachView(FyberOfferAPIMvpView view) {
    this.fyberOfferAPIMvpView = view;
  }

  @Override public void detachView() {
    this.fyberOfferAPIMvpView = null;
    if (subscription != null) subscription.unsubscribe();
  }

  public void getFyberOfferAPI(String appid, String format, String pub0, String timestamp,
      String uid, String hashkey) {
    FyberOfferAPIApplication application =
        FyberOfferAPIApplication.get(fyberOfferAPIMvpView.getContext());
    final FyberOfferAPIService fyberOfferAPIService = application.getFyberOfferAPIService();
    subscription = fyberOfferAPIService.getFyberOffers(appid, format, pub0, timestamp, uid, hashkey)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(application.defaultSubscribeScheduler())
        .subscribe(new Observer<Response<ResponseBody>>() {
                     @Override public void onCompleted() {

                     }

                     @Override public void onError(Throwable e) {
                       if (e instanceof HttpException) {
                         fyberOfferAPIMvpView.processFyberOfferAPIFailed(((HttpException) e).message());
                       }
                     }

                     @Override public void onNext(Response<ResponseBody> fyberOffersResponse) {
                       String responseBody = null;

                       if (fyberOffersResponse.body() != null) {
                         try {
                           responseBody = fyberOffersResponse.body().string();
                         } catch (IOException e) {
                           e.printStackTrace();
                         }
                       }

                       if (fyberOffersResponse.code() == HttpURLConnection.HTTP_OK) {
                         if (!isValidSignature(fyberOffersResponse, responseBody)) {
                           fyberOfferAPIMvpView.processFyberOfferAPIFailed(fyberOfferAPIMvpView.getContext()
                               .getString(R.string.invalid_response_signature));
                         } else {
                           final FyberOffers offersData =
                               new GsonBuilder().create().fromJson(responseBody, FyberOffers.class);
                           fyberOfferAPIMvpView.navigateToListOffersActivity(
                               new ArrayList<Offers>(Arrays.asList(offersData.getOffers())));
                         }
                       } else {
                         fyberOfferAPIMvpView.processFyberOfferAPIFailed(fyberOffersResponse.message());
                       }
                     }
                   }

        );
  }

  private boolean isValidSignature(Response<ResponseBody> fyberOffersResponse,
      String responseBody) {
    String signatureHash = fyberOffersResponse.headers()
        .get(SIGNATURE_HEADER_FIELD);  // Security check for the signature
    String apiBodyResponseAndApi = responseBody.concat(ApiParamsUtility.API_KEY);
    String apiBodyResponseAndApiKeyHash =
        new String(Hex.encodeHex(DigestUtils.sha1(apiBodyResponseAndApi)));
    return signatureHash.equals(apiBodyResponseAndApiKeyHash);
  }
}
