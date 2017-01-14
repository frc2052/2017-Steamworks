package com.first.team2052.steamworks;

import com.first.team2052.lib.ControlLoop;
import edu.wpi.first.wpilibj.IterativeRobot;


public class Robot extends IterativeRobot {
    private ControlLoop controlLoop = new ControlLoop(Constants.kControlLoopPeriod);

    @Override
    public void robotInit() {
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
    }

    @Override
    public void testPeriodic() {
    }

    @Override
    public void disabledInit() {
        controlLoop.stop();
    }
}

