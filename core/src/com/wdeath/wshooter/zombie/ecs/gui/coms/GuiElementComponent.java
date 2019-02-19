package com.wdeath.wshooter.zombie.ecs.gui.coms;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class GuiElementComponent implements Component {

    public Label infoFPS;
    public Label shot;

    public Label infoLevel;
    public Label healthPlayer;
    public Label infoKill;
}
