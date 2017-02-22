package com.first.team2052.steamworks;

import com.first.team2052.lib.FlipFlopLatch;
import com.first.team2052.steamworks.subsystems.Climber;
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
        double tank = Util.checkForDeadzone(-joystick0.getY(), 0.10);
        if (joystick1.getTrigger()) {
            tank *= -1;
        }
        return tank;
    }

    public double getTurn() {
        double turn = Util.checkForDeadzone(joystick1.getX(), 0.10);
        turn = Math.sin(Math.PI / 2.0 * Constants.kDriveSpeedCurveTurn * turn)
                / Math.sin(Math.PI / 2.0 * Constants.kDriveSpeedCurveTurn);
        turn = Math.sin(Math.PI / 2.0 * Constants.kDriveSpeedCurveTurn * turn)
                / Math.sin(Math.PI / 2.0 * Constants.kDriveSpeedCurveTurn);
        return turn * 0.75;
    }

    public boolean getHighGear() {
        return joystick0.getRawButton(2);
    }

    public GearMan.GearManState getGearManState() {
        gearManLatch.update(secondaryStick.getRawButton(4));
        return gearManLatch.get() ? GearMan.GearManState.OPEN : GearMan.GearManState.CLOSED;
    }

    public Pickup.IntakeState getIntakeState() {
        if (secondaryStick.getRawButton(2)) {
            return Pickup.IntakeState.IN;
        } else if (secondaryStick.getRawButton(3)) {
            return Pickup.IntakeState.OUT;
        } else {
            return Pickup.IntakeState.STOP;
        }
    }

    public Climber.ClimberState getClimberState() {
        if(secondaryStick.getRawButton(5)){
            return Climber.ClimberState.UP;
        } else if(secondaryStick.getRawButton(6)) {
            return Climber.ClimberState.SLOW_UP;
        }
        return Climber.ClimberState.STOP;
    }

    public static Controls getInstance() {
        return instance;
    }

    public boolean getWantShoot() {
        return joystick0.getTrigger();
    }

    public boolean getWantReverseAgitator() {
        return joystick1.getRawButton(3);
    }
}
