package com.first.team2052.steamworks.subsystems;

import com.ctre.CANTalon;
import com.first.team2052.steamworks.Constants;
import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;

/**
 * This class congregates the functionality of the intake apparatus; this is just the fuel intake.
 * Created by Ben Mildenberger on 1/21/2017.
 */
public class Intake {
    CANTalon intakeMotor = new CANTalon(Constants.kIntakeMotorPort);
    private static Intake instance = new Intake();

    private Intake() {
    }

    /**
     * This function takes in a value from the joystick and if the appropriate button (for enabling the intake) is pressed,
     */
    public void setIntakeState(IntakeState state) {
        intakeMotor.set(state.getSpeed());
    }

    public enum IntakeState {
        IN(Constants.kIntakeMotorSpeedIn),
        OUT(Constants.kIntakeMotorSpeedOut),
        STOP(0.0);
        double speed;

        IntakeState(double speed) {
            this.speed = speed;
        }

        public double getSpeed() {
            return speed;
        }
    }

    public static Intake getInstance() {
        return instance;
    }
}