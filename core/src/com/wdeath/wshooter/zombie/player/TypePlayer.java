package com.wdeath.wshooter.zombie.player;

import java.util.HashMap;
import java.util.Set;

public class TypePlayer {

    public static HashMap<String, TypePlayer> typesPlayers;

    public static void load(){
        typesPlayers = new HashMap<>();
        add(new TypePlayer().setName("A").setDescription("A").setIdTexture(0));
        add(new TypePlayer().setName("B").setDescription("B").setIdTexture(1));
        add(new TypePlayer().setName("C").setDescription("C").setIdTexture(2));
        add(new TypePlayer().setName("D").setDescription("D").setIdTexture(3));
        add(new TypePlayer().setName("E").setDescription("E").setIdTexture(4));
    }

    private static void add(TypePlayer type){
        typesPlayers.put(type.name, type);
    }

    public static Set<String> getNames(){
        return typesPlayers.keySet();
    }

    public String name;
    public int idTexture;
    public String description;

    public TypePlayer setName(String name){
        this.name = name;
        return this;
    }

    public TypePlayer setDescription(String str){
        this.description = str;
        return this;
    }

    public TypePlayer setIdTexture(int id){
        this.idTexture = id;
        return this;
    }
}
