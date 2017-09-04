package com.divelix.rocket.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.divelix.rocket.Main;
import com.divelix.rocket.Resource;

import static com.divelix.rocket.Resource.prefs;

/**
 * Created by Sergei Sergienko on 09.02.2017.
 */

public class Rocket extends Actor {

    private static final int ROCKET_HEIGHT = 80;
    private static final int START_HEIGHT = 110;
    private static final int DEFAULT_SPEED_LIMIT_X = 400;
    private static final int DEFAULT_SPEED_LIMIT_Y = 300;
    private static final int DEFAULT_VELOCITY_Y = 10;
//    private static final int VELOCITY = 250;
//    private static final int MAX_ANGLE = 30;
//    public static final int JUMP_HEIGHT = 450;
    private static float maxHeight = 0;
    private static int speedLimitX = DEFAULT_SPEED_LIMIT_X;
    private static int speedLimitY = DEFAULT_SPEED_LIMIT_Y;
    public boolean isControlled;
    private Vector2 position;
    private Vector2 velocity;
    private Rectangle bounds;
//    private Circle bounds;
    private Sprite sprite;
    private float forceX;

    public Rocket() {
        position = new Vector2(0, 0);
        velocity = new Vector2(0, 0);
        forceX = 0;
        sprite = new Sprite(Resource.rockets.get(prefs.getString("ActiveRocket")));
//        sprite.setColor(Color.BLACK);
        float aspectRatio = sprite.getWidth()/sprite.getHeight();
        setBounds(position.x, position.y, ROCKET_HEIGHT * aspectRatio, ROCKET_HEIGHT);
        sprite.setBounds(getX(), getY(), getWidth(), getHeight());
        sprite.setOriginCenter();
        setOrigin(getWidth()/2, getHeight()/2);
        bounds = new Rectangle(getX()+(getWidth()/8), getY()+(getHeight()/8), getWidth()/8*6, getHeight()/8*6);
//        bounds = new Circle(getX() + getWidth()/2, getY() + getHeight()/2, getWidth()/2);
        position.set(Main.WIDTH/2 - getWidth()/2, START_HEIGHT);
    }

    public void rotate(int deg) {
        sprite.setRotation(deg);
    }

    public float getX() { return position.x; }
    public void setX(float x) { position.x = x; }

    public float getY() { return position.y; }
    public void setY(float y) { position.y = y; }

    public int getSpeedLimit() { return speedLimitY; }
    public void setSpeedLimit(int speed) { speedLimitY = speed; }

    public void setForceX(float forceX) {
        if(forceX > 100) {
            this.forceX = 100;
        } else if(forceX < -100) {
            this.forceX = -100;
        } else {
            this.forceX = forceX;
        }
    }

    public void increaseSpeedLimitY() { speedLimitY += 10; }
    public void decreaseSpeedLimitY() { speedLimitY -= velocity.y * 0.01f + 5; }

    public Rectangle getBounds() { return bounds; }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch);
    }


    @Override
    public void act(float delta) {
        if(position.y > 100)
            velocity.add(forceX * 5, DEFAULT_VELOCITY_Y);
        velocity.scl(delta);
        position.add(velocity.x, velocity.y);
        velocity.scl(1 / delta);
        if(position.y > maxHeight)
            maxHeight = position.y;

        if(position.x > Main.WIDTH)
            position.x = Main.WIDTH;
        if(position.x < 0 - this.getWidth())
            position.x = 0 - this.getWidth();

        //Speed limiting
        if(!isControlled) {
            if(speedLimitX > 0) {
                speedLimitX -= 10;
            } else {
                speedLimitX = 0;
            }
        } else {
            speedLimitX = DEFAULT_SPEED_LIMIT_X;
        }
        //X axis
        if(velocity.x >= speedLimitX)
            velocity.x = speedLimitX;
        if(velocity.x <= -speedLimitX)
            velocity.x = -speedLimitX;
        //Y axis
        if(velocity.y <= 0)
            velocity.y -= 20;
        //Speed limit
        if(velocity.y > speedLimitY)
            velocity.y = speedLimitY;

        this.setPosition(position.x, position.y);
        sprite.setPosition(getX(), getY());
        if(isControlled) {
            sprite.setRotation(-velocity.x / 15);
        } else {
//            sprite.setRotation(-velocity.x / 10);
            sprite.setRotation(0);
        }
        bounds.setPosition(getX()+(getWidth()/8), getY()+(getHeight()/8));
        super.act(delta);
    }

    public float getMaxHeight() {
        return maxHeight;
    }

    public void setMaxHeight(int i) { maxHeight = i; }
}
