package com.first.team2052.steamworks.subsystems.controllers;

import com.first.team2052.lib.TrajectoryFollower;
import com.first.team2052.steamworks.Constants;
import com.first.team2052.steamworks.subsystems.drive.DriveTrain;

public class DriveStraightController extends DriveController {
    private TrajectoryFollower trajectoryFollower;
    private double startAngle;

    public DriveStraightController(DriveTrain driveTrain, double max_vel, double distance) {
        super(driveTrain);
        driveTrain.setHighGear(false);

        startAngle = driveTrain.getGyroAngleDegrees();

        TrajectoryFollower.TrajectoryConfig config = new TrajectoryFollower.TrajectoryConfig();
        config.dt = Constants.kControlLoopPeriod;
        config.max_acc = Constants.Drive.kDriveMaxAcceleration;
        config.max_vel = max_vel;

        trajectoryFollower = new TrajectoryFollower();
        trajectoryFollower.configure(
                Constants.Drive.kDriveStraightKp,
                Constants.Drive.kDriveStraightKi,
                Constants.Drive.kDriveStraightKd,
                Constants.Drive.kDriveStraightKv,
                Constants.Drive.kDriveStraightKa, config);

        TrajectoryFollower.TrajectorySetpoint currentState = new TrajectoryFollower.TrajectorySetpoint();
        currentState.pos = (driveTrain.getLeftDistanceInches() + driveTrain.getRightDistanceInches()) / 2.0;
        currentState.vel = (driveTrain.getLeftVelocityInchesPerSec() + driveTrain.getRightVelocityInchesPerSec()) / 2.0;

        trajectoryFollower.setGoal(currentState, distance);
    }

    public TrajectoryFollower.TrajectorySetpoint getTrajectorySetpoint() {
        return trajectoryFollower.getCurrentSetpoint();
    }

    public double getGoal() {
        return trajectoryFollower.getGoal();
    }

    @Override
    public DriveSignal calculate() {
        double output = trajectoryFollower.calculate((driveTrain.getLeftDistanceInches() + driveTrain.getRightDistanceInches()) / 2.0);
        //DriveStraightTurnKp is the P gain in a PID loop (multiplied by your error)
        return DriveSignal.arcadeSignal(output, (driveTrain.getGyroAngleDegrees() - startAngle) * Constants.Drive.kDriveStraightTurnKp);
    }

    @Override
    public boolean isFinished() {
        return trajectoryFollower.isFinishedTrajectory();
    }
}
