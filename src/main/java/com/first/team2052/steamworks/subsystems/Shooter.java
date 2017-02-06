package com.first.team2052.steamworks.subsystems;

import com.ctre.CANTalon;
import com.first.team2052.steamworks.Constants;

public class Shooter {
    private CANTalon leftMotor, rightMotor, shootMotor;

    private static Shooter instance = new Shooter();

    private Shooter() {
        leftMotor = new CANTalon(Constants.CAN.kLeftAgitatorMotorPort);
        rightMotor = new CANTalon(Constants.CAN.kRightAgitatorMotorPort);
        shootMotor = new CANTalon(Constants.CAN.kShooterMotorPort);
    }

    public void setShooterState(ShooterState state) {
        shootMotor.set(state.getSpeed());
    }

    public void setAgitatorState(AgitatorState state) {
        leftMotor.set(-state.getSpeed());
        rightMotor.set(state.getSpeed());
    }

    public enum ShooterState {
        SHOOT(Constants.Shooter.kShooterMotorSpeed),
        STOP(0.0);

        double speed;

        ShooterState(double speed) {
            this.speed = speed;
        }

        public double getSpeed() {
            return speed;
        }
    }

    public enum AgitatorState {
        FORWARD(Constants.Shooter.kTatorSpeed),
        BACK(Constants.Shooter.kTatorSpeedBack),
        STOP(0.0);

        double tatorSpeed;

        AgitatorState(double tatorSpeed) {
            this.tatorSpeed = tatorSpeed;
        }

        public double getSpeed() {
            return tatorSpeed;
        }
    }

    public static Shooter getInstance() {
        return instance;
    }
}
