package com.first.team2052.steamworks.subsystems;

import com.first.team2052.steamworks.Constants;
import edu.wpi.first.wpilibj.Solenoid;

public class GearMan {
    private static GearMan instance = new GearMan();
    private Solenoid inSolenoid, outSolenoid;

    private GearMan() {
        inSolenoid = new Solenoid(Constants.Drive.kDriveInSolenoidId);
        outSolenoid = new Solenoid(Constants.Drive.kDriveOutSolenoidId);
    }

    public void setGearManState(GearManState gearManState) {
        inSolenoid.set(!gearManState.out);
        outSolenoid.set(gearManState.out);
    }

    public static GearMan getInstance() {
        return instance;
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
