package com.first.team2052.steamworks.auto.modes;

import com.first.team2052.lib.path.Path;
import com.first.team2052.lib.vec.Translation2d;
import com.first.team2052.steamworks.Util;
import com.first.team2052.steamworks.auto.AutoMode;
import com.first.team2052.steamworks.auto.AutoModeEndedException;
import com.first.team2052.steamworks.auto.actions.FollowPathAction;
import com.first.team2052.steamworks.auto.actions.SeriesAction;
import com.first.team2052.steamworks.auto.actions.StartShootingAction;
import com.first.team2052.steamworks.auto.actions.WaitAction;
import com.first.team2052.steamworks.subsystems.Pickup;
import com.first.team2052.steamworks.subsystems.drive.DriveSignal;
import com.first.team2052.steamworks.subsystems.drive.DriveTrain;
import com.first.team2052.steamworks.subsystems.shooter.Shooter;
import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.List;

/**
 * Starts: Boiler
 * Desc: triggers a hopper and then shoots
 * Ends: boiler
 */
public class PosBoilerHopperShoot002 extends AutoMode {
    @Override
    protected void init() throws AutoModeEndedException {
        double fwd = 100.5;

        double cosA = Math.cos(Math.PI / 3);
        double sinA = Math.sin(Math.PI / 3);


        List<Path.Waypoint> forwardPath = Lists.newArrayList();
        forwardPath.add(new Path.Waypoint(new Translation2d(0, 0), 80));
        forwardPath.add(new Path.Waypoint(new Translation2d(fwd - 20, 0), 50));
        forwardPath.add(new Path.Waypoint(new Translation2d(fwd - 10, 5), 30));
        forwardPath.add(new Path.Waypoint(new Translation2d(fwd, 20), 30));
        forwardPath.add(new Path.Waypoint(new Translation2d(fwd, 55), 30));

        List<Path.Waypoint> middlePath = Lists.newArrayList();
        middlePath.add(new Path.Waypoint(new Translation2d(fwd, 55), 30));
        middlePath.add(new Path.Waypoint(new Translation2d(fwd, 40), 30));
        middlePath.add(new Path.Waypoint(new Translation2d(fwd + 5, 30), 30));
        middlePath.add(new Path.Waypoint(new Translation2d(fwd + 15, 20), 30));
        middlePath.add(new Path.Waypoint(new Translation2d(fwd + 20, 20), 30));

        List<Path.Waypoint> backwardPath = Lists.newArrayList();
        backwardPath.add(new Path.Waypoint(new Translation2d(fwd + 20, 20), 60));
        backwardPath.add(new Path.Waypoint(new Translation2d(150, 20 ), 60));
        backwardPath.add(new Path.Waypoint(new Translation2d(100, 15 * sinA), 50));
        backwardPath.add(new Path.Waypoint(new Translation2d(42-15 * cosA, 15 * sinA), 30));
        backwardPath.add(new Path.Waypoint(new Translation2d(42-39 * cosA, 39 * sinA), 30));

        if (isRed()) {
            forwardPath = Util.inverseY(forwardPath);
            middlePath = Util.inverseY(middlePath);
            backwardPath = Util.inverseY(backwardPath);
        }

        //Drive up to the hopper and wait to load balls
        runAction(new SeriesAction(Arrays.asList(new FollowPathAction(new Path(forwardPath), true))));

        DriveTrain.getInstance().setOpenLoop(new DriveSignal(-.25, -.25));
        runAction(new WaitAction(0.25));
        DriveTrain.getInstance().setOpenLoop(DriveSignal.NEUTRAL);

        //reverse into a postion to pick up balls
        runAction(new SeriesAction(Arrays.asList(new FollowPathAction(new Path(middlePath), false))));

        //Start running the shooter, but don't shoot
        Shooter.getInstance().setWantIdleRampUp(true);

        //turn on intake
        Pickup.getInstance().setIntakeState(Pickup.PickupState.IN);

        //Drive back and drive towards boiler
        runAction(new SeriesAction(Arrays.asList(new FollowPathAction(new Path(backwardPath), true))));

        //turn off intake
        Pickup.getInstance().setIntakeState(Pickup.PickupState.STOP);

        DriveTrain.getInstance().setOpenLoop(new DriveSignal(-.25, -.25));
        runAction(new WaitAction(0.25));
        DriveTrain.getInstance().setOpenLoop(DriveSignal.NEUTRAL);

        runAction(new SeriesAction(Arrays.asList(new StartShootingAction(), new WaitAction(4))));

        //Start shooting
        Shooter.getInstance().setWantIdleRampUp(false);
        Shooter.getInstance().setWantShoot(false);
    }
}
