package com.first.team2052.steamworks.subsystems;

import com.ctre.CANTalon;
import com.first.team2052.steamworks.Constants;

/**
 * This class congregates the functionality of the intake apparatus; this is just the fuel intake.
 * Created by Ben Mildenberger on 1/21/2017.
 */
public class Intake {
    private static Intake instance = new Intake();
    CANTalon intakeMotor = new CANTalon(Constants.CAN.kIntakeMotorPort);

    private Intake() {
    }

    public static Intake getInstance() {
        return instance;
    }

    /**
     * This function takes in a value from the joystick and if the appropriate button (for enabling the intake) is pressed,
     */
    public void setIntakeState(IntakeState state) {
        intakeMotor.set(state.getSpeed());
    }

    public enum IntakeState {
        IN(Constants.Intake.kIntakeMotorSpeedIn),
        OUT(Constants.Intake.kIntakeMotorSpeedOut),
        STOP(0.0);
        double speed;

        IntakeState(double speed) {
            this.speed = speed;
        }

        public double getSpeed() {
            return speed;
        }
    }
}