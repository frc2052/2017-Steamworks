package com.first.team2052.steamworks;

import com.first.team2052.lib.ControlLoop;
import com.first.team2052.lib.Loopable;
import com.first.team2052.steamworks.auto.AutoPaths;
import com.first.team2052.steamworks.subsystems.DriveTrain;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;


public class Robot extends IterativeRobot {
    private ControlLoop controlLoop = new ControlLoop(Constants.kControlLoopPeriod);
    DriveTrain driveTrain = new DriveTrain();
    Joystick joystick0 = new Joystick(0);
    AutoPaths autoPaths = new AutoPaths();
    Controls controls = Controls.getInstance();

    @Override
    public void robotInit() {
        controlLoop.addLoopable(driveTrain);
    }

    @Override
    public void autonomousInit() {
        driveTrain.setHighGear(true);
        driveTrain.resetEncoders();
        driveTrain.setOpenLeftRight(0, 0);
        controlLoop.start();
        driveTrain.setDistanceTrajectory(12 * 10);
    }

    @Override
    public void autonomousPeriodic() {
    }

    @Override
    public void teleopInit() {
        controlLoop.start();
        driveTrain.resetEncoders();
    }

    @Override
    public void teleopPeriodic() {
        driveTrain.setHighGear(controls.getHighGear());

        double turn = controls.getTurn();
        double tank = controls.getTank();

        driveTrain.setOpenLeftRight(tank + turn, tank - turn);
    }

    @Override
    public void testPeriodic() {
    }

    @Override
    public void disabledInit() {
        controlLoop.stop();
        driveTrain.resetEncoders();
        driveTrain.setOpenLeftRight(0, 0);
    }
}

