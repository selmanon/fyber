package io.abdellah.fyber.data.model;

import okhttp3.ResponseBody;
import retrofit2.http.Headers;

/**
 * Created by abdellahselassi on 11/20/16.
 */

public class Response<T> {
  T body;
}