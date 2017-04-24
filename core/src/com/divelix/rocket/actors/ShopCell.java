package com.divelix.rocket.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.divelix.rocket.Resource;

/**
 * Created by Sergei Sergienko on 28.02.2017.
 */

public class ShopCell extends Group {

    private final float WIDTH = 100;
    private final float HEIGHT = 150;
    private Image rocket, cellBg;
    private Label label;

    public ShopCell(TextureRegion rocketImg, int price) {
        super.setSize(WIDTH, HEIGHT);
        cellBg = new Image(Resource.cellBg);
        cellBg.setSize(WIDTH, HEIGHT);
        rocket = new Image(rocketImg);
        float aspectRatio = rocket.getWidth()/rocket.getHeight();
        rocket.setBounds(cellBg.getX() + WIDTH/2-(HEIGHT*0.3f*aspectRatio), cellBg.getY() + HEIGHT/3, HEIGHT*0.6f*aspectRatio, HEIGHT*0.6f);
        label = new Label("" + price, new Label.LabelStyle(Resource.font, Color.YELLOW));
        label.setPosition(cellBg.getX() + WIDTH/7, cellBg.getY() + HEIGHT/15);
        super.addActor(cellBg);
        super.addActor(rocket);
        super.addActor(label);
//        float aspectRatio = rocket.getWidth()/rocket.getHeight();
//        rocket.setBounds(getX() + WIDTH/2-(HEIGHT*0.3f*aspectRatio), getY() + HEIGHT/3, HEIGHT*0.6f*aspectRatio, HEIGHT*0.6f);
//        label.setPosition(getX() + WIDTH/7, getY() + HEIGHT/15);
    }

//    @Override
//    public void draw(Batch batch, float parentAlpha) {
//        cellBg.draw(batch, parentAlpha);
//        rocket.draw(batch, parentAlpha);
//        label.draw(batch, parentAlpha);
//    }
}
