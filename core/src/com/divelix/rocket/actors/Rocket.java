package com.divelix.rocket.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
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
    public static final int JUMP_HEIGHT = 450;
    private static final int ROCKET_WIDTH = 50;
    private static float maxHeight = 0;
    private Vector2 position;
    public Vector2 velocity;
    private Rectangle bounds;
    private Sprite sprite;

    public Rocket() {
        position = new Vector2(0, 0);
        velocity = new Vector2(0, 0);
        sprite = new Sprite(Resource.rocket);
        float aspectRatio = sprite.getHeight()/sprite.getWidth();
        setBounds(position.x, position.y, ROCKET_WIDTH, ROCKET_WIDTH * aspectRatio);
        sprite.setBounds(getX(), getY(), getWidth(), getHeight());
        bounds = new Rectangle(getX()+(getWidth()/6), getY()+(getHeight()/6), getWidth()/6*4, getHeight()/6*4);
        position.set(Main.WIDTH/2 - getWidth()/2, START_HEIGHT);
    }

    public Vector2 getPosition() { return position; }

    public Rectangle getBounds() { return bounds; }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch);
    }


    @Override
    public void act(float delta) {
        if(position.y > 100)
            velocity.add(0, GRAVITY);
        velocity.scl(delta);
        position.add(0, velocity.y);
        if(position.y < 100)
            position.y = 100;

        velocity.scl(1 / delta);
        if(position.y > maxHeight)
            maxHeight = position.y;

//        if(Gdx.input.isTouched()) {
//            velocity.y += 15;
//        }

        //Ограничение скорости вниз
        if(velocity.y <= -1000)
            velocity.y = -1000;
        //Ограничение скорости вверх
        if(velocity.y > 1500)
            velocity.y = 1500;

        this.setPosition(position.x, position.y);
        sprite.setPosition(position.x, position.y);
        bounds.setPosition(getX()+getWidth()/6, getY()+(getHeight()/6));
        super.act(delta);
    }

    public float getMaxHeight() {
        return maxHeight;
    }

    public void setMaxHeight(int i) { maxHeight = i; }
}
