package com.mbrdzanebeli;


import com.mbrdzanebeli.App.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;


import android.annotation.SuppressLint;
import android.app.Dialog;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
///import android.content.DialogInterface;
import android.net.Uri;
import android.view.KeyEvent;

import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

public class BootstrapActivity extends AbstractActivity implements  OnClickListener, AdapterView.OnItemClickListener {

	Button gauziaresxvas;
	Dialog dialog;




	@Override
	protected void onCreate(Bundle savedInstanceState) {


		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bootstrap);


		findViewById(R.id.damexmaret).setOnClickListener(this);


		gauziaresxvas = findViewById(R.id.gauziaresxvas);
		gauziaresxvas.setOnClickListener(v -> {
			String message = "https://play.google.com/store/apps/details?id=lotr.mbrdzanebeli";
			Intent share = new Intent(Intent.ACTION_SEND);
			share.setType("text/plain");
			share.putExtra(Intent.EXTRA_TEXT, message);

			startActivity(Intent.createChooser(share, "share with friends"));



		});
		init();










//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		findViewById(R.id.btnRate);
		findViewById(R.id.gauziaresxvas);
		findViewById(R.id.play);
		///Typeface myTypeface = Typeface.createFromAsset(getAssets(), "font/sharpe.ttf"); /// ბუტსტრაპ ფონტი

		///battle.setTypeface(myTypeface);
		///	sheapase.setTypeface(myTypeface);
		///gauziaresxvas.setTypeface(myTypeface);




//ფონტებიიიიიიიიიიიიიიიი
////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	}

	private void showdamexmaret() {
		dialog = new Dialog(this, android.R.style.Theme_NoTitleBar_Fullscreen);
		ColorDrawable dialogColor = new ColorDrawable(Color.GREEN);
		dialogColor.setAlpha(100);
		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.activity_damexmaret);

		dialog.findViewById(R.id.bCancel).setOnClickListener(this);

		dialog.show();


	}

	public void rateMe(View v) {
		try {
			startActivity(new Intent(Intent.ACTION_VIEW,
					Uri.parse("market://details?id=" + getPackageName())));////გაზიარება თამაშის
		} catch (ActivityNotFoundException e) {
			startActivity(new Intent(Intent.ACTION_VIEW,
					Uri.parse("https://play.google.com/store/apps/details?id=lotr.mbrdzanebeli" )));
		}

////////////////////////////////////////////////////////////////////////////////////////////////////////////
	}


	private void init() {
		findViewById(R.id.play).setOnClickListener(arg0 -> {
			//			sound.destroy();

			startActivity(new Intent(getApplicationContext(), MainActivity.class));
		});


	}


	@SuppressLint("SetTextI18n")
	@Override
	protected void onStart() {

		level = pre.getInt(KEY_LEVEL, 1);  //ლეველი ეწერება

		TextView tv = findViewById(R.id.tvLevel0);
		tv.setText("" + level);
		super.onStart();


	}


	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			exitByBackKey();

			//moveTaskToBack(false);

			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	///protected void exitByBackKey() {

		// do something when the button is clicked
		// do something when the button is clicked
		// do something when the button is clicked
		//new AlertDialog.Builder(this)
			//	.setMessage("DO YOU WANT TO LEAVE LOTW?")
		//		.setPositiveButton("Exit", (arg0, arg1) -> finish())


			//	.setNeutralButton("Please Push here and Rate", (arg0, arg1) -> {

				//	{
					//	Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=lotr.mbrdzanebeli");
					//	Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
					//	try {
						//	startActivity(goToMarket);
					//	} catch (ActivityNotFoundException e) {
						//	Toast.makeText(getApplicationContext(), "",
						//			Toast.LENGTH_LONG).show();
						//}
				//	}


			//	})

			//	.setNegativeButton("Stay", (arg0, arg1) -> {
				//})
				//.show();


	//}


	@SuppressLint("NonConstantResourceId")
	@Override
	public void onClick(View v) {
		//sound.play("click", context);
		switch (v.getId()) {
			case R.id.ivZoom:


			case R.id.damexmaret:

				this.showdamexmaret();

				break;
			case R.id.bCancel:////////////////////////////////////////////////////////////// ქანსელის კქნოპკა
				dialog.dismiss();
				break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

	}






	public void exitByBackKey() {
		finish();
		super.onBackPressed();


	}

}









