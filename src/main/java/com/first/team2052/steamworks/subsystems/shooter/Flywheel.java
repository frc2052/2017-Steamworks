package com.first.team2052.steamworks.subsystems.shooter;

import com.ctre.CANTalon;
import com.first.team2052.steamworks.Constants;

class Flywheel {
    private final CANTalon master_motor;
    private static Flywheel instance = new Flywheel();

    private Flywheel() {
        master_motor = new CANTalon(Constants.CAN.kShooterMotorPort);
        CANTalon slave_motor = new CANTalon(Constants.CAN.kShooterMotorSlavePort);

        //Configure for follower mode
        slave_motor.changeControlMode(CANTalon.TalonControlMode.Follower);
        slave_motor.set(master_motor.getDeviceID());

        master_motor.enableBrakeMode(false);
        slave_motor.enableBrakeMode(false);

        //Config for our encoder options
        if (Constants.Shooter.kUseDoubleEncoderExtender) {
            master_motor.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
            master_motor.configEncoderCodesPerRev(1024);
        } else {
            master_motor.setFeedbackDevice(CANTalon.FeedbackDevice.CtreMagEncoder_Relative);
        }

        master_motor.setVoltageRampRate(36.0);
        slave_motor.setVoltageRampRate(36.0);

        master_motor.setPID(Constants.Shooter.kShooterVelocityKp,
                Constants.Shooter.kShooterVelocityKi,
                Constants.Shooter.kShooterVelocityKd,
                Constants.Shooter.kShooterVelocityKf,
                Constants.Shooter.kShooterVelocityIZone,
                Constants.Shooter.kShooterVelocityCloseLoopRampRate,
                0);

        master_motor.setProfile(0);

        master_motor.changeControlMode(CANTalon.TalonControlMode.Speed);
        master_motor.set(0.0);
    }

    public void setRpm(double speed) {
        master_motor.set(speed);
    }

    public boolean isOnTarget(){
        return Math.abs(getRpm() - getSetpoint()) < Constants.Shooter.kShooterVelocityWindow;
    }

    public double getRpm() {
        return master_motor.getSpeed();
    }

    public synchronized double getSetpoint() {
        return master_motor.getSetpoint();
    }

    public static Flywheel getInstance() {
        return instance;
    }
}
