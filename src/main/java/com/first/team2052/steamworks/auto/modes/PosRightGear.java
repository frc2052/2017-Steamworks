package com.first.team2052.steamworks.auto.modes;

import com.first.team2052.steamworks.auto.AutoMode;
import com.first.team2052.steamworks.auto.AutoModeEndedException;
import com.first.team2052.steamworks.auto.AutoPaths;
import com.first.team2052.steamworks.subsystems.VisionProcessor;
import com.first.team2052.steamworks.subsystems.drive.DriveTrain;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Starts: Boiler
 * Desc: Places the gear on the right side of Airship
 * Ends: Airship
 */
public class PosRightGear extends AutoMode {
    @Override
    protected void init() throws AutoModeEndedException {
//        driveStraightDistance(-65, 24);
//        DriveTrain.getInstance().turnInPlace(60);
//        Timer.delay(10);
//        driveStraightDistance(-80, 24);
        //drivePath(AutoPaths.getInstance().getPath("TestPath"), false, true);


        driveStraightDistance(-72, 25);
        DriveTrain.getInstance().turnInPlace(-60);
        Timer.delay(5);
        driveStraightDistance(-50, 25);
        double angle = VisionProcessor.getInstance().getXAngleFromCenter();
        int attempts = 0;
        while (attempts < 10 && (angle > 1 || angle < -1))
        {
            attempts++;
            DriveTrain.getInstance().turnInPlace(angle);
            Timer.delay(1);
            angle = VisionProcessor.getInstance().getXAngleFromCenter();
        }
        driveStraightDistance(-15, 25);
    }
}
