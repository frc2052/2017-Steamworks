package com.first.team2052.steamworks.auto.modes;

import com.first.team2052.steamworks.auto.AutoMode;
import com.first.team2052.steamworks.auto.AutoModeEndedException;
import com.first.team2052.steamworks.auto.AutoModeSelector;
import com.first.team2052.steamworks.auto.AutoPaths;
import com.first.team2052.steamworks.subsystems.drive.DriveTrain;
import com.first.team2052.trajectory.common.Path;
import com.google.common.base.Optional;
import edu.wpi.first.wpilibj.DriverStation;

/**
 * Starts: Boiler
 * Desc: Presses against the hopper
 * Ends: Hopper
 */

public class PosBoilerHopper extends AutoMode{

    @Override
    protected void init() throws AutoModeEndedException {
        AutoPaths myPathFactory = AutoPaths.getInstance();
        Optional<Path> myPath = myPathFactory.getPath("PosBoilerHopper");
        if (myPath.isPresent()) {
            DriveTrain dt = DriveTrain.getInstance();
            if(AutoModeSelector.isOnBlue()) {
                myPath.get().goLeft();
            }else{
                myPath.get().goRight();
            }
            dt.drivePathTrajectory(myPath.get());
        }
    }



}
