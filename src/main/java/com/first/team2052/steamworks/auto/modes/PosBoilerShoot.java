package com.first.team2052.steamworks.auto.modes;

import com.first.team2052.lib.path.Path;
import com.first.team2052.lib.vec.Rotation2d;
import com.first.team2052.lib.vec.Translation2d;
import com.first.team2052.steamworks.auto.AutoMode;
import com.first.team2052.steamworks.auto.AutoModeEndedException;
import com.first.team2052.steamworks.auto.actions.FollowPathAction;
import com.first.team2052.steamworks.subsystems.drive.DriveTrain;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * Starts: Boiler
 * Desc: Turns and shoots
 * Ends: Back at the Boiler
 */
public class PosBoilerShoot extends AutoMode {
    @Override
    protected void init() throws AutoModeEndedException {
//        driveStraightDistance(46, 36);
//        if (AutoModeSelector.getSide() == AutoModeSelector.Side.RED) {
//            DriveTrain.getInstance().turnInPlace(-44.25);
//            runAction(new WaitUntilAngle(-44.25));
//        } else {
//            DriveTrain.getInstance().turnInPlace(44.25);
//            runAction(new WaitUntilAngle(44.25));
//        }
//        driveStraightDistance(-39, 24);
//        Timer.delay(0.5);
//        DriveTrain.getInstance().setOpenLoop(-.25, -.25);
//        Timer.delay(0.5);
//        DriveTrain.getInstance().setOpenLoop(0, 0);
//        Shooter.getInstance().setWantShoot(true);
//        Timer.delay(4.5);
//        Shooter.getInstance().setWantShoot(false);
//        driveStraightDistance(100, 36);

        List<Path.Waypoint> forwardPath = Lists.newArrayList();
        forwardPath.add(new Path.Waypoint(new Translation2d(0, 0), 20));
        forwardPath.add(new Path.Waypoint(new Translation2d(-30, -30), 20));
        forwardPath.add(new Path.Waypoint(new Translation2d(-55, -10), 20));
        forwardPath.add(new Path.Waypoint(new Translation2d(-48, -5), 20));
        forwardPath.add(new Path.Waypoint(new Translation2d(-12, 30), 20));

        runAction(new FollowPathAction(new Path(forwardPath), true));

        DriveTrain.getInstance().setVelocityHeadingSetpoint(20, Rotation2d.fromDegrees(-180));
    }
}
