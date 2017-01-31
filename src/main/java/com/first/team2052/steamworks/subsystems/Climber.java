package com.first.team2052.steamworks.subsystems;

import com.first.team2052.steamworks.Constants;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Talon;

/**
 * Created by Bedivere on 1/30/2017.
 */
public class Climber {
    Talon grapple = new Talon(Constants.kClimbingMotorPort);
    //DigitalInput digit = new DigitalInput(627);//these are random numbers that need a rightful port number

    private static Climber instance = new Climber();

    private Climber(){ //why?
    }

    public void setClimberSpeed(climberState state){
        grapple.set(state.getSpeed());
    }

    public enum climberState {
        UP(Constants.kClimberMotorSpeedUp),
        DOWN(Constants.kClimberMotorSpeedDown),
        STOP(0.0);
        Double speed;

        climberState(double speed){
            this.speed = speed;
        }

        public double getSpeed(){
            return speed;
        }
    }
    public static Climber getInstance(){
        return instance;
    }
}
