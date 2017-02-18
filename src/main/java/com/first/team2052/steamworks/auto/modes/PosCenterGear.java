package com.first.team2052.steamworks.auto.modes;

import com.first.team2052.steamworks.auto.AutoMode;
import com.first.team2052.steamworks.auto.AutoModeEndedException;
import com.first.team2052.steamworks.auto.AutoPaths;
import com.first.team2052.steamworks.subsystems.DriveTrain;
import com.first.team2052.trajectory.common.Path;
import com.google.common.base.Optional;

/**
 * Starts: Center against alliance wall
 * Desc: Brings a gear to the airship
 * Ends: Airship
 */
public class PosCenterGear extends AutoMode {
    @Override
    protected void init() throws AutoModeEndedException {
        AutoPaths myPathFactory = AutoPaths.getInstance();
        Optional<Path> myPath = myPathFactory.getPath("PosCenterGear");
        if (myPath.isPresent()) {
            DriveTrain dt = DriveTrain.getInstance();
            dt.setPathTrajectory(myPath.get());
        }
    }
}
