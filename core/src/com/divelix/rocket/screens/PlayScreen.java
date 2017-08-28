package com.divelix.rocket.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.divelix.rocket.AdHandler;
import com.divelix.rocket.Main;
import com.divelix.rocket.Resource;
import com.divelix.rocket.actors.Cloud;
import com.divelix.rocket.actors.Rocket;
import com.divelix.rocket.actors.Star;

import static com.divelix.rocket.Resource.prefs;
import static com.divelix.rocket.Resource.skin;

/**
 * Created by Sergei Sergienko on 05.02.2017.
 */

public class PlayScreen implements Screen, InputProcessor {

    private static final String TAG = "PlayScreen";
    private static final int PAUSE_BTN_SIZE = 50;
    public static final int DISTANCE = 300;

    private Game game;
    private AdHandler handler;

    public static OrthographicCamera camera;
    private SpriteBatch batch;
    private Viewport view;
    private Stage stage;
    private ImageButton pauseBtn;
    public static Container<Label> scoreWrapper;
    public static Label scoreLabel;
    private Label scoreWordLabel, speedLabel, pauseLabel;
    public static Rocket rocket;
    public static int score = 0;
    private float reducer = 1, dimmer = 1;
    private float scoreHeight;
    private boolean pause = false;
    private Dialog dialog;
//    private Window backWindow;


    public PlayScreen(final Game game, AdHandler handler) {
        this.game = game;
        this.handler = handler;
        Gdx.input.setCatchBackKey(true);
    }

    @Override
    public void show() {
        Gdx.app.log(TAG, "PlayScreen - show");
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Main.WIDTH, Main.HEIGHT);
        scoreHeight = camera.position.y + 320;
        view = new FillViewport(Main.WIDTH, Main.HEIGHT, camera);
        stage = new Stage(view, batch);

        Image landscape = new Image(new TextureRegion(Resource.landscape));
        Label.LabelStyle labelStyle = new Label.LabelStyle(Resource.robotoFont, Color.YELLOW);
        scoreWordLabel = new Label("Score:  ", labelStyle);
        scoreWordLabel.setPosition(0, scoreHeight);
        scoreLabel = new Label("" + score, labelStyle);
        scoreWrapper = new Container<Label>(scoreLabel);
        scoreWrapper.setTransform(true);
        scoreWrapper.setSize(scoreLabel.getWidth(), scoreLabel.getHeight());
        scoreWrapper.setOrigin(scoreWrapper.getWidth()/2, scoreWrapper.getHeight()/2);
        scoreWrapper.setPosition(scoreWordLabel.getWidth(), scoreHeight);
        speedLabel = new Label("Speed: ", labelStyle);
        speedLabel.setPosition(0, scoreHeight - 40);

        pauseBtn = new ImageButton(skin, "pause");
        pauseBtn.setBounds(Main.WIDTH - PAUSE_BTN_SIZE, scoreHeight, PAUSE_BTN_SIZE, PAUSE_BTN_SIZE);
        pauseBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                pause();
            }
        });

        dialog = new Dialog("", skin) {
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
        dialog.text("Quit?");
        dialog.button("Yes     ", true);
        dialog.button("     No", false);
        dialog.key(Input.Keys.ENTER, true);
        dialog.key(Input.Keys.ESCAPE, false);

        dialog.pad(20, 20, 20, 20);
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
        stage.addActor(scoreWordLabel);
        stage.addActor(scoreWrapper);
        stage.addActor(speedLabel);
        stage.addActor(pauseBtn);
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

        if(!pause) stage.act(delta);
//        stage.getViewport().apply();
        view.apply();
        stage.draw();

        if(Gdx.input.isKeyJustPressed(Input.Keys.BACK) || Gdx.input.isKeyJustPressed(Input.Keys.A)) {
            if(!pause) pause();
            dialog.show(stage);
            dialog.setPosition(Main.WIDTH/2-dialog.getWidth()/2, camera.position.y - 50);
            stage.addActor(dialog);
        }

        changeCameraPosition();
        camera.update();
//        scoreLabel.setText("Score: " + score + " | " + rocket.getSpeedLimit() + " | " + rocket.getY());
        scoreLabel.setText("" + score);
        speedLabel.setText("Speed: " + rocket.getSpeedLimit()/10);
        if(rocket.getY() < camera.position.y - camera.viewportHeight/2 || rocket.getY() <= 100) {
            gameOver();
        }
    }

    @Override
    public void resize(int width, int height) {
        Gdx.app.log(TAG, "PlayScreen - resize");
        view.update(width, height, false);
        camera.update();
    }

    @Override
    public void pause() {
        Gdx.app.log(TAG, "PlayScreen - pause");
        if(!pause) {
            pause = true;
            pauseLabel = new Label("PAUSE", new Label.LabelStyle(Resource.robotoFont, Color.WHITE));
            pauseLabel.setPosition(Main.WIDTH / 2 - pauseLabel.getWidth() / 2, camera.position.y + Main.HEIGHT / 5);
            stage.addActor(pauseLabel);
        } else {
            pause = false;
            pauseLabel.remove();
        }
    }

    @Override
    public void resume() {
        Gdx.app.log(TAG, "PlayScreen - resume");
    }

    @Override
    public void hide() {
        Gdx.app.log(TAG, "PlayScreen - hide");
        dispose();
    }

    @Override
    public void dispose() {
        Gdx.app.log("PlayScreen", "PlayScreen - dispose");
        stage.dispose();
        batch.dispose();
    }

    private void changeCameraPosition() {
        if(rocket.getY() > 250) {
            camera.position.y = rocket.getMaxHeight() + 150;
            scoreHeight = camera.position.y + 320;
            scoreWordLabel.setY(scoreHeight);
            scoreWrapper.setY(scoreHeight);
            speedLabel.setY(scoreHeight - speedLabel.getHeight());
            pauseBtn.setY(scoreHeight);
        }
    }

    private void changeBgColor() {
        if(reducer >= 0.1) {
            reducer = 1 - rocket.getMaxHeight() / 100000;
        }
        if(rocket.getMaxHeight() > 7000 && dimmer > 0.1) {
            dimmer = 1 - (rocket.getMaxHeight() - 7000) / 100000;
        }
    }

    private void gameOver() {
        rocket.setMaxHeight(0);
        int bestScore = prefs.getInteger("bestScore");
        if(bestScore < score) {
            prefs.putInteger("bestScore", score);
        }
        int stars = prefs.getInteger("stars");
        prefs.putInteger("stars", stars+score);
        prefs.flush();
        score = 0;
        rocket.setSpeedLimit(300);
        game.setScreen(new MenuScreen(game, handler));
    }
//-----------------------------------------GESTURES------------------------------------------
    private Vector3 touchPosition = new Vector3();
    private float prevX = Main.WIDTH/2;
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
        prevX = screenX;
        rocket.isControlled = true;
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        camera.unproject(touchPosition.set(screenX, screenY, 0));
        rocket.isControlled = false;
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        camera.unproject(touchPosition.set(screenX, screenY, 0));
//        rocket.setX(touchPosition.x - rocket.getWidth() / 2);
//        int delta = MathUtils.round(touchPosition.x - prevX);
//        Gdx.app.log(TAG, "PlayScreen delta - " + delta);
//        rocket.rotate(-delta * 2);
        rocket.setForceX(screenX - prevX);
        prevX = screenX;//TODO fix rotation
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
