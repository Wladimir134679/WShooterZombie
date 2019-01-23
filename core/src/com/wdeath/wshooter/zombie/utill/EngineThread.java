/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wdeath.wshooter.zombie.utill;

import com.badlogic.ashley.core.Engine;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Дом
 */
public abstract class EngineThread {
    
    private Engine engine;
    private Thread thread;
    private long currentTime, timeBegin, elapsedTime;
    
    public EngineThread(){
        engine = new Engine();
        thread = new Thread(this::run, "EngineThread");
    }
    
    public EngineThread start(){
        thread.start();
        return this;
    }
    
    public Engine getEngine(){
        return engine;
    }
    
    public abstract void init();
    
    private void run(){
        currentTime = System.currentTimeMillis();
        timeBegin = System.currentTimeMillis();
        init();
        int fps = 60;
        long delay = 1000/fps;
        float delta = 1/(float)fps;
        long lag = 0;
        long timeSleep = 0;
        
        long fpsNum = 0, fpsCurrent = 0, fpsTime = 0;
        
        while(true){
            currentTime = System.currentTimeMillis();
            elapsedTime = currentTime - timeBegin;
            delta = elapsedTime / 1000f;
            timeBegin = currentTime;
            
            if(currentTime - fpsTime > 1000){
                fpsTime = currentTime;
                fpsCurrent++;
                fpsNum = fpsCurrent;
                fpsCurrent = 0;
                System.out.println("FPS: " + fpsNum);
            }else{
                fpsCurrent++;
            }
            
            lag += elapsedTime;
            while(lag >= delay){
                engine.update(delta);
                lag -= delay;
            }
            
            timeSleep = System.currentTimeMillis() - timeBegin;
            if(timeSleep < delay){
                try {
                    Thread.sleep(delay - timeSleep);
                } catch (InterruptedException ex) {
                    Logger.getLogger(EngineThread.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
