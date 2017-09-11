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
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
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

import java.util.Locale;

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
    private Label pauseLabel;
    public static Rocket rocket;
    public static int score = 0;
    private float reducer = 1, dimmer = 1;
    private float scoreHeight;
    private boolean pause = false;
    private Dialog dialog;
    public static boolean isStarTaken;


    public PlayScreen(final Game game, AdHandler handler) {
        this.game = game;
        this.handler = handler;
        Gdx.input.setCatchBackKey(true);
    }

    @Override
    public void show() {
        Gdx.app.log(TAG, "show()");
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Main.WIDTH, Main.HEIGHT);
        scoreHeight = camera.position.y + 350;
        view = new FillViewport(Main.WIDTH, Main.HEIGHT, camera);
        stage = new Stage(view, batch);

        rocket = new Rocket();

        Image landscape = new Image(new TextureRegion(Resource.landscape));
        float aspectRatio = landscape.getHeight() / landscape.getHeight();
        landscape.setSize(Main.WIDTH, Main.WIDTH * aspectRatio);

        HUD HUD = new HUD();

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
        stage.addActor(HUD);
//        stage.setDebugAll(true);

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
        if(rocket.getY() < camera.position.y - camera.viewportHeight/2 || rocket.getY() <= 100) {
            gameOver();
        }
    }

    @Override
    public void resize(int width, int height) {
        Gdx.app.log(TAG, "resize()");
        view.update(width, height, false);
        camera.update();
    }

    @Override
    public void pause() {
        Gdx.app.log(TAG, "pause()");
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
        Gdx.app.log(TAG, "resume()");
    }

    @Override
    public void hide() {
        Gdx.app.log(TAG, "hide()");
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
            scoreHeight = camera.position.y + 350;
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
        prevX = screenX;
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

    //---------------------------------------HUD-----------------------------------
    private class HUD extends Group {

        public Image star, speedometer;
        public Label scoreLabel, speedLabel;
        public Container<Label> scoreWrapper;
        public ImageButton pauseBtn;

        public HUD() {
            Label.LabelStyle labelStyle = new Label.LabelStyle(Resource.robotoFont, Color.YELLOW);
            star = new Image(Resource.star);
            star.setSize(50, 50);
            star.setPosition(25, scoreHeight);
            scoreLabel = new Label(String.format("%03d", score), labelStyle);
            scoreWrapper = new Container<Label>(scoreLabel);
            scoreWrapper.setTransform(true);
            scoreWrapper.setSize(scoreLabel.getWidth(), scoreLabel.getHeight());
            scoreWrapper.setOrigin(scoreWrapper.getWidth()/2, scoreWrapper.getHeight()/2);
            scoreWrapper.setPosition(star.getX() + star.getWidth(), scoreHeight);
            speedometer = new Image(Resource.speedometer);
            speedometer.setSize(50, 50);
            speedometer.setPosition(25, scoreHeight - 50);
            speedLabel = new Label(String.format("%03d", rocket.getSpeedLimit()/10), labelStyle);
            speedLabel.setPosition(speedometer.getX() + speedometer.getWidth(), scoreHeight - 50);

            pauseBtn = new ImageButton(skin, "pause");
            pauseBtn.setBounds(Main.WIDTH - PAUSE_BTN_SIZE - 15, scoreHeight, PAUSE_BTN_SIZE, PAUSE_BTN_SIZE);
            pauseBtn.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    pause();
                }
            });

            this.addActor(star);
            this.addActor(scoreWrapper);
            this.addActor(speedometer);
            this.addActor(speedLabel);
            this.addActor(pauseBtn);
        }

        @Override
        public void act(float delta) {
            super.act(delta);
            if(isStarTaken) {
                score++;
                scoreLabel.setText(String.format("%03d", score));
                scoreLabel.addAction(Actions.sequence(Actions.color(Color.ORANGE, 0.05f), Actions.color(Color.YELLOW, 0.05f)));
                scoreWrapper.addAction(Actions.sequence(Actions.scaleBy(0.5f, 0.5f, 0.05f), Actions.scaleBy(-0.5f, -0.5f, 0.05f)));
                Resource.starSound.play(1.0f);
                rocket.increaseSpeedLimitY();
                isStarTaken = !isStarTaken;
            }
            speedLabel.setText(String.format("%03d", rocket.getSpeedLimit()/10));
            raiseHUD();
        }

        public void raiseHUD() {
            star.setY(scoreHeight);
            scoreWrapper.setY(scoreHeight);
            speedometer.setY(scoreHeight - 50);
            speedLabel.setY(scoreHeight - 50);
            pauseBtn.setY(scoreHeight);
        }
    }
}

