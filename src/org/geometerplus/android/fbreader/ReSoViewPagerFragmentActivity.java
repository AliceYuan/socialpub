package org.geometerplus.android.fbreader;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Vector;

import org.geometerplus.zlibrary.core.json.ZJSONParser;
import org.geometerplus.zlibrary.text.view.ZLTextFixedPosition;
import org.geometerplus.zlibrary.text.view.ZLTextPosition;
import org.geometerplus.zlibrary.ui.android.R;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class ReSoViewPagerFragmentActivity extends FragmentActivity{
	/** maintains the pager adapter*/
	private PagerAdapter mPagerAdapter;
	private ImageLoaderConfiguration config;
	private static ArrayList<ResultObject> listData = new ArrayList<ResultObject>();
	ZJSONParser jsonParser;
	public static EditText commentText;
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
		//fragments.add(Fragment.instantiate(this, MediaFragment.class.getName()));
		fragments.add(Fragment.instantiate(this, DiscussionFragment.class.getName()));
		config = new ImageLoaderConfiguration.Builder(getApplicationContext()).build();
		ImageLoader.getInstance().init(config);
		
		
		String bookId = Long.valueOf(getIntent().getLongExtra("bookId", 0)).toString();
		int startParagraphIndex = getIntent().getIntExtra("startParagraphIndex", 0);
		int startElementIndex = getIntent().getIntExtra("startElementIndex", 0);
		int startCharIndex = getIntent().getIntExtra("startCharIndex", 0);
		int endParagraphIndex = getIntent().getIntExtra("endParagraphIndex", 0);
		int endElementIndex = getIntent().getIntExtra("endElementIndex", 0);
		int endCharIndex = getIntent().getIntExtra("startCharIndex", 0);	
		
		ZLTextPosition startPosition = new ZLTextFixedPosition(startParagraphIndex, startElementIndex, startCharIndex);
		ZLTextPosition endPosition = new ZLTextFixedPosition(endParagraphIndex, endElementIndex, endCharIndex);
		jsonParser = new ZJSONParser();
		listData.clear();
		ResultObject[] comments = jsonParser.fetchComments(bookId, startPosition, endPosition);
		for (ResultObject comment : comments) {
			listData.add(comment);
		}
		
		this.mPagerAdapter  = new ReSoPagerAdapter(super.getSupportFragmentManager(), fragments);
		//
		
		ViewPager pager = (ViewPager)super.findViewById(R.id.viewpager);
		pager.setAdapter(this.mPagerAdapter);
	}

	
	public void sendComment(View view){
		//get user entered search term
		String comment = commentText.getText().toString();
		if(comment.length()>0){
			try{
				//instantiate and execute AsyncTask
				InputMethodManager in = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
				in.hideSoftInputFromWindow(commentText.getWindowToken(), 0);
				Calendar c = Calendar.getInstance();
		        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		        String formattedDate = df.format(c.getTime());
				String bookId = Long.valueOf(getIntent().getLongExtra("bookId", 0)).toString();
				ResultObject commentObj = new ResultObject(comment, bookId, "title", FBReader.userName , formattedDate, FBReader.userProfileImage, "123" );
				int startParagraphIndex = getIntent().getIntExtra("startParagraphIndex", 0);
				int startElementIndex = getIntent().getIntExtra("startElementIndex", 0);
				int startCharIndex = getIntent().getIntExtra("startCharIndex", 0);
				int endParagraphIndex = getIntent().getIntExtra("endParagraphIndex", 0);
				int endElementIndex = getIntent().getIntExtra("endElementIndex", 0);
				int endCharIndex = getIntent().getIntExtra("startCharIndex", 0);	
				commentObj.setRange(startParagraphIndex, endParagraphIndex, startElementIndex, endElementIndex, startCharIndex, endCharIndex);
				ZLTextPosition startPosition = new ZLTextFixedPosition(startParagraphIndex, startElementIndex, startCharIndex);
				ZLTextPosition endPosition = new ZLTextFixedPosition(endParagraphIndex, endElementIndex, endCharIndex);
				listData.add(commentObj);
				mPagerAdapter.notifyDataSetChanged();
				jsonParser.addComment(commentObj, startPosition, endPosition);
			}
			catch(Exception e){ 
				e.printStackTrace(); 
			}
		}
	}

	public static ArrayList<ResultObject> getListData() {
		return listData;
	}
}