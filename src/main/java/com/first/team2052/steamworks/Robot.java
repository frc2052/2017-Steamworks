package com.first.team2052.steamworks;

import com.first.team2052.lib.ControlLoop;
import com.first.team2052.lib.Loopable;
import com.first.team2052.steamworks.auto.AutoPaths;
import com.first.team2052.steamworks.subsystems.DriveTrain;
import com.first.team2052.trajectory.common.Path;
import com.google.common.base.Optional;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Robot extends IterativeRobot {
    private ControlLoop controlLoop = new ControlLoop(Constants.kControlLoopPeriod);
    private DriveTrain driveTrain = DriveTrain.getInstance();
    private Controls controls = Controls.getInstance();

    @Override
    public void robotInit() {
        controlLoop.addLoopable(driveTrain);
    }

    @Override
    public void autonomousInit() {
        driveTrain.setHighGear(true);
        driveTrain.resetEncoders();
        controlLoop.start();
        driveTrain.setDistanceTrajectory(20 * 12);
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
        driveTrain.setBrakeMode(controls.getBrake());

        double turn = controls.getTurn();
        double tank = controls.getTank();

        driveTrain.setOpenLeftRight(tank + turn, tank - turn);

        SmartDashboard.putNumber("velocity", driveTrain.getAverageVelocity());
        SmartDashboard.putNumber("position", driveTrain.getAverageDistance());
        SmartDashboard.putNumber("gyro", driveTrain.getGyroAngle());
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

