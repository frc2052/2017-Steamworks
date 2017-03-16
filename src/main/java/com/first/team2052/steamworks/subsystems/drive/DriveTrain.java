package com.first.team2052.steamworks.subsystems.drive;

import com.ctre.CANTalon;
import com.first.team2052.lib.Loopable;
import com.first.team2052.lib.pathingv2.AdaptivePurePursuitController;
import com.first.team2052.lib.pathingv2.Path;
import com.first.team2052.lib.vec.RigidTransform2d;
import com.first.team2052.lib.vec.Rotation2d;
import com.first.team2052.steamworks.Constants;
import com.first.team2052.steamworks.Kinematics;
import com.first.team2052.steamworks.RobotState;
import com.first.team2052.steamworks.subsystems.controllers.*;
import edu.wpi.first.wpilibj.Timer;

import java.util.Set;

public class DriveTrain extends DriveTrainHardware {
    private volatile DriveController controller;
    private static DriveTrain instance = new DriveTrain();
    private DriveControlState driveControlState;

    private AdaptivePurePursuitController pathFollowingController;

    private final Loopable loopable = new Loopable() {
        @Override
        public void onStart() {
            setOpenLoop(0.0, 0.0);
        }

        @Override
        public void update() {
            if (driveControlState == DriveControlState.OPEN_LOOP) {
                return;
            }
            //Always be in low gear for controllers
            setHighGear(false);

            switch (driveControlState) {
                case PATH_FOLLOWING_CONTROL:
                    updatePathFollower();
                    if (isFinishedPath()) {
                        setOpenLoop(0.0, 0.0);
                    }
                    break;
            }

            DriveSignal drive = controller.calculate();
            setLeftRightPower(drive.left, drive.right);
        }

        @Override
        public void onStop() {
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
    @Deprecated
    public void setDistanceTrajectory(double distanceInches) {
        setDistanceTrajectory(distanceInches, Constants.Drive.kDriveMaxVelocity);
    }

    /**
     * Starts a controller to drive to the desired distance and speed
     *
     * @param distanceInches The distance in inches you want to travel
     * @param maxVelocity    The maximum speed in inches you want to go
     */
    @Deprecated
    public void setDistanceTrajectory(double distanceInches, double maxVelocity) {
        controller = new DriveStraightController(this, Math.min(maxVelocity, Constants.Drive.kDriveMaxVelocity), distanceInches);
    }

    /**
     * Drives a certain path
     * MAKE SURE YOU RESET THE GYRO AND THE ENCODERS
     *
     * @param path the path object you want to track
     */
    @Deprecated
    public void turnInPlace(double angle) {
        controller = new DriveTurnController(this, angle, 720);
    }

    public synchronized void followPath(Path path, boolean reversed) {
        if (driveControlState != DriveControlState.PATH_FOLLOWING_CONTROL) {
            configureTalonsForSpeedControl();
            driveControlState = DriveControlState.PATH_FOLLOWING_CONTROL;
         }
        pathFollowingController = new AdaptivePurePursuitController(Constants.DriveControllers.kPathFollowingLookahead,
                Constants.DriveControllers.kPathFollowingMaxAccel, Constants.kControlLoopPeriod, path, reversed, 0.25);
        updatePathFollower();
    }

    private synchronized void updateVelocitySetpoint(double left_inches_per_sec, double right_inches_per_sec) {
        if (driveControlState == DriveControlState.PATH_FOLLOWING_CONTROL) {
            leftMaster.set(inchesPerSecondToRpm(left_inches_per_sec));
            rightMaster.set(inchesPerSecondToRpm(right_inches_per_sec));
        } else {
            System.out.println("Hit a bad velocity control state");
            leftMaster.set(0);
            rightMaster.set(0);
        }
    }


    private void updatePathFollower() {
        RigidTransform2d robot_pose = RobotState.getInstance().getLatestFieldToVehicle().getValue();
        RigidTransform2d.Delta command = pathFollowingController.update(robot_pose, Timer.getFPGATimestamp());
        Kinematics.DriveVelocity setpoint = Kinematics.inverseKinematics(command);

        // Scale the command to respect the max velocity limits
        double max_vel = 0.0;
        max_vel = Math.max(max_vel, Math.abs(setpoint.left));
        max_vel = Math.max(max_vel, Math.abs(setpoint.right));
        if (max_vel > Constants.DriveControllers.kPathFollowingMaxVel) {
            double scaling = Constants.DriveControllers.kPathFollowingMaxVel / max_vel;
            setpoint = new Kinematics.DriveVelocity(setpoint.left * scaling, setpoint.right * scaling);
        }
        updateVelocitySetpoint(setpoint.left, setpoint.right);
    }

    public boolean isFinishedPath() {
        return (driveControlState == DriveControlState.PATH_FOLLOWING_CONTROL && pathFollowingController.isDone())
                || driveControlState != DriveControlState.PATH_FOLLOWING_CONTROL;
    }

    /**
     * Path Markers are an optional functionality that name the various
     * Waypoints in a Path with a String. This can make defining set locations
     * much easier.
     *
     * @return Set of Strings with Path Markers that the robot has crossed.
     */
    public synchronized Set<String> getPathMarkersCrossed() {
        if (driveControlState != DriveControlState.PATH_FOLLOWING_CONTROL) {
            return null;
        } else {
            return pathFollowingController.getMarkersCrossed();
        }
    }

    /**
     * Sets the motor speeds in percent mode and disables all controllers
     */
    public void setOpenLoop(double left_power, double right_power) {
        if (driveControlState != DriveControlState.OPEN_LOOP) {
            leftMaster.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
            rightMaster.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
            driveControlState = DriveControlState.OPEN_LOOP;
        }
        setLeftRightPower(left_power, right_power);
    }

    /**
     * Set's the speeds of the motor without resetting a controller
     * This method is used by controllers directly
     */
    private void setLeftRightPower(double left_power, double right_power) {
        leftMaster.set(-left_power);
        rightMaster.set(right_power);
    }

    public boolean isHighGear() {
        return shifterOut.get();
    }

    protected void configureTalonsForSpeedControl() {
        if (driveControlState != DriveControlState.PATH_FOLLOWING_CONTROL) {
            leftMaster.changeControlMode(CANTalon.TalonControlMode.Speed);
            leftMaster.setProfile(kVelocityControlSlot);
            leftMaster.setAllowableClosedLoopErr(Constants.DriveControllers.kDriveVelocityAllowableError);
            rightMaster.changeControlMode(CANTalon.TalonControlMode.Speed);
            rightMaster.setProfile(kVelocityControlSlot);
            rightMaster.setAllowableClosedLoopErr(Constants.DriveControllers.kDriveVelocityAllowableError);
        }
    }

    public void setHighGear(boolean highGear) {
        shifterOut.set(highGear);
        shifterIn.set(!highGear);
    }

    public void zeroEncoders() {
        //Set the rotations to zero
        rightMaster.setPosition(0.0);
        leftMaster.setPosition(0.0);

        //Set the encoder position to zero (ticks)
        rightMaster.setEncPosition(0);
        leftMaster.setEncPosition(0);
    }

    public void zeroGyro() {
        gyro.reset();
    }

    public double getGyroAngleDegrees() {
        // It just so happens that the gyro outputs 4x the amount that it actually turned
        return gyro.getAngleZ() / 4.0;
    }

    public Rotation2d getGyroAngle() {
        return Rotation2d.fromDegrees(getGyroAngleDegrees());
    }

    public double getGyroRateDegrees() {
        return gyro.getRateZ() / 4.0;
    }

    private static double rotationsToInches(double rotations) {
        return rotations * (Constants.Drive.kDriveWheelDiameterInches * Math.PI);
    }

    private static double rpmToInchesPerSecond(double rpm) {
        return rotationsToInches(rpm) / 60.0;
    }

    private static double inchesToRotations(double inches) {
        return inches / (Constants.Drive.kDriveWheelDiameterInches * Math.PI);
    }

    private static double inchesPerSecondToRpm(double inches_per_second) {
        return inchesToRotations(inches_per_second) * 60;
    }

    public double getLeftDistanceInches() {
        return rotationsToInches(leftMaster.getPosition());
    }

    public double getRightDistanceInches() {
        return rotationsToInches(rightMaster.getPosition());
    }

    public double getLeftVelocityInchesPerSec() {
        return rpmToInchesPerSecond(leftMaster.getSpeed());
    }

    public double getRightVelocityInchesPerSec() {
        return rpmToInchesPerSecond(rightMaster.getSpeed());
    }

    public DriveController getController() {
        return controller;
    }

    public Loopable getLoopable() {
        return loopable;
    }

    public synchronized static DriveTrain getInstance() {
        return instance;
    }

    public enum DriveControlState {
        OPEN_LOOP, PATH_FOLLOWING_CONTROL
    }
}
