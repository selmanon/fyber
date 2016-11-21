package io.abdellah.fyber.fyberform.view;

import io.abdellah.fyber.base.BaseMvpView;
import io.abdellah.fyber.data.model.Offers;
import java.util.ArrayList;
import java.util.List;

public interface FyberOfferAPIMvpView extends BaseMvpView {

  void navigateToListOffersActivity(ArrayList<Offers> offersList);

  void processFyberOfferAPIFailed(String toast);
}
