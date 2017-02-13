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
    public static Controls instance = new Controls();
    private Joystick joystick0 = new Joystick(0);
    private Joystick joystick1 = new Joystick(1);
    private Joystick secondaryStick = new Joystick(2);
    FlipFlopLatch gearManLatch = new FlipFlopLatch();

    private Controls() {
    }

    public double getTank() {
        return -joystick0.getY();
    }

    public double getTurn() {
        return joystick1.getX();
    }

    public boolean getHighGear() {
        return joystick0.getRawButton(2);
    }

    public GearMan.GearManState getGearManState(){
        gearManLatch.update(secondaryStick.getRawButton(4));
        return gearManLatch.get() ? GearMan.GearManState.OPEN : GearMan.GearManState.CLOSED;
    }

    public Pickup.IntakeState getIntakeState() {
        if(secondaryStick.getRawButton(2)){
            return Pickup.IntakeState.IN;
        } else if(secondaryStick.getRawButton(3)){
            return Pickup.IntakeState.OUT;
        } else {
            return Pickup.IntakeState.STOP;
        }
    }

    public static Controls getInstance() {
        return instance;
    }
    public boolean getWantShoot(){
        return joystick0.getRawButton(3);
    }
}
