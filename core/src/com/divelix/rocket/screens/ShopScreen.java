package com.divelix.rocket.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.divelix.rocket.Main;
import com.divelix.rocket.Resource;

import static com.divelix.rocket.Resource.prefs;
import static com.divelix.rocket.Resource.skin;

/**
 * Created by Sergei Sergienko on 14.02.2017.
 */

public class ShopScreen implements Screen {

    private static final int STAR_SIZE = 75;

    private Game game;
    private Viewport view;
    private Stage stage;
    private Dialog dialog;
    private int starsCount;
    private static Label starsCountLabel;

    public ShopScreen(final Game game) {
        this.game = game;
        Gdx.input.setCatchBackKey(true);
    }

    @Override
    public void show() {
        Gdx.app.log("RocketLogs", "ShopScreen - show");
        view = new ExtendViewport(Main.WIDTH, Main.HEIGHT);
        stage = new Stage(view);
        Gdx.input.setInputProcessor(stage);
        try {
            starsCount = prefs.getInteger("stars");
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
        starsCountLabel = new Label(String.valueOf(starsCount), new Label.LabelStyle(Resource.robotoThinFont, Color.YELLOW));
        //Shop as a scrollable table
        Table rocketTable = new Table();

        //SHOP TABLE
        for (int i = 1; i <= Resource.rockets.size; i++) {
            rocketTable.add(new ShopCell(Resource.rockets.getKeyAt(i-1), i*100)).width(100).pad(15);
            if(i % 3 == 0) rocketTable.row();
        }

        ScrollPane scrollPane = new ScrollPane(rocketTable);

        //WHOLE SCREEN TABLE
        Table screenTable = new Table();
        screenTable.setFillParent(true);
        screenTable.setSize(Main.WIDTH, Main.HEIGHT);
//        table.setWidth(Main.WIDTH);
//        table.align(Align.center|Align.top);
//        table.setPosition(0, Main.HEIGHT);
        screenTable.top();
        screenTable.add(backBtn).height(50).left().expandX();
        screenTable.add(shopText).height(50).center().expandX();
        screenTable.add(playBtn).height(50).right().expandX();
        screenTable.row();
        screenTable.add(starImg).width(STAR_SIZE).height(STAR_SIZE).colspan(3).padTop(25);
        screenTable.row();
        screenTable.add(starsCountLabel).colspan(3);
        screenTable.row();
        screenTable.add(scrollPane).colspan(3).padTop(25);//(scrollable) table with rockets

//        table.debugAll();
        screenTable.setDebug(true);//TODO delete

        dialog = new Dialog("", skin) {
            @Override
            protected void result(Object object) {
                if(object.equals(true)) {
                    hide(null);
                }
            }
        };
        dialog.text("Not enough stars");
        dialog.button("Ok", true);
        dialog.key(Input.Keys.ENTER, true);
        dialog.key(Input.Keys.ESCAPE, false);

        dialog.pad(20, 20, 20, 20);
        dialog.getButtonTable().padTop(30);
//        dialog.getButtonTable().defaults().height(150);
//        dialog.getButtonTable().defaults().width(150);
        dialog.setModal(true);
        dialog.setMovable(false);
        dialog.setResizable(true);
        dialog.setVisible(true);

        stage.addActor(screenTable);
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
        Gdx.app.log("RocketLogs", "ShopScreen - pause");
    }

    @Override
    public void resume() {
        Gdx.app.log("RocketLogs", "ShopScreen - resume");
    }

    @Override
    public void hide() {
        Gdx.app.log("RocketLogs", "ShopScreen - hide");
        dispose();
    }

    @Override
    public void dispose() {
        Gdx.app.log("RocketLogs", "ShopScreen - dispose");
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
                    game.setScreen(new MenuScreen(game));
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
                    game.setScreen(new PlayScreen(game));
                }
            });
        }
    }

    private class ShopCell extends Group {
        private Label label;

        private ShopCell(final String rocketName, final int price) {
            final float WIDTH = 100;
            final float HEIGHT = 150;
            this.setSize(WIDTH, HEIGHT);
            Image cellBg = new Image(Resource.cellBg);
            cellBg.setSize(WIDTH, HEIGHT);
            Image rocket = new Image(Resource.rockets.get(rocketName));
            final Image shadow = new Image(Resource.rockets.get(rocketName));
            shadow.setColor(Color.BLACK);
            float aspectRatio = rocket.getWidth()/rocket.getHeight();
            rocket.setBounds(cellBg.getX() + WIDTH/2-(HEIGHT*0.3f*aspectRatio), cellBg.getY() + HEIGHT/3, HEIGHT*0.6f*aspectRatio, HEIGHT*0.6f);
            shadow.setBounds(cellBg.getX() + WIDTH/2-(HEIGHT*0.3f*aspectRatio), cellBg.getY() + HEIGHT/3, HEIGHT*0.6f*aspectRatio, HEIGHT*0.6f);
            label = new Label(String.valueOf(price), new Label.LabelStyle(Resource.robotoThinFont, Color.YELLOW));
            if(prefs.getBoolean(rocketName)) {
                label.setText("-");
            }
            label.setPosition(cellBg.getX() + WIDTH/2 - label.getWidth()/2, cellBg.getY() + HEIGHT/15);
            this.addActor(cellBg);
            this.addActor(rocket);
            if(!prefs.getBoolean(rocketName))
                this.addActor(shadow);
            this.addActor(label);
//        float aspectRatio = rocket.getWidth()/rocket.getHeight();
//        rocket.setBounds(getX() + WIDTH/2-(HEIGHT*0.3f*aspectRatio), getY() + HEIGHT/3, HEIGHT*0.6f*aspectRatio, HEIGHT*0.6f);
//        label.setPosition(getX() + WIDTH/7, getY() + HEIGHT/15);
            this.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if(!prefs.getBoolean(rocketName) && starsCount >= price) {
                        starsCount -= price;
                        prefs.putBoolean(rocketName, true);
                        Gdx.app.log("RocketPrefs", "New rocket - " + rocketName);
                        prefs.putString("ActiveRocket", rocketName);
                        Gdx.app.log("RocketPrefs", "ActiveRocket = " + rocketName);
                        prefs.putInteger("stars", starsCount);
                        prefs.flush();
                        label.setText("+");
                        label.setStyle(new Label.LabelStyle(Resource.robotoThinFont, Color.GREEN));
                        shadow.remove();
                        starsCountLabel.setText(String.valueOf(prefs.getInteger("stars")));
                    } else if(prefs.getBoolean(rocketName)) {
                        prefs.putString("ActiveRocket", rocketName);
                        Gdx.app.log("RocketPrefs", "ActiveRocket = " + rocketName);
                        label.setText("+");
                        label.setStyle(new Label.LabelStyle(Resource.robotoThinFont, Color.GREEN));
                    } else {
                        stage.addActor(dialog);
                        dialog.show(stage);
                    }
                }
            });
        }
    }
}
