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
    private boolean wantOpen = false;
    private Timer stateTimer;

    private GearMan() {
        inSolenoid = new Solenoid(Constants.GearMan.kGearManInSolenoidId);
        outSolenoid = new Solenoid(Constants.GearMan.kGearManOutSolenoidId);
        punchInSolenoid = new Solenoid(Constants.GearMan.kGearManPunchInSolenoidId);
        punchOutSolenoid = new Solenoid(Constants.GearMan.kGearManPunchOutSolenoidId);

        stateTimer = new Timer();
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
                if (!wantOpen) {
                    newState = GearManState.CLOSED;
                }

                setOpenPincers(true);
                setPushGear(true);
                break;
        }

        if (newState != currentState) {
            System.out.println("Gearman State Changed");
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

    public enum GearManState {
        OPEN,
        OPEN_PUNCHED,
        CLOSED
    }
}
