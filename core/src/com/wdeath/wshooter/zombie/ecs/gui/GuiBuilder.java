package com.wdeath.wshooter.zombie.ecs.gui;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.wdeath.wshooter.zombie.Assets;
import com.wdeath.wshooter.zombie.ecs.gui.coms.GuiComponent;
import com.wdeath.wshooter.zombie.ecs.gui.coms.GuiElementComponent;

public class GuiBuilder {

    public Entity gui;
    private GuiComponent component;

    public Entity create(){
        gui = new Entity();
        createComponent();
        createElement();
        return gui;
    }

    private void createComponent(){
        component = new GuiComponent();
        component.stage = new Stage();
        gui.add(component);
    }

    private void createElement(){
        GuiElementComponent element = new GuiElementComponent();
        createFpsElement(element);
        createShotElement(element);
        createInfoLevel(element);
        createHealthPlayer(element);
        createKill(element);
        gui.add(element);
    }

    private void createKill(GuiElementComponent element){
        Label kill = new Label("", Assets.skinUI, "small");
        kill.setPosition(10, 10 + 20 + 5 + 20 + 5 + 20 + 5);
        component.stage.addActor(kill);
        element.infoKill = kill;
    }

    private void createInfoLevel(GuiElementComponent element){
        Label info = new Label("", Assets.skinUI, "small");
        info.setPosition(10, 10 + 20 + 5 + 20 + 5);
        component.stage.addActor(info);
        element.infoLevel = info;
    }

    private void createHealthPlayer(GuiElementComponent element){
        Label info = new Label("", Assets.skinUI, "small");
        info.setPosition(10, 10 + 20 + 5);
        component.stage.addActor(info);
        element.healthPlayer = info;
    }

    private void createShotElement(GuiElementComponent element){
        Label shot = new Label("", Assets.skinUI, "small");
        shot.setPosition(10, 10);
        component.stage.addActor(shot);
        element.shot = shot;
    }

    private void createFpsElement(GuiElementComponent element){
        Label fps = new Label("", Assets.skinUI, "small");
        fps.setPosition(10, Gdx.graphics.getHeight() - 15);
        component.stage.addActor(fps);
        element.infoFPS = fps;
    }
}
