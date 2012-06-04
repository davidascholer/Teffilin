package com.TefillinGuide;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

/*
 * This class is fairly strait forward. A common approach as soon on the Android Developers site.
 */
public class TefillinGuideActivity extends TabActivity {

	TabHost mTabHost;
	Resources res;
	Intent intent;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		mTabHost = (TabHost) findViewById(android.R.id.tabhost);
		res = getResources(); // Resource object to get Drawables
		intent = new Intent().setClass(this, Home.class);
		setupTab(new TextView(this), "Home",
				res.getDrawable(R.drawable.tab_home), intent);
		intent = new Intent().setClass(this, Invite.class);
		setupTab(new TextView(this), "Invite friends",
				res.getDrawable(R.drawable.tab_invite), intent);
		intent = new Intent().setClass(this, Mirror.class);
		setupTab(new TextView(this), "Mirror",
				res.getDrawable(R.drawable.tab_mirror), intent);
		intent = new Intent().setClass(this, Info.class);
		setupTab(new TextView(this), "Info",
				res.getDrawable(R.drawable.tab_info), intent);

		/*
		 * Ugly code here. The purpose is to recreate the iphone function of
		 * re-clicking a particular tab to restart the entire View.
		 * 
		 * In this case, we only need it for the main tab.
		 */
		getTabWidget().getChildAt(0).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(TefillinGuideActivity.this,
						TefillinGuideActivity.class));
				TefillinGuideActivity.this.finish(); 

			}
		});

	}

	private void setupTab(final View view, final String tag, final Drawable d,
			Intent in) {
		View tabview = createTabView(mTabHost.getContext(), tag, d);
		TabSpec setContent = mTabHost.newTabSpec(tag).setIndicator(tabview)
				.setContent(in);
		mTabHost.addTab(setContent);

	}

	private static View createTabView(final Context context, final String text,
			final Drawable d) {
		View view = LayoutInflater.from(context)
				.inflate(R.layout.tabs_bg, null);
		TextView tv = (TextView) view.findViewById(R.id.tabsText);
		ImageView iv = (ImageView) view.findViewById(R.id.tabsImage);
		tv.setText(text);
		iv.setImageDrawable(d);
		return view;
	}

}