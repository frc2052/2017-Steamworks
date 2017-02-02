package com.first.team2052.steamworks;

import com.first.team2052.lib.FlipFlopLatch;
import edu.wpi.first.wpilibj.Joystick;

public class Controls {
    public static Controls instance = new Controls();
    private Joystick joystick0 = new Joystick(0);
    private Joystick joystick1 = new Joystick(1);
    FlipFlopLatch shiftLatch = new FlipFlopLatch();
    FlipFlopLatch brakeLatch = new FlipFlopLatch();

    public double getTank() {
        return -joystick0.getY();
    }

    public double getTurn() {
        return joystick1.getX();
    }

    public boolean getHighGear() {
        shiftLatch.update(joystick0.getRawButton(2));
        return shiftLatch.get();
    }

    public boolean getBrake() {
        brakeLatch.update(joystick1.getRawButton(2));
        return brakeLatch.get();
    }

    public static Controls getInstance() {
        return instance;
    }
}
