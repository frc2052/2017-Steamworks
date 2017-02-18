package com.first.team2052.steamworks.auto.actions;

import com.first.team2052.steamworks.auto.AutoModeBase;
import com.first.team2052.steamworks.subsystems.drive.DriveTrain;
import edu.wpi.first.wpilibj.Timer;

public class WaitUntilDistanceAction extends AutoModeBase.Action {
    private double curPos, goalPos;

    public WaitUntilDistanceAction(double distance) {
        curPos =  (DriveTrain.getInstance().getLeftDistanceInches()
                + DriveTrain.getInstance().getRightDistanceInches()) / 2;
        goalPos = distance + curPos;
    }

    @Override
    public void done() {
    }

    @Override
    public boolean isFinished() {
        return (curPos <= goalPos + 0.25
                && curPos >= goalPos - 0.25);
    }

    @Override
    public void start() {
    }

    @Override
    public void update() {
        curPos = (DriveTrain.getInstance().getLeftDistanceInches()
                + DriveTrain.getInstance().getRightDistanceInches()) / 2;
    }
}
