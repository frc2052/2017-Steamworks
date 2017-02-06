package com.first.team2052.steamworks.subsystems.drive;

import com.first.team2052.lib.Loopable;
import com.first.team2052.steamworks.Constants;
import com.first.team2052.steamworks.subsystems.controllers.DriveController;
import com.first.team2052.steamworks.subsystems.controllers.DrivePathController;
import com.first.team2052.steamworks.subsystems.controllers.DriveSignal;
import com.first.team2052.steamworks.subsystems.controllers.DriveStraightController;
import com.first.team2052.trajectory.common.Path;

public class DriveTrain extends DriveTrainHardware {
    private DriveController controller;
    private static DriveTrain instance = new DriveTrain();

    private final Loopable loopable = new Loopable() {
        @Override
        public void update() {
            if (controller == null) {
                return;
            }
            //Always be in low gear for controllers
            setHighGear(false);
            DriveSignal drive = controller.calculate();
            setLeftRightPower(drive.left, drive.right);
        }

        @Override
        public void onStart() {
            controller = null;
            setOpenLoop(0.0, 0.0);
        }

        @Override
        public void onStop() {
            controller = null;
            setOpenLoop(0.0, 0.0);
        }
    };

    private DriveTrain() {
        setHighGear(Constants.Drive.kDriveDefaultHighGear);
        setOpenLoop(0.0, 0.0);
    }

    /**
     * Starts a controller to drive to the desired distance
     *
     * @param distanceInches The distance in inches you want to travel
     */
    public void setDistanceTrajectory(double distanceInches) {
        setDistanceTrajectory(distanceInches, Constants.Drive.kDriveMaxVelocity);
    }

    /**
     * Starts a controller to drive to the desired distance and speed
     *
     * @param distanceInches The distance in inches you want to travel
     * @param maxVelocity    The maximum speed in inches you want to go
     */
    public void setDistanceTrajectory(double distanceInches, double maxVelocity) {
        controller = new DriveStraightController(this, Math.min(maxVelocity, Constants.Drive.kDriveMaxVelocity), distanceInches);
    }

    /**
     * Drives a certain path
     * MAKE SURE YOU RESET THE GYRO AND THE ENCODERS
     *
     * @param path the path object you want to track
     */
    public void drivePathTrajectory(Path path) {
        controller = new DrivePathController(this, path);
    }

    /**
     * Sets the motor speeds in percent mode and disables all controllers
     */
    public synchronized void setOpenLoop(double left_power, double right_power) {
        controller = null;
        setLeftRightPower(left_power, right_power);
    }

    /**
     * Set's the speeds of the motor without resetting a controller
     * This method is used by controllers directly
     */
    protected synchronized void setLeftRightPower(double left_power, double right_power) {
        leftMaster.set(left_power);
        rightMaster.set(-right_power);
    }

    public void setHighGear(boolean highGear) {
        shifterOut.set(highGear);
        shifterIn.set(!highGear);
    }

    public boolean isHighGear() {
        return shifterOut.get();
    }

    public double getGyroAngleDegrees() {
        return gyro.getAngleZ() / 4.0;
    }

    public void zeroGyro() {
        gyro.reset();
    }

    public double getLeftDistanceInches() {
        return leftMaster.getPosition() * Constants.Drive.kDriveWheelDiameterInches * Math.PI;
    }

    public double getRightDistanceInches() {
        return rightMaster.getPosition() * Constants.Drive.kDriveWheelDiameterInches * Math.PI;
    }

    public double getRightVelocityInchesPerSec() {
        return rightMaster.getSpeed() / 60 * Constants.Drive.kDriveWheelDiameterInches * Math.PI;
    }

    public double getLeftVelocityInchesPerSec() {
        return leftMaster.getSpeed() / 60 * Constants.Drive.kDriveWheelDiameterInches * Math.PI;
    }

    public void zeroEncoders() {
        rightMaster.setPosition(0.0);
        leftMaster.setPosition(0.0);

        rightMaster.setEncPosition(0);
        leftMaster.setEncPosition(0);
    }

    public static DriveTrain getInstance() {
        return instance;
    }

    public Loopable getLoopable() {
        return loopable;
    }
}
