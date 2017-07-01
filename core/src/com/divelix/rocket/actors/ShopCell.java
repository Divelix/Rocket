package com.divelix.rocket.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.divelix.rocket.Resource;
import com.divelix.rocket.screens.MenuScreen;
import com.divelix.rocket.screens.ShopScreen;

/**
 * Created by Sergei Sergienko on 28.02.2017.
 */

public class ShopCell extends Group {
    private final float WIDTH = 100;
    private final float HEIGHT = 150;
    private Label label;

    public ShopCell(TextureRegion rocketTxt, int price) {
        this.setSize(WIDTH, HEIGHT);
        Image cellBg = new Image(Resource.cellBg);
        cellBg.setSize(WIDTH, HEIGHT);
//        Sprite sprite = new Sprite(rocketTxt);
//        sprite.setColor(Color.BLACK);
        Image rocket = new Image(rocketTxt);
        float aspectRatio = rocket.getWidth()/rocket.getHeight();
        rocket.setBounds(cellBg.getX() + WIDTH/2-(HEIGHT*0.3f*aspectRatio), cellBg.getY() + HEIGHT/3, HEIGHT*0.6f*aspectRatio, HEIGHT*0.6f);
        label = new Label(String.valueOf(price), new Label.LabelStyle(Resource.font, Color.YELLOW));
        label.setPosition(cellBg.getX() + WIDTH/2 - label.getWidth()/2, cellBg.getY() + HEIGHT/15);
        this.addActor(cellBg);
        this.addActor(rocket);
        this.addActor(label);
//        float aspectRatio = rocket.getWidth()/rocket.getHeight();
//        rocket.setBounds(getX() + WIDTH/2-(HEIGHT*0.3f*aspectRatio), getY() + HEIGHT/3, HEIGHT*0.6f*aspectRatio, HEIGHT*0.6f);
//        label.setPosition(getX() + WIDTH/7, getY() + HEIGHT/15);
        this.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                label.setText("sold");
                label.setStyle(new Label.LabelStyle(Resource.font, Color.GREEN));
            }
        });
    }

    public void openRocket() {

    }
}
