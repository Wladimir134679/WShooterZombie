package com.wdeath.wshooter.zombie.game.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class LevelData {

    public static ArrayList<LevelData> levels;

    public static void init(){
        FileHandle file = Gdx.files.internal("levels/level-info.json");
        String data = new String(file.readBytes());
        levels = new ArrayList<>();
        JSONArray arr = new JSONArray(data);
        for(int i = 0; i < arr.length(); i++){
            JSONObject obj = arr.getJSONObject(i);
            LevelData level = new LevelData();
            level.id = obj.getInt("id");
            level.file = obj.getString("file");
            level.timeBegin = obj.getFloat("timeBegin");
            level.timeWave = obj.getFloat("timeWave");
            level.numberMoney = obj.getInt("numberMoney");
            level.numberKill = obj.getInt("numberKill");
            levels.add(level);
        }
    }

    public int id;
    public String file;
    public int numberKill;
    public int numberMoney;

    public float timeWave;
    public float timeBegin;
}
