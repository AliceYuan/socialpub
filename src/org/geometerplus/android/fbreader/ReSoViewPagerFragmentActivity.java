package org.geometerplus.android.fbreader;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.geometerplus.zlibrary.ui.android.R;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

public class ReSoViewPagerFragmentActivity extends FragmentActivity{
	/** maintains the pager adapter*/
	private PagerAdapter mPagerAdapter;
	private ImageLoaderConfiguration config;
	private static ArrayList<ResultObject> listData = new ArrayList<ResultObject>();

	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.pager_reso_main);

		//Initialise the pager
		this.initialisePaging();
	}

	/**
	 * Initialise the fragments to be paged
	 */
	private void initialisePaging() {

		List<Fragment> fragments = new Vector<Fragment>();
		fragments.add(Fragment.instantiate(this, MediaFragment.class.getName()));
		fragments.add(Fragment.instantiate(this, DiscussionFragment.class.getName()));
		config = new ImageLoaderConfiguration.Builder(getApplicationContext()).build();
		ImageLoader.getInstance().init(config);
		listData.add(new ResultObject("test","test","test","test","test","http://farm9.staticflickr.com/8086/8571183569_f366bbea75_s.jpg"));
		listData.add(new ResultObject("alice","alice","alice","alice","alice","http://farm5.staticflickr.com/4121/4795424879_7e35b2cbf4_s.jpg"));
		this.mPagerAdapter  = new ReSoPagerAdapter(super.getSupportFragmentManager(), fragments);
		//
		ViewPager pager = (ViewPager)super.findViewById(R.id.viewpager);
		pager.setAdapter(this.mPagerAdapter);
	}


	public static ArrayList<ResultObject> getListData() {
		return listData;
	}
}