package com.first.team2052.steamworks;

import com.first.team2052.lib.ControlLoop;
import com.first.team2052.lib.Loopable;
import com.first.team2052.steamworks.auto.AutoPaths;
import com.first.team2052.steamworks.subsystems.DriveTrain;
import com.first.team2052.steamworks.subsystems.Intake;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Solenoid;


public class Robot extends IterativeRobot {
    private ControlLoop controlLoop = new ControlLoop(Constants.kControlLoopPeriod);
    DriveTrain driveTrain = new DriveTrain();
    Joystick joystick0 = new Joystick(0);
    AutoPaths autoPaths = new AutoPaths();
    Intake intake = new Intake();

    public static double INTAKE_VELOCITY = 0.8;//changable variable

    @Override
    public void robotInit() {
        controlLoop.addLoopable(driveTrain);
    }

    @Override
    public void autonomousInit() {
        controlLoop.start();
    }

    @Override
    public void autonomousPeriodic() {
    }

    @Override
    public void teleopInit() {
        controlLoop.start();
    }

    @Override
    public void teleopPeriodic() {
        double turn = joystick0.getX();
        double tank = joystick0.getY();

        driveTrain.setLeftRight(tank + turn, tank - turn);
    }

    @Override
    public void testPeriodic() {
        //gear intake control
        if(joystick0.getRawButton(1)){
            gearToggle(true);
        } else {
            gearToggle(false);
        }

        //fuel intake control
        intake.setIntakeVelocity(INTAKE_VELOCITY, joystick0.getRawButton(3));
    }

    @Override
    public void disabledInit() {
        controlLoop.stop();
    }


    //creates solenoids for gear
    Solenoid gearIn = new Solenoid(0); //whats the channels for the gear motors?
    Solenoid gearOut = new Solenoid(1);
    public void gearToggle(boolean button){ //if button is pressed, toggle gear holder
            gearIn.set(button);
            gearOut.set(!button);
    }
}


