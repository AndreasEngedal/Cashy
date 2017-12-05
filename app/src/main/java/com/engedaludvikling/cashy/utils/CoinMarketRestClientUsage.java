package com.engedaludvikling.cashy.utils;

/**
 * Created by Andreas Engedal on 04-12-2017.
 */

import android.app.Activity;
import android.util.Log;

import org.json.*;

import com.engedaludvikling.cashy.MainActivity;
import com.loopj.android.http.*;

import cz.msebera.android.httpclient.Header;

public class CoinMarketRestClientUsage {

    private MainActivity mDelegate;

    public CoinMarketRestClientUsage(MainActivity delegate) {
        mDelegate = delegate;
    }

    public void getPriceInUSD() throws JSONException {
        CoinMarketRestClient.get("", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray
                try {
                    mDelegate.updateValues(response.getDouble("last_price"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
                // Pull out the first event on the public timeline
                try {
                    JSONObject firstEvent = timeline.getJSONObject(0);
                    mDelegate.updateValues(firstEvent.getDouble("last_price"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
