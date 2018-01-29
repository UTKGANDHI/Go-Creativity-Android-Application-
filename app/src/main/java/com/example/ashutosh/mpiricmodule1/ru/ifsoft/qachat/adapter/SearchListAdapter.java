package com.example.ashutosh.mpiricmodule1.ru.ifsoft.qachat.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.pkmmte.view.CircularImageView;

import java.util.List;

import com.example.ashutosh.mpiricmodule1.R;
import com.example.ashutosh.mpiricmodule1.ru.ifsoft.qachat.app.App;
import com.example.ashutosh.mpiricmodule1.ru.ifsoft.qachat.constants.Constants;
import com.example.ashutosh.mpiricmodule1.ru.ifsoft.qachat.model.User;


public class SearchListAdapter extends BaseAdapter {

	private Activity activity;
	private LayoutInflater inflater;
	private List<User> searchResults;

	ImageLoader imageLoader = App.getInstance().getImageLoader();

	public SearchListAdapter(Activity activity, List<User> searchResults) {

		this.activity = activity;
		this.searchResults = searchResults;
	}

	@Override
	public int getCount() {

		return searchResults.size();
	}

	@Override
	public Object getItem(int location) {

		return searchResults.get(location);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}
	
	static class ViewHolder {

        public TextView profileUsername;
		public TextView profileFullname;
		public TextView mOnline;
        public TextView mLocation;
		public CircularImageView profilePhoto;
		public Boolean isMyRow = false;
	        
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder viewHolder = null;

		if (inflater == null) {

            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

		if (convertView == null) {
			
			convertView = inflater.inflate(R.layout.search_list_row, null);
			
			User u = searchResults.get(position);
			
			viewHolder = new ViewHolder();
			
			viewHolder.profilePhoto = (CircularImageView) convertView.findViewById(R.id.personPhoto);
			viewHolder.mOnline = (TextView) convertView.findViewById(R.id.online);
            viewHolder.mLocation = (TextView) convertView.findViewById(R.id.timeAgo);
			viewHolder.profileFullname = (TextView) convertView.findViewById(R.id.personFullName);
            viewHolder.profileUsername = (TextView) convertView.findViewById(R.id.personUsername);

			convertView.setTag(viewHolder);

		} else {
			
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.mOnline.setTag(position);
        viewHolder.mLocation.setTag(position);
		viewHolder.profilePhoto.setTag(position);
		
		final User u = searchResults.get(position);

        if (u.getFullname().length() == 0) {

            viewHolder.profileFullname.setText(u.getUsername());

        } else {

            viewHolder.profileFullname.setText(u.getFullname());
        }

        viewHolder.profileUsername.setText("@" + u.getUsername());
		
        if (imageLoader == null) {

            imageLoader = App.getInstance().getImageLoader();
        }
			
        if (u.getLocation().length() > 0) {

            viewHolder.mLocation.setText(u.getLocation());
            viewHolder.mLocation.setVisibility(View.VISIBLE);

        } else {

            viewHolder.mLocation.setVisibility(View.GONE);
        }

        if (u.isOnline()) {

            viewHolder.mOnline.setVisibility(View.VISIBLE);
            viewHolder.mOnline.setText(activity.getString(R.string.label_online));

        } else {

            viewHolder.mOnline.setVisibility(View.GONE);
            viewHolder.mOnline.setText(activity.getString(R.string.label_offline));
        }

        if (!searchResults.get(position).isVerify()) {

            viewHolder.profileFullname.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);

        } else {

            viewHolder.profileFullname.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.profile_verify_icon, 0);
        }

        if (u.getLowPhotoUrl().length() > 0 && u.getState() == Constants.ACCOUNT_STATE_ENABLED) {

            imageLoader.get(u.getLowPhotoUrl(), ImageLoader.getImageListener(viewHolder.profilePhoto, R.drawable.profile_default_photo, R.drawable.profile_default_photo));

        } else {

            viewHolder.profilePhoto.setImageResource(R.drawable.profile_default_photo);
        }

		return convertView;
	}
}