package com.divelix.rocket;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.divelix.rocket.managers.Assets;
import com.divelix.rocket.screens.MenuScreen;

import static com.divelix.rocket.managers.Assets.prefs;

public class Main extends Game {
	public static final String TITLE = "Rocket";
	private static final String TAG = "Main";
	public static final int WIDTH = 480;
	public static final int HEIGHT = 800;

	private AdHandler handler;

	public Main(AdHandler handler) {
		this.handler = handler;
	}

	@Override
	public void create () {
		Gdx.app.log(TAG, "create()");
		Assets.load();
//		prefs.putInteger("bestScore", 0);
//		prefs.putBoolean("Alien", false);
//		prefs.putBoolean("Despicable", false);
//		prefs.putBoolean("Shuttle", false);
//		prefs.putBoolean("Colibri", false);
//		prefs.putBoolean("Lemon", false);
//		prefs.putBoolean("Hawk", false);
//		prefs.putBoolean("Eagle", false);
//		prefs.putBoolean("Raven", false);
		if(!prefs.getBoolean("Original"))
			prefs.putBoolean("Original", true);
		if(!prefs.getBoolean(prefs.getString("ActiveRocket")) || prefs.getString("ActiveRocket").equals(""))
			prefs.putString("ActiveRocket", "Original");
		prefs.flush();
		setScreen(new MenuScreen(this, handler));
	}

	@Override
	public void pause() {
		Gdx.app.log(TAG, "pause()");
		super.pause();
	}

	@Override
	public void resume() {
		Gdx.app.log(TAG, "resume()");
		super.resume();
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void resize(int width, int height) {
		Gdx.app.log(TAG, "resize()");
		super.resize(width, height);
	}
	
	@Override
	public void dispose () {
		Gdx.app.log(TAG, "dispose()");
		Assets.dispose();
		super.dispose();
	}
}
