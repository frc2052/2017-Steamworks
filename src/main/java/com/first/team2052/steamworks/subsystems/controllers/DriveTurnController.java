package com.first.team2052.steamworks.subsystems.controllers;

import com.first.team2052.lib.TrajectoryFollower;
import com.first.team2052.steamworks.Constants;
import com.first.team2052.steamworks.subsystems.drive.DriveTrain;

/**
 * Created by KnightKrawler on 2/19/2017.
 */
public class DriveTurnController extends DriveController {
    private final TrajectoryFollower mController;

    public DriveTurnController(DriveTrain driveTrain, double destDeg, double turn_vel) {
        super(driveTrain);
        TrajectoryFollower.TrajectoryConfig config = new TrajectoryFollower.TrajectoryConfig();
        config.dt = Constants.kControlLoopPeriod;
        config.max_acc = Constants.Drive.kTurnMaxAccelRadsPerSec2;
        config.max_vel = Math.toRadians(turn_vel);

        mController = new TrajectoryFollower();
        mController.configure(Constants.Drive.kTurnKp, Constants.Drive.kTurnKi, Constants.Drive.kTurnKd, Constants.Drive.kTurnKv, Constants.Drive.kTurnKa, config);

        TrajectoryFollower.TrajectorySetpoint initialSetpoint = new TrajectoryFollower.TrajectorySetpoint();
        initialSetpoint.pos = Math.toRadians(driveTrain.getGyroAngleDegrees());
        initialSetpoint.vel = Math.toRadians(driveTrain.getGyroRateDegrees());
        mController.setGoal(initialSetpoint, Math.toRadians(destDeg));
    }


    @Override
    public DriveSignal calculate() {
        double turn = mController.calculate(Math.toRadians(driveTrain.getGyroAngleDegrees()));
        return DriveSignal.arcadeSignal(0.0, turn);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
