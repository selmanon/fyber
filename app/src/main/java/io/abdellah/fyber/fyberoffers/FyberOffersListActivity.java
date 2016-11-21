package io.abdellah.fyber.fyberoffers;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import io.abdellah.fyber.R;
import io.abdellah.fyber.data.model.Offers;
import io.abdellah.fyber.fyberoffers.presenter.OfferPresenter;
import io.abdellah.fyber.fyberoffers.view.OfferMvpView;
import java.util.ArrayList;
import java.util.List;

public class FyberOffersListActivity extends AppCompatActivity implements OfferMvpView {
  private static final String OFFERS_EXTRA_KEY = "offers";

  private OfferPresenter presenter;

  private RecyclerView mOfferRecycleView;
  private ProgressBar progressBar;
  private TextView infoTextView;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    //Set up presenter
    presenter = new OfferPresenter();
    presenter.attachView(this);

    setContentView(R.layout.activity_list_offers);

    initUI();

    presenter.showFyberOfferAPI(
        (ArrayList<Offers>) getIntent().getSerializableExtra(OFFERS_EXTRA_KEY));
  }

  private void initUI() {
    progressBar = (ProgressBar) findViewById(R.id.progress);
    infoTextView = (TextView) findViewById(R.id.text_info);

    //Set up ToolBar
    setUpToolBar();

    //Set up RecyclerView
    setUpRecyclerView();
  }

  private void setUpRecyclerView() {
    mOfferRecycleView = (RecyclerView) findViewById(R.id.offers_recycler_view);
    setupRecyclerView(mOfferRecycleView);
  }

  private void setupRecyclerView(RecyclerView recyclerView) {
    OffersAdapter adapter = new OffersAdapter();
    recyclerView.setAdapter(adapter);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
  }

  private void setUpToolBar() {
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setDisplayShowHomeEnabled(false);
    }
  }

  // OfferMvpView interface methods implementation

  @Override protected void onDestroy() {
    presenter.detachView();
    super.onDestroy();
  }

  @Override public Context getContext() {
    return this;
  }

  @Override public void showOffers(List<Offers> offers) {
    OffersAdapter adapter = (OffersAdapter) mOfferRecycleView.getAdapter();
    adapter.setOffers(offers);
    adapter.notifyDataSetChanged();
    mOfferRecycleView.requestFocus();
    progressBar.setVisibility(View.INVISIBLE);
    infoTextView.setVisibility(View.INVISIBLE);
    mOfferRecycleView.setVisibility(View.VISIBLE);
  }

  @Override public void showMessage(int stringId) {
    progressBar.setVisibility(View.INVISIBLE);
    infoTextView.setVisibility(View.VISIBLE);
    mOfferRecycleView.setVisibility(View.INVISIBLE);
    infoTextView.setText(getString(stringId));
  }

  @Override public void showProgressIndicator() {
    progressBar.setVisibility(View.VISIBLE);
    infoTextView.setVisibility(View.INVISIBLE);
    mOfferRecycleView.setVisibility(View.INVISIBLE);
  }

}
