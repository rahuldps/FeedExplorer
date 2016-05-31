package com.example.rj.feedexplorer.network;

import android.util.Log;

import com.google.gson.Gson;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by Rj on 5/29/16.
 */
public class NetworkUtils {


  public static <T> ServerResponse<T> doGetCall(String url, Class<T> responseClass)
      throws Exception {
    Log.w("url hahahha", " " + url);
    Response response = getResponseForGetCall(url);
    T responseObject = parseFromResponseToClass(response, responseClass);

    return new ServerResponse<T>(responseObject, response.message(), response.code());
  }

  private static Response getResponseForGetCall(String url) throws IOException {
    OkHttpClient client = new OkHttpClient();
    Request request = new Request.Builder()
        .url(url)
        .build();
    Call call = client.newCall(request);
    Response response = call.execute();
    return response;
  }

  private static <T> T parseFromResponseToClass(Response response, Class<T> tClass)
      throws Exception {
    Gson gson = new Gson();
    if (tClass == null) {
      tClass = (Class<T>) Object.class;
    }
    try {
      return (gson.fromJson(response.body().charStream(), tClass));
    } catch (Exception e) {
      throw new Exception(e);
    }
  }


}