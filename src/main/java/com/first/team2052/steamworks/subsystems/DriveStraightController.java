package com.first.team2052.steamworks.subsystems;

import com.first.team2052.lib.TrajectoryFollower;

public class DriveStraightController extends DriveController {
    private TrajectoryFollower trajectoryFollower;
    private double startAngle;

    public DriveStraightController(DriveTrain driveTrain, double max_vel, double distance) {
        super(driveTrain);
        startAngle = driveTrain.getGyroAngle();

        TrajectoryFollower.TrajectoryConfig config = new TrajectoryFollower.TrajectoryConfig();
        config.dt = Constants.kControlLoopPeriod;
        config.max_acc = Constants.kDriveMaxAcceleration;
        config.max_vel = Constants.kDriveMaxVelocity;
        trajectoryFollower = new TrajectoryFollower();

        trajectoryFollower.configure(
                Constants.kDriveStraightKp,
                Constants.kDriveStraightKi,
                Constants.kDriveStraightKd,
                Constants.kDriveStraightKv,
                Constants.kDriveStraightKa, config);

        TrajectoryFollower.TrajectorySetpoint currentState = new TrajectoryFollower.TrajectorySetpoint();
        currentState.pos = driveTrain.getAverageDistance();
        currentState.vel = driveTrain.getAverageVelocity();

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
        double output = trajectoryFollower.calculate(driveTrain.getAverageDistance());
        return DriveSignal.arcadeSignal(output, (driveTrain.getGyroAngle() - startAngle) * -0.025);
    }

    @Override
    public boolean isFinished() {
        return trajectoryFollower.isFinishedTrajectory();
    }
}
