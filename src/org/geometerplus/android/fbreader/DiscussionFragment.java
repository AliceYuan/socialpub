package org.geometerplus.android.fbreader;

import java.util.ArrayList;

import org.geometerplus.zlibrary.ui.android.R;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

public class DiscussionFragment extends Fragment {
    /** (non-Javadoc)
     * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
     */
	private ThumbsResultListAdapter resultAdapter;
	private ListView listDisplay;
	public static ImageLoader imageLoader = ImageLoader.getInstance();
	public static ImageLoaderConfiguration config;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        if (container == null) {
            // We have different layouts, and in one of them this
            // fragment's containing frame doesn't exist.  The fragment
            // may still be created from its saved state, but there is
            // no reason to try to create its view hierarchy because it
            // won't be displayed.  Note this is not needed -- we could
            // just run the code below, where we would create and return
            // the view hierarchy; it would just never be used.
            return null;
        }
        return (LinearLayout)inflater.inflate(R.layout.tabs_fragment_discussion, container, false);
    }
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
        listDisplay = (ListView) getView().findViewById(R.id.list_view);

        resultAdapter = new ThumbsResultListAdapter(getActivity(), R.layout.listview_item_thumb_result, ReSoViewPagerFragmentActivity.getListData());
        listDisplay.setAdapter(resultAdapter);
	}
    
    
}
