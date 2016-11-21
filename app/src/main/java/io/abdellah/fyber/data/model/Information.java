package io.abdellah.fyber.data.model;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by abdellahselassi on 11/17/16.
 */
@ToString @Getter @Setter public class Information implements Serializable{
  private String virtual_currency_sale_enabled;
  private String support_url;
  private String appid;
  private String virtual_currency;
  private String language;
  private String app_name;
  private String country;
}
