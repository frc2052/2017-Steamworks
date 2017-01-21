package com.first.team2052.steamworks;

import com.first.team2052.lib.ControlLoop;
import com.first.team2052.lib.Loopable;
import com.first.team2052.steamworks.subsystems.DriveTrain;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;


public class Robot extends IterativeRobot {
    private ControlLoop controlLoop = new ControlLoop(Constants.kControlLoopPeriod);
    DriveTrain driveTrain = new DriveTrain();
    Joystick joystick0 = new Joystick(0);

    @Override
    public void robotInit() {
        controlLoop.addLoopable(driveTrain);
        controlLoop.addLoopable(new Loopable() {
            @Override
            public void update() {
                System.out.println("Hello 2017");
            }
        });
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
    }

    @Override
    public void disabledInit() {
        controlLoop.stop();
    }
}

