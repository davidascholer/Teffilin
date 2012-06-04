package com.TefillinGuide;

import java.util.ArrayList; 

import android.app.ActivityGroup;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class Home extends ActivityGroup {

	ArrayList<Integer> imagesArrayList = new ArrayList<Integer>();
	ArrayList<String> stringsArrayList = new ArrayList<String>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);

		// This is the array to hold the initial strings for the ListView
		String[] listItemStrings = getResources().getStringArray(
				R.array.list_item_strings);
		addDataToArrays();

		// Set up the List View
		ListView lv = (ListView) findViewById(R.id.listView);
		lv.setAdapter(new MyCustomAdapter(this, R.layout.list_item,
				listItemStrings));

		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				if (position == 0) {
					setAboutScreen();
				} else {
					setTextScreen(position);
				}
			}
		});
	}

	/*
	 * This is a custom list view to act similarly to the iphone list view.
	 */
	public class MyCustomAdapter extends ArrayAdapter<String> {

		public MyCustomAdapter(Context context, int textViewResourceId,
				String[] objects) {
			super(context, textViewResourceId, objects);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			View row = convertView;

			if (row == null) {
				LayoutInflater inflater = getLayoutInflater();
				row = inflater.inflate(R.layout.list_item, parent, false);
			}

			TextView label = (TextView) row.findViewById(R.id.listitem_txt);
			label.setText(stringsArrayList.get(position));
			ImageView icon = (ImageView) row.findViewById(R.id.listitem_img);
			icon.setImageResource(imagesArrayList.get(position));

			return row;
		}
	}

	/*
	 * Here, we are setting up ArrayLists from the strings data to go into our
	 * custom list view.
	 */
	private void addDataToArrays() {

		String[] listItemStrings = getResources().getStringArray(
				R.array.list_item_strings);
		String[] listItemImages = getResources().getStringArray(
				R.array.list_item_images);
		for (int i = 0; i < listItemImages.length; i++) {
			int presFind = this.getResources().getIdentifier(listItemImages[i],
					"drawable", this.getPackageName());

			imagesArrayList.add(presFind);
			stringsArrayList.add(listItemStrings[i]);

		}

	}

	/*
	 * Here we are simply switching the view, not the activity, to keep the
	 * current context under the tab view
	 */
	private void setTextScreen(int pos) {

		Intent intent = new Intent(this, HomeText.class);
		intent.putExtra("position", pos);
		View mView = getLocalActivityManager()
				.startActivity("TextScreen",
						intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
				.getDecorView();
		this.setContentView(mView);

	}

	/*
	 * Sing the "About Tifillin" screen is the same as the "Info" page, we are
	 * going to set the same view accordingly.
	 */
	private void setAboutScreen() {
		View mView = getLocalActivityManager().startActivity(
				"AboutScreen",
				new Intent(this, Info.class)
						.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
				.getDecorView();
		this.setContentView(mView);

	}

}