package com.wdeath.wshooter.zombie;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Assets {

    public static Skin skinUI;
    public static TextureRegion[][] players;

    public static void loadSkin(){
        skinUI = new Skin(Gdx.files.internal("uiskin.jsonUI"));
    }

    public static void load(){
        players = new TextureRegion[5][5];
        TextureRegion[][] textureRegions = TextureRegion.split(new Texture(Gdx.files.internal("models.png")), 32, 32);
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 5; j++){
                players[i][j] = textureRegions[i][j];
            }
        }
    }
}
