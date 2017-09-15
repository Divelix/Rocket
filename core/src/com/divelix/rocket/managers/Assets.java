package com.divelix.rocket.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader.FreeTypeFontLoaderParameter;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ArrayMap;

/**
 * Created by Sergei Sergienko on 11.09.2017.
 */

public class Assets {
    public static final Preferences prefs = Gdx.app.getPreferences("com.divelix.rocket");
    public static final AssetManager manager = new AssetManager();
    public static TextureAtlas skinPack;
    private static TextureAtlas rocketPack;
    public static TextureAtlas gamePack;
    private static TextureRegion landscape,
            originalRocket, alienRocket, despicableRocket, shuttleRocket, colibriRocket, lemonRocket, hawkRocket, eagleRocket, ravenRocket,
            cellBgWhite, cellBgYellow,
            whitePixel, popUp,
            star, speedometer,
            backArrow, frontArrow,
            rocketLogo,
            cloud0, cloud1, cloud2, cloud3, cloud4, cloud5, cloud6, cloud7, cloud8, cloud9;
    public static BitmapFont robotoFont, smallerFont;
    public static ArrayMap<String, TextureRegion> rockets = new ArrayMap<String, TextureRegion>();
    public static Sound starSound;
    public static Skin skin;

    public static void load() {
        manager.load("skin.atlas", TextureAtlas.class);
        manager.load("rocketPack.atlas", TextureAtlas.class);
        manager.load("gamePack.atlas", TextureAtlas.class);
        manager.load("audio/pisk.wav", Sound.class);

        FileHandleResolver resolver = new InternalFileHandleResolver();
        manager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        manager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));

        FreeTypeFontLoaderParameter robotoFontLP = new FreeTypeFontLoaderParameter();
        robotoFontLP.fontFileName = "fonts/Roboto-Regular.ttf";
        robotoFontLP.fontParameters.size = 80;
        manager.load("big.ttf", BitmapFont.class, robotoFontLP);

        FreeTypeFontLoaderParameter robotoFontLPs = new FreeTypeFontLoaderParameter();
        robotoFontLPs.fontFileName = "fonts/Roboto-Regular.ttf";
        robotoFontLPs.fontParameters.size = 40;
        manager.load("small.ttf", BitmapFont.class, robotoFontLPs);

        manager.finishLoading();
//      -------------------------------------AFTER_LOADING-------------------------

        skinPack = manager.get("skin.atlas", TextureAtlas.class);
        rocketPack = manager.get("rocketPack.atlas", TextureAtlas.class);
        gamePack = manager.get("gamePack.atlas", TextureAtlas.class);
        starSound = manager.get("audio/pisk.wav", Sound.class);

//        skin = new Skin(Gdx.files.internal("skin.json"));

//        landscape = new TextureRegion(gamePack.findRegion("landscape"));
//
//        backArrow = new TextureRegion(skinPack.findRegion("backArrow"));
//        frontArrow = new TextureRegion(skinPack.findRegion("backArrow"));
//        frontArrow.flip(true, false);
//
        originalRocket = rocketPack.findRegion("originalRocket");
        alienRocket =  rocketPack.findRegion("alienRocket");
        despicableRocket = rocketPack.findRegion("despicableRocket");
        shuttleRocket = rocketPack.findRegion("shuttleRocket");
        colibriRocket = rocketPack.findRegion("colibriRocket");
        lemonRocket = rocketPack.findRegion("lemonRocket");
        hawkRocket = rocketPack.findRegion("hawkRocket");
        eagleRocket = rocketPack.findRegion("eagleRocket");
        ravenRocket = rocketPack.findRegion("ravenRocket");
        rockets.put("Original", originalRocket);
        rockets.put("Alien", alienRocket);
        rockets.put("Despicable", despicableRocket);
        rockets.put("Shuttle", shuttleRocket);
        rockets.put("Colibri", colibriRocket);
        rockets.put("Lemon", lemonRocket);
        rockets.put("Hawk", hawkRocket);
        rockets.put("Eagle", eagleRocket);
        rockets.put("Raven", ravenRocket);

//        cellBgWhite = new TextureRegion(skinPack.findRegion("cellBgWhite"));
//        cellBgYellow = new TextureRegion(skinPack.findRegion("cellBgYellow"));
//
//        whitePixel = new TextureRegion(skinPack.findRegion("whitePixel"));
//        popUp = new TextureRegion(skinPack.findRegion("popUp"));
//
//        rocketLogo = new TextureRegion(skinPack.findRegion("rocketLogo"));
//
//        star = new TextureRegion(gamePack.findRegion("star"));
//        speedometer = new TextureRegion(gamePack.findRegion("speedometer"));
//
//        cloud0 = new TextureRegion(gamePack.findRegion("cloud0"));
//        cloud1 = new TextureRegion(gamePack.findRegion("cloud1"));
//        cloud2 = new TextureRegion(gamePack.findRegion("cloud2"));
//        cloud3 = new TextureRegion(gamePack.findRegion("cloud3"));
//        cloud4 = new TextureRegion(gamePack.findRegion("cloud4"));
//        cloud5 = new TextureRegion(gamePack.findRegion("cloud5"));
//        cloud6 = new TextureRegion(gamePack.findRegion("cloud6"));
//        cloud7 = new TextureRegion(gamePack.findRegion("cloud7"));
//        cloud8 = new TextureRegion(gamePack.findRegion("cloud8"));
//        cloud9 = new TextureRegion(gamePack.findRegion("cloud9"));
//
////        missile = new TextureRegion(gamePack.findRegion("missile"));
//
        BitmapFont robotoFontBig = manager.get("big.ttf", BitmapFont.class);
        robotoFontBig.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        robotoFontBig.getData().setScale(0.5f);
        BitmapFont robotoFontSmall = manager.get("small.ttf", BitmapFont.class);
        robotoFontSmall.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        robotoFontSmall.getData().setScale(0.6f);

//        FreeTypeFontGenerator generatorRoboto = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Roboto-Regular.ttf"));
//        FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();
//        params.size = 80;
//        robotoFont = generatorRoboto.generateFont(params);
//        robotoFont.getData().setScale(0.5f);
//        robotoFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
//        params.size = 40;
//        smallerFont = generatorRoboto.generateFont(params);
//        smallerFont.getData().setScale(0.6f);
//        smallerFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        skin = new Skin();
        skin.add("roboto-regular-font", robotoFontBig, BitmapFont.class);
        skin.add("roboto-smaller-font", robotoFontSmall, BitmapFont.class);
        FileHandle fileHandle = Gdx.files.internal("skin.json");
        FileHandle atlasFile = fileHandle.sibling("skin.atlas");
        if (atlasFile.exists()) {
            skin.addRegions(new TextureAtlas(atlasFile));
        }
        skin.load(fileHandle);
    }

    public static void dispose() {
        manager.dispose();
    }
}
