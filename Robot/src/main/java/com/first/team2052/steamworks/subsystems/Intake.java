package com.first.team2052.steamworks;

import com.ctre.CANTalon;

/**
 * This class congregates the functionality of the intake apparatus; this is just the fuel intake.
 * Created by Ben Mildenberger on 1/21/2017.
 */
public class Intake {
    CANTalon intakeMotor = new CANTalon(Constants.kIntakeMotorPort);

    /**
     * This function takes in a value from the joystick and if the appropriate button (for enabling the intake) is pressed,
     * then the speed of the intake will optimize, based on the Robot's Velocity.
     * @param joystickValue
     * @param button
     */
    public void setIntakeVelocity(double joystickValue, boolean button){
        if(button) {//press the intake button
            double speed = -0.904 * (Math.abs(joystickValue)) + .95;//-0.90476190476
            intakeMotor.set(speed);
        }
    }

}