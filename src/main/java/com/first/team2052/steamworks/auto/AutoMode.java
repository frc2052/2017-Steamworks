package com.first.team2052.steamworks.auto;

import com.first.team2052.steamworks.Constants;
import com.first.team2052.steamworks.auto.actions.WaitUntilDistanceAction;
import com.first.team2052.steamworks.subsystems.drive.DriveTrain;
import com.first.team2052.trajectory.common.Path;
import com.google.common.base.Optional;

/**
 * Specific to this years game (Steamworks)
 */
public abstract class AutoMode extends AutoModeBase {


    public void drivePath(Optional<Path> path) throws AutoModeEndedException {
        drivePath(path, false);
    }

    public void drivePath(Optional<Path> path, boolean right, boolean backwards) throws AutoModeEndedException {
        isRunningWithThrow();
        if (!path.isPresent()) {
            errorStop("Path cannot be null. To avoid running people over, auto will stop ;)");
            return;
        }
        Path path_ = path.get();
        if (right) {
            path_.goRight();
        } else {
            path_.goLeft();
        }
        DriveTrain.getInstance().drivePathTrajectory(path_, backwards);
    }

    public void drivePath(Optional<Path> path, boolean right) throws AutoModeEndedException {
        drivePath(path, right, false);
    }

    public void driveStraightDistance(double distance) throws AutoModeEndedException {
        driveStraightDistance(distance, Constants.Drive.kDriveMaxVelocity);
    }

    public void driveStraightDistance(double distanceInches, double maxVelInches) throws AutoModeEndedException {
        isRunningWithThrow();
        DriveTrain.getInstance().setDistanceTrajectory(distanceInches, maxVelInches);
        waitUntilDistance(distanceInches);
    }

    public void waitUntilDistance(double distance) throws AutoModeEndedException {
        runAction(new WaitUntilDistanceAction(distance));
    }
}
