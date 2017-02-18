package com.first.team2052.steamworks.auto.modes;

import com.first.team2052.steamworks.auto.AutoMode;
import com.first.team2052.steamworks.auto.AutoModeEndedException;
import com.first.team2052.steamworks.auto.AutoModeSelector;
import com.first.team2052.steamworks.auto.AutoPaths;
import com.first.team2052.steamworks.subsystems.drive.DriveTrain;
import com.first.team2052.trajectory.common.Path;
import com.google.common.base.Optional;

/**
 * Starts: Boiler
 * Desc: Presses the Hopper, prepares to shoot
 * Ends: Boiler
 */
public class PosBoilerHopperShoot extends AutoMode{
    @Override
    protected void init() throws AutoModeEndedException {
        DriveTrain dt = DriveTrain.getInstance();
        AutoPaths myPathFactory = AutoPaths.getInstance();
        Optional<Path> myPath = myPathFactory.getPath("PosBoilerHopper"); //goes to hopper
        if (myPath.isPresent()) {
            if(AutoModeSelector.isOnBlue()) {
                myPath.get().goLeft();
            }else{
                myPath.get().goRight();
            }
            dt.drivePathTrajectory(myPath.get());
        }
        dt.setDistanceTrajectory(-60); //back up
        Optional<Path> myPath2 = myPathFactory.getPath("PosBoilerHopperToBoiler"); //go to boiler
        if (myPath2.isPresent()) {
            if(AutoModeSelector.isOnBlue()) {
                myPath2.get().goLeft();
            }else{
                myPath2.get().goRight();
            }
            dt.drivePathTrajectory(myPath2.get());
        }
        //shoot
    }
}
