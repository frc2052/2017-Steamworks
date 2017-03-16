package com.first.team2052.steamworks.auto.modes;

import com.first.team2052.steamworks.auto.AutoMode;
import com.first.team2052.steamworks.auto.AutoModeEndedException;
import com.first.team2052.steamworks.auto.AutoModeSelector;
import com.first.team2052.steamworks.auto.AutoPaths;
import com.first.team2052.steamworks.auto.actions.WaitUntilAngle;
import com.first.team2052.steamworks.subsystems.Shooter;
import com.first.team2052.steamworks.subsystems.drive.DriveTrain;
import com.first.team2052.trajectory.common.Path;
import com.google.common.base.Optional;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;

/**
 * Starts: Boiler
 * Desc: Turns and shoots
 * Ends: Back at the Boiler
 */
public class PosBoilerShoot extends AutoMode {
    @Override
    protected void init() throws AutoModeEndedException {
        driveStraightDistance(46, 36);
        if (AutoModeSelector.getSide() == AutoModeSelector.Side.RED) {
            DriveTrain.getInstance().turnInPlace(-44.25);
            runAction(new WaitUntilAngle(-44.25));
        } else {
            DriveTrain.getInstance().turnInPlace(44.25);
            runAction(new WaitUntilAngle(44.25));
        }
        driveStraightDistance(-39, 24);
        Timer.delay(0.5);
        DriveTrain.getInstance().setOpenLoop(-.25, -.25);
        Timer.delay(0.5);
        DriveTrain.getInstance().setOpenLoop(0, 0);
        Shooter.getInstance().setWantShoot(true);
        Timer.delay(4.5);
        Shooter.getInstance().setWantShoot(false);
        driveStraightDistance(100, 36);
    }
}
