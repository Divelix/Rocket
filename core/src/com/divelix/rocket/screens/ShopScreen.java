package com.divelix.rocket.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.divelix.rocket.AdHandler;
import com.divelix.rocket.Main;
import com.divelix.rocket.managers.Assets;

import java.util.ArrayList;

import static com.divelix.rocket.managers.Assets.prefs;
import static com.divelix.rocket.managers.Assets.skin;

/**
 * Created by Sergei Sergienko on 14.02.2017.
 */

public class ShopScreen implements Screen {

    private static final String TAG = "ShopScreen";
    private static final int STAR_SIZE = 75;

    private Game game;
    private AdHandler handler;

    private Viewport view;
    private Stage stage;
    private ArrayList<ShopCell> shopCells;
    private Dialog dialog;
    private int starsCount;
    private static Label starsCountLabel;
    private String activeRocket;

    public ShopScreen(final Game game, AdHandler handler) {
        this.game = game;
        this.handler = handler;
        Gdx.input.setCatchBackKey(true);
    }

    @Override
    public void show() {
        Gdx.app.log(TAG, "show()");
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
        activeRocket = prefs.getString("ActiveRocket");
        //Top nav elements
        BackButton backBtn = new BackButton();
        Label shopText = new Label("SHOP", skin, "whiteBigFont");
        PlayButton playBtn = new PlayButton();
        //Star image and count
        Image starImg = new Image(Assets.gamePack.findRegion("star"));
        starsCountLabel = new Label(String.valueOf(starsCount), skin, "yellowBigFont");
        //Shop as a scrollable table
        Table rocketTable = new Table();

        //SHOP TABLE
        shopCells = new ArrayList<ShopCell>();
        for (int i = 1; i <= Assets.rockets.size; i++) {
            shopCells.add(new ShopCell(Assets.rockets.getKeyAt(i-1), i*100));
            rocketTable.add(shopCells.get(i-1)).width(100).pad(15);
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
//        screenTable.setDebug(true);

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
        if(Gdx.input.isKeyJustPressed(Input.Keys.BACK) || Gdx.input.isKeyJustPressed(Input.Keys.A)) {
            stage.addAction(Actions.sequence(Actions.fadeOut(0.1f), Actions.run(new Runnable() {
                @Override
                public void run() {
                    game.setScreen(new MenuScreen(game, handler));
                }
            })));
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

    private class BackButton extends Group {
        private BackButton() {
            Image arrow = new Image(Assets.skinPack.findRegion("backArrow"));
            float aspectRatio = arrow.getWidth()/arrow.getHeight();
            int arrowHeight = 50;
            arrow.setSize(arrowHeight*aspectRatio, arrowHeight);
            Label text = new Label("BACK", skin, "whiteBigFont");
            text.setX(arrow.getX() + arrow.getWidth());
            setSize(text.getWidth()+arrow.getWidth(), arrowHeight);
            this.addActor(arrow);
            this.addActor(text);
            this.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    game.setScreen(new MenuScreen(game, handler));
                }
            });
        }
    }

    private class PlayButton extends Group {
        private PlayButton() {
            Image arrow = new Image( Assets.skinPack.findRegion("frontArrow"));
            float aspectRatio = arrow.getWidth()/arrow.getHeight();
            int arrowHeight = 50;
            arrow.setSize(arrowHeight*aspectRatio, arrowHeight);
            Label text = new Label("PLAY", skin, "whiteBigFont");
            text.setX(getX());
            arrow.setX(getX() + text.getWidth());
            setSize(text.getWidth()+arrow.getWidth(), arrowHeight);
            this.addActor(text);
            this.addActor(arrow);
            this.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    game.setScreen(new PlayScreen(game, handler));
                }
            });
        }
    }

    private class ShopCell extends Group {
        private Label label;
        final Image cellBg;
        String TITLE;
        final float WIDTH = 100;
        final float HEIGHT = 170;

        private ShopCell(final String rocketName, final int price) {
            TITLE = rocketName;
            this.setSize(WIDTH, HEIGHT);
            cellBg = new Image(Assets.skinPack.findRegion("cellBgWhite"));
            if(rocketName.equals(activeRocket))
                cellBg.setDrawable(new TextureRegionDrawable(Assets.skinPack.findRegion("cellBgWhite")));
            cellBg.setSize(WIDTH, HEIGHT);
            Image rocket = new Image(Assets.rockets.get(rocketName));
            final Image shadow = new Image(Assets.rockets.get(rocketName));
            shadow.setColor(Color.BLACK);
            float aspectRatio = rocket.getWidth()/rocket.getHeight();
            rocket.setBounds(cellBg.getX() + WIDTH/2-(HEIGHT*0.3f*aspectRatio), cellBg.getY() + HEIGHT/3, HEIGHT*0.6f*aspectRatio, HEIGHT*0.6f);
            shadow.setBounds(cellBg.getX() + WIDTH/2-(HEIGHT*0.3f*aspectRatio), cellBg.getY() + HEIGHT/3, HEIGHT*0.6f*aspectRatio, HEIGHT*0.6f);
            shadow.setOrigin(shadow.getWidth()/2, shadow.getHeight()/2);
            label = new Label(String.valueOf(price), skin, "yellowBigFont");
            label.setPosition(cellBg.getX() + WIDTH/2 - label.getWidth()/2, cellBg.getY() + HEIGHT/15);
            this.addActor(cellBg);
            this.addActor(rocket);
            this.addActor(label);
            this.addActor(shadow);
            if(prefs.getBoolean(rocketName)) {
                label.setText("");
                shadow.remove();
            }
            this.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if(!prefs.getBoolean(rocketName) && starsCount >= price) {
                        starsCount -= price;
                        prefs.putBoolean(rocketName, true);
                        Gdx.app.log(TAG, "New rocket - " + rocketName);
                        prefs.putInteger("stars", starsCount);
                        prefs.flush();
                        makeRocketActive(rocketName);
                        label.setText("");
                        shadow.addAction(Actions.parallel(
                                Actions.fadeOut(0.2f),
                                Actions.scaleBy(0.3f, 0.3f, 0.2f)));
                        starsCountLabel.setText(String.valueOf(prefs.getInteger("stars")));
                    } else if(prefs.getBoolean(rocketName)) {
                        Gdx.app.log(TAG, "Switch ActiveRocket to " + rocketName);
                        makeRocketActive(rocketName);
                    } else {
                        stage.addActor(dialog);
                        dialog.show(stage);
                    }
                    for(ShopCell cell : shopCells)
                        if(!cell.getTitle().equals(activeRocket))
                            cell.cellBg.setDrawable(new TextureRegionDrawable(Assets.skinPack.findRegion("cellBgWhite")));
                }
            });
        }
        private String getTitle() { return TITLE;}

        private void makeRocketActive(String rocketName) {
            activeRocket = rocketName;
            prefs.putString("ActiveRocket", rocketName);
            prefs.flush();
            Gdx.app.log(TAG, "ActiveRocket = " + rocketName);
            cellBg.setDrawable(new TextureRegionDrawable(Assets.skinPack.findRegion("cellBgYellow")));
        }
    }
}