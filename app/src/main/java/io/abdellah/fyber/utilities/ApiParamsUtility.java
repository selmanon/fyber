package io.abdellah.fyber.utilities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * Created by abdellahselassi on 11/16/16.
 */

public final class ApiParamsUtility {
  public static final String AMPERSAND_SEPARATOR = "&";
  public static final String EQUAL_SIGN_SEPARATOR = "=";
  public static final String APPID_KEY = "appid";
  public static final String FORMAT_KEY = "format";
  public static final String PUB0_KEY = "pub0";
  public static final String TIMESTAMP_KEY = "timestamp";
  public static final String UID_KEY = "uid";
  public static final String API_KEY = "1c915e3b5d42d05136185030892fbb846c278927";

  /**
   *
   * @return
   */
  public static final String getStringTimeStamp() {
    return Long.toString(System.currentTimeMillis() / 1000);
  }

  public static final String calculateAuthHashKey(String appid, String format, String pub0,
      String timestamp, String uid) {
    List params = new ArrayList();
    String authHashKey;

    params.add(APPID_KEY + EQUAL_SIGN_SEPARATOR + appid);
    params.add(FORMAT_KEY + EQUAL_SIGN_SEPARATOR + format);
    params.add(PUB0_KEY + EQUAL_SIGN_SEPARATOR + pub0);
    params.add(TIMESTAMP_KEY + EQUAL_SIGN_SEPARATOR + timestamp);
    params.add(UID_KEY + EQUAL_SIGN_SEPARATOR + uid);

    // Order alphabetically by parameter name
    Collections.sort(params);

    params.add(API_KEY);

    authHashKey = new String(Hex.encodeHex(DigestUtils.sha1(join(params, AMPERSAND_SEPARATOR))));

    return authHashKey;
  }

  public static String join(Iterable<? extends CharSequence> s, String delimiter) {
    Iterator<? extends CharSequence> iter = s.iterator();
    if (!iter.hasNext()) return "";
    StringBuilder buffer = new StringBuilder(iter.next());
    while (iter.hasNext()) buffer.append(delimiter).append(iter.next());
    return buffer.toString();
  }
}
