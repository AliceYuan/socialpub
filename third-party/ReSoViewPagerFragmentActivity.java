package org.geometerplus.android.fbreader;

import java.util.List;
import java.util.Vector;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

public class ReSoViewPagerFragmentActivity extends FragmentActivity{
   /** maintains the pager adapter*/
   private PagerAdapter mPagerAdapter;
   /* (non-Javadoc)
    * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
    */
   @Override
   protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       super.setContentView(R.layout.root_view);
       //Initialise the pager
       this.initialisePaging();
   }

   /**
    * Initialise the fragments to be paged
    */
   private void initialisePaging() {

       List<Fragment> fragments = new Vector<Fragment>();
       fragments.add(Fragment.instantiate(this, MainFragment.class.getName()));
       fragments.add(Fragment.instantiate(this, DiscussionFragment.class.getName()));
       this.mPagerAdapter  = new ReSoPagerAdapter(super.getSupportFragmentManager(), fragments);
       //
       ViewPager pager = (ViewPager)super.findViewById(R.id.main_pager);
       pager.setAdapter(this.mPagerAdapter);
   }
}