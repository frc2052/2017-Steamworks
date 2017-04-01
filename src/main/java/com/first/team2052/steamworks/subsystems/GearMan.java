package com.first.team2052.steamworks.subsystems;

import com.first.team2052.lib.Loopable;
import com.first.team2052.steamworks.Constants;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;

public class GearMan implements Loopable {
    private static GearMan instance = new GearMan();
    private Solenoid inSolenoid, outSolenoid,
            punchInSolenoid, punchOutSolenoid;

    private GearManState currentState = GearManState.CLOSED;
    private boolean wantOpen = false, wantPunch = false;
    private Timer stateTimer;

    private GearMan() {
        inSolenoid = new Solenoid(Constants.GearMan.kGearManInSolenoidId);
        outSolenoid = new Solenoid(Constants.GearMan.kGearManOutSolenoidId);
        punchInSolenoid = new Solenoid(Constants.GearMan.kGearManPunchInSolenoidId);
        punchOutSolenoid = new Solenoid(Constants.GearMan.kGearManPunchOutSolenoidId);

        stateTimer = new Timer();//This timer is for the puncher
    }

    public void setWantOpen(boolean wantOpen) {
        this.wantOpen = wantOpen;
    }

    public static GearMan getInstance() {
        return instance;
    }

    public boolean getSolenoidState() {
        return outSolenoid.get();
    }

    public GearManState getCurrentState() {
        return currentState;
    }

    //We need a robot to test this
    @Override
    public void update() {
        GearManState newState = currentState;
        switch (currentState) {
            case CLOSED:
                if (wantOpen) {
                    newState = GearManState.OPEN;
                }
                setOpenPincers(false);
                setPushGear(false);
                break;
            case OPEN:
                //the piston opens
                if (!wantOpen) {
                    newState = GearManState.CLOSED;
                }

                setOpenPincers(true);
                //This waits a set amount of time to push the gear on to the peg further
                if (stateTimer.get() > Constants.GearMan.kGearManPunchWaitSeconds) {
                    newState = GearManState.OPEN_PUNCHED;
                }
                break;
            case OPEN_PUNCHED:
                //combination of the piston opening, and the puncher extending
                if (!wantOpen) {
                    newState = GearManState.CLOSING;
                }

                if(stateTimer.get() > 0.5){
                    newState = GearManState.OPEN_MANUAL;
                }

                setOpenPincers(true);
                setPushGear(true);
                break;
            case OPEN_MANUAL:
                setOpenPincers(true);
                setPushGear(false);

                if(!wantOpen){
                    newState = GearManState.CLOSING;
                } else if(wantPunch && stateTimer.get() > 0.5){
                    newState = GearManState.OPEN_PUNCHED;
                }
                break;
            case CLOSING:
                setOpenPincers(true);
                setPushGear(false);

                if (stateTimer.get() > 0.5) {
                    newState = GearManState.CLOSED;
                }
                break;
        }
        //this code resets the timer and assigns a new state
        if (newState != currentState) {
            System.out.println("Gearman State Changed " + currentState);
            stateTimer.reset();
            stateTimer.start();
            currentState = newState;
        }
    }

    @Override
    public void onStart() {
        setWantOpen(false);
    }

    @Override
    public void onStop() {
    }

    private void setOpenPincers(boolean open) {
        inSolenoid.set(open);
        outSolenoid.set(!open);
    }

    private void setPushGear(boolean push) {
        punchInSolenoid.set(!push);
        punchOutSolenoid.set(push);
    }

    public void setWantPunch(boolean wantPunch) {
        this.wantPunch = wantPunch;
    }

    public enum GearManState {
        OPEN,
        OPEN_PUNCHED,
        OPEN_MANUAL,
        CLOSING,
        CLOSED,
    }
}
