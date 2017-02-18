package com.first.team2052.steamworks.auto.modes;

import com.first.team2052.steamworks.auto.AutoMode;
import com.first.team2052.steamworks.auto.AutoModeEndedException;
import com.first.team2052.steamworks.auto.AutoModeSelector;
import com.first.team2052.steamworks.auto.AutoPaths;
import com.first.team2052.steamworks.subsystems.DriveTrain;
import com.first.team2052.trajectory.common.Path;
import com.google.common.base.Optional;
import edu.wpi.first.wpilibj.DriverStation;

/**
 * Starts: Boiler
 * Desc: Turns and shoots
 * Ends: Back at the Boiler
 */
public class PosBoilerShoot extends AutoMode{
    @Override
    protected void init() throws AutoModeEndedException {
        AutoPaths myPathFactory = AutoPaths.getInstance();
        Optional<Path> myPath = myPathFactory.getPath("PosBoilerShoot");
        if (myPath.isPresent()) {
            DriveTrain dt = DriveTrain.getInstance();
            if(AutoModeSelector.isOnBlue()) {
                myPath.get().goLeft();
            }else{
                myPath.get().goRight();
            }
            dt.setPathTrajectory(myPath.get());
        }
    }
}
