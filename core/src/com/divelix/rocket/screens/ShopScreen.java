package com.divelix.rocket.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
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
    private Table table;
    private ScrollPane scrollPane;

    public ShopScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        view = new FillViewport(Main.WIDTH, Main.HEIGHT);
        stage = new Stage(view);
        table = new Table();
        table.setSize(Main.WIDTH - Main.WIDTH/10, Main.HEIGHT - 250);
//        table.align(Align.center|Align.top);
        table.setPosition(Main.WIDTH/20, 0);
//        table.setDebug(true);
        table.add(new ShopCell(Resource.rocket, 100)).width(100).pad(5);
        table.add(new ShopCell(Resource.doubleGreenRocket, 200)).width(100).pad(5);
        table.add(new ShopCell(Resource.greenRocket, 300)).width(100).pad(5);
        table.row();
        table.add(new ShopCell(Resource.shuttleRocket, 400)).width(100).pad(5);
        table.add(new ShopCell(Resource.smallRocket, 500)).width(100).pad(5);
        table.add(new ShopCell(Resource.yellowRocket, 600)).width(100).pad(5);
        table.row();
        table.add(new ShopCell(Resource.rocket, 100)).width(100).pad(5);
        table.add(new ShopCell(Resource.rocket, 100)).width(100).pad(5);
        table.add(new ShopCell(Resource.rocket, 100)).width(100).pad(5);
        table.row();
        table.add(new ShopCell(Resource.rocket, 100)).width(100).pad(5);
        table.add(new ShopCell(Resource.rocket, 100)).width(100).pad(5);
        table.add(new ShopCell(Resource.rocket, 100)).width(100).pad(5);
        scrollPane = new ScrollPane(table);
        scrollPane.debugAll();
        scrollPane.setSize(table.getWidth(), table.getHeight());
        scrollPane.setPosition(Main.WIDTH/20, 0);
        stage.addActor(scrollPane);
//----------------------------------------------------------------------
//        sc1 = new ShopCell(40, 500, Resource.rocket, 500);
//        sc2 = new ShopCell(190, 500, Resource.doubleGreenRocket, 500);
//        sc3 = new ShopCell(340, 500, Resource.greenRocket, 500);
//        sc4 = new ShopCell(40, 300, Resource.shuttleRocket, 500);
//        sc5 = new ShopCell(190, 300, Resource.smallRocket, 500);
//        sc6 = new ShopCell(340, 300, Resource.yellowRocket, 500);
//
//        stage.addActor(sc1);
//        stage.addActor(sc2);
//        stage.addActor(sc3);
//        stage.addActor(sc4);
//        stage.addActor(sc5);
//        stage.addActor(sc6);
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
