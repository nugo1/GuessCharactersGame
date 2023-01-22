package com.mbrdzanebeli;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.android.gms.ads.AdView;
import com.mbrdzanebeli.Adapter.PictureAdapter;
import com.mbrdzanebeli.Adapter.SuggestAdapter;
import com.mbrdzanebeli.Entity.Model;
import com.mbrdzanebeli.Entity.Solution;
import com.mbrdzanebeli.Entity.Suggest;
import com.mbrdzanebeli.Public.Sound;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.mbrdzanebeli.Adapter.SolutionAdapter;
import com.mbrdzanebeli.Adapter.SolutionAdapter2;
import com.mbrdzanebeli.App.R;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import androidx.annotation.NonNull;

import hotchemi.android.rate.AppRate;

public class MainActivity extends AbstractActivity implements OnClickListener,
		OnItemClickListener {
private ImageButton click;
private TextView coins;
private RewardedAd mRewardedAd;
	private AdView mAdView;
	final static int BONUS = 1,  COIN_REVEAL = 10;//COIN_REMOVE = 10,;
	final static boolean DEV = false;
	Sound sound = new Sound();
	GridView gv1, gv2, gv3;
	SolutionAdapter adtSolution;
	SuggestAdapter adtSuggest;
	PictureAdapter adtPicture;
	TextView tvLevel, tvCoin;
	ImageView ivzoom;
	RelativeLayout rzoom;
	Dialog dialog;
	Model model;
	List<Solution> listSolution = new ArrayList<Solution>();
	List<Suggest> listSuggest = new ArrayList<Suggest>();
	List<Model> listModel = new ArrayList<Model>();
    Button reklama;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);


		AppRate.with(this)
				.setInstallDays(1) // default 10, 0 means install day.
				.setLaunchTimes(3) // default 10
				.setRemindInterval(2) // default 1
				.monitor();

// Show a dialog if meets conditions
		AppRate.showRateDialogIfMeetsConditions(this);
		///AppRate.with(this).showRateDialog(this);






		init();

		MobileAds.initialize(this, new OnInitializationCompleteListener() {
			@Override
			public void onInitializationComplete(InitializationStatus initializationStatus) {
			}
		});

rewAds();
		click = findViewById(R.id.reklama);///////////////////rewardedisღილაკი
		coins = findViewById(R.id.tvCoin);
		click.setOnClickListener(new OnClickListener() {
	@Override
	public void onClick(View v) {
		if (mRewardedAd != null) {
			Activity activityContext = MainActivity.this;
			mRewardedAd.show(activityContext, new OnUserEarnedRewardListener() {
				@Override
				public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
					// Handle the reward.
					int rewardAmount = rewardItem.getAmount();
					String rewardType = rewardItem.getType();

					String rew = String.valueOf(10+coin);////////////////////////////////rewarded
					coin+= rewardAmount;//////////////////////////////////////////////rewarded
					coins.setText(rew);
					mRewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
						@Override
						public void onAdDismissedFullScreenContent() {

							rewAds();
							mRewardedAd = null;
							super.onAdDismissedFullScreenContent();
						}
					});
				}
			});
		} else {
			Toast.makeText(MainActivity.this,"ads not loaded check internet", Toast.LENGTH_SHORT).show();
		}

	}

});


	}
	public void rewAds(){
		AdRequest adRequest = new AdRequest.Builder().build();
		RewardedAd.load(this, getString(R.string.rewarded),
				adRequest, new RewardedAdLoadCallback() {
					@Override
					public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
						// Handle the error.
						mRewardedAd = null;
					}

					@Override
					public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
						mRewardedAd = rewardedAd;
					}
				});

	}

	protected void init() {
		level = pre.getInt(KEY_LEVEL, 1);
		coin = pre.getInt(KEY_COIN, 80);
		poolId = pre.getInt(KEY_POOLID, 1);

		String sModdel = pre.getString(KEY_MODELS, null);
		String keyword = pre.getString(KEY_WORD, null);
		String so = pre.getString(KEY_SOLUTION, null);
		String sg = pre.getString(KEY_SUGGEST, null);

		listModel.addAll(Arrays.asList(gson.fromJson(sModdel, Model[].class)));
		if (
				listModel.size() - 1 > 0)
			model = listModel.get(r.nextInt(listModel.size() - 1));

		if (keyword != null) {
			listSolution.addAll(Arrays.asList(gson.fromJson(so,
					Solution[].class)));
			listSuggest
					.addAll(Arrays.asList(gson.fromJson(sg, Suggest[].class)));
			for (Model m : listModel) {
				if (m.getSolution().equals(keyword)) {
					this.model = m;

				}

			}
		}

		run();


	}


	private void run() {
		setContentView(R.layout.activity_main);
		Log.d("SOLUTION", model.getSolution() + "es raaris" + poolId);
		if (DEV) {
			ColorDrawable dialogColor = new ColorDrawable(Color.RED);

			Toast.makeText(getApplicationContext(), model.getSolution(),
					Toast.LENGTH_SHORT).show();


		}

		tvLevel = (TextView) findViewById(R.id.tvLevel);
		tvCoin = (TextView) findViewById(R.id.tvCoin);
		rzoom = (RelativeLayout) findViewById(R.id.rZoom);
		ivzoom = (ImageView) findViewById(R.id.ivZoom);

		gv1 = (GridView) findViewById(R.id.gv1);
		gv2 = (GridView) findViewById(R.id.gv2);
		gv3 = (GridView) findViewById(R.id.gv3);

		gv1.setOnItemClickListener(this);
		gv2.setOnItemClickListener(this);
		gv3.setOnItemClickListener(this);
		ivzoom.setOnClickListener(this);
		rzoom.setOnClickListener(this);

		///findViewById(R.id.ivBack).setOnClickListener(this);
		///findViewById(R.id.ivRemove).setOnClickListener(this);
		findViewById(R.id.ivReveal).setOnClickListener(this);
		///findViewById(R.id.button99).setOnClickListener(this);

		adtPicture = new PictureAdapter(this, model.getId());
		adtSolution = new SolutionAdapter(this, model.getSolution(),
				listSolution);
		adtSuggest = new SuggestAdapter(this, model.getSolution(), listSuggest);

		if (adtPicture != null)
			gv1.setAdapter(adtPicture);
		if (adtSolution != null)
			gv2.setAdapter(adtSolution);
		if (adtSuggest != null)
			gv3.setAdapter(adtSuggest);

		// Update level va coin
		tvLevel.setText(level + "");
		tvCoin.setText(coin + "");

		MobileAds.initialize(this, new OnInitializationCompleteListener() {
			@Override
			public void onInitializationComplete(InitializationStatus initializationStatus) {
			}
		});
	}


	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

		switch (parent.getId()) {
			case R.id.gv1:
				ivzoom.setImageResource(adtPicture.getPicId(position));
				rzoom.setVisibility(View.VISIBLE);
				gv1.setVisibility(View.INVISIBLE);
				break;
			case R.id.gv2:
				String text = ((TextView) v.findViewById(R.id.tvSolution))
						.getText().toString();
				if (!text.isEmpty() && !adtSolution.isRevealed(position)) {
					///sound.play("no", context);
					int tag = adtSolution.getItem(position).getTag();
					adtSuggest.show(tag);
					adtSolution.remove(position);


				}

				break;

			case R.id.gv3:
				String s = ((TextView) v.findViewById(R.id.tvSuggest)).getText()
						.toString();
				if (!adtSolution.isFull() && !s.isEmpty()) {
				///	sound.play("click", context);
					adtSolution.add(s.charAt(0), position);
					adtSuggest.hidden(position);
				}
				this.onCheck();
				break;

		}
	}

	@Override
	public void onClick(View v) {
		///sound.play("click", context);
		switch (v.getId()) {
			case R.id.ivZoom:
				gv1.setVisibility(View.VISIBLE);
				rzoom.setVisibility(View.INVISIBLE);
				break;
			//case R.id.ivBack:
			//	startActivity(new Intent(getApplicationContext(),
			///	///		BootstrapActivity.class));
			///break;
			///case R.id.ivRemove:
			///this.showRemoveDialog();
			///break;
			///case R.id.showads:
			//this.showTraki();
			///break;
		//	case R.id.button99:////////////////////////////////////////////////////////////////////////////////////////GETMORECOINS
		///		this.showTraki();
				///break;
			case R.id.btnContinue:
				dialog.dismiss();
				this.nextModel();
				break;
			case R.id.ivReveal:
				//dialog.dismiss();
				if (!subCoin(COIN_REVEAL)) {
				} else {
					if (!adtSolution.isFull()) {
						adtSuggest.hidden(adtSolution.autoInsert());
					} else {
						Solution so = adtSolution.autoReplace();
						adtSuggest.show(so.getTag());
						adtSuggest.hidden(so.getSolution());
					}
					this.onCheck();
				}
				break;
			///case R.id.bRemove:
			///dialog.dismiss();
			///if (subCoin(COIN_REMOVE) && adtSuggest.removable()) {
			///adtSuggest.remove();

		//	break;
			//case R.id.bCancel:
			//	dialog.dismiss();
				//break;
		}
	}

	private void nextModel() {
		// Thuong Coin va tang level
		coin += BONUS;
		level+=poolId;

		// Tang poolId neu xoa het model
		if (listModel.isEmpty()) {
			poolId++;
			listModel = jp.getData(poolId);
		} else if (listModel.contains(model)) {
			listModel.remove(model);
			listSolution.clear();
			listSuggest.clear();
		}

		// Load model moi
		if (listModel.size() - 1 > 0)
			model = listModel.get(r.nextInt(listModel.size() - 1));

		// Reload lai toan bo layout
		run();
	}

	private void onCheck() {
		// So khop cau tra loi voi dap an
		if (adtSolution.isMatch()) {
			this.showContinueDialog();
		}
	}


	private boolean subCoin(int coin) {
		if (this.coin < coin) {
			return false;
		} else {
			this.coin -= coin;
			tvCoin.setText("" + this.coin);
			return true;

		}

	}

	private void showContinueDialog() {
		sound.play("applause", context);
		dialog = new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
		dialog.setCancelable(false);//// ამის დახმარებით ითიშება უკან გამოსვლა დიალოგში ბაქ ბუტონი რა

		//dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

		ColorDrawable dialogColor = new ColorDrawable(Color.BLACK);
		dialogColor.setAlpha(1);
		dialog.getWindow().setBackgroundDrawable(dialogColor);

		dialog.setContentView(R.layout.dialog_continue);
		dialog.findViewById(R.id.btnContinue).setOnClickListener(this);
		GridView gv = dialog.findViewById(R.id.gvContinue);


		new SolutionAdapter2(this, model.getSolution());

		// gv.setAdapter(adt);
		///gv.setVisibility(View.INVISIBLE);

		TextView tv = dialog.findViewById(R.id.tvResult);
		String[] kw = model.getSolution().split("xxxxxxxxxxxxxx");
		StringBuilder keyword = new StringBuilder();
		for (String c : kw) {
			keyword.append(c).append(" ");
		}
		tv.setText(keyword.toString());
		dialog.show();
	}

	//private void showRevealDialog() {
	//	dialog = new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar);
	//	ColorDrawable dialogColor = new ColorDrawable(Color.BLACK);
	//	dialogColor.setAlpha(45);
	//	dialog.getWindow().setBackgroundDrawable(dialogColor);
	//	dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
	//	dialog.setContentView(R.layout.dialog_reveal);
	//	dialog.findViewById(R.id.bReveal).setOnClickListener(this);
	///	dialog.findViewById(R.id.bCancel).setOnClickListener(this);
	//	dialog.show();
	//}
///	private void showTraki() {
	//	dialog = new Dialog(this, android.R.style.Theme_NoTitleBar_Fullscreen);
	//	ColorDrawable dialogColor = new ColorDrawable(Color.GREEN);
	//	dialogColor.setAlpha(100);
		//dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
	//	dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
	//	dialog.setContentView(R.layout.traki);
	//	//dialog.findViewById(R.id.reklama).setOnClickListener(this);
	///	dialog.findViewById(R.id.bCancel).setOnClickListener(this);

	///	dialog.show();

	//private void showRemoveDialog() {
	//	dialog = new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar);
	//	ColorDrawable dialogColor = new ColorDrawable(Color.BLACK);
	//	//dialogColor.setAlpha(1);
	//	dialog.getWindow().setBackgroundDrawable(dialogColor);
	//	dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
	//	dialog.setContentView(R.layout.dialog_remove);
	//	dialog.findViewById(R.id.bRemove).setOnClickListener(this);
	//	dialog.findViewById(R.id.bCancel).setOnClickListener(this);
	//	dialog.show();
	///}

	@Override
	protected void onStop() {
		saveAll();
		super.onStop();
	}

	@Override
	protected void onPause() {
		saveAll();
		super.onPause();

	}


	void saveAll() {
		String so = gson.toJson(adtSolution.getSolutions());
		String sg = gson.toJson(adtSuggest.getSuggests());

		String models = gson.toJson(listModel.toArray(new Model[listModel
				.size()]));

		if (!(so.isEmpty() && sg.isEmpty() && models.isEmpty())) {
			pre = getSharedPreferences(KEY_FILE, MODE_PRIVATE);
			SharedPreferences.Editor editor = pre.edit();
			editor.putInt(KEY_LEVEL, level);
			editor.putInt(KEY_COIN, coin);
			editor.putInt(KEY_POOLID, poolId);

			editor.putString(KEY_SOLUTION, so);
			editor.putString(KEY_SUGGEST, sg);
			editor.putString(KEY_WORD, model.getSolution());
			editor.putString(KEY_MODELS, models);
			editor.commit();
		}


	}

}





