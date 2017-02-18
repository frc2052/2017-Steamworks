package com.first.team2052.steamworks.subsystems.controllers;

import com.first.team2052.steamworks.Constants;
import com.first.team2052.steamworks.Util;
import com.first.team2052.steamworks.subsystems.drive.DriveTrain;
import com.first.team2052.trajectory.common.LegacyTrajectoryFollower;
import com.first.team2052.trajectory.common.Path;

public class DrivePathController extends DriveController {
    private LegacyTrajectoryFollower rightPath = new LegacyTrajectoryFollower();
    private LegacyTrajectoryFollower leftPath = new LegacyTrajectoryFollower();

    private double direction = 1.0;
    private double kTurn = Constants.Drive.kDrivePathkTurn;

    public DrivePathController(DriveTrain driveTrain, Path path, boolean backwards) {
        super(driveTrain);

        rightPath.configure(Constants.Drive.kDriveStraightKp, Constants.Drive.kDriveStraightKd, Constants.Drive.kDriveStraightKv, Constants.Drive.kDriveStraightKa);
        leftPath.configure(Constants.Drive.kDriveStraightKp, Constants.Drive.kDriveStraightKd, Constants.Drive.kDriveStraightKv, Constants.Drive.kDriveStraightKa);

        rightPath.setTrajectory(path.getRightWheelTrajectory());
        leftPath.setTrajectory(path.getLeftWheelTrajectory());
        setBackwards(backwards);
    }

    public DrivePathController(DriveTrain driveTrain, Path path){
        this(driveTrain, path, false);
    }

    public void setBackwards(boolean backwards){
        direction = backwards ? -1.0 : 1.0;
    }

    @Override
    public DriveSignal calculate() {
        if (isFinished()) {
            return new DriveSignal(0.0, 0.0);
        }
        // Calculate the wheel speeds for the error and current segment
        double rightSpeed = rightPath.calculate(driveTrain.getRightDistanceInches() * direction) * direction;
        double leftSpeed = leftPath.calculate(driveTrain.getLeftDistanceInches() * direction) * direction;

        // Get a either side of the path heading direction
        double goalHeading = leftPath.getHeading();
		/*
		 * The go left case is default, so if we want it to go right with a
		 * positive angle, it'll flip the other way, so we make the angle
		 * negative
		 */
        double actualHeading = Util.toRadians(-driveTrain.getGyroAngleDegrees());

        // Get the turn error and apply the turn gain
        double angleDiffRad = Util.getDifferenceInAngleRadians(actualHeading, goalHeading);
        double angleDiff = Math.toDegrees(angleDiffRad);
        double turn = -kTurn * angleDiff;

        return new DriveSignal(leftSpeed + turn, rightSpeed - turn);
    }

    @Override
    public boolean isFinished() {
        return leftPath.isFinishedTrajectory();
    }

}
