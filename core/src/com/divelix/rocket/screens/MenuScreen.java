package com.divelix.rocket.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.divelix.rocket.AdHandler;
import com.divelix.rocket.Main;
import com.divelix.rocket.Resource;

import static com.divelix.rocket.Resource.prefs;
import static com.divelix.rocket.Resource.skin;

/**
 * Created by Sergei Sergienko on 05.02.2017.
 */

public class MenuScreen implements Screen {

    private static final String TAG = "MenuScreen";
    private static final int LOGO_WIDTH = 400;
    private static final int PLAY_BTN_SIZE = 240;
    private static final int MENU_BTN_SIZE = 70;
    private static final int STAR_SIZE = 75;
    private static final int STAR_Y = 150;
    private static final int AD_VIDEO_DELAY = 10;//delay in seconds

    private Game game;
    private AdHandler handler;

    public static boolean START_COUNTING;
    private int bestScore;
    private int starsCount;
    private Viewport view;
    private Stage stage;
    private ImageButton adsBtn;
    private Dialog dialog;
    private float adTimer;

    public MenuScreen(final Game game, AdHandler handler) {
        this.game = game;
        this.handler = handler;
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
        Gdx.app.log(TAG, "MenuScreen - show");

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

        adsBtn = new ImageButton(skin, "adsBtn");
        adsBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                adsBtnClicked();
            }
        });

        ImageButton shopBtn = new ImageButton(skin, "shopBtn");
        shopBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                shopBtnClicked();
            }
        });

        ImageButton rateBtn = new ImageButton(skin, "rateBtn");
        rateBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                rateBtnClicked();
            }
        });
        Table menuButtonsTable = new Table();
        menuButtonsTable.add(adsBtn).width(MENU_BTN_SIZE).height(MENU_BTN_SIZE).pad(20, 10, 0, 10);
        menuButtonsTable.add(shopBtn).width(MENU_BTN_SIZE).height(MENU_BTN_SIZE).pad(20, 10, 0, 10);
        menuButtonsTable.add(rateBtn).width(MENU_BTN_SIZE).height(MENU_BTN_SIZE).pad(20, 10, 0, 10);

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
        table.add(menuButtonsTable);

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
        stage.setDebugAll(true);

        Gdx.input.setInputProcessor(stage);
    }

    private void playBtnClicked() {
        stage.addAction(Actions.sequence(Actions.fadeOut(0.1f), Actions.run(new Runnable() {
            @Override
            public void run() {
                game.setScreen(new PlayScreen(game, handler));
            }
        })));
    }

    private void adsBtnClicked() {
        Gdx.app.log(TAG, "AdsBtn clicked");
        adTimer = 0;
        handler.showAds(true);
        adsBtn.setTouchable(Touchable.disabled);
    }

    private void shopBtnClicked() {
        stage.addAction(Actions.sequence(Actions.fadeOut(0.1f), Actions.run(new Runnable() {
            @Override
            public void run() {
                game.setScreen(new ShopScreen(game, handler));
            }
        })));
    }

    private void rateBtnClicked() {
        Gdx.net.openURI("https://play.google.com/store/apps/details?id=com.divelix.rocket");//TODO change
    }

    @Override
    public void render(float delta) {
        if(START_COUNTING) adTimer += delta;
        if(adTimer >= AD_VIDEO_DELAY) adsBtn.setTouchable(Touchable.enabled);

        Gdx.gl.glClearColor(0 / 255.0f, 156 / 255.0f, 225 / 255.0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        view.apply();
        stage.act(delta);
        stage.draw();
        if(Gdx.input.isKeyJustPressed(Input.Keys.BACK) || Gdx.input.isKeyJustPressed(Input.Keys.A)) {
            stage.addActor(dialog);
            dialog.show(stage);
        }
    }

    @Override
    public void resize(int width, int height) {
        Gdx.app.log(TAG, "resize()");
        view.update(width, height, false);
    }

    @Override
    public void pause() {
        Gdx.app.log(TAG, "pause()");
    }

    @Override
    public void resume() {
        Gdx.app.log(TAG, "resume()");
    }

    @Override
    public void hide() {
        Gdx.app.log(TAG, "hide()");
        dispose();
    }

    @Override
    public void dispose() {
        Gdx.app.log(TAG, "dispose()");
        stage.dispose();
    }
}
