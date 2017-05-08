package com.divelix.rocket.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.divelix.rocket.Main;
import com.divelix.rocket.Resource;

/**
 * Created by Sergei Sergienko on 05.02.2017.
 */

public class MenuScreen implements Screen {
    private static final int LOGO_WIDTH = 480;
    private static final int PLAY_BTN_SIZE = 200;
    private static final int MENU_BTN_SIZE = 100;
    public static int bestScore;
    public static int starsCount;
    private Game game;
    private Viewport view;
    private Stage stage;

    public MenuScreen(Game game) {
        this.game = game;
        Preferences pref = Gdx.app.getPreferences("com.divelix.rocket");
        try {
            bestScore = pref.getInteger("bestScore");
            System.out.println("Best score: " + bestScore);
        }
        catch (NullPointerException e) {
            bestScore = 0;
            System.out.println("Best score: 0");
        }

        try {
            starsCount = pref.getInteger("stars");
            System.out.println("Stars: " + starsCount);
        }
        catch (NullPointerException e) {
            starsCount = 0;
            System.out.println("Stars: 0");
        }
    }

    @Override
    public void show() {
        System.out.println("Show method!");

        Label bestScoreLabel = new Label("Best Score: " + bestScore, new Label.LabelStyle(Resource.font, Color.RED));

        Image logo = new Image(Resource.rocketLogo);
        float aspectRatio = logo.getHeight()/logo.getWidth();
        logo.setBounds(Main.WIDTH/2 - LOGO_WIDTH /2, Main.HEIGHT - LOGO_WIDTH *aspectRatio*2, LOGO_WIDTH, LOGO_WIDTH *aspectRatio);
        Image playBtnUpImg = new Image(Resource.playBtnUp);
        Image playBtnDownImg = new Image(Resource.playBtnDown);
        Image shopBtnUpImg = new Image(Resource.shopBtnUp);
        Image shopBtnDownImg = new Image(Resource.shopBtnDown);

        ImageButton playBtn = new ImageButton(playBtnUpImg.getDrawable(), playBtnDownImg.getDrawable());
        playBtn.setBounds(Main.WIDTH / 2 - PLAY_BTN_SIZE / 2, Main.HEIGHT / 2.5f - PLAY_BTN_SIZE / 2, PLAY_BTN_SIZE, PLAY_BTN_SIZE);
        playBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                playBtnClicked();
            }
        });

        Image star = new Image(Resource.star);
        star.setBounds(200, 150, 50, 50);
        Label starsCountLabel = new Label(String.valueOf(starsCount), new Label.LabelStyle(Resource.font, Color.YELLOW));
        starsCountLabel.setPosition(250, 155);

        ImageButton shopBtn = new ImageButton(shopBtnUpImg.getDrawable(), shopBtnDownImg.getDrawable());
        shopBtn.setBounds(50, 50, MENU_BTN_SIZE, MENU_BTN_SIZE);
        shopBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                shopBtnClicked();
            }
        });

        Table table = new Table();
        table.top();
        table.setFillParent(true);
        table.add(bestScoreLabel).expandX().padTop(300);

        view = new ExtendViewport(Main.WIDTH, Main.HEIGHT);
        stage = new Stage(view);
        stage.addActor(logo);
        stage.addActor(table);
        stage.addActor(playBtn);
        stage.addActor(star);
        stage.addActor(starsCountLabel);
        stage.addActor(shopBtn);

        Gdx.input.setInputProcessor(stage);
    }

    private void playBtnClicked() {
        stage.addAction(Actions.sequence(Actions.fadeOut(0.1f), Actions.run(new Runnable() {
            @Override
            public void run() {
                game.setScreen(new PlayScreen(game));
            }
        })));
    }

    private void shopBtnClicked() {
        stage.addAction(Actions.sequence(Actions.fadeOut(0.1f), Actions.run(new Runnable() {
            @Override
            public void run() {
                game.setScreen(new ShopScreen(game));
            }
        })));
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
        Gdx.app.log("RocketLogs", "MenuScreen - resize");
        view.update(width, height, false);
    }

    @Override
    public void pause() {
        Gdx.app.log("RocketLogs", "MenuScreen - pause");
    }

    @Override
    public void resume() {
        Gdx.app.log("RocketLogs", "MenuScreen - resume");
    }

    @Override
    public void hide() {
        Gdx.app.log("RocketLogs", "MenuScreen - hide");
    }

    @Override
    public void dispose() {
        Gdx.app.log("RocketLogs", "MenuScreen - dispose");
        stage.dispose();
    }
}
