package com.divelix.rocket.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.divelix.rocket.Main;
import com.divelix.rocket.Resource;
import com.divelix.rocket.actors.ShopCell;
import com.divelix.rocket.actors.Star;

/**
 * Created by Sergei Sergienko on 14.02.2017.
 */

public class ShopScreen implements Screen {

    public static Game game;
    private Viewport view;
    private Stage stage;
    private Table table;
    private ScrollPane scrollPane;
    private Image topBar;
    private Preferences pref;
    public static int starsCount;
    private Label starText;
    private Image starImg;

    public ShopScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        view = new FillViewport(Main.WIDTH, Main.HEIGHT);
        stage = new Stage(view);
        Gdx.input.setInputProcessor(stage);
        pref = Gdx.app.getPreferences("com.divelix.rocket");
        try {
            starsCount = pref.getInteger("stars");
            System.out.println("Stars: " + starsCount);
        }
        catch (NullPointerException e) {
            starsCount = 0;
            System.out.println("Stars: 0");
        }
        topBar = new Image(Resource.topBar);
        topBar.setPosition(0, 650);
        starImg = new Image(Resource.star);
        starImg.setBounds(Main.WIDTH/2 - 50, 600, 50, 50);
        starText = new Label(String.valueOf(starsCount), new Label.LabelStyle(Resource.font, Color.YELLOW));
        starText.setPosition(Main.WIDTH/2, 600);
        table = new Table();
        table.setSize(Main.WIDTH - Main.WIDTH/10, Main.HEIGHT - 200);
//        table.align(Align.center|Align.top);
        table.setPosition(Main.WIDTH/20, 0);
//        table.setDebug(true);

        Array<TextureRegion> rockets = Resource.rockets;
        for (int i = 1; i <= rockets.size; i++) {
            table.add(new ShopCell(rockets.get(i-1), 300)).width(100).pad(10);
            if(i % 3 == 0) table.row();
        }

//        table.add(new ShopCell(Resource.rocket, 100)).width(100).pad(10);
//        table.add(new ShopCell(Resource.doubleGreenRocket, 200)).width(100).pad(10);
//        table.add(new ShopCell(Resource.greenRocket, 300)).width(100).pad(10);
//        table.row();
//        table.add(new ShopCell(Resource.shuttleRocket, 400)).width(100).pad(10);
//        table.add(new ShopCell(Resource.smallRocket, 500)).width(100).pad(10);
//        table.add(new ShopCell(Resource.yellowRocket, 600)).width(100).pad(10);
//        table.row();
//        table.add(new ShopCell(Resource.rocket, 100)).width(100).pad(10);
//        table.add(new ShopCell(Resource.rocket, 100)).width(100).pad(10);
//        table.add(new ShopCell(Resource.rocket, 100)).width(100).pad(10);
//        table.row();
//        table.add(new ShopCell(Resource.rocket, 100)).width(100).pad(10);
//        table.add(new ShopCell(Resource.rocket, 100)).width(100).pad(10);
//        table.add(new ShopCell(Resource.rocket, 100)).width(100).pad(10);

        scrollPane = new ScrollPane(table);
        scrollPane.debugAll();
        scrollPane.setSize(table.getWidth(), table.getHeight());
        scrollPane.setPosition(table.getX(), table.getY());

        stage.addActor(starImg);
        stage.addActor(starText);
        stage.addActor(topBar);
        stage.addActor(scrollPane);
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
