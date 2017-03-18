package com.first.team2052.steamworks.auto.actions;

import com.first.team2052.lib.KnightTimer;
import com.first.team2052.steamworks.subsystems.GearMan;


public class DropGearAction implements Action {

    KnightTimer delayTimer;
    GearMan gearMan;

    public DropGearAction() {
        delayTimer = new KnightTimer();
        this.gearMan = GearMan.getInstance();
    }

    @Override
    public void done() {

    }

    @Override
    public boolean isFinished() {
        return gearMan.getCurrentState() == GearMan.GearManState.OPEN_PUNCHED && delayTimer.hasPassedTime(1.0);
    }

    @Override
    public void start() {
        gearMan.setWantOpen(true);
    }

    @Override
    public void update() {
        if (gearMan.getCurrentState() == GearMan.GearManState.OPEN_PUNCHED && !delayTimer.isRunning()) {
            delayTimer.start();
        }
    }
}
