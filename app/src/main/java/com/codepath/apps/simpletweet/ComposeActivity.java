package com.codepath.apps.simpletweet;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;


public class ComposeActivity extends ActionBarActivity {

    private static final String EMPTY_MSG = "Tweets cannot be empty";

    private TwitterClient client;
    private EditText etTweet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        client = TwitterApplication.getRestClient();
        etTweet = (EditText) findViewById(R.id.etTweet);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_compose, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void postTweet(View view) {
        String tweet = etTweet.getText().toString();
        if (tweet == null || tweet.isEmpty()) {
            Toast.makeText(getBaseContext(),EMPTY_MSG,Toast.LENGTH_SHORT).show();
            return;
        }
        client.postTweet(tweet, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                backToPreviousActivity();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable th, JSONObject errorResponse) {
                Toast.makeText(getBaseContext(), "Issue while fetching data from server", Toast.LENGTH_SHORT).show();
                th.printStackTrace();
            }
        });
        backToPreviousActivity();
    }

    public void cancelTweet(View view) {
        backToPreviousActivity();
    }

    private void backToPreviousActivity() {
        Intent prevIntent = new Intent();
        setResult(RESULT_OK, prevIntent);
        finish();
    }
}
