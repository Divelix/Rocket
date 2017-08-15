package com.divelix.rocket.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.divelix.rocket.Main;
import com.divelix.rocket.Resource;

import static com.divelix.rocket.Resource.prefs;
import static com.divelix.rocket.Resource.skin;

/**
 * Created by Sergei Sergienko on 05.02.2017.
 */

public class MenuScreen implements Screen {

    private static final int LOGO_WIDTH = 400;
    private static final int PLAY_BTN_SIZE = 240;
    private static final int MENU_BTN_SIZE = 70;
    private static final int STAR_SIZE = 75;
    private static final int STAR_Y = 150;
    private int bestScore;
    private int starsCount;
    private Game game;
    private Viewport view;
    private Stage stage;
    private Dialog dialog;

    public MenuScreen(final Game game) {
        this.game = game;
        Gdx.input.setCatchBackKey(true);
        try {
            bestScore = prefs.getInteger("bestScore");
            System.out.println("Best score: " + bestScore);
        }
        catch (NullPointerException e) {
            bestScore = 0;
            System.out.println("Best score: 0");
        }

        try {
            starsCount = prefs.getInteger("stars");
            System.out.println("Stars: " + starsCount);
        }
        catch (NullPointerException e) {
            starsCount = 0;
            System.out.println("Stars: 0");
        }
    }

    @Override
    public void show() {
        Gdx.app.log("RocketLogs", "MenuScreen - show");

        view = new FillViewport(Main.WIDTH, Main.HEIGHT);

        Label bestScoreLabel = new Label("Best Score: " + bestScore, new Label.LabelStyle(Resource.robotoFont, Color.RED));

        Image logo = new Image(Resource.rocketLogo);
        float aspectRatio = logo.getHeight() / logo.getWidth();
//        logo.setSize(LOGO_WIDTH, LOGO_WIDTH * aspectRatio);

        ImageButton playBtn = new ImageButton(skin, "playBtn");
        playBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                playBtnClicked();
            }
        });

        Image star = new Image(Resource.star);
        star.setBounds(Main.WIDTH/2-STAR_SIZE/2, STAR_Y, STAR_SIZE, STAR_SIZE);
        Label starsCountLabel = new Label(String.valueOf(starsCount), new Label.LabelStyle(Resource.robotoFont, Color.YELLOW));
        starsCountLabel.setPosition(Main.WIDTH/2-starsCountLabel.getWidth()/2, star.getY()-starsCountLabel.getHeight());

        ImageButton shopBtn = new ImageButton(skin, "shopBtn");
        shopBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                shopBtnClicked();
            }
        });

        Table table = new Table();
        table.setFillParent(true);
        table.top();
        table.add(logo).width(LOGO_WIDTH).height(LOGO_WIDTH*aspectRatio).padTop(50);
        table.row();
        table.add(bestScoreLabel).padTop(40);
        table.row();
        table.add(playBtn).width(PLAY_BTN_SIZE).height(PLAY_BTN_SIZE).padTop(40);
        table.row();
        table.add(star).width(STAR_SIZE).height(STAR_SIZE).padTop(25);
        table.row();
        table.add(starsCountLabel);
        table.row();
        table.add(shopBtn).width(MENU_BTN_SIZE).height(MENU_BTN_SIZE).padTop(20);

        dialog = new Dialog("", skin){
            @Override
            protected void result(Object object) {
                if(object.equals(true)) Gdx.app.exit();
            }
        };
        dialog.text("Exit?");
        dialog.button("Yes     ", true);
        dialog.button("     No", false);
//        dialog.key(Input.Keys.ENTER, true);
//        dialog.key(Input.Keys.ESCAPE, false);

        dialog.pad(20, 20, 20, 20);
        dialog.getButtonTable().padTop(30);
        dialog.getButtonTable().defaults().height(150);
        dialog.getButtonTable().defaults().width(150);
        dialog.setModal(true);
        dialog.setMovable(false);
        dialog.setResizable(true);
        dialog.setVisible(true);

        stage = new Stage(view);
        stage.addActor(table);
//        stage.setDebugAll(true);

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
        Gdx.gl.glClearColor(0 / 255.0f, 156 / 255.0f, 225 / 255.0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
        if(Gdx.input.isKeyJustPressed(Input.Keys.BACK)) {
            stage.addActor(dialog);
            dialog.show(stage);
        }
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
        dispose();
    }

    @Override
    public void dispose() {
        Gdx.app.log("RocketLogs", "MenuScreen - dispose");
        stage.dispose();
    }
}
