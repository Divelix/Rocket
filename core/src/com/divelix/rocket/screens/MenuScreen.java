package com.divelix.rocket.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.divelix.rocket.AdHandler;
import com.divelix.rocket.Main;
import com.divelix.rocket.managers.Assets;

import static com.divelix.rocket.managers.Assets.prefs;
import static com.divelix.rocket.managers.Assets.skin;

/**
 * Created by Sergei Sergienko on 05.02.2017.
 */

public class MenuScreen implements Screen {

    private static final String TAG = "MenuScreen";
    private static final int LOGO_WIDTH = 400;
    private static final int PLAY_BTN_SIZE = 240;
    private static final int MENU_BTN_SIZE = 80;
    private static final int STAR_SIZE = 75;
    private static final int STAR_Y = 150;
    private static final int AD_VIDEO_DELAY = 60;//delay in seconds

    private Game game;
    private AdHandler handler;

    public static boolean startCounting;
    public static boolean isLoaded;
    public static long startTime;
    private long adBreakTimer;
    private int bestScore;
    private int starsCount;
    private Viewport view;
    private Stage stage;
    private Label starsCountLabel;
    private PopUp popUp;
    private Dialog dialog;

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
        Gdx.app.log(TAG, "show()");

        view = new FillViewport(Main.WIDTH, Main.HEIGHT);

        Label bestScoreLabel = new Label("Best Score: " + bestScore, skin);

//        Image logo = new Image(Resource.rocketLogo);
        Image logo = new Image(Assets.skinPack.findRegion("rocketLogo"));
        float aspectRatio = logo.getHeight() / logo.getWidth();
//        logo.setSize(LOGO_WIDTH, LOGO_WIDTH * aspectRatio);

        ImageButton playBtn = new ImageButton(skin, "playBtn");
        playBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                playBtnClicked();
            }
        });

        Image star = new Image(Assets.gamePack.findRegion("star"));
        star.setBounds(Main.WIDTH/2-STAR_SIZE/2, STAR_Y, STAR_SIZE, STAR_SIZE);
        starsCountLabel = new Label(String.valueOf(starsCount), skin, "yellowBigFont");
        starsCountLabel.setPosition(Main.WIDTH/2-starsCountLabel.getWidth()/2, star.getY()-starsCountLabel.getHeight());


        ImageButton adsBtn = new ImageButton(skin, "adsBtn");
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
        int topMargin = 20;
        int sideMargin = 15;
        menuButtonsTable.add(adsBtn).width(MENU_BTN_SIZE).height(MENU_BTN_SIZE).pad(topMargin, sideMargin, 0, sideMargin);
        menuButtonsTable.add(shopBtn).width(MENU_BTN_SIZE).height(MENU_BTN_SIZE).pad(topMargin, sideMargin, 0, sideMargin);
        menuButtonsTable.add(rateBtn).width(MENU_BTN_SIZE).height(MENU_BTN_SIZE).pad(topMargin, sideMargin, 0, sideMargin);
//        timerLabel = new Label(String.valueOf(AD_VIDEO_DELAY), new Label.LabelStyle(Resource.robotoFont, Color.YELLOW));
//        timerLabel.setPosition(110, 110);
        popUp = new PopUp(130, 110);
//        popUp.setVisible(false);

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
//        stage.setDebugAll(true);

        Gdx.input.setInputProcessor(stage);
    }

    private void playBtnClicked() {
        Gdx.app.log(TAG, "PlayBtn clicked");
        stage.addAction(Actions.sequence(Actions.fadeOut(0.1f), Actions.run(new Runnable() {
            @Override
            public void run() {
                game.setScreen(new PlayScreen(game, handler));
            }
        })));
    }

    private void adsBtnClicked() {
        Gdx.app.log(TAG, "AdsBtn clicked");
        if(!startCounting)
            handler.showAds(true);
    }

    private void shopBtnClicked() {
        Gdx.app.log(TAG, "ShopBtn clicked");
        stage.addAction(Actions.sequence(Actions.fadeOut(0.1f), Actions.run(new Runnable() {
            @Override
            public void run() {
                game.setScreen(new ShopScreen(game, handler));
            }
        })));
    }

    private void rateBtnClicked() {
        Gdx.net.openURI("https://play.google.com/store/apps/details?id=com.divelix.rocket");//TODO change (maybe)
    }

    @Override
    public void render(float delta) {
        if(isLoaded && popUp.getStage() == null) {
            stage.addActor(popUp);
        }
        adBreakTimer = TimeUtils.timeSinceMillis(startTime) / 1000;
        if(adBreakTimer >= AD_VIDEO_DELAY) {
            startCounting = false;
        }

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
        starsCountLabel.setText(String.valueOf(prefs.getInteger("stars")));
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

    private class PopUp extends Group {
        Image bgBalloon;
        Label text;
        Image star;

        private PopUp(int x, int y) {
            this.setSize(115, 65);
            this.setPosition(x - 57, y);
            bgBalloon = new Image(Assets.skinPack.findRegion("popUp"));
            bgBalloon.setSize(this.getWidth(), this.getHeight());
            text = new Label("Get 50", skin, "yellowSmallFont");
            text.setPosition(bgBalloon.getX() + 10, bgBalloon.getY() + 24);
            star = new Image(Assets.gamePack.findRegion("star"));
            star.setScale(0.2f);
            star.setPosition(text.getX() + text.getWidth() + 3, text.getY() + 3);

            this.addActor(bgBalloon);
            this.addActor(text);
            this.addActor(star);
        }

        @Override
        public void act(float delta) {
            super.act(delta);
            if(startCounting) {
                if(star.getStage() != null) star.remove();
                text.setText(String.valueOf(AD_VIDEO_DELAY - adBreakTimer));
                text.setX(bgBalloon.getX() + 45);
            } else {
                if(star.getStage() == null) {
                    text.setText("Get 50");
                    text.setX(bgBalloon.getX() + 10);
                    this.addActor(star);
                }
            }
        }
    }
}
