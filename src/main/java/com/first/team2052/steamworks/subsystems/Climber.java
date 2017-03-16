package com.first.team2052.steamworks.subsystems;

import com.ctre.CANTalon;
import com.first.team2052.steamworks.Constants;

public class Climber {
    CANTalon grapple = new CANTalon(Constants.CAN.kClimbId);

    private static Climber instance = new Climber();

    private Climber() {
        grapple.configPeakOutputVoltage(+12.0f, 0.0f);
        grapple.setVoltageRampRate(15.0);
    }

    public void setClimberState(ClimberState state) {
        grapple.set(state.getSpeed());
    }

    public enum ClimberState {
        UP(Constants.Climber.kClimberMotorSpeedUp),
        SLOW_UP(Constants.Climber.kClimberMotorSlowSpeed),
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
