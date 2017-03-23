package com.first.team2052.steamworks.subsystems.shooter;

import com.first.team2052.lib.Loopable;
import com.first.team2052.steamworks.Constants;

public class Shooter implements Loopable {

    private static Shooter instance = new Shooter();

    private final Indexer indexer;
    private final Flywheel flywheel;

    private ShooterState shooterState = ShooterState.STOP;
    private boolean wantShoot = false, wantReverseAgitator = false, wantIdleRampUp = false;

    private Shooter() {
        indexer = Indexer.getInsance();
        flywheel = Flywheel.getInstance();
    }

    @Override
    public void update() {
        ShooterState newState = shooterState;
        switch (shooterState) {
            case STOP:
                indexer.setAgitatorSpeed(0.0);
                indexer.setIndexerSpeed(0.0);
                flywheel.setRpm(0.0);

                if (wantShoot) {
                    newState = ShooterState.RAMP_UP;
                } else if (wantIdleRampUp) {
                    newState = ShooterState.IDLE_RAMP_UP;
                } else if (wantReverseAgitator) {
                    newState = ShooterState.REVERSE_AGITATOR;
                }
                break;
            case IDLE_RAMP_UP:
                indexer.setAgitatorSpeed(0.0);
                indexer.setIndexerSpeed(0.0);
                flywheel.setRpm(Constants.Shooter.kShooterKeyVelocity);
                if (wantShoot) {
                    newState = ShooterState.RAMP_UP;
                } else if (!wantIdleRampUp) {
                    newState = ShooterState.STOP;
                }
            case RAMP_UP:
                indexer.setAgitatorSpeed(0.0);
                indexer.setIndexerSpeed(0.0);
                flywheel.setRpm(Constants.Shooter.kShooterKeyVelocity);

                if (!wantShoot) {
                    newState = ShooterState.STOP;
                } else if (flywheel.isOnTarget()) {
                    newState = ShooterState.SHOOTING;
                }
                break;
            case SHOOTING:
                indexer.setAgitatorSpeed(Constants.Shooter.kTatorSpeed);
                indexer.setIndexerSpeed(0.5);
                flywheel.setRpm(Constants.Shooter.kShooterKeyVelocity);

                if (!wantShoot) {
                    if (wantIdleRampUp) {
                        newState = ShooterState.IDLE_RAMP_UP;
                    } else {
                        newState = ShooterState.STOP;
                    }
                } else if (!flywheel.isOnTarget()) {
                    newState = ShooterState.RAMP_UP;
                }
                break;
            case REVERSE_AGITATOR:
                indexer.setAgitatorSpeed(-Constants.Shooter.kTatorSpeed);
                indexer.setIndexerSpeed(-0.5);
                flywheel.setRpm(0.0);
                if (!wantReverseAgitator) {
                    newState = ShooterState.STOP;
                }
                break;
        }

        if (newState != shooterState) {
            shooterState = newState;
            System.out.println(String.format("Shoot state changed to %s", newState.name()));
        }
        //System.out.println("rpm: " + shootMotor.getSpeed());
    }


    @Override
    public void onStart() {
        flywheel.setRpm(0);
        setWantShoot(false);
        shooterState = ShooterState.STOP;
    }

    @Override
    public void onStop() {
        flywheel.setRpm(0);
        setWantShoot(false);
        shooterState = ShooterState.STOP;
    }

    public ShooterState getShooterState() {
        return shooterState;
    }

    public void setWantReverseAgitator(boolean wantReverseAgitator) {
        this.wantReverseAgitator = wantReverseAgitator;
    }

    public void setWantShoot(boolean wantShoot) {
        this.wantShoot = wantShoot;
    }

    public void setWantIdleRampUp(boolean wantIdleRampUp) {
        this.wantIdleRampUp = wantIdleRampUp;
    }

    public enum ShooterState {
        IDLE_RAMP_UP,
        RAMP_UP,
        SHOOTING,
        STOP,
        REVERSE_AGITATOR
    }

    public static Shooter getInstance() {
        return instance;
    }
}
