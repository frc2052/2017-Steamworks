package com.first.team2052.steamworks.auto.modes;

import com.first.team2052.steamworks.Robot;
import com.first.team2052.steamworks.auto.AutoMode;
import com.first.team2052.steamworks.auto.AutoModeEndedException;
import com.first.team2052.steamworks.auto.AutoPaths;
import com.first.team2052.steamworks.subsystems.VisionProcessor;
import com.first.team2052.steamworks.subsystems.drive.DriveTrain;
import com.first.team2052.trajectory.common.Path;
import com.google.common.base.Optional;
import edu.wpi.first.wpilibj.Timer;

/**
 * Starts: Boiler
 * Desc: Places the gear on the left side of Airship
 * Ends: Airship
 */
public class PosLeftGear extends AutoMode {
    @Override
    protected void init() throws AutoModeEndedException {
//        driveStraightDistance(-65, 24);
//        DriveTrain.getInstance().turnInPlace(60);
//        Timer.delay(10);
//        driveStraightDistance(-80, 24);
        //drivePath(AutoPaths.getInstance().getPath("TestPath"), false, true);


        driveStraightDistance(72, 25);
        DriveTrain.getInstance().turnInPlace(60);
        Timer.delay(5);
        driveStraightDistance(50, 25);
    }
}