package com.first.team2052.steamworks.subsystems;

import com.ctre.CANTalon;
import com.first.team2052.steamworks.Constants;

public class Climber {
    CANTalon grapple = new CANTalon(Constants.kClimbingMotorPort);
    //DigitalInput digit = new DigitalInput(627);//these are random numbers that need a rightful port number

    private static Climber instance = new Climber();

    private Climber() {
    }

    public void setClimberState(ClimberState state) {
        grapple.set(state.getSpeed());
    }

    public enum ClimberState {
        UP(Constants.kClimberMotorSpeedUp),
        DOWN(Constants.kClimberMotorSpeedDown),
        STOP(0.0);
        double speed;

        ClimberState(double speed) {
            this.speed = speed;
        }

        public double getSpeed() {
            return speed;
        }
    }

    public static Climber getInstance() {
        return instance;
    }
}
