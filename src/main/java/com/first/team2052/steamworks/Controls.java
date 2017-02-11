package com.first.team2052.steamworks;

import com.first.team2052.lib.FlipFlopLatch;
import com.first.team2052.steamworks.subsystems.GearMan;
import com.first.team2052.steamworks.subsystems.Pickup;
import edu.wpi.first.wpilibj.Joystick;

/**
 * All the logic for all the control functions on the robot
 * This is the interface between the driver station and the real robot values
 */
public class Controls {
    //Primary joystick constants
    private static int kPJoy0HighGear = 2;
    //Secondary joystick constants
    private static int kSJoyIntakeIn = 2;
    private static int kSJoyIntakeOut = 3;
    private static int kSJoyGearLatch = 4;

    public static Controls instance = new Controls();
    private Joystick primaryJoy0 = new Joystick(0);
    private Joystick primaryJoy1 = new Joystick(1);
    private Joystick secondaryJoy = new Joystick(2);
    FlipFlopLatch gearManLatch = new FlipFlopLatch();

    private Controls() {
    }

    public double getTank() {
        return -primaryJoy0.getY();
    }

    public double getTurn() {
        return primaryJoy1.getX();
    }

    public boolean getHighGear() {
        return primaryJoy0.getRawButton(kPJoy0HighGear);
    }

    public GearMan.GearManState getGearManState(){
        gearManLatch.update(secondaryJoy.getRawButton(kSJoyGearLatch));
        return gearManLatch.get() ? GearMan.GearManState.OPEN : GearMan.GearManState.CLOSED;
    }

    public Pickup.IntakeState getIntakeState() {
        if(secondaryJoy.getRawButton(kSJoyIntakeIn)){
            return Pickup.IntakeState.IN;
        } else if(secondaryJoy.getRawButton(kSJoyIntakeOut)){
            return Pickup.IntakeState.OUT;
        } else {
            return Pickup.IntakeState.STOP;
        }
    }

    public static Controls getInstance() {
        return instance;
    }
}
