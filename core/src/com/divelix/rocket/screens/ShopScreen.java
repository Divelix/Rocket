package com.divelix.rocket.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.divelix.rocket.Main;
import com.divelix.rocket.Resource;
import com.divelix.rocket.actors.ShopCell;

/**
 * Created by Sergei Sergienko on 14.02.2017.
 */

public class ShopScreen implements Screen {

    private Game game;
    private Viewport view;
    private Stage stage;
    private ShopCell sc1, sc2, sc3, sc4, sc5, sc6;

    public ShopScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        view = new FillViewport(Main.WIDTH, Main.HEIGHT);
        stage = new Stage(view);

        sc1 = new ShopCell(40, 500, Resource.rocket, 500);
        sc2 = new ShopCell(190, 500, Resource.doubleGreenRocket, 500);
        sc3 = new ShopCell(340, 500, Resource.greenRocket, 500);
        sc4 = new ShopCell(40, 300, Resource.shuttleRocket, 500);
        sc5 = new ShopCell(190, 300, Resource.smallRocket, 500);
        sc6 = new ShopCell(340, 300, Resource.yellowRocket, 500);

        stage.addActor(sc1);
        stage.addActor(sc2);
        stage.addActor(sc3);
        stage.addActor(sc4);
        stage.addActor(sc5);
        stage.addActor(sc6);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(130 / 255.0f, 200 / 255.0f, 225 / 255.0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        Gdx.app.log("RocketLogs", "ShopScreen - resize");
        view.update(width, height, false);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
