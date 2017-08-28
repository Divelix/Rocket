package com.divelix.rocket;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.divelix.rocket.screens.MenuScreen;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

public class AndroidLauncher extends AndroidApplication implements RewardedVideoAdListener, AdHandler {
	private static final String TAG = "AndroidLauncher";
	private RewardedVideoAd video;

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if(video.isLoaded()) {
				video.show();
			}
		}
	};

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useAccelerometer = false;
		config.useCompass = false;
		config.useImmersiveMode = true;//Hides navigation bar
		initialize(new Main(this), config);

		video = MobileAds.getRewardedVideoAdInstance(this);
		video.setRewardedVideoAdListener(this);
		loadAd();
	}

	private void loadAd() {
		Gdx.app.log(TAG, "Loading ad video...");
		if(!video.isLoaded()) {
			video.loadAd("ca-app-pub-3940256099942544/5224354917", new AdRequest.Builder().build());
		}
	}

	@Override
	public void onRewardedVideoAdLoaded() {
		Gdx.app.log(TAG, "AdLoaded()");
	}

	@Override
	public void onRewardedVideoAdOpened() {
		Gdx.app.log(TAG, "AdOpened()");
	}

	@Override
	public void onRewardedVideoStarted() {
		Gdx.app.log(TAG, "AdStarted()");
	}

	@Override
	public void onRewardedVideoAdClosed() {
		Gdx.app.log(TAG, "AdClosed()");
		MenuScreen.START_COUNTING = true;
		loadAd();
	}

	@Override
	public void onRewarded(RewardItem rewardItem) {
		Gdx.app.log(TAG, "onRewarded()");
	}

	@Override
	public void onRewardedVideoAdLeftApplication() {
		Gdx.app.log(TAG, "AdLeftApplication()");
	}

	@Override
	public void onRewardedVideoAdFailedToLoad(int i) {
		Gdx.app.log(TAG, "AdFailedToLoad() " + i);
	}

	@Override
	public void showAds(boolean show) {
		handler.sendEmptyMessage(1);
	}
}
