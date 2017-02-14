package com.first.team2052.steamworks.subsystems;

import com.ctre.CANTalon;
import com.first.team2052.lib.ADIS16448_IMU;
import com.first.team2052.lib.Loopable;
import com.first.team2052.steamworks.Constants;
import com.first.team2052.trajectory.common.Path;
import edu.wpi.first.wpilibj.Solenoid;

public class DriveTrain implements Loopable {
    private ADIS16448_IMU gyro;
    private CANTalon rightMaster, rightSlave, rightSlave1;
    private CANTalon leftMaster, leftSlave, leftSlave1;
    private Solenoid shifterIn, shifterOut;
    private DriveController controller;

    private static DriveTrain instance = new DriveTrain();
    private boolean brakeMode = false;

    private DriveTrain() {
        rightMaster = new CANTalon(Constants.kDriveRight1Id);
        rightSlave = new CANTalon(Constants.kDriveRight2Id);
        rightSlave1 = new CANTalon(Constants.kDriveRight3Id);

        leftMaster = new CANTalon(Constants.kDriveLeft1Id);
        leftSlave = new CANTalon(Constants.kDriveLeft2Id);
        leftSlave1 = new CANTalon(Constants.kDriveLeft3Id);

        leftMaster.configEncoderCodesPerRev((int) (256 * 3 * 1.8));
        rightMaster.configEncoderCodesPerRev((int) (256 * 3 * 1.8));
        leftMaster.reverseSensor(true);

        rightSlave.changeControlMode(CANTalon.TalonControlMode.Follower);
        rightSlave1.changeControlMode(CANTalon.TalonControlMode.Follower);
        rightSlave.set(rightMaster.getDeviceID());
        rightSlave1.set(rightMaster.getDeviceID());

        leftSlave.changeControlMode(CANTalon.TalonControlMode.Follower);
        leftSlave1.changeControlMode(CANTalon.TalonControlMode.Follower);
        leftSlave.set(leftMaster.getDeviceID());
        leftSlave1.set(leftMaster.getDeviceID());

        shifterIn = new Solenoid(0);
        shifterOut = new Solenoid(1);
        setBrakeMode(true);
        setHighGear(true);

        gyro = new ADIS16448_IMU();
    }

    private void setLeftRight(double leftSpeed, double rightSpeed) {
        leftMaster.set(-leftSpeed);
        rightMaster.set(rightSpeed);
    }

    public void setOpenLeftRight(double leftSpeed, double rightSpeed) {
        controller = null;
        setLeftRight(leftSpeed, rightSpeed);
    }

    public void setBrakeMode(boolean brake) {
        if(brakeMode == brake)
            return;
        rightMaster.enableBrakeMode(brake);
        rightSlave.enableBrakeMode(brake);
        rightSlave1.enableBrakeMode(brake);

        leftMaster.enableBrakeMode(brake);
        leftSlave.enableBrakeMode(brake);
        leftSlave1.enableBrakeMode(brake);
    }

    public void setPathTrajectory(Path pathTrajectory){
        controller = new DrivePathController(this, pathTrajectory, false);
    }
    public void setBackwardsPathTrajectory(Path pathTrajectory){
        controller = new DrivePathController(this, pathTrajectory, true);
    }

    public void setHighGear(boolean highGear) {
        shifterOut.set(highGear);
        shifterIn.set(!highGear);
    }

    @Override
    public void update() {
        if (controller == null) {
            return;
        }

        DriveSignal drive = controller.calculate();
        setLeftRight(drive.left, drive.right);
    }

    @Override
    public void start() {
        setOpenLeftRight(0.0, 0.0);
    }

    @Override
    public void stop() {
        setOpenLeftRight(0.0, 0.0);
    }

    public void setDistanceTrajectory(double distance) {
        controller = new DriveStraightController(this, Constants.kDriveMaxVelocity, distance);
    }

    public double getLeftDistance() {
        return leftMaster.getPosition() * Constants.kDriveWheelDiameterInches * Math.PI;
    }

    public double getRightDistance() {
        return rightMaster.getPosition() * Constants.kDriveWheelDiameterInches * Math.PI;
    }

    public double getRightVelocity() {
        return rightMaster.getSpeed() / 60 * Constants.kDriveWheelDiameterInches * Math.PI;
    }

    public double getLeftVelocity() {
        return leftMaster.getSpeed() / 60 * Constants.kDriveWheelDiameterInches * Math.PI;
    }


    public double getGyroAngle() {
        return gyro.getAngleZ() / 4;
    }

    public double getAverageDistance() {
        return (getLeftDistance() + getRightDistance()) / 2.0;
    }

    public double getAverageVelocity() {
        return (getRightVelocity() + getLeftVelocity()) / 2.0;
    }

    public void resetEncoders() {
        rightMaster.setPosition(0.0);
        leftMaster.setPosition(0.0);

        rightMaster.setEncPosition(0);
        leftMaster.setEncPosition(0);
    }

    public static DriveTrain getInstance() {
        return instance;
    }
}
