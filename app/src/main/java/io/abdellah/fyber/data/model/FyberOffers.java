package io.abdellah.fyber.data.model;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by abdellahselassi on 10/28/16.
 */
@ToString @Getter @Setter
public class FyberOffers implements Serializable{
  private String message;
  private Information information;
  private String count;
  private Offers[] offers;
  private String pages;
  private String code;
}
