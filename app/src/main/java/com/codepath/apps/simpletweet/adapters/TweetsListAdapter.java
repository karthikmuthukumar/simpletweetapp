package com.codepath.apps.simpletweet.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.simpletweet.R;
import com.codepath.apps.simpletweet.models.Tweet;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TweetsListAdapter extends ArrayAdapter<Tweet> {

    public static class ViewHolder {
        ImageView ivImageProfile;
        TextView tvUserName;
        TextView tvBody;
        TextView tvScreenName;
        TextView tvTimestamp;
    }
    public TweetsListAdapter(Context context, List<Tweet> tweets) {
        super(context, 0, tweets);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Tweet tweet = getItem(position);
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.tweet_item,parent, false);
            viewHolder.ivImageProfile = (ImageView) convertView.findViewById(R.id.ivProfileImage);
            viewHolder.tvBody = (TextView) convertView.findViewById(R.id.tvBody);
            viewHolder.tvUserName = (TextView) convertView.findViewById(R.id.tvUserName);
            viewHolder.tvScreenName = (TextView) convertView.findViewById(R.id.tvScreenName);
            viewHolder.tvTimestamp = (TextView) convertView.findViewById(R.id.tvTimestamp);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvUserName.setText(tweet.getUser().getName());
        viewHolder.tvBody.setText(tweet.getBody());
        Picasso.with(getContext()).load(tweet.getUser().getProfileImageUrl()).into(viewHolder.ivImageProfile);
        viewHolder.tvScreenName.setText(tweet.getUser().getScreenName());
        viewHolder.tvTimestamp.setText(tweet.getCreatedAt());
        return convertView;
    }
}
