package com.codepath.apps.simpletweet;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import com.codepath.apps.simpletweet.adapters.TweetsListAdapter;
import com.codepath.apps.simpletweet.listeners.EndlessScrollListener;
import com.codepath.apps.simpletweet.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TimeLineActivity extends ActionBarActivity {

    private final int COMPOSE_REQUEST_CODE = 101;

    private TwitterClient client;
    private TweetsListAdapter adapter;
    private List<Tweet> tweets;
    private ListView lvTweets;
    long lastId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        client = TwitterApplication.getRestClient();
        tweets = new ArrayList<>();
        adapter = new TweetsListAdapter(this, tweets);
        lvTweets = (ListView) findViewById(R.id.lvTweets);
        lvTweets.setAdapter(adapter);
        lvTweets.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                Log.d("DEBUG","Page: "+page+" TotalItemsCount: "+totalItemsCount);
                customLoadMoreDataFromApi(page);
                return true;
            }

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }
        });
        customLoadMoreDataFromApi(0);
    }

    private void customLoadMoreDataFromApi(int page) {
        client.getHomeTimeLines(page,lastId,new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                List<Tweet> tweets = Tweet.fromJson(response);
                if (tweets != null && !tweets.isEmpty()) {
                    Tweet tweet = tweets.get(tweets.size()-1);
                    lastId = tweet.getUid() - 1;
                    adapter.addAll(Tweet.fromJson(response));
                }
            }

            public void onFailure(int statusCode, Header[] headers, Throwable th, JSONObject errorResponse) {
                Toast.makeText(getBaseContext(),"Issue while fetching data from server", Toast.LENGTH_SHORT).show();
                th.printStackTrace();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_time_line, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.miCompose:
                composeMessage();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void composeMessage() {
        Intent intent = new Intent(this, ComposeActivity.class);
        startActivityForResult(intent, COMPOSE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == COMPOSE_REQUEST_CODE) {
            // Extract itemData,position value from result extras
            customLoadMoreDataFromApi(0);
        }
    }
}
