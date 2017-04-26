package com.divelix.rocket;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Sergei Sergienko on 07.02.2017.
 */

public class Resource {
    private static TextureAtlas uiPack;
    private static TextureAtlas rocketPack;
    private static TextureAtlas gamePack;
    public static TextureRegion landscape,
            rocket, doubleGreenRocket, greenRocket, shuttleRocket, smallRocket, yellowRocket,
            cellBg, topBar,
            star,
            playBtnUp, playBtnDown, rateBtnUp, rateBtnDown, leadBtnUp, leadBtnDown, adsBtnUp, adsBtnDown, shopBtnUp, shopBtnDown, pauseOnBtn, pauseOffBtn,
            rocketLogo,
            cloud0, cloud1, cloud2, cloud3, cloud4, cloud5, cloud6, cloud7, cloud8, cloud9;
    public static BitmapFont font;
    public static Array<TextureRegion> rockets = new Array<TextureRegion>();

    public static void load() {
        uiPack = new TextureAtlas("uiPack.atlas");
        rocketPack = new TextureAtlas("rocketPack.atlas");
        gamePack = new TextureAtlas("gamePack.atlas");

        landscape = new TextureRegion(gamePack.findRegion("landscape"));

        playBtnUp = new TextureRegion(uiPack.findRegion("btnUp"));
        playBtnDown = new TextureRegion(uiPack.findRegion("btnDown"));

        adsBtnUp = new TextureRegion(uiPack.findRegion("adsBtnUp"));
        adsBtnDown = new TextureRegion(uiPack.findRegion("adsBtnDown"));

        shopBtnUp = new TextureRegion(uiPack.findRegion("shopBtnUp"));
        shopBtnDown = new TextureRegion(uiPack.findRegion("shopBtnDown"));

        leadBtnUp = new TextureRegion(uiPack.findRegion("leadBtnUp"));
        leadBtnDown = new TextureRegion(uiPack.findRegion("leadBtnDown"));

        rateBtnUp = new TextureRegion(uiPack.findRegion("rateBtnUp"));
        rateBtnDown = new TextureRegion(uiPack.findRegion("rateBtnDown"));

        pauseOnBtn = new TextureRegion(uiPack.findRegion("pauseOn"));
        pauseOffBtn = new TextureRegion(uiPack.findRegion("pauseOff"));

        rocket = new TextureRegion(rocketPack.findRegion("rocket"));
        doubleGreenRocket = new TextureRegion(rocketPack.findRegion("doubleGreenRocket"));
        greenRocket = new TextureRegion(rocketPack.findRegion("greenRocket"));
        shuttleRocket = new TextureRegion(rocketPack.findRegion("shattleRocket"));
        smallRocket = new TextureRegion(rocketPack.findRegion("smallRocket"));
        yellowRocket = new TextureRegion(rocketPack.findRegion("yellowRocket"));
        rockets.add(rocket);
        rockets.add(doubleGreenRocket);
        rockets.add(greenRocket);
        rockets.add(shuttleRocket);
        rockets.add(smallRocket);
        rockets.add(yellowRocket);

        cellBg = new TextureRegion(uiPack.findRegion("cellBg"));
        topBar = new TextureRegion(uiPack.findRegion("topBar"));

        rocketLogo = new TextureRegion(uiPack.findRegion("rocketSpaceLogo"));

        star = new TextureRegion(gamePack.findRegion("star"));

        cloud0 = new TextureRegion(gamePack.findRegion("cloud0"));
        cloud1 = new TextureRegion(gamePack.findRegion("cloud1"));
        cloud2 = new TextureRegion(gamePack.findRegion("cloud2"));
        cloud3 = new TextureRegion(gamePack.findRegion("cloud3"));
        cloud4 = new TextureRegion(gamePack.findRegion("cloud4"));
        cloud5 = new TextureRegion(gamePack.findRegion("cloud5"));
        cloud6 = new TextureRegion(gamePack.findRegion("cloud6"));
        cloud7 = new TextureRegion(gamePack.findRegion("cloud7"));
        cloud8 = new TextureRegion(gamePack.findRegion("cloud8"));
        cloud9 = new TextureRegion(gamePack.findRegion("cloud9"));

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/AGRevueCyrRoman.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();
        params.size = 24;
        font = generator.generateFont(params);
    }
    public static void dispose() {
        gamePack.dispose();
        rocketPack.dispose();
        uiPack.dispose();
    }
}
