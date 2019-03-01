package com.wdeath.wshooter.zombie.screens;

import com.wdeath.wshooter.zombie.menu.MenuScreen;

public class ScreensData {

    public static MenuScreen menu;
    public static SelectLevel selectLevel;
    public static ProfilePlayer profilePlayer;
    public static WeaponShopScreen weaponsShop;

    public static void init(){
        menu = new MenuScreen();
        selectLevel = new SelectLevel();
        profilePlayer = new ProfilePlayer();
        weaponsShop = new WeaponShopScreen();
    }
}
