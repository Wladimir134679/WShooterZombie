package com.wdeath.wshooter.zombie;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Assets {

    public static Skin skinUI;

    public static void load(){
        skinUI = new Skin(Gdx.files.internal("uiskin.jsonUI"));
    }
}
