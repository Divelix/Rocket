package com.divelix.rocket.actors;

import com.badlogic.gdx.graphics.Color;
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

    public ShopCell(TextureRegion rocketImg, int price) {
        final float WIDTH = 100;
        final float HEIGHT = 150;
        super.setSize(WIDTH, HEIGHT);
        final Image cellBg = new Image(Resource.cellBg);
        cellBg.setSize(WIDTH, HEIGHT);
        Image rocket = new Image(rocketImg);
        float aspectRatio = rocket.getWidth()/rocket.getHeight();
        rocket.setBounds(cellBg.getX() + WIDTH/2-(HEIGHT*0.3f*aspectRatio), cellBg.getY() + HEIGHT/3, HEIGHT*0.6f*aspectRatio, HEIGHT*0.6f);
        final Label label = new Label(String.valueOf(price), new Label.LabelStyle(Resource.font, Color.YELLOW));
        label.setPosition(cellBg.getX() + WIDTH/2 - label.getWidth()/2, cellBg.getY() + HEIGHT/15);
        super.addActor(cellBg);
        super.addActor(rocket);
        super.addActor(label);
//        float aspectRatio = rocket.getWidth()/rocket.getHeight();
//        rocket.setBounds(getX() + WIDTH/2-(HEIGHT*0.3f*aspectRatio), getY() + HEIGHT/3, HEIGHT*0.6f*aspectRatio, HEIGHT*0.6f);
//        label.setPosition(getX() + WIDTH/7, getY() + HEIGHT/15);
        super.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
//                label.setText("sold");
//                label.setStyle(new Label.LabelStyle(Resource.font, Color.GREEN));
                ShopCell.super.removeActor(label);
                Label newLabel = new Label("sold", new Label.LabelStyle(Resource.font, Color.GREEN));
                newLabel.setPosition(cellBg.getX() + WIDTH/2 - newLabel.getWidth()/2, cellBg.getY() + HEIGHT/15);
                addActor(newLabel);
            }
        });
    }

//    @Override
//    public void draw(Batch batch, float parentAlpha) {
//        cellBg.draw(batch, parentAlpha);
//        rocket.draw(batch, parentAlpha);
//        label.draw(batch, parentAlpha);
//    }
}
