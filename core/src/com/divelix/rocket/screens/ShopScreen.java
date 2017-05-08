package com.divelix.rocket.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
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

    public static Game game;
    private Viewport view;
    private Stage stage;

    public ShopScreen(Game game) {
        this.game = game;
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
        //Back button
//        final int BACK_ARROW_HEIGHT = 100;
//        Image backArrowImg = new Image(Resource.backArrow);
//        ImageButton backBtn = new ImageButton(backArrowImg.getDrawable(), backArrowImg.getDrawable());
//        float aspectRatio = backArrowImg.getWidth()/backArrowImg.getHeight();
//        backBtn.setSize(BACK_ARROW_HEIGHT*aspectRatio, BACK_ARROW_HEIGHT);
//        backBtn.setPosition(50, Main.HEIGHT-BACK_ARROW_HEIGHT);
        BackButton backBtn = new BackButton();
        backBtn.setPosition(0, Main.HEIGHT-70);
        //Star image and count
        Image starImg = new Image(Resource.star);
        starImg.setBounds(Main.WIDTH/2 - 50, 600, 50, 50);
        Label starText = new Label(String.valueOf(starsCount), new Label.LabelStyle(Resource.font, Color.YELLOW));
        starText.setPosition(Main.WIDTH/2, 600);
        //Shop as a scrollable table
        Table table = new Table();
        table.setSize(Main.WIDTH - Main.WIDTH/10, Main.HEIGHT - 200);
        table.setPosition(Main.WIDTH/20, 0);

        for (int i = 1; i <= Resource.rockets.size; i++) {
            table.add(new ShopCell(Resource.rockets.getKeyAt(i-1), Resource.rockets.getValueAt(i-1))).width(100).pad(15);
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

        ScrollPane scrollPane = new ScrollPane(table);
        scrollPane.debugAll();//TODO delete later
        scrollPane.setSize(table.getWidth(), table.getHeight());
        scrollPane.setPosition(table.getX(), table.getY());

        stage.addActor(backBtn);
        stage.addActor(starImg);
        stage.addActor(starText);
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

    public class BackButton extends Group {
        public BackButton() {
            Image arrow = new Image(Resource.backArrow);
            float aspectRatio = arrow.getWidth()/arrow.getHeight();
            int arrowHeight = 50;
            arrow.setSize(arrowHeight*aspectRatio, arrowHeight);
            Label text = new Label("BACK", new Label.LabelStyle(Resource.robotoThinFont, Color.WHITE));
            text.setX(arrow.getX() + arrow.getWidth());
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
}
