package com.first.team2052.steamworks.subsystems.drive;

import com.ctre.CANTalon;
import com.first.team2052.lib.ADIS16448_IMU;
import com.first.team2052.steamworks.Constants;
import edu.wpi.first.wpilibj.Solenoid;

/**
 * Stores hardware and configuration for the drive train hardware
 * Purely for organization
 */
class DriveTrainHardware {
    protected static final int kVelocityControlSlot = 0;

    final CANTalon rightMaster;
    private final CANTalon rightSlave, rightSlave1;
    final CANTalon leftMaster;
    private final CANTalon leftSlave, leftSlave1;
    ADIS16448_IMU gyro;
    Solenoid shifterIn, shifterOut;

    DriveTrainHardware() {
        rightMaster = new CANTalon(Constants.CAN.kDriveRight1Id);
        rightSlave = new CANTalon(Constants.CAN.kDriveRight2Id);
        rightSlave1 = new CANTalon(Constants.CAN.kDriveRight3Id);

        leftMaster = new CANTalon(Constants.CAN.kDriveLeft1Id);
        leftSlave = new CANTalon(Constants.CAN.kDriveLeft2Id);
        leftSlave1 = new CANTalon(Constants.CAN.kDriveLeft3Id);

        //Set how many encoder ticks per revolution of the wheels
        leftMaster.configEncoderCodesPerRev(Constants.Drive.kDriveEncoderTicksPerRot);
        rightMaster.configEncoderCodesPerRev(Constants.Drive.kDriveEncoderTicksPerRot);

        //Fix sensor polarity
        leftMaster.reverseSensor(false);
        rightMaster.reverseSensor(true);

        //Configure talons for follower mode
        rightSlave.changeControlMode(CANTalon.TalonControlMode.Follower);
        rightSlave1.changeControlMode(CANTalon.TalonControlMode.Follower);
        rightSlave.set(rightMaster.getDeviceID());
        rightSlave1.set(rightMaster.getDeviceID());

        leftSlave.changeControlMode(CANTalon.TalonControlMode.Follower);
        leftSlave1.changeControlMode(CANTalon.TalonControlMode.Follower);
        leftSlave.set(leftMaster.getDeviceID());
        leftSlave1.set(leftMaster.getDeviceID());

        // Load velocity control gains
        leftMaster.setPID(Constants.DriveControllers.kDriveVelocityKp, Constants.DriveControllers.kDriveVelocityKi, Constants.DriveControllers.kDriveVelocityKd,
                Constants.DriveControllers.kDriveVelocityKf, Constants.DriveControllers.kDriveVelocityIZone, Constants.DriveControllers.kDriveVelocityRampRate,
                kVelocityControlSlot);
        rightMaster.setPID(Constants.DriveControllers.kDriveVelocityKp, Constants.DriveControllers.kDriveVelocityKi, Constants.DriveControllers.kDriveVelocityKd,
                Constants.DriveControllers.kDriveVelocityKf, Constants.DriveControllers.kDriveVelocityIZone, Constants.DriveControllers.kDriveVelocityRampRate,
                kVelocityControlSlot);

        shifterIn = new Solenoid(Constants.Drive.kDriveInSolenoidId);
        shifterOut = new Solenoid(Constants.Drive.kDriveOutSolenoidId);

        gyro = new ADIS16448_IMU();
    }
}
