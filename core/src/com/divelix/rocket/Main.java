package com.divelix.rocket;

import com.badlogic.gdx.Game;
import com.divelix.rocket.screens.MenuScreen;

public class Main extends Game {
	public static final int WIDTH = 480;
	public static final int HEIGHT = 800;
	public static final String TITLE = "Rocket";
	
	@Override
	public void create () {
		Resource.load();
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
