package com.first.team2052.steamworks.subsystems;

import com.ctre.CANTalon;
import com.first.team2052.lib.Loopable;
import com.first.team2052.steamworks.Constants;
import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;

public class Shooter implements Loopable {
    public static final int kVelocityProfile = 0;
    private CANTalon leftAgitator, rightAgitator, shootMotor, shootMotorSlave;
    private ShooterState shooterState = ShooterState.STOP;
    private boolean wantShoot = false;
    private static Shooter instance = new Shooter();
    private int shooterVeloitySetpoint = Constants.Shooter.kShooterKeyVelocity;

    private Shooter() {
        leftAgitator = new CANTalon(Constants.CAN.kLeftAgitatorMotorPort);
        rightAgitator = new CANTalon(Constants.CAN.kRightAgitatorMotorPort);
        shootMotor = new CANTalon(Constants.CAN.kShooterMotorPort);
        shootMotorSlave = new CANTalon(Constants.CAN.kShooterMotorSlavePort);

        shootMotorSlave.changeControlMode(CANTalon.TalonControlMode.Follower);
        shootMotorSlave.set(shootMotor.getDeviceID());

        shootMotor.changeControlMode(CANTalon.TalonControlMode.Speed);
        shootMotor.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
        shootMotor.configEncoderCodesPerRev(1024);
        shootMotor.configPeakOutputVoltage(12.0f, -0.0f);
        shootMotor.reverseOutput(false);
        shootMotorSlave.reverseOutput(false);
        shootMotor.reverseSensor(false);
        shootMotor.enableBrakeMode(false);
        shootMotorSlave.enableBrakeMode(false);
        shootMotor.setPID(Constants.Shooter.kShooterVelocityKp, Constants.Shooter.kShooterVelocityKi, Constants.Shooter.kShooterVelocityKd, Constants.Shooter.kShooterVelocityKf, Constants.Shooter.kShooterVelocityIZone, Constants.Shooter.kShooterVelocityCloseLoopRampRate, kVelocityProfile);
        shootMotor.set(0.0);
    }

    public void setAgitatorSpeed(double speed) {
        leftAgitator.set(speed);
        rightAgitator.set(-speed);
    }

    public double getAgitatorAdaptiveSpeed() {
        int maxError = 400;
        double speed = -1.0 / maxError * (shootMotor.getError()) + 1.0;
        return speed <= 0.0 ? 0.0 : speed;
    }

    public void setMotorSpeedOpen() {
        //shootMotor.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
        shootMotor.set(3500);
    }

    @Override
    public void update() {
        ShooterState newState = shooterState;
        switch (shooterState) {
            case RAMP_UP:
                shootMotor.set(shooterVeloitySetpoint);
                if (!wantShoot) {
                    newState = ShooterState.STOP;
                } else if (isOnTarget()) {
                    newState = ShooterState.SHOOTING;
                }
                setAgitatorSpeed(0.0);
                break;
            case SHOOTING:
                if (!wantShoot) {
                    newState = ShooterState.STOP;
                } else if (!isOnTarget()) {
                    newState = ShooterState.RAMP_UP;
                }
                setAgitatorSpeed(Constants.Shooter.kTatorSpeed);
                shootMotor.set(shooterVeloitySetpoint);
                break;
            case STOP:
                if (wantShoot) {
                    newState = ShooterState.RAMP_UP;
                }
                setAgitatorSpeed(0.0);
                shootMotor.set(0.0);
                break;
        }

        if (newState != shooterState) {
            shooterState = newState;
            System.out.println("Shoot state changed");
        }
        System.out.println("shooter rpm:" + shootMotor.getSpeed());
    }

    public boolean isOnTarget() {
        return Math.abs(shootMotor.getSpeed() - shooterVeloitySetpoint) < 300;
    }

    public void setShooterSpeed(int rpm) {
        shooterVeloitySetpoint = rpm;
        shootMotor.set(rpm);
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
    }

    public void setWantShoot(boolean wantShoot) {
        this.wantShoot = wantShoot;
    }

    public enum ShooterState {
        RAMP_UP,
        SHOOTING,
        STOP
    }

    public static Shooter getInstance() {
        return instance;
    }
}
