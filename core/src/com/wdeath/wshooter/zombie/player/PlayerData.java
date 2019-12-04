package com.wdeath.wshooter.zombie.player;

public class PlayerData {

    public static TypePlayer typePlayer;
    public static int numberWinLevel;
    public static int money;

    public static int killMonster;
    public static void init(){
        typePlayer = TypePlayer.typesPlayers.get(TypePlayer.getNames().iterator().next());
        numberWinLevel = 0;
        killMonster = 0;
        money = 1000;
    }
}
