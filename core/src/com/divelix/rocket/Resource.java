package com.divelix.rocket;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.OrderedMap;

/**
 * Created by Sergei Sergienko on 07.02.2017.
 */

public class Resource {
    public static final Preferences prefs = Gdx.app.getPreferences("com.divelix.rocket");
    private static TextureAtlas skinPack;
    private static TextureAtlas rocketPack;
    private static TextureAtlas gamePack;
    public static TextureRegion landscape,
            rocket, doubleGreenRocket, greenRocket, shuttleRocket, smallRocket, yellowRocket,
            cellBg,
            whitePixel,
            star,
            playBtnUp, playBtnDown, rateBtnUp, rateBtnDown, leadBtnUp, leadBtnDown, adsBtnUp, adsBtnDown, shopBtnUp, shopBtnDown, pauseOnBtn, pauseOffBtn, backArrow, frontArrow,
            rocketLogo,
            cloud0, cloud1, cloud2, cloud3, cloud4, cloud5, cloud6, cloud7, cloud8, cloud9,
            missile;
    public static BitmapFont font, robotoThinFont;
//    public static ArrayMap<TextureRegion, Integer> rockets = new ArrayMap<TextureRegion, Integer>();
    public static ArrayMap<String, TextureRegion> rockets = new ArrayMap<String, TextureRegion>();
    public static Skin skin;

    public static void load() {
        skinPack = new TextureAtlas("skin.atlas");
        rocketPack = new TextureAtlas("rocketPack.atlas");
        gamePack = new TextureAtlas("gamePack.atlas");

//        skin = new Skin(Gdx.files.internal("skin.json"));

        landscape = new TextureRegion(gamePack.findRegion("landscape"));

//        playBtnUp = new TextureRegion(skinPack.findRegion("playBtnUp"));
//        playBtnDown = new TextureRegion(skinPack.findRegion("playBtnDown"));
//
//        adsBtnUp = new TextureRegion(skinPack.findRegion("adsBtnUp"));
//        adsBtnDown = new TextureRegion(skinPack.findRegion("adsBtnDown"));
//
//        shopBtnUp = new TextureRegion(skinPack.findRegion("shopBtnUp"));
//        shopBtnDown = new TextureRegion(skinPack.findRegion("shopBtnDown"));
//
//        leadBtnUp = new TextureRegion(skinPack.findRegion("leadBtnUp"));
//        leadBtnDown = new TextureRegion(skinPack.findRegion("leadBtnDown"));
//
//        rateBtnUp = new TextureRegion(skinPack.findRegion("rateBtnUp"));
//        rateBtnDown = new TextureRegion(skinPack.findRegion("rateBtnDown"));

//        pauseOnBtn = new TextureRegion(skinPack.findRegion("pauseOn"));
//        pauseOffBtn = new TextureRegion(skinPack.findRegion("pauseOff"));

        backArrow = new TextureRegion(skinPack.findRegion("backArrow"));
        frontArrow = new TextureRegion(skinPack.findRegion("backArrow"));
        frontArrow.flip(true, false);

        rocket = new TextureRegion(rocketPack.findRegion("rocket"));
        doubleGreenRocket = new TextureRegion(rocketPack.findRegion("doubleGreenRocket"));
        greenRocket = new TextureRegion(rocketPack.findRegion("greenRocket"));
        shuttleRocket = new TextureRegion(rocketPack.findRegion("shattleRocket"));//TODO typo (shuttle)
        smallRocket = new TextureRegion(rocketPack.findRegion("smallRocket"));
        yellowRocket = new TextureRegion(rocketPack.findRegion("yellowRocket"));
        rockets.put("Original", rocket);
        rockets.put("Alien", doubleGreenRocket);
        rockets.put("Despicable", greenRocket);
        rockets.put("Shuttle", shuttleRocket);
        rockets.put("Colibri", smallRocket);
        rockets.put("Lemon", yellowRocket);

        cellBg = new TextureRegion(skinPack.findRegion("cellBg"));

        whitePixel = new TextureRegion(skinPack.findRegion("whitePixel"));

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

        missile = new TextureRegion(gamePack.findRegion("missile"));

        FreeTypeFontGenerator generatorDef = new FreeTypeFontGenerator(Gdx.files.internal("fonts/AGRevueCyrRoman.ttf"));
        FreeTypeFontGenerator generatorRobotoThin = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Roboto-Thin.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();
        params.size = 24;
        font = generatorDef.generateFont(params);
        params.size = 40;
        robotoThinFont = generatorRobotoThin.generateFont(params);

        skin = new Skin();
        skin.add("default-font", font, BitmapFont.class);
        skin.add("roboto-thin-font", robotoThinFont, BitmapFont.class);
        FileHandle fileHandle = Gdx.files.internal("skin.json");
        FileHandle atlasFile = fileHandle.sibling("skin.atlas");
        if (atlasFile.exists()) {
            skin.addRegions(new TextureAtlas(atlasFile));
        }
        skin.load(fileHandle);
    }
    public static void dispose() {
        gamePack.dispose();
        rocketPack.dispose();
        skinPack.dispose();
        skin.dispose();
    }
}
