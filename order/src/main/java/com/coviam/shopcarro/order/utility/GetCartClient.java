package com.coviam.shopcarro.order.utility;

import okhttp3.*;


/**
 *
 * To make a post request from one server to another using a okHttpRequest method using dependencies from okHttp3
 *
 * Through this class one can have the post request by using the post method inside GetCartClient
 *
 * */
import java.io.IOException;

public class GetCartClient {
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient();

    public String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

     String emailJson(String email) {
        return "{'email':"+email+"}";
    }
}
