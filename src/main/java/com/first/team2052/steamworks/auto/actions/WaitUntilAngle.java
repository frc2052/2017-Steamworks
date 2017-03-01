package com.first.team2052.steamworks.auto.actions;

import com.first.team2052.steamworks.auto.AutoModeBase;
import com.first.team2052.steamworks.subsystems.drive.DriveTrain;

/**
 * Created by KnightKrawler on 2/28/2017.
 */
public class WaitUntilAngle extends AutoModeBase.Action {
    private final double angle;

    public WaitUntilAngle(double angle){
        this.angle = angle;
    }
    @Override
    public void done() {

    }

    @Override
    public boolean isFinished() {
        return Math.abs(DriveTrain.getInstance().getGyroAngleDegrees() - angle) < 1.5;
    }

    @Override
    public void start() {

    }

    @Override
    public void update() {

    }
}
