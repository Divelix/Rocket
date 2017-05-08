package com.divelix.rocket.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.divelix.rocket.Main;
import com.divelix.rocket.Resource;
import com.divelix.rocket.actors.Cloud;
import com.divelix.rocket.actors.Rocket;
import com.divelix.rocket.actors.Star;

/**
 * Created by Sergei Sergienko on 05.02.2017.
 */

public class PlayScreen implements Screen, GestureDetector.GestureListener {

    public static final int DISTANCE = 300;
    private static final int PAUSE_BTN_SIZE = 50;
    public static int bestScore, stars, score = 0;
    private static boolean pause = false;
    private static Game game;
    public static OrthographicCamera camera;
    private SpriteBatch batch;
    private Viewport view;
    private Stage stage;
    private Image pauseBtn;
    private Label scoreLabel;
    public static Rocket rocket;
    private float reducer = 1, dimmer = 1;
    private float scoreHeight;


    public PlayScreen(Game game) {
        this.game = game;
        Gdx.input.setCatchBackKey(true);
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Main.WIDTH, Main.HEIGHT);
        view = new FillViewport(Main.WIDTH, Main.HEIGHT, camera);
        stage = new Stage(view, batch);
        GestureDetector gestureDetector = new GestureDetector(this);
        Gdx.input.setInputProcessor(gestureDetector);

        Image landscape = new Image(new TextureRegion(Resource.landscape));
        scoreLabel = new Label("Score: " + score, new Label.LabelStyle(Resource.font, Color.RED));
        scoreHeight = camera.position.y + 320;
        scoreLabel.setPosition(0, scoreHeight);

//        pauseOnImg = new Image(Resource.pauseOnBtn);
//        pauseOffImg = new Image(Resource.pauseOffBtn);
//        pauseBtn = new ImageButton(pauseOffImg.getDrawable(), pauseOnImg.getDrawable());
//        pauseBtn.setBounds(Main.WIDTH - PAUSE_BTN_SIZE, camera.position.y + 300, PAUSE_BTN_SIZE, PAUSE_BTN_SIZE);
        pauseBtn = new Image(Resource.pauseOffBtn);
        pauseBtn.setBounds(Main.WIDTH - PAUSE_BTN_SIZE, scoreHeight, PAUSE_BTN_SIZE, PAUSE_BTN_SIZE);

        rocket = new Rocket();
        Cloud cloud1 = new Cloud(DISTANCE);
        Cloud cloud2 = new Cloud(DISTANCE * 2);
        Cloud cloud3 = new Cloud(DISTANCE * 3);
        Star star1 = new Star(Main.WIDTH/2, DISTANCE * 1.5f);
        Star star2 = new Star(Main.WIDTH/2, DISTANCE * 2.5f);
        Star star3 = new Star(Main.WIDTH/2, DISTANCE * 3.5f);

        stage.addActor(landscape);
        stage.addActor(rocket);
        stage.addActor(cloud1);
        stage.addActor(cloud2);
        stage.addActor(cloud3);
        stage.addActor(star1);
        stage.addActor(star2);
        stage.addActor(star3);
        stage.addActor(scoreLabel);
        stage.addActor(pauseBtn);
        stage.setDebugAll(true);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(130 * reducer / 255.0f, 200 * reducer / 255.0f, 225 * dimmer / 255.0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        changeBgColor();

        if(!pause)
            stage.act(delta);
        stage.draw();

        changeCameraPosition();
        camera.update();
        scoreLabel.setText("Score: " + score + " | " + camera.position.x + ", " + camera.position.y);
        if(rocket.getPosition().y < camera.position.y - camera.viewportHeight/2) {
            gameOver();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.BACK)) {
            gameOver();
        }
    }

    @Override
    public void resize(int width, int height) {
        Gdx.app.log("RocketLogs", "PlayScreen - resize");
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

    }

    private void changeCameraPosition() {
        if(rocket.getPosition().y > 250) {
            camera.position.y = rocket.getMaxHeight() + 150;
            scoreHeight = camera.position.y + 320;
            scoreLabel.setPosition(0, scoreHeight);
            pauseBtn.setY(scoreHeight);
        }
    }

    private void changeBgColor() {
        if(reducer >= 0.1) {
            reducer = 1 - rocket.getMaxHeight() / 10000;
        }
        if(rocket.getMaxHeight() > 7000 && dimmer > 0.1) {
            dimmer = 1 - (rocket.getMaxHeight() - 7000) / 10000;
        }
    }

    public static void gameOver() {
        rocket.setMaxHeight(0);
        Preferences pref = Gdx.app.getPreferences("com.divelix.rocket");
        bestScore = pref.getInteger("bestScore");
        if(bestScore < score) {
            pref.putInteger("bestScore", score);
        }
        stars = pref.getInteger("stars");
        pref.putInteger("stars", stars+score);
        pref.flush();
        score = 0;
        game.setScreen(new MenuScreen(game));
    }
//-----------------------------------------GESTURES------------------------------------------
    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        if(x > pauseBtn.getX() && y < pauseBtn.getHeight()) {
            if(pause) {
                pause = false;
            } else {
                pause = true;
            }
        } else {
            rocket.velocity.y = Rocket.JUMP_HEIGHT;
        }
        return true;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        //срабатывает при отпускании
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        rocket.velocity.y += deltaY*3;
        return true;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

    @Override
    public void pinchStop() {

    }
}
