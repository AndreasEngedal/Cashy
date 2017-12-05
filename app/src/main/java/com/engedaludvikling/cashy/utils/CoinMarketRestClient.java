package com.engedaludvikling.cashy.utils;

/**
 * Created by Andreas Engedal on 04-12-2017.
 */

import com.loopj.android.http.*;

public class CoinMarketRestClient {
    private static final String BASE_URL = "https://api.coinmarketcap.com/v1/ticker/iota/";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
}

