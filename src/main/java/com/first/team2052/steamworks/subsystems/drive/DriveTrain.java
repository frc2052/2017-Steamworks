package com.first.team2052.steamworks.subsystems.drive;

import com.ctre.CANTalon;
import com.first.team2052.lib.Loopable;
import com.first.team2052.lib.SynchronousPID;
import com.first.team2052.lib.path.AdaptivePurePursuitController;
import com.first.team2052.lib.path.Path;
import com.first.team2052.lib.vec.RigidTransform2d;
import com.first.team2052.lib.vec.Rotation2d;
import com.first.team2052.steamworks.Constants;
import com.first.team2052.steamworks.Kinematics;
import com.first.team2052.steamworks.RobotState;
import edu.wpi.first.wpilibj.Timer;

import java.util.Set;

public class DriveTrain extends DriveTrainHardware {
    private static DriveTrain instance = new DriveTrain();
    private DriveControlState driveControlState;
    private double mLastHeadingErrorDegrees;
    private AdaptivePurePursuitController pathFollowingController;
    private VelocityHeadingSetpoint velocityHeadingSetpoint;
    private SynchronousPID velocityHeadingPid;
    private final Loopable loopable = new Loopable() {
        @Override
        public void onStart() {
            setOpenLoop(0.0, 0.0);
        }

        @Override
        public void update() {
            if (getDriveControlState() == DriveControlState.OPEN_LOOP) {
                return;
            }
            //Always be in low gear for controllers
            setHighGear(false);

            switch (getDriveControlState()) {
                case PATH_FOLLOWING_CONTROL:
                    updatePathFollower();
                    if (isFinishedPath()) {
                        setOpenLoop(0.0, 0.0);
                    }
                    return;
                case VELOCITY_HEADING_CONTROL:
                    updateVelocityHeadingSetpoint();
                    return;
            }
        }

        @Override
        public void onStop() {
            setOpenLoop(0.0, 0.0);
        }
    };

    private DriveTrain() {
        setHighGear(Constants.Drive.kDriveDefaultHighGear);
        setOpenLoop(0.0, 0.0);

        velocityHeadingPid = new SynchronousPID(Constants.Drive.kDriveHeadingVelocityKp, Constants.Drive.kDriveHeadingVelocityKi,
                Constants.Drive.kDriveHeadingVelocityKd);
        velocityHeadingPid.setOutputRange(-30, 30);
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

    public synchronized static DriveTrain getInstance() {
        return instance;
    }

    /**
     * Sets the motor speeds in percent mode and disables all controllers
     */
    public void setOpenLoop(double left_power, double right_power) {
        if (getDriveControlState() != DriveControlState.OPEN_LOOP) {
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

    public synchronized void followPath(Path path, boolean reversed) {
        if (getDriveControlState() != DriveControlState.PATH_FOLLOWING_CONTROL) {
            configureTalonsForSpeedControl();
            driveControlState = DriveControlState.PATH_FOLLOWING_CONTROL;
            velocityHeadingPid.reset();
        }
        pathFollowingController = new AdaptivePurePursuitController(Constants.Drive.kPathFollowingLookahead,
                Constants.Drive.kPathFollowingMaxAccel, Constants.kControlLoopPeriod, path, reversed, 0.25);
        updatePathFollower();
    }

    public synchronized void setVelocityHeadingSetpoint(double forward_inches_per_sec, Rotation2d headingSetpoint) {
        if (getDriveControlState() != DriveControlState.VELOCITY_HEADING_CONTROL) {
            configureTalonsForSpeedControl();
            driveControlState = DriveControlState.VELOCITY_HEADING_CONTROL;
            velocityHeadingPid.reset();
        }
        velocityHeadingSetpoint = new VelocityHeadingSetpoint(forward_inches_per_sec, forward_inches_per_sec,
                headingSetpoint);
        updateVelocityHeadingSetpoint();
    }

    private synchronized void updateVelocitySetpoint(double left_inches_per_sec, double right_inches_per_sec) {
        if (getDriveControlState() == DriveControlState.PATH_FOLLOWING_CONTROL
                || getDriveControlState() == DriveControlState.VELOCITY_HEADING_CONTROL) {
            leftMaster.set(inchesPerSecondToRpm(left_inches_per_sec));
            rightMaster.set(inchesPerSecondToRpm(right_inches_per_sec));
        } else {
            System.out.println("Hit a bad velocity control state");
            leftMaster.set(0);
            rightMaster.set(0);
        }
    }

    private void updateVelocityHeadingSetpoint() {
        Rotation2d actualGyroAngle = getGyroAngle();

        mLastHeadingErrorDegrees = velocityHeadingSetpoint.getHeading().rotateBy(actualGyroAngle.inverse())
                .getDegrees();

        double deltaSpeed = velocityHeadingPid.calculate(mLastHeadingErrorDegrees);
        updateVelocitySetpoint(velocityHeadingSetpoint.getLeftSpeed() + deltaSpeed / 2,
                velocityHeadingSetpoint.getRightSpeed() - deltaSpeed / 2);
    }

    private void updatePathFollower() {
        RigidTransform2d robot_pose = RobotState.getInstance().getLatestFieldToVehicle().getValue();
        RigidTransform2d.Delta command = pathFollowingController.update(robot_pose, Timer.getFPGATimestamp());
        Kinematics.DriveVelocity setpoint = Kinematics.inverseKinematics(command);

        // Scale the command to respect the max velocity limits
        // We don't want our robot setpoints for turning, etc over it's limits, so we scale our outputs
        double max_vel = 0.0;
        max_vel = Math.max(max_vel, Math.abs(setpoint.left));
        max_vel = Math.max(max_vel, Math.abs(setpoint.right));
        if (max_vel > Constants.Drive.kPathFollowingMaxVel) {
            double scaling = Constants.Drive.kPathFollowingMaxVel / max_vel;
            setpoint = new Kinematics.DriveVelocity(setpoint.left * scaling, setpoint.right * scaling);
        }

        updateVelocitySetpoint(setpoint.left, setpoint.right);
    }

    public boolean isFinishedPath() {
        return (getDriveControlState() == DriveControlState.PATH_FOLLOWING_CONTROL && pathFollowingController.isDone())
                || getDriveControlState() != DriveControlState.PATH_FOLLOWING_CONTROL;
    }

    /**
     * Path Markers are an optional functionality that name the various
     * Waypoints in a Path with a String. This can make defining set locations
     * much easier.
     *
     * @return Set of Strings with Path Markers that the robot has crossed.
     */
    public synchronized Set<String> getPathMarkersCrossed() {
        if (getDriveControlState() != DriveControlState.PATH_FOLLOWING_CONTROL) {
            return null;
        } else {
            return pathFollowingController.getMarkersCrossed();
        }
    }

    protected void configureTalonsForSpeedControl() {
        if (driveControlState != DriveControlState.PATH_FOLLOWING_CONTROL
                && driveControlState != DriveControlState.VELOCITY_HEADING_CONTROL) {
            leftMaster.changeControlMode(CANTalon.TalonControlMode.Speed);
            leftMaster.setAllowableClosedLoopErr(Constants.Drive.kDriveVelocityAllowableError);
            leftMaster.setProfile(kVelocityControlSlot);
            rightMaster.changeControlMode(CANTalon.TalonControlMode.Speed);
            rightMaster.setProfile(kVelocityControlSlot);
            rightMaster.setAllowableClosedLoopErr(Constants.Drive.kDriveVelocityAllowableError);
        }
    }

    public boolean isHighGear() {
        return shifterOut.get();
    }

    public void setHighGear(boolean highGear) {
        shifterOut.set(highGear);
        shifterIn.set(!highGear);
    }

    public void resetEncoders() {
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

    public synchronized Rotation2d getGyroAngle() {
        return Rotation2d.fromDegrees(getGyroAngleDegrees());
    }

    public double getGyroRateDegrees() {
        return gyro.getRateZ() / 4.0;
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

    public Loopable getLoopable() {
        return loopable;
    }

    public DriveControlState getDriveControlState() {
        return driveControlState;
    }

    public enum DriveControlState {
        OPEN_LOOP, VELOCITY_HEADING_CONTROL, PATH_FOLLOWING_CONTROL
    }
}
