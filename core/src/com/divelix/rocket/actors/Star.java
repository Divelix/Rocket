package com.divelix.rocket.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.divelix.rocket.Main;
import com.divelix.rocket.Resource;
import com.divelix.rocket.screens.PlayScreen;

/**
 * Created by Sergei Sergienko on 10.02.2017.
 */

public class Star extends Actor {

    private static final float DEGREES = 2;
    private static final float SIZE = 50;
    private Sprite sprite;
    private Vector2 position;
    private Rectangle bounds;

    public Star(float x, float y) {
        position = new Vector2(x - SIZE/2, y - SIZE/2);
        setBounds(position.x, position.y, SIZE, SIZE);
        sprite = new Sprite(Resource.star);
        sprite.setOrigin(SIZE/2, SIZE/2);
        sprite.setBounds(getX(), getY(), getWidth(), getHeight());
        bounds = new Rectangle(getX(), getY(), getWidth(), getHeight());
    }

    public Rectangle getBounds() { return bounds; }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch);
    }

    @Override
    public void act(float delta) {
        setPosition(position.x, position.y);
        bounds.setPosition(getX(), getY());
        sprite.setPosition(getX(), getY());
        sprite.rotate(DEGREES);
        if(Intersector.overlaps(PlayScreen.rocket.getBounds(), bounds)) {
            position.y += PlayScreen.DISTANCE * 3;
            PlayScreen.score++;
        }
        super.act(delta);
    }
}
