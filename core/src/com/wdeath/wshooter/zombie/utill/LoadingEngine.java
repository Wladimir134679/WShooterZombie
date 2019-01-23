package com.wdeath.wshooter.zombie.utill;

import java.util.ArrayList;

public class LoadingEngine {

    public ArrayList<Runnable> list;

    private Runnable end = null;

    public LoadingEngine() {
        this.list = new ArrayList<>();
    }

    public LoadingEngine add(Runnable run){
        list.add(run);
        return this;
    }

    public void run(){
        if(list.isEmpty()){
            if(end != null){
                end.run();
                end = null;
            }
            return;
        }
        Runnable run = list.remove(0);
        run.run();
    }

    public void setEnd(Runnable end) {
        this.end = end;
    }

}
