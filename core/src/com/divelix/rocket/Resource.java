package com.divelix.rocket;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ArrayMap;

/**
 * Created by Sergei Sergienko on 07.02.2017.
 */

public class Resource {
    public static final Preferences prefs = Gdx.app.getPreferences("com.divelix.rocket");
    private static TextureAtlas skinPack;
    private static TextureAtlas rocketPack;
    private static TextureAtlas gamePack;
    public static TextureRegion landscape,
            originalRocket, alienRocket, despicableRocket, shuttleRocket, colibriRocket, lemonRocket, hawkRocket, eagleRocket, ravenRocket,
            cellBgWhite, cellBgYellow,
            whitePixel,
            star,
            backArrow, frontArrow,
            rocketLogo,
            cloud0, cloud1, cloud2, cloud3, cloud4, cloud5, cloud6, cloud7, cloud8, cloud9,
            missile;
    public static BitmapFont font, robotoFont;
//    public static ArrayMap<TextureRegion, Integer> rockets = new ArrayMap<TextureRegion, Integer>();
    public static ArrayMap<String, TextureRegion> rockets = new ArrayMap<String, TextureRegion>();
    public static Sound starSound;
    public static Skin skin;

    public static void load() {
        skinPack = new TextureAtlas("skin.atlas");
        rocketPack = new TextureAtlas("rocketPack.atlas");
        gamePack = new TextureAtlas("gamePack.atlas");

//        skin = new Skin(Gdx.files.internal("skin.json"));

        landscape = new TextureRegion(gamePack.findRegion("landscape"));

        backArrow = new TextureRegion(skinPack.findRegion("backArrow"));
        frontArrow = new TextureRegion(skinPack.findRegion("backArrow"));
        frontArrow.flip(true, false);

        originalRocket = new TextureRegion(rocketPack.findRegion("originalRocket"));
        alienRocket = new TextureRegion(rocketPack.findRegion("alienRocket"));
        despicableRocket = new TextureRegion(rocketPack.findRegion("despicableRocket"));
        shuttleRocket = new TextureRegion(rocketPack.findRegion("shuttleRocket"));
        colibriRocket = new TextureRegion(rocketPack.findRegion("colibriRocket"));
        lemonRocket = new TextureRegion(rocketPack.findRegion("lemonRocket"));
        hawkRocket = new TextureRegion(rocketPack.findRegion("hawkRocket"));
        eagleRocket = new TextureRegion(rocketPack.findRegion("eagleRocket"));
        ravenRocket = new TextureRegion(rocketPack.findRegion("ravenRocket"));
        rockets.put("Original", originalRocket);
        rockets.put("Alien", alienRocket);
        rockets.put("Despicable", despicableRocket);
        rockets.put("Shuttle", shuttleRocket);
        rockets.put("Colibri", colibriRocket);
        rockets.put("Lemon", lemonRocket);
        rockets.put("Hawk", hawkRocket);
        rockets.put("Eagle", eagleRocket);
        rockets.put("Raven", ravenRocket);

        cellBgWhite = new TextureRegion(skinPack.findRegion("cellBgWhite"));
        cellBgYellow = new TextureRegion(skinPack.findRegion("cellBgYellow"));

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
        FreeTypeFontGenerator generatorRoboto = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Roboto-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();
        params.size = 24;
        font = generatorDef.generateFont(params);
        params.size = 40;
        robotoFont = generatorRoboto.generateFont(params);

        skin = new Skin();
        skin.add("default-font", font, BitmapFont.class);
        skin.add("roboto-thin-font", robotoFont, BitmapFont.class);
        FileHandle fileHandle = Gdx.files.internal("skin.json");
        FileHandle atlasFile = fileHandle.sibling("skin.atlas");
        if (atlasFile.exists()) {
            skin.addRegions(new TextureAtlas(atlasFile));
        }
        skin.load(fileHandle);

        starSound = Gdx.audio.newSound(Gdx.files.internal("audio/pisk.wav"));
    }
    public static void dispose() {
        gamePack.dispose();
        rocketPack.dispose();
        skinPack.dispose();
        skin.dispose();
    }
}
