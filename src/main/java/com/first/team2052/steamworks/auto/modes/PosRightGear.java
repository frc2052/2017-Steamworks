package com.first.team2052.steamworks.auto.modes;

import com.first.team2052.steamworks.auto.AutoMode;
import com.first.team2052.steamworks.auto.AutoModeEndedException;
import com.first.team2052.steamworks.auto.AutoPaths;
import com.first.team2052.steamworks.subsystems.DriveTrain;
import com.first.team2052.trajectory.common.Path;
import com.google.common.base.Optional;

/**
 * Starts: Boiler
 * Desc: Places the gear on the right side of Airship
 * Ends: Airship
 */
public class PosRightGear extends AutoMode {
    @Override
    protected void init() throws AutoModeEndedException {
        AutoPaths myPathFactory = AutoPaths.getInstance();
        Optional<Path> myPath = myPathFactory.getPath("PosEdgeGear");
        if (myPath.isPresent()) {
            DriveTrain dt = DriveTrain.getInstance();
            myPath.get().goLeft();
            dt.setPathTrajectory(myPath.get());
        }
    }
}
