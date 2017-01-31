package com.first.team2052.steamworks;

import com.first.team2052.lib.FlipFlopLatch;
import com.first.team2052.steamworks.subsystems.Climber;
import com.first.team2052.steamworks.subsystems.GearMan;
import com.first.team2052.steamworks.subsystems.Intake;
import edu.wpi.first.wpilibj.Joystick;

public class Controls {
    public static Controls instance = new Controls();
    private Joystick joystick0 = new Joystick(0);
    private Joystick joystick1 = new Joystick(1);
    private Joystick secondaryStick = new Joystick(2);
    FlipFlopLatch shiftLatch = new FlipFlopLatch();
    FlipFlopLatch brakeLatch = new FlipFlopLatch();
    FlipFlopLatch gearManLatch = new FlipFlopLatch();

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

    public Intake.IntakeState getIntakeState() {
        if(secondaryStick.getRawButton(2)){
            return Intake.IntakeState.IN;
        } else if(secondaryStick.getRawButton(3)){
            return Intake.IntakeState.OUT;
        } else {
            return Intake.IntakeState.STOP;
        }
    }

    public Climber.climberState getClimberState(){
        if(secondaryStick.getRawButton(4)){
            return Climber.climberState.UP;
        }else if(secondaryStick.getRawButton(5)){
            return Climber.climberState.DOWN;
        }else{
            return Climber.climberState.STOP;
        }

    }

    public GearMan.GearManState getGearManState(){
        gearManLatch.update(secondaryStick.getRawButton(4));
        return gearManLatch.get() ? GearMan.GearManState.OPEN : GearMan.GearManState.CLOSED;

    }

    public boolean getBrake() {
        brakeLatch.update(joystick1.getRawButton(2));
        return brakeLatch.get();
    }

    public static Controls getInstance() {
        return instance;
    }
}
