package io.abdellah.fyber.fyberoffers.view;

import io.abdellah.fyber.base.BaseMvpView;
import io.abdellah.fyber.data.model.Offers;
import java.util.List;

/**
 * Created by abdellahselassi on 11/16/16.
 */

public interface OfferMvpView extends BaseMvpView{
  void showProgressIndicator();
  void showOffers(List<Offers> Offer);
  void showMessage(int stringId);
}
