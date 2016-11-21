package io.abdellah.fyber.data.model;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by abdellahselassi on 11/17/16.
 */
@ToString @Getter @Setter public class Offers implements Serializable {
  private String title;
  private Thumbnail thumbnail;
  private String offer_id;
  private TimePayout time_to_payout;
  private String link;
  private String required_actions;
  private String teaser;
  private String store_id;
  private OfferTypes[] offer_types;
  private String payout;
}

