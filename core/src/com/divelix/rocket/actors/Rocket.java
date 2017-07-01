package com.divelix.rocket.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.divelix.rocket.Main;
import com.divelix.rocket.Resource;

/**
 * Created by Sergei Sergienko on 09.02.2017.
 */

public class Rocket extends Actor {

    private static final int GRAVITY = -15;
    private static final int START_HEIGHT = 100;
    private static final int VELOCITY = 250;
    private static final int MAX_ANGLE = 30;
//    public static final int JUMP_HEIGHT = 450;
    private static final int ROCKET_WIDTH = 50;
    private static float maxHeight = 0;
    public Vector2 position;
    public Vector2 velocity;
//    private Rectangle bounds;
    private Circle bounds;
    private Sprite sprite;

    public Rocket() {
        position = new Vector2(0, 0);
        velocity = new Vector2(0, 0);
        sprite = new Sprite(Resource.rocket);
//        sprite.setColor(Color.BLACK);
        float aspectRatio = sprite.getHeight()/sprite.getWidth();
        setBounds(position.x, position.y, ROCKET_WIDTH, ROCKET_WIDTH * aspectRatio);
        sprite.setBounds(getX(), getY(), getWidth(), getHeight());
        sprite.setOriginCenter();
//        bounds = new Rectangle(getX()+(getWidth()/6), getY()+(getHeight()/6), getWidth()/6*4, getHeight()/6*4);
        bounds = new Circle(getX() + getWidth()/2, getY() + getHeight()/2, getWidth()/2);
        position.set(Main.WIDTH/2 - getWidth()/2, START_HEIGHT);
    }

    public void rotate(int deg) {
        sprite.setRotation(deg);
    }

    public Vector2 getPosition() { return position; }

    public Circle getBounds() { return bounds; }

//    public void moveLeft() {
//        velocity.x = -VELOCITY;
////        sprite.setRotation(MAX_ANGLE);
//    }
//
//    public void moveRight() {
//        velocity.x = VELOCITY;
////        sprite.setRotation(-MAX_ANGLE);
//    }
//
//    public void resetMove() {
//        velocity.x = 0;
//        sprite.setRotation(0);
//    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch);
    }


    @Override
    public void act(float delta) {
        if(position.y > 100)
            velocity.add(0, GRAVITY);
        velocity.scl(delta);
        position.add(velocity.x, velocity.y);
        if(position.y < 100)
            position.y = 100;

        velocity.scl(1 / delta);
        if(position.y > maxHeight)
            maxHeight = position.y;

        if(position.x > Main.WIDTH)
            position.x = Main.WIDTH;
        if(position.x < 0 - this.getWidth())
            position.x = 0 - this.getWidth();

//        if(Gdx.input.isTouched()) {
//            velocity.y += 15;
//        }
        velocity.y += 20;

        //Speed limit down
        if(velocity.y <= -1000)
            velocity.y = -1000;
        //Speed limit up
        if(velocity.y > 300)
            velocity.y = 300;

        this.setPosition(position.x, position.y);
        sprite.setPosition(position.x, position.y);
//        sprite.rotate(-velocity.x/100);//TODO rotate according to movement
        bounds.setPosition(getX()+getWidth()/2, getY()+(getHeight()/2));
        super.act(delta);
    }

    public float getMaxHeight() {
        return maxHeight;
    }

    public void setMaxHeight(int i) { maxHeight = i; }
}
