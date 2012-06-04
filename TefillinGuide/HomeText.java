package com.TefillinGuide;

import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.Gravity;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TextView.BufferType;

public class HomeText extends Activity {

	String str = "null error";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_text);

		Intent mIntent = getIntent();
		int tabPos = mIntent.getIntExtra("position", 0);

		TextView tv = (TextView) findViewById(R.id.homeTextView);
		Button button = (Button) findViewById(R.id.button1);

		switch (tabPos) {
		case 1:
			str = this.getString(R.string.english_short);
			tv.setText(str);
			button.setText("English - on the go");
			break;
		case 2:
			str = this.getString(R.string.english_full);
			tv.setText(str);
			button.setText("English - full version");
			break;
		case 3:
			str = this.getString(R.string.hebrew_short);
			tv.setText(str);
			tv.setGravity(Gravity.RIGHT);
			button.setText("Hebrew - on the go");
			break;
		case 4:
			str = this.getString(R.string.hebrew_full);
			tv.setText(str);
			tv.setGravity(Gravity.RIGHT);
			button.setText("Hebrew - full version");
			break;
		case 5:
			str = this.getString(R.string.russian_short);
			tv.setText(str);
			button.setText("Russian - on the go");
			break;
		case 6:
			str = this.getString(R.string.russian_full);
			tv.setText(str);
			button.setText("Russian - full version");
			break;
		case 7:
			str = this.getString(R.string.about_saber);
			tv.setTextSize(18);
			tv.setText(Html.fromHtml(str));
			tv.setMovementMethod(LinkMovementMethod.getInstance());
			button.setText("About the SABER Team");

			break;
		default:
			str = "error";
			break;
		}

	}
}
