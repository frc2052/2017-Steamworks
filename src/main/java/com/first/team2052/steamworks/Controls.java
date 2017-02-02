package com.first.team2052.steamworks;

import com.first.team2052.lib.FlipFlopLatch;
import com.first.team2052.steamworks.subsystems.Intake;
import edu.wpi.first.wpilibj.Joystick;

public class Controls {
    public static Controls instance = new Controls();
    FlipFlopLatch shiftLatch = new FlipFlopLatch();
    FlipFlopLatch brakeLatch = new FlipFlopLatch();
    private Joystick joystick0 = new Joystick(0);
    private Joystick joystick1 = new Joystick(1);
    private Joystick secondaryStick = new Joystick(2);

    public static Controls getInstance() {
        return instance;
    }

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

    public Intake.IntakeState getIntakeState() {
        if (secondaryStick.getRawButton(2)) {
            return Intake.IntakeState.IN;
        } else if (secondaryStick.getRawButton(3)) {
            return Intake.IntakeState.OUT;
        } else {
            return Intake.IntakeState.STOP;
        }
    }
}
