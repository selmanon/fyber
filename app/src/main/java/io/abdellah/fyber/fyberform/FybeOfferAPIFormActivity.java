package io.abdellah.fyber.fyberform;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.abdellah.fyber.R;
import io.abdellah.fyber.fyberform.presenter.FybeOfferAPIFormPresenter;
import io.abdellah.fyber.fyberform.view.FyberOfferAPIMvpView;
import io.abdellah.fyber.fyberoffers.FyberOffersListActivity;
import io.abdellah.fyber.utilities.ApiParamsUtility;
import java.util.ArrayList;

public class FybeOfferAPIFormActivity extends AppCompatActivity implements FyberOfferAPIMvpView {

  private static final String OFFERS_EXTRA_KEY = "offers";
  private static final String FYBER_SERVER_RESPONSE_FORMAT = "json";

  @BindView(R.id.input_uid) EditText inputUid;
  @BindView(R.id.input_api_key) EditText inputApiKey;
  @BindView(R.id.input_app_id) EditText inputAppId;
  @BindView(R.id.input_pub0) EditText inputPub0;
  @BindView(R.id.btn_process) Button btnProcess;

  private FybeOfferAPIFormPresenter presenter;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    //Set up presenter
    initializePresenter();

    setContentView(R.layout.activity_offers_form);
    ButterKnife.bind(this);
  }

  private void initializePresenter() {
    presenter = new FybeOfferAPIFormPresenter();
    presenter.attachView(this);
  }

  @Override public void navigateToListOffersActivity(ArrayList offers) {
    Intent navigateToOffers = new Intent(this, FyberOffersListActivity.class);
    navigateToOffers.putExtra(OFFERS_EXTRA_KEY, offers);
    startActivity(navigateToOffers);
  }

  @Override public void processFyberOfferAPIFailed(String message) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
  }

  @Override public Context getContext() {
    return this;
  }

  @OnClick(R.id.btn_process) public void onClick() {
    if (checkRequiredField()) {
      presenter.getFyberOfferAPI(inputAppId.getText().toString(), FYBER_SERVER_RESPONSE_FORMAT,
          inputPub0.getText().toString(), ApiParamsUtility.getStringTimeStamp(),
          inputUid.getText().toString(), getHashkey());
    }
  }

  private boolean checkRequiredField() {
    boolean isFilled = true;

    if (inputAppId.getText().toString().length() == 0) {
      inputAppId.setError(getString(R.string.appid_required));
      isFilled = false;
    }

    if (inputApiKey.getText().toString().length() == 0) {
      inputApiKey.setError(getString(R.string.api_key_required));
      isFilled = false;
    }

    if (inputPub0.getText().toString().length() == 0) {
      inputPub0.setError(getString(R.string.pub0_required));
      isFilled = false;
    }

    if (inputUid.getText().toString().length() == 0) {
      inputUid.setError(getString(R.string.uid_required));
      isFilled = false;
    }

    return isFilled;
  }

  private String getHashkey() {
    return ApiParamsUtility.calculateAuthHashKey(inputAppId.getText().toString(),
        FYBER_SERVER_RESPONSE_FORMAT, inputPub0.getText().toString(),
        ApiParamsUtility.getStringTimeStamp(), inputUid.getText().toString());
  }
}
