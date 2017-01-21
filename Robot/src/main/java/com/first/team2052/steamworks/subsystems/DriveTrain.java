package com.first.team2052.steamworks.subsystems;

import com.ctre.CANTalon;
import com.first.team2052.lib.Loopable;
import com.first.team2052.steamworks.Constants;

public class DriveTrain implements Loopable {
    CANTalon rightMaster, rightSlave/*, rightSlave1*/;
    CANTalon leftMaster, leftSlave/*, leftSlave1*/;

    public DriveTrain() {
        rightMaster = new CANTalon(Constants.kDriveRight1Id);
        rightSlave = new CANTalon(Constants.kDriveRight2Id);
        /*rightSlave1 = new CANTalon(Constants.kDriveRight3Id);*/

        leftMaster = new CANTalon(Constants.kDriveLeft1Id);
        leftSlave = new CANTalon(Constants.kDriveLeft2Id);
        /*leftSlave1 = new CANTalon(Constants.kDriveLeft3Id);*/

        rightSlave.changeControlMode(CANTalon.TalonControlMode.Follower);
        /*rightSlave1.changeControlMode(CANTalon.TalonControlMode.Follower);*/
        rightSlave.set(rightMaster.getDeviceID());
        /*rightSlave1.set(rightMaster.getDeviceID());*/

        leftSlave.changeControlMode(CANTalon.TalonControlMode.Follower);
        /*leftSlave1.changeControlMode(CANTalon.TalonControlMode.Follower);*/
        leftSlave.set(leftMaster.getDeviceID());
        /*leftSlave1.set(leftMaster.getDeviceID());*/
    }

    public void setLeftRight(double leftSpeed, double rightSpeed) {
        leftMaster.set(-leftSpeed);
        rightMaster.set(rightSpeed);
    }

    @Override
    public void update() {

    }
}
