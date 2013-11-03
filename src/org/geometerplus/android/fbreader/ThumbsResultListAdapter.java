package org.geometerplus.android.fbreader;

import java.util.ArrayList;

import org.geometerplus.zlibrary.ui.android.R;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;

public class ThumbsResultListAdapter extends ArrayAdapter<ResultObject> {
	private Context context;
	private int layoutResourceId;
	private ArrayList<ResultObject> data = null;
	private resultHolder holder = null;

	public ThumbsResultListAdapter(FragmentActivity fragmentActivity,
			int layoutResourceId, ArrayList<ResultObject> listData) {
		super(fragmentActivity, layoutResourceId, listData);
		this.layoutResourceId = layoutResourceId;
		this.context = fragmentActivity;
		this.data = listData;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;

		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);

			holder = new resultHolder();
			holder.profileIcon = (RoundedImageView) row
					.findViewById(R.id.img_thumbnail);
			holder.commentText = (TextView) row
					.findViewById(R.id.comment_text_field);
			row.setTag(holder);
		} else {
			holder = (resultHolder) row.getTag();
			holder.profileIcon.setImageDrawable(null);
		}

		final ResultObject result = data.get(position);
		holder.commentText.setText(result.getTitle());
		DiscussionFragment.imageLoader.displayImage(result.getImageUrl(),
				holder.profileIcon, new SimpleImageLoadingListener() {
//					@Override
//					public void onLoadingComplete(String imageUri, View view,
//							Bitmap loadedImage) {
//					}
				});

		return row;
	}

	// photo result holder of views
	private class resultHolder {
		RoundedImageView profileIcon;
		TextView commentText;
	}
}