package com.divelix.rocket.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
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

    private static final int STAR_SIZE = 75;

    private static Game game;
    private Viewport view;
    private Stage stage;

    public ShopScreen(Game game) {
        this.game = game;
        Gdx.input.setCatchBackKey(true);
    }

    @Override
    public void show() {
        view = new ExtendViewport(Main.WIDTH, Main.HEIGHT);
        stage = new Stage(view);
        Gdx.input.setInputProcessor(stage);
        Preferences pref = Gdx.app.getPreferences("com.divelix.rocket");
        int starsCount;
        try {
            starsCount = pref.getInteger("stars");
            System.out.println("Stars: " + starsCount);
        }
        catch (NullPointerException e) {
            starsCount = 0;
            System.out.println("Stars: 0");
        }
        //Top nav elements
        BackButton backBtn = new BackButton();
        Label shopText = new Label("SHOP", new Label.LabelStyle(Resource.robotoThinFont, Color.WHITE));
        PlayButton playBtn = new PlayButton();
        //Star image and count
        Image starImg = new Image(Resource.star);
        Label starsCountLabel = new Label(String.valueOf(starsCount), new Label.LabelStyle(Resource.robotoThinFont, Color.YELLOW));
        //Shop as a scrollable table
        Table rocketTable = new Table();

        //SHOP TABLE
        for (int i = 1; i <= Resource.rockets.size; i++) {
            rocketTable.add(new ShopCell(Resource.rockets.getKeyAt(i-1), Resource.rockets.getValueAt(i-1))).width(100).pad(15);
            if(i % 3 == 0) rocketTable.row();
        }

        ScrollPane scrollPane = new ScrollPane(rocketTable);

        //WHOLE SCREEN TABLE
        Table table = new Table();
        table.setFillParent(true);
        table.setSize(Main.WIDTH, Main.HEIGHT);
//        table.setWidth(Main.WIDTH);
//        table.align(Align.center|Align.top);
//        table.setPosition(0, Main.HEIGHT);
        table.top();
        table.add(backBtn).height(50).left().expandX();
        table.add(shopText).height(50).center().expandX();
        table.add(playBtn).height(50).right().expandX();
        table.row();
        table.add(starImg).width(STAR_SIZE).height(STAR_SIZE).colspan(3).padTop(25);
        table.row();
        table.add(starsCountLabel).colspan(3);
        table.row();
        table.add(scrollPane).colspan(3).padTop(25);//(scrollable) table with rockets

//        table.debugAll();
        table.setDebug(true);

        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0 / 255.0f, 156 / 255.0f, 225 / 255.0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
        if(Gdx.input.isKeyJustPressed(Input.Keys.BACK)) {
            stage.addAction(Actions.sequence(Actions.fadeOut(0.1f), Actions.run(new Runnable() {
                @Override
                public void run() {
                    game.setScreen(new MenuScreen(game));
                }
            })));
        }
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

    private class BackButton extends Group {
        private BackButton() {
            Image arrow = new Image(Resource.backArrow);
            float aspectRatio = arrow.getWidth()/arrow.getHeight();
            int arrowHeight = 50;
            arrow.setSize(arrowHeight*aspectRatio, arrowHeight);
            Label text = new Label("BACK", new Label.LabelStyle(Resource.robotoThinFont, Color.WHITE));
            text.setX(arrow.getX() + arrow.getWidth());
            setSize(text.getWidth()+arrow.getWidth(), arrowHeight);
            super.addActor(arrow);
            super.addActor(text);
            super.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    stage.addAction(Actions.sequence(Actions.fadeOut(0.1f), Actions.run(new Runnable() {
                        @Override
                        public void run() {
                            game.setScreen(new MenuScreen(game));
                        }
                    })));
                }
            });
        }
    }

    private class PlayButton extends Group {
        private PlayButton() {
            Image arrow = new Image(Resource.frontArrow);
            float aspectRatio = arrow.getWidth()/arrow.getHeight();
            int arrowHeight = 50;
            arrow.setSize(arrowHeight*aspectRatio, arrowHeight);
            Label text = new Label("PLAY", new Label.LabelStyle(Resource.robotoThinFont, Color.WHITE));
            text.setX(getX());
            arrow.setX(getX() + text.getWidth());
            setSize(text.getWidth()+arrow.getWidth(), arrowHeight);
            super.addActor(text);
            super.addActor(arrow);
            super.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    stage.addAction(Actions.sequence(Actions.fadeOut(0.1f), Actions.run(new Runnable() {
                        @Override
                        public void run() {
                            game.setScreen(new PlayScreen(game));
                        }
                    })));
                }
            });
        }
    }
}
