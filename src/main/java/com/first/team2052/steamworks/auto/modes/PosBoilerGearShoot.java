package com.first.team2052.steamworks.auto.modes;

import com.first.team2052.lib.path.Path;
import com.first.team2052.lib.vec.Translation2d;
import com.first.team2052.steamworks.auto.AutoMode;
import com.first.team2052.steamworks.auto.AutoModeEndedException;
import com.first.team2052.steamworks.auto.actions.*;
import com.first.team2052.steamworks.subsystems.shooter.Shooter;
import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.List;

/**
 * Starts: Boiler
 * Desc: Places the gear on the right side of Airship then shoots into the Boiler
 * Ends: Boiler
 */
public class PosBoilerGearShoot extends AutoMode {
    @Override
    protected void init() throws AutoModeEndedException {
        double distance = 79.0;
        double distance_to_peg_polar = 59.0;
        double turn = 42.25;
        double distance_backward = 42;

        List<Path.Waypoint> forwardPath = Lists.newArrayList();
        forwardPath.add(new Path.Waypoint(new Translation2d(0, 0), 50));
        forwardPath.add(new Path.Waypoint(new Translation2d(distance, 0), 40));
        forwardPath.add(new Path.Waypoint(new Translation2d(distance + (20 * Math.cos(Math.PI / 3)), isBlue() ? -1 : 1 * (20 * Math.sin(Math.PI / 3))), 20));
        forwardPath.add(new Path.Waypoint(new Translation2d(distance + (distance_to_peg_polar * Math.cos(Math.PI / 3)), isBlue() ? -1 : 1 * (distance_to_peg_polar * Math.sin(Math.PI / 3))), 20));

        List<Path.Waypoint> backwardPath = Lists.newArrayList();
        backwardPath.add(new Path.Waypoint(new Translation2d(distance + (distance_to_peg_polar * Math.cos(Math.PI / 3)), isBlue() ? -1 : 1 * (distance_to_peg_polar * Math.sin(Math.PI / 3))), 12));
        backwardPath.add(new Path.Waypoint(new Translation2d(distance + (20 * Math.cos(Math.PI / 3)), isBlue() ? -1 : 1 * (20 * Math.sin(Math.PI / 3))), 12));
        backwardPath.add(new Path.Waypoint(new Translation2d(distance, 0), 40, "CloseGearMan"));
        backwardPath.add(new Path.Waypoint(new Translation2d(distance_backward - (15 * Math.cos(turn)), isBlue() ? -1 : 1 * 15 * Math.sin(turn)), 20));
        backwardPath.add(new Path.Waypoint(new Translation2d(distance_backward - (39 * Math.cos(turn)), isBlue() ? -1 : 1 * 39 * Math.sin(turn)), 15));

        //Drive up to the peg and drop gear
        runAction(new SeriesAction(Arrays.asList(new FollowPathAction(new Path(forwardPath), false), new DropGearAction())));

        //Start running the shooter, but don't shoot
        Shooter.getInstance().setWantIdleRampUp(true);

        //Drive back to the boiler
        runAction(new ParallelAction(Arrays.asList(
                new SeriesAction(Arrays.asList(new FollowPathAction(new Path(backwardPath), true))),
                new SeriesAction(Arrays.asList(new WaitForPathMarkerAction("CloseGearMan"), new CloseGearAction()))
        )));

        runAction(new SeriesAction(Arrays.asList(new StartShootingAction(), new WaitAction(4))));

        //Start shooting
        Shooter.getInstance().setWantIdleRampUp(false);
        Shooter.getInstance().setWantShoot(false);
    }
}
