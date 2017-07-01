package com.divelix.rocket.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.divelix.rocket.Main;
import com.divelix.rocket.Resource;
import com.divelix.rocket.actors.Cloud;
import com.divelix.rocket.actors.Rocket;
import com.divelix.rocket.actors.Star;

import static com.divelix.rocket.Resource.skin;

/**
 * Created by Sergei Sergienko on 05.02.2017.
 */

public class PlayScreen implements Screen, InputProcessor {

    public static final int DISTANCE = 300;
    private static final int PAUSE_BTN_SIZE = 50;
    public static int bestScore, stars, score = 0;
    private boolean pause = false;
    private static Game game;
    public static OrthographicCamera camera;
    private SpriteBatch batch;
    private Viewport view;
    private Stage stage;
    private ImageButton pauseBtn;
    private Label scoreLabel, pauseLabel;
    public static Rocket rocket;
    private float reducer = 1, dimmer = 1;
    private float scoreHeight;
    private Dialog dialog;
//    private Window backWindow;


    public PlayScreen(Game game) {
        this.game = game;
        Gdx.input.setCatchBackKey(true);
    }

    @Override
    public void show() {
        Gdx.app.log("RocketLogs", "PlayScreen - show");
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Main.WIDTH, Main.HEIGHT);
        view = new FillViewport(Main.WIDTH, Main.HEIGHT, camera);
        stage = new Stage(view, batch);

        Image landscape = new Image(new TextureRegion(Resource.landscape));
        scoreLabel = new Label("Score: " + score, new Label.LabelStyle(Resource.font, Color.RED));
        scoreHeight = camera.position.y + 320;
        scoreLabel.setPosition(0, scoreHeight);

        pauseBtn = new ImageButton(skin, "pause");
        pauseBtn.setBounds(Main.WIDTH - PAUSE_BTN_SIZE, scoreHeight, PAUSE_BTN_SIZE, PAUSE_BTN_SIZE);
        pauseBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                pause();
            }
        });

        dialog = new Dialog("", skin){
            @Override
            protected void result(Object object) {
                if(object.equals(true)) {
                    gameOver();
                } else {
                    hide(null);
                    pause();
                }
            }
        };
        dialog.pad(20, 20, 20, 20);
        dialog.text("Quit?");
        dialog.button("Yes", true);
        dialog.button("No", false);
        dialog.key(Input.Keys.ENTER, true);
        dialog.key(Input.Keys.ESCAPE, false);

        dialog.getButtonTable().padTop(30);
        dialog.setModal(true);
        dialog.setMovable(true);
        dialog.setResizable(true);
        dialog.setVisible(true);

//        backWindow = new Window("Ma first window", Resource.skin);
//        backWindow.setSize(300, 100);
//        backWindow.setPosition(Main.WIDTH/2-backWindow.getWidth()/2, camera.position.y - backWindow.getHeight()/2);
//        backWindow.setMovable(true);
//        backWindow.setModal(true);
//        backWindow.setVisible(false);

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
//        stage.addActor(dialog);
//        stage.addActor(backWindow);
        stage.setDebugAll(true);

        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(this);
        Gdx.input.setInputProcessor(multiplexer);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(130 * reducer / 255.0f, 200 * reducer / 255.0f, 225 * dimmer / 255.0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        changeBgColor();

        if(Gdx.input.isKeyJustPressed(Input.Keys.A)) {
            if(!pause) pause();
            dialog.setPosition(Main.WIDTH/2-dialog.getWidth()/2, camera.position.y-Main.HEIGHT/5);
            stage.addActor(dialog);
            dialog.show(stage);
        }

        if(!pause) stage.act(delta);
//        stage.getViewport().apply();
        view.apply();
        stage.draw();

        changeCameraPosition();
        camera.update();
        scoreLabel.setText("Score: " + score + " | " + camera.position.x + ", " + camera.position.y);
//        if(rocket.getPosition().y < camera.position.y - camera.viewportHeight/2) {
//            gameOver();
//        }
    }

    @Override
    public void resize(int width, int height) {
        Gdx.app.log("RocketLogs", "PlayScreen - resize");
        view.update(width, height, false);
        camera.update();
    }

    @Override
    public void pause() {
        if(!pause) {
            pause = true;
            Gdx.app.log("RocketLogs", "PlayScreen - pause");
            pauseLabel = new Label("PAUSE", new Label.LabelStyle(Resource.robotoThinFont, Color.WHITE));
            pauseLabel.setPosition(Main.WIDTH / 2 - pauseLabel.getWidth() / 2, camera.position.y + Main.HEIGHT / 5);
            stage.addActor(pauseLabel);
        } else {
            pause = false;
            pauseLabel.remove();
        }
    }

    @Override
    public void resume() {
        Gdx.app.log("RocketLogs", "PlayScreen - resume");
    }

    @Override
    public void hide() {
        Gdx.app.log("RocketLogs", "PlayScreen - hide");
    }

    @Override
    public void dispose() {
        stage.dispose();
        batch.dispose();
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

    private void gameOver() {
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
    private Vector3 touchPosition = new Vector3();
//    private float prevX = Main.WIDTH/2;
    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        camera.unproject(touchPosition.set(screenX, screenY, 0));
//        rocket.position.x = screenX - rocket.getWidth()/2;
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        camera.unproject(touchPosition.set(screenX, screenY, 0));
//        rocket.rotate(0);
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        camera.unproject(touchPosition.set(screenX, screenY, 0));
        rocket.position.x = touchPosition.x;
//        int delta = MathUtils.round(touchPosition.x - prevX);
//        Gdx.app.log("RocketLogs", "PlayScreen delta - " + delta);
//        rocket.rotate(-delta);
//        prevX = screenX;
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
