package com.divelix.rocket;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.divelix.rocket.screens.MenuScreen;

public class Main extends Game {
	public static final int WIDTH = 480;
	public static final int HEIGHT = 800;
	public static final String TITLE = "Rocket";
	
	@Override
	public void create () {
		Resource.load();
		final Preferences prefs = Gdx.app.getPreferences("com.divelix.rocket");
		prefs.putInteger("stars", 500);
		prefs.putBoolean("Alien", false);
		prefs.putBoolean("Despicable", false);
		prefs.putBoolean("Shuttle", false);
		prefs.putBoolean("Colibri", false);
		prefs.putBoolean("Lemon", false);
		prefs.putBoolean("Hawk", false);
		prefs.putBoolean("Eagle", false);
		prefs.putBoolean("Raven", false);
		if(!prefs.getBoolean("Original"))
			prefs.putBoolean("Original", true);
		if(!prefs.getBoolean(prefs.getString("ActiveRocket")) || prefs.getString("ActiveRocket").equals(""))
			prefs.putString("ActiveRocket", "Original");
		prefs.flush();
		setScreen(new MenuScreen(this));
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}
	
	@Override
	public void dispose () {
		Resource.dispose();
		super.dispose();
	}
}
