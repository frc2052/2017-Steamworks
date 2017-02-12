package com.first.team2052.steamworks;

import edu.wpi.first.wpilibj.Joystick;

/**
 * All the logic for all the control functions on the robot
 * This is the interface between the driver station and the real robot values
 */
public class Controls {
    public static Controls instance = new Controls();
    private Joystick joystick0 = new Joystick(0);
    private Joystick joystick1 = new Joystick(1);

    private Controls() {
    }

    public double getTank() {
        return -joystick0.getY();
    }

    public double getTurn() {
        return joystick1.getX();
    }

    public boolean getHighGear() {
        return !joystick0.getRawButton(2);
    }

    public static Controls getInstance() {
        return instance;
    }
    public boolean getWantShoot(){
        return joystick0.getRawButton(3);
    }
}
