package com.wdeath.wshooter.zombie.weapon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

public class WeaponData {

    public static HashMap<Integer, WeaponData> weapons;

    public static void load(){
        FileHandle file = Gdx.files.internal("weapons/weaponsList.json");
        String stringDataArray = null;
        try {
            stringDataArray = new String(file.readBytes(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        JSONArray array = new JSONArray(stringDataArray);
        weapons = new HashMap<>();
        for(int i = 0; i < array.length(); i++){
            JSONObject obj = array.getJSONObject(i);
            WeaponData data = parse(obj);
            weapons.put(data.id, data);
        }
    }


    private static WeaponData parse(JSONObject obj){
        WeaponData data = new WeaponData();
        data.id = obj.getInt("id");
        data.name = obj.getString("name");
        data.store = obj.getInt("store");
        data.price = obj.getInt("price");
        data.damage = obj.getFloat("damage");
        data.timeShot = obj.getFloat("timeShot");
        data.timeRecharge = obj.getFloat("timeRecharge");
        return data;
    }

    public int id;
    public String name;
    public float timeShot;
    public float timeRecharge;
    public int store;
    public float damage;
    public int price;

}
