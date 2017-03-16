package com.first.team2052.steamworks.subsystems;

import com.first.team2052.lib.Loopable;
import com.first.team2052.steamworks.Constants;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;

public class GearMan implements Loopable {
    private static GearMan instance = new GearMan();
    private Solenoid inSolenoid, outSolenoid;
    private Solenoid punchInSolenoid, punchOutSolenoid;
    private GearManState currentState = GearManState.CLOSED;
    private GearManState wantedState = GearManState.CLOSED;
    private Timer stateTimer = new Timer();

    private GearMan() {
        inSolenoid = new Solenoid(Constants.GearMan.kGearManInSolenoidId);
        outSolenoid = new Solenoid(Constants.GearMan.kGearManOutSolenoidId);
        punchInSolenoid = new Solenoid(Constants.GearMan.kGearManPunchInSolenoidId);
        punchOutSolenoid = new Solenoid(Constants.GearMan.kGearManPunchOutSolenoidId);
    }

    public void setGearManState(GearManState gearManState) {
        wantedState = gearManState;
    }

    public static GearMan getInstance() {
        return instance;
    }

    public boolean getGearManState() {
        return outSolenoid.get();
    }

    @Override
    public void update() {
        GearManState newState;
        switch (currentState) {
            case CLOSED:
                setOpenPincers(false);
                setPushGear(false);
                break;
            case OPEN:
                setOpenPincers(true);
                //This waits a set amount of time to push the gear on to the peg further
                if (stateTimer.get() > .75) {
                    setPushGear(true);
                }
                break;
        }

        newState = wantedState;

        if (newState != currentState) {
            System.out.println("Gearman State Changed");
            stateTimer.reset();
            stateTimer.start();
            currentState = newState;
        }
    }

    @Override
    public void onStart() {

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
        OPEN(false),
        CLOSED(true);

        public final boolean out;

        GearManState(boolean out) {
            this.out = out;
        }
    }
}
