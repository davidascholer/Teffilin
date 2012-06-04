package com.TefillinGuide;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

public class Invite extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.invite);

		String emailBody = "You are invited to download the Tefillin App. Here is the link:\nIphone:\nhttp://itunes.apple.com/us/app/tefillin-guide/id517338348?mt=8%26ls=1\n\nThis app is a multilingual program aimed at helping assist you put on Tefillin. More languages and guides will be coming soon but for now enjoy the Hebrew, English and Russian transliterations. Please give this app a 5 star rating.\n\n- The SABER Team\nwww.thesaberteam.com";

		Intent intent = new Intent(Intent.ACTION_SENDTO); // it's not 
															// ACTION_SEND
		intent.setType("text/plain");
		intent.putExtra(Intent.EXTRA_SUBJECT, "A Tefillin Guide Invite");
		intent.putExtra(Intent.EXTRA_TEXT, emailBody);
		intent.setData(Uri.parse("mailto:")); // or just "mailto:" for blank
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // this will make such
														// that when user
														// returns to your app,
														// your app is
														// displayed, instead of
														// the email app.
		startActivity(intent);
	}

	/*
	 * To refresh the appliation after webpage has been viewed.
	 */
	@Override
	protected void onResume() {
		super.onResume();
		startActivity(new Intent(Invite.this, TefillinGuideActivity.class));
		Invite.this.finish();

	}
}