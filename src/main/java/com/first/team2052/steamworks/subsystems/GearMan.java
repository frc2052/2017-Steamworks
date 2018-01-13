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

    /**
     * @return if the gear manipulator is closed via a .get from the PCM not based off state - this is more reliable than the states sometimes
     */
    public boolean getSolenoidState() {
        return outSolenoid.get();
    }

    /**
     * @return The current state of the gear manipulator based off the enumerated values
     */
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

                setPushGear(false);
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

                //Once the puncher has been out for a set amount of time, go to OPEN_MANUAL which gives the drivers to choose to close it or punch it more
                if (stateTimer.get() > 0.5) {
                    newState = GearManState.OPEN_MANUAL;
                }

                setOpenPincers(true);
                setPushGear(true);
                break;
            case OPEN_MANUAL:
                setOpenPincers(true);
                setPushGear(false);

                if (!wantOpen && stateTimer.get() > 0.5) {
                    //If we don't want to be open anymore, then close.
                    // If we've been in this state for more than half a second, then close right away if not then close and wait a half a second and then close
                    newState = GearManState.CLOSED;
                } else if (!wantOpen) {
                    newState = GearManState.CLOSING;
                } else if (wantPunch && stateTimer.get() > 0.5) {
                    //If the drivers want to punch for another half a second then switch the state to OPEN_PUNCHED so it punches and goes back to this state (OPEN_MANUAL)
                    newState = GearManState.OPEN_PUNCHED;
                }
                break;
            case CLOSING:
                //This makes sure that the puncher is in before we close the pincers
                setOpenPincers(true);
                setPushGear(false);

                if (stateTimer.get() > 0.5) {
                    //Go to closing state after we've been in this state for more than a set amount of time
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

    /**
     * Opens the pincers to let go of the gear
     */
    private void setOpenPincers(boolean open) {
        if (inSolenoid.get() != open) {
            System.out.println("Pincers are going to " + (open ? "open" : "closed"));
        }
        inSolenoid.set(open);
        outSolenoid.set(!open);
    }

    /**
     * To push the gear onto the peg farther
     */
    private void setPushGear(boolean push) {
        if (punchOutSolenoid.get() != push) {
            System.out.println("Pusher is going to " + (push ? "push" : "not push"));
        }
        punchInSolenoid.set(!push);
        punchOutSolenoid.set(push);
}

    public void setWantPunch(boolean wantPunch) {
        this.wantPunch = wantPunch;
    }

    public void setWantOpen(boolean wantOpen) {
        this.wantOpen = wantOpen;
    }

    public static GearMan getInstance() {
        return instance;
    }

    /**
     * Enumerated Gear States based on the possible combinations that the gear manipulator can be in
     */
    public enum GearManState {
        OPEN,
        OPEN_PUNCHED,
        OPEN_MANUAL,
        CLOSING,
        CLOSED,
    }
}
