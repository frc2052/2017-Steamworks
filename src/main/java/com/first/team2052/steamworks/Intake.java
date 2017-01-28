package com.first.team2052.steamworks.subsystems;

import com.ctre.CANTalon;
import com.first.team2052.steamworks.Constants;

/**
 * This class congregates the functionality of the intake apparatus; this is just the fuel intake.
 * Created by Ben Mildenberger on 1/21/2017.
 */
public class Intake {
    CANTalon intakeMotor = new CANTalon(Constants.kIntakeMotorPort);

    /**
     * This function takes in a value from the joystick and if the appropriate button (for enabling the intake) is pressed,
     * @param inVelocity
     * @param button
     */
    public void setIntakeVelocity(double inVelocity, boolean button){
        if(button) {//press the intake button
            intakeMotor.set(inVelocity);
        }
    }

}