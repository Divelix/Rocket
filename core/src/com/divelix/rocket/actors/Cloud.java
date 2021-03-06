package com.divelix.rocket.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.divelix.rocket.Main;
import com.divelix.rocket.managers.Assets;
import com.divelix.rocket.screens.PlayScreen;

/**
 * Created by Sergei Sergienko on 10.02.2017.
 */
public class Cloud extends Actor {

    private static final int cloudWidth = 150;
    private int speed;
    private Vector2 position;
    private Sprite sprite;
    private float aspectRatio;
    private Rectangle bounds;
    private boolean moveRight;

    public Cloud(float height) {
        sprite = new Sprite(pickSprite(MathUtils.random(9)));
        aspectRatio = sprite.getHeight()/sprite.getWidth();
        sprite.setSize(cloudWidth, cloudWidth*aspectRatio);
        position = new Vector2(MathUtils.random(0, Main.WIDTH/2), height);
        bounds = new Rectangle(position.x+2, position.y, cloudWidth-4, cloudWidth*aspectRatio*0.7f);
        moveRight = true;
        speed = MathUtils.random(20, 25);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch);
    }

    @Override
    public void act(float delta) {
        if(moveRight) {
            position.add(2 * speed * delta, 0);
            if(position.x >= 480-getWidth()) moveRight = false;
        } else {
            position.add(2 * - speed * delta, 0);
            if(position.x <= 0) moveRight = true;
        }
        setBounds(position.x, position.y, sprite.getWidth(), sprite.getHeight());
        sprite.setPosition(getX(), getY());
        bounds.setPosition(getX(), getY());
        if(position.y < PlayScreen.camera.position.y - 400 - this.getHeight()) {
            position.y += PlayScreen.DISTANCE * 3;
            position.x = MathUtils.random(0, 480);
            changeSprite();
        }
        if(Intersector.overlaps(PlayScreen.rocket.getBounds(), bounds)) {
            PlayScreen.rocket.decreaseSpeedLimitY();
            Gdx.input.vibrate(100);
        }
        super.act(delta);
    }

    private TextureRegion pickSprite(int i) {
        TextureRegion textureRegion;
        switch (i) {
            case 0: textureRegion = Assets.gamePack.findRegion("cloud0"); break;
            case 1: textureRegion = Assets.gamePack.findRegion("cloud1"); break;
            case 2: textureRegion = Assets.gamePack.findRegion("cloud2"); break;
            case 3: textureRegion = Assets.gamePack.findRegion("cloud3"); break;
            case 4: textureRegion = Assets.gamePack.findRegion("cloud4"); break;
            case 5: textureRegion = Assets.gamePack.findRegion("cloud5"); break;
            case 6: textureRegion = Assets.gamePack.findRegion("cloud6"); break;
            case 7: textureRegion = Assets.gamePack.findRegion("cloud7"); break;
            case 8: textureRegion = Assets.gamePack.findRegion("cloud8"); break;
            case 9: textureRegion = Assets.gamePack.findRegion("cloud9"); break;
            default: textureRegion = Assets.gamePack.findRegion("cloud0"); break;
        }
        return textureRegion;
    }

    private void changeSprite() {
        sprite = new Sprite(pickSprite(MathUtils.random(9)));
        aspectRatio = sprite.getHeight()/sprite.getWidth();
        sprite.setSize(cloudWidth, cloudWidth * aspectRatio);
    }

    public Rectangle getBounds() { return bounds; }
}
