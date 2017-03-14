package com.divelix.rocket.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.divelix.rocket.Resource;

/**
 * Created by Sergei Sergienko on 28.02.2017.
 */

public class ShopCell extends Actor {

    private final float WIDTH = 100;
    private final float HEIGHT = 150;
    private Sprite rocket, cellBg;
    private Label label;

    public ShopCell(float x, float y, TextureRegion tr, int price) {
        setBounds(x, y, WIDTH, HEIGHT);
        cellBg = new Sprite(Resource.cellBg);
        cellBg.setBounds(getX(), getY(), getWidth(), getHeight());
        rocket = new Sprite(tr);
        float aspectRatio = rocket.getWidth()/rocket.getHeight();
        rocket.setBounds(getX() + WIDTH/2-(HEIGHT*0.3f*aspectRatio), getY() + HEIGHT/3, HEIGHT*0.6f*aspectRatio, HEIGHT*0.6f);
        label = new Label("" + price, new Label.LabelStyle(Resource.font, Color.YELLOW));
        label.setPosition(getX() + WIDTH/7, getY() + HEIGHT/15);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        cellBg.draw(batch, parentAlpha);
        rocket.draw(batch, parentAlpha);
        label.draw(batch, parentAlpha);
    }
}
