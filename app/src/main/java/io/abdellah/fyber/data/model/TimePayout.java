package io.abdellah.fyber.data.model;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by abdellahselassi on 11/17/16.
 */
@ToString @Getter @Setter

public class TimePayout implements Serializable{
  private String amount;
  private String readable;
}
