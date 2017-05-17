package com.divelix.rocket;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;

/**
 * Created by Sergei Sergienko on 07.02.2017.
 */

public class Resource {
    private static TextureAtlas skinPack;
    private static TextureAtlas rocketPack;
    private static TextureAtlas gamePack;
    public static TextureRegion landscape,
            rocket, doubleGreenRocket, greenRocket, shuttleRocket, smallRocket, yellowRocket,
            cellBg,
            star,
            playBtnUp, playBtnDown, rateBtnUp, rateBtnDown, leadBtnUp, leadBtnDown, adsBtnUp, adsBtnDown, shopBtnUp, shopBtnDown, pauseOnBtn, pauseOffBtn, backArrow, frontArrow,
            rocketLogo,
            cloud0, cloud1, cloud2, cloud3, cloud4, cloud5, cloud6, cloud7, cloud8, cloud9;
    public static BitmapFont font, robotoThinFont;
    public static Array<TextureRegion> rockets2 = new Array<TextureRegion>();
    public static ArrayMap<TextureRegion, Integer> rockets = new ArrayMap<TextureRegion, Integer>();
    public static Skin skin;

    public static void load() {
        skinPack = new TextureAtlas("skin.atlas");
        rocketPack = new TextureAtlas("rocketPack.atlas");
        gamePack = new TextureAtlas("gamePack.atlas");

        skin = new Skin(Gdx.files.internal("skin.json"));

        landscape = new TextureRegion(gamePack.findRegion("landscape"));

        playBtnUp = new TextureRegion(skinPack.findRegion("btnUp"));
        playBtnDown = new TextureRegion(skinPack.findRegion("btnDown"));

        adsBtnUp = new TextureRegion(skinPack.findRegion("adsBtnUp"));
        adsBtnDown = new TextureRegion(skinPack.findRegion("adsBtnDown"));

        shopBtnUp = new TextureRegion(skinPack.findRegion("shopBtnUp"));
        shopBtnDown = new TextureRegion(skinPack.findRegion("shopBtnDown"));

        leadBtnUp = new TextureRegion(skinPack.findRegion("leadBtnUp"));
        leadBtnDown = new TextureRegion(skinPack.findRegion("leadBtnDown"));

        rateBtnUp = new TextureRegion(skinPack.findRegion("rateBtnUp"));
        rateBtnDown = new TextureRegion(skinPack.findRegion("rateBtnDown"));

        pauseOnBtn = new TextureRegion(skinPack.findRegion("pauseOn"));
        pauseOffBtn = new TextureRegion(skinPack.findRegion("pauseOff"));

        backArrow = new TextureRegion(skinPack.findRegion("backArrow"));
        frontArrow = new TextureRegion(skinPack.findRegion("backArrow"));
        frontArrow.flip(true, false);

        rocket = new TextureRegion(rocketPack.findRegion("rocket"));
        doubleGreenRocket = new TextureRegion(rocketPack.findRegion("doubleGreenRocket"));
        greenRocket = new TextureRegion(rocketPack.findRegion("greenRocket"));
        shuttleRocket = new TextureRegion(rocketPack.findRegion("shattleRocket"));
        smallRocket = new TextureRegion(rocketPack.findRegion("smallRocket"));
        yellowRocket = new TextureRegion(rocketPack.findRegion("yellowRocket"));
        rockets.put(rocket, 100);
        rockets.put(doubleGreenRocket, 200);
        rockets.put(greenRocket, 300);
        rockets.put(shuttleRocket, 400);
        rockets.put(smallRocket, 500);
        rockets.put(yellowRocket, 600);

        cellBg = new TextureRegion(skinPack.findRegion("cellBg"));

        rocketLogo = new TextureRegion(skinPack.findRegion("rocketLogo"));

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

        FreeTypeFontGenerator generatorDef = new FreeTypeFontGenerator(Gdx.files.internal("fonts/AGRevueCyrRoman.ttf"));
        FreeTypeFontGenerator generatorRobotoThin = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Roboto-Thin.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();
        params.size = 24;
        font = generatorDef.generateFont(params);
        params.size = 40;
        robotoThinFont = generatorRobotoThin.generateFont(params);
    }
    public static void dispose() {
        gamePack.dispose();
        rocketPack.dispose();
        skinPack.dispose();
        skin.dispose();
    }
}
