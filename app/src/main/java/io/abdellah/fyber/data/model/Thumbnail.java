package io.abdellah.fyber.data.model;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by abdellahselassi on 11/17/16.
 */

@ToString @Getter @Setter
public class Thumbnail implements Serializable{
  private String lowres;
  private String hires;
}
