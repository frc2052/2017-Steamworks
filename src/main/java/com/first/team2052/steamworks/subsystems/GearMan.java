package com.first.team2052.steamworks.subsystems;

import edu.wpi.first.wpilibj.Solenoid;

public class GearMan {
    static GearMan instance = new GearMan();
    Solenoid inSolenoid = new Solenoid(2);
    Solenoid outSolenoid = new Solenoid(3);

    private GearMan() {
    }

    public void setGearManState(GearManState gearManState){
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

        GearManState(boolean out){
            this.out = out;
        }
    }
}
