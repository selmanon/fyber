package io.abdellah.fyber.fyberoffers.presenter;

import io.abdellah.fyber.R;
import io.abdellah.fyber.base.BasePresenter;
import io.abdellah.fyber.data.model.Offers;
import io.abdellah.fyber.fyberoffers.view.OfferMvpView;
import java.util.ArrayList;
import rx.Subscription;

/**
 * Created by abdellahselassi on 11/16/16.
 */

public class OfferPresenter implements BasePresenter<OfferMvpView> {
  private static final String TAG = "FybeOfferAPIFormPresenter";

  private OfferMvpView offerMvpView;
  private Subscription subscription;

  @Override public void attachView(OfferMvpView view) {
    this.offerMvpView = view;
  }

  @Override public void detachView() {
    this.offerMvpView = null;
    if (subscription != null) subscription.unsubscribe();
  }

  public void showFyberOfferAPI(ArrayList<Offers> offers) {
    if (offers.size() > 0) {
      offerMvpView.showOffers(offers);
    } else {
      offerMvpView.showMessage(R.string.no_offers);
    }
  }
}
