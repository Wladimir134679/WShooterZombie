package com.wdeath.wshooter.zombie.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.wdeath.wshooter.zombie.Assets;
import com.wdeath.wshooter.zombie.utill.GUIActions;

public class DialogWindow {

    private Window window;
    private VBox boxInfa;
    private Label labelInfa;
    private HBox boxButtons;
    private TextButton ok, back;
    private Skin skin;
    private float width;

    public DialogWindow(Skin skin) {
        this.skin = skin;
        window = new Window("", skin);
        window.top();
        window.getTitleTable().center();
        labelInfa = new Label("", skin);
        boxInfa = new VBox(skin);
        boxInfa.addActor(labelInfa);
        boxButtons = new HBox(skin);
        this.width = 500;

        window.clearChildren();
        Cell cell;
        cell = window.add(boxInfa);
        cell.size(width, 100);
        window.row();
        cell = window.add(boxButtons);
        cell.size(width, 40);
        window.pack();
    }

    public DialogWindow openInfa(Stage stage, String infa, Runnable close){
        window.getTitleLabel().setText("Информация");
        labelInfa.setText(infa);
        back = new TextButton("Закрыть", Assets.skinUI);
        back.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(close != null)
                    close.run();
                close();
            }
        });
        boxButtons.addActor(back);

        stage.addActor(window);
        open(stage);
        return this;
    }

    public DialogWindow openConfirmation(Stage stage, String infa, Runnable okRun, Runnable close){
        window.getTitleLabel().setText("Подтверждение");
        labelInfa.setText(infa);
        ok = new TextButton("Да", Assets.skinUI);
        ok.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(okRun != null)
                    okRun.run();
                close();
            }
        });
        boxButtons.addActor(ok);
        back = new TextButton("Нет", Assets.skinUI);
        back.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(close != null)
                    close.run();
                close();
            }
        });
        boxButtons.addActor(back);
        open(stage);
        return this;
    }

    private void open(Stage stage){
        stage.addActor(window);
        window.layout();
        window.getColor().a = 0;
        GUIActions.alpha(window, 1, 0.1f);
        window.setPosition(Gdx.graphics.getWidth() / 2 - window.getWidth() / 2,
                Gdx.graphics.getHeight() / 2 - window.getHeight() / 2);
    }

    public void close(){
        GUIActions.alpha(window, 0, 0.1f);
        GUIActions.timer(window, () -> {
            window.getStage().getActors().removeValue(window, true);
        }, 0.15f);
    }
}
