package com.TefillinGuide;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class Info  extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.info);
		
		ImageButton howIB = (ImageButton)findViewById(R.id.howToImageButton);
		howIB.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.chabad.org/library/article_cdo/aid/272666/jewish/Guide.htm"));
				startActivity(browserIntent);

			}
		});
		
		ImageButton vidIB = (ImageButton)findViewById(R.id.videoImageButton);
		vidIB.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=FjYB-ntmx-M"));
				startActivity(browserIntent);

			}
		});
		
		ImageButton animIB = (ImageButton)findViewById(R.id.animationImageButton);
		animIB.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=Y8r3QlF07Ac"));
				startActivity(browserIntent);

			}
		});


	}

}
