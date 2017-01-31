package com.first.team2052.steamworks.subsystems;

import com.ctre.CANTalon;
import com.first.team2052.steamworks.Constants;

public class Shooter {
    CANTalon leftMotor = new CANTalon(Constants.kLeftAgitatorMotorPort);
    CANTalon rightMotor = new CANTalon(Constants.kRightAgitatorMotorPort);
    CANTalon shootMotor = new CANTalon(Constants.kShooterMotorPort);

    private static Shooter instance = new Shooter();

    private Shooter(){
        //nobody uses
    }

    public void setShooterVelocity(ShooterState state, AgitatorState tatorState){
        leftMotor.set(-tatorState.getSpeed());
        rightMotor.set(tatorState.getSpeed());
        shootMotor.set(state.getSpeed());
    }

    public enum ShooterState {
        SHOOT(Constants.kShooterMotorSpeed),
        REVERSE(Constants.kShooterMotorBack),
        STOP(0.0);

        double speed;

        ShooterState(double speed){
            this.speed = speed;
        }

        public double getSpeed(){
            return speed;
        }
    }

    public enum AgitatorState {
        FORWARD(Constants.kTatorSpeed),
        BACK(Constants.kTatorBack),
        STOP(0.0);

        double tatorSpeed;

        AgitatorState(double tatorSpeed){
            this.tatorSpeed = tatorSpeed;
        }
        public double getSpeed(){
            return tatorSpeed;
        }
    }

    public static Shooter getInstance(){
        return instance;
    }

}
