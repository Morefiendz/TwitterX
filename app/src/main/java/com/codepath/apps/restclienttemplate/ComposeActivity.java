package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;

public class ComposeActivity extends AppCompatActivity {
    private TwitterClient client = TwitterApp.getTwitterClient(this);
    EditText etTweet;
    Button btnSendTweet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
    }

    public void networkRequest(View v){
        client.sendTweet(etTweet.getText().toString(), new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    Tweet tweet = Tweet.fromJSON(response);

                    Intent intent = new Intent();
                    intent.putExtra("name", Parcels.wrap(tweet));
                    intent.putExtra("code", 200);

                    setResult(RESULT_OK, intent);
                    finish();
                } catch (JSONException e) {
                    Log.e("sendtweetjson", "sendtweet json failed");
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.e("sendtweetjson", "sendtweet json failed");
            }
        });
    }

    public void onSubmit(View v) {
        // closes the activity and returns to first screen
        this.finish();
    }
}