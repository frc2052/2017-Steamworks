package com.first.team2052.steamworks.subsystems;

import com.ctre.CANTalon;
import com.first.team2052.steamworks.Constants;

public class Climber {
    private CANTalon climbing_motor = new CANTalon(Constants.CAN.kClimbId);

    private static Climber instance = new Climber();

    private Climber() {
        climbing_motor.configPeakOutputVoltage(+12.0f, 0.0f);
        climbing_motor.setVoltageRampRate(15.0);
    }

    public void setClimberState(ClimberState state) {
        climbing_motor.set(state.getSpeed());
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
