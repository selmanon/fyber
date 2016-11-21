package io.abdellah.fyber.fyberoffers;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import io.abdellah.fyber.R;
import io.abdellah.fyber.data.model.Offers;
import java.util.Collections;
import java.util.List;

public class OffersAdapter extends RecyclerView.Adapter<OffersAdapter.OffersViewHolder> {

  private List<Offers> offerses;
  private Callback callback;

  public OffersAdapter() {
    this.offerses = Collections.emptyList();
  }

  public OffersAdapter(List<Offers> Offers) {
    this.offerses = Offers;
  }

  public void setOffers(List<Offers> Offers) {
    this.offerses = Offers;
  }

  public void setCallback(Callback callback) {
    this.callback = callback;
  }

  @Override public OffersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    final View itemView =
        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_offer, parent, false);
    final OffersViewHolder viewHolder = new OffersViewHolder(itemView);
    viewHolder.contentLayout.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if (callback != null) {
          callback.onItemClick(viewHolder.offers);
        }
      }
    });
    return viewHolder;
  }

  @Override public void onBindViewHolder(final OffersViewHolder holder, int position) {
    Offers offers = offerses.get(position);
    Context context = holder.nameTextView.getContext();

    holder.nameTextView.setText(offers.getTitle());
    holder.teaserTextView.setText(offers.getTeaser());
    holder.payoutTextView.setText(offers.getPayout());

    holder.offers = offers;

    Glide.with(context)
        .load(offers.getThumbnail().getHires())
        .into(new GlideDrawableImageViewTarget(holder.thumbnailImage) {
          @Override public void onResourceReady(GlideDrawable drawable, GlideAnimation anim) {
            super.onResourceReady(drawable, anim);
          }
        });
  }

  @Override public int getItemCount() {
    return offerses.size();
  }

  public static class OffersViewHolder extends RecyclerView.ViewHolder {
    public View contentLayout;
    public TextView nameTextView, teaserTextView, payoutTextView;
    public ImageView thumbnailImage;
    public Offers offers;

    public OffersViewHolder(View itemView) {
      super(itemView);
      contentLayout = itemView.findViewById(R.id.linear_layout_offer);
      nameTextView = (TextView) itemView.findViewById(R.id.view_offer_title);
      teaserTextView = (TextView) itemView.findViewById(R.id.view_offer_teaser);
      payoutTextView = (TextView) itemView.findViewById(R.id.view_offer_payout);
      thumbnailImage = (ImageView) itemView.findViewById(R.id.view_offer_icon);
    }
  }

  public interface Callback {
    void onItemClick(Offers Offers);
  }
}
