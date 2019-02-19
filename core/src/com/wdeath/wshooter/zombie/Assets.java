package com.wdeath.wshooter.zombie;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Assets {

    public static Skin skinUI;

    public static void loadSkin(){
        skinUI = new Skin(Gdx.files.internal("uiskin.jsonUI"));
    }

    public static void load(){
    }
}
