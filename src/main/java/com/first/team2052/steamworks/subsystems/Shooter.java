package com.first.team2052.steamworks.subsystems;

import com.ctre.CANTalon;
import com.first.team2052.lib.Loopable;
import com.first.team2052.steamworks.Constants;

public class Shooter implements Loopable {
    public static final int kVelocityProfile = 0;
    private CANTalon leftAgitator, rightAgitator, shootMotor, shootMotorSlave;
    private ShooterState shooterState = ShooterState.STOP;
    private boolean wantShoot = false;
    private static Shooter instance = new Shooter();
    private int shooterVelocitySetpoint = Constants.Shooter.kShooterKeyVelocity;

    private Shooter() {
        leftAgitator = new CANTalon(Constants.CAN.kLeftAgitatorMotorPort);
        rightAgitator = new CANTalon(Constants.CAN.kRightAgitatorMotorPort);
        shootMotor = new CANTalon(Constants.CAN.kShooterMotorPort);
        shootMotorSlave = new CANTalon(Constants.CAN.kShooterMotorSlavePort);


        //Ensure never go backwards. No matter what. If you send a negative power/voltage it'll be 0 speed (coast)
        shootMotor.configPeakOutputVoltage(12.0f, -0.0f);

        //Configure for follower mode
        shootMotorSlave.changeControlMode(CANTalon.TalonControlMode.Follower);
        shootMotorSlave.set(shootMotor.getDeviceID());

        //Config for our encoder options
        if (Constants.Shooter.kUseDoubleEncoderExtender) {
            shootMotor.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
            shootMotor.configEncoderCodesPerRev(1024);
        } else {
            shootMotor.setFeedbackDevice(CANTalon.FeedbackDevice.CtreMagEncoder_Relative);
        }

        shootMotor.reverseSensor(false);

        shootMotor.reverseOutput(false);
        shootMotorSlave.reverseOutput(false);

        shootMotor.enableBrakeMode(false);
        shootMotorSlave.enableBrakeMode(false);

        shootMotor.changeControlMode(CANTalon.TalonControlMode.Speed);

        shootMotor.setPID(Constants.Shooter.kShooterVelocityKp,
                Constants.Shooter.kShooterVelocityKi,
                Constants.Shooter.kShooterVelocityKd,
                Constants.Shooter.kShooterVelocityKf,
                Constants.Shooter.kShooterVelocityIZone,
                Constants.Shooter.kShooterVelocityCloseLoopRampRate,
                kVelocityProfile);
        shootMotor.set(0.0);
    }

    private void setAgitatorSpeed(double speed) {
        leftAgitator.set(-speed);
        rightAgitator.set(speed);
    }

    @Override
    public void update() {
        ShooterState newState = shooterState;
        switch (shooterState) {
            case RAMP_UP:
                shootMotor.set(shooterVelocitySetpoint);
                if (!wantShoot) {
                    newState = ShooterState.STOP;
                } else if (isOnTargetSpeed()) {
                    newState = ShooterState.SHOOTING;
                }
                setAgitatorSpeed(0.0);
                break;
            case SHOOTING:
                if (!wantShoot) {
                    newState = ShooterState.STOP;
                } else if (!isOnTargetSpeed()) {
                    newState = ShooterState.RAMP_UP;
                }
                setAgitatorSpeed(Constants.Shooter.kTatorSpeed);
                shootMotor.set(shooterVelocitySetpoint);
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
            System.out.println(String.format("Shoot state changed to %s", newState.name()));
        }
        System.out.println("rpm: " + shootMotor.getSpeed());
    }

    public boolean isOnTargetSpeed() {
        // the speed we want +- the window
        return Math.abs(shootMotor.getSpeed() - shooterVelocitySetpoint) <= Constants.Shooter.kShooterVelocityWindow;
    }

    public void setShooterSpeed(int rpm) {
        shooterVelocitySetpoint = rpm;
    }

    @Override
    public void onStart() {
        shootMotor.set(0);
        setWantShoot(false);
        shooterState = ShooterState.STOP;
    }

    @Override
    public void onStop() {
        shootMotor.set(0);
        setWantShoot(false);
        shooterState = ShooterState.STOP;
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
