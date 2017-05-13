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
public class PosBoilerHopperShoot extends AutoMode {
    @Override
    protected void init() throws AutoModeEndedException {
        double fwd = 200.0;

        double cosA = Math.cos(Math.PI / 3);
        double sinA = Math.sin(Math.PI / 3);

        double turn = Math.toRadians(42.25);
        double distance_backward = 35;


        List<Path.Waypoint> forwardPath = Lists.newArrayList();
        forwardPath.add(new Path.Waypoint(new Translation2d(0, 0), 80));
        forwardPath.add(new Path.Waypoint(new Translation2d(-82, 20), 80));
        forwardPath.add(new Path.Waypoint(new Translation2d(-90, 25), 80));
        forwardPath.add(new Path.Waypoint(new Translation2d(-123, -8.5), 80));
        forwardPath.add(new Path.Waypoint(new Translation2d(-123, -28), 80));

        List<Path.Waypoint> backwardPath = Lists.newArrayList();
        backwardPath.add(new Path.Waypoint(new Translation2d(-123, -28), 60));
        backwardPath.add(new Path.Waypoint(new Translation2d(-123, -19.5), 60));
        backwardPath.add(new Path.Waypoint(new Translation2d(-130, 4), 60));


        List<Path.Waypoint> boilerPath = Lists.newArrayList();
        boilerPath.add(new Path.Waypoint(new Translation2d(-130, 4), 80));
        boilerPath.add(new Path.Waypoint(new Translation2d(-77, -20), 80));
        boilerPath.add(new Path.Waypoint(new Translation2d(-distance_backward, 0), 80));
        boilerPath.add(new Path.Waypoint(new Translation2d(-distance_backward + (24 * Math.cos(turn)), -24 * Math.sin(turn)), 80));

        if (isRed()) {
            forwardPath = Util.inverseY(forwardPath);
            backwardPath = Util.inverseY(backwardPath);
            boilerPath = Util.inverseY(boilerPath);
        }
        Pickup.getInstance().setIntakeState(Pickup.PickupState.IN);

        //Drive up to the hopper and wait to load balls
        runAction(new SeriesAction(Arrays.asList(new FollowPathAction(new Path(forwardPath), true))));

        DriveTrain.getInstance().setOpenLoop(new DriveSignal(-.50, -.50));
        runAction(new WaitAction(0.25));
        DriveTrain.getInstance().setOpenLoop(DriveSignal.NEUTRAL);

        //Start running the shooter, but don't shoot
        //Shooter.getInstance().setWantIdleRampUp(true);

        //Drive back and drive towards boiler
        runAction(new SeriesAction(Arrays.asList(new FollowPathAction(new Path(backwardPath), false))));
        runAction(new SeriesAction(Arrays.asList(new FollowPathAction(new Path(boilerPath), true))));


        runAction(new StartShootingAction());
        DriveTrain.getInstance().setOpenLoop(new DriveSignal(-.50, -.50));
        runAction(new WaitAction(0.5));
        DriveTrain.getInstance().setOpenLoop(DriveSignal.NEUTRAL);

        runAction(new WaitAction(4));

        Pickup.getInstance().setIntakeState(Pickup.PickupState.STOP);
        //Start shooting
        Shooter.getInstance().setWantIdleRampUp(false);
        Shooter.getInstance().setWantShoot(false);
    }
}
