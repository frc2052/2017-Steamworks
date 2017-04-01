package com.first.team2052.steamworks.auto.modes;

import com.first.team2052.lib.path.Path;
import com.first.team2052.lib.vec.Translation2d;
import com.first.team2052.steamworks.auto.AutoMode;
import com.first.team2052.steamworks.auto.AutoModeEndedException;
import com.first.team2052.steamworks.auto.actions.*;
import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.List;

/**
 * Starts: Boiler
 * Desc: Places the gear on the left side of Airship
 * Ends: Airship
 */
public class PosLeftGear extends AutoMode {
    @Override
    protected void init() throws AutoModeEndedException {
        double distance = 74.0;
        double distance_to_peg_polar = 57.0;

        List<Path.Waypoint> forwardPath = Lists.newArrayList();
        forwardPath.add(new Path.Waypoint(new Translation2d(0, 0), 80));
        forwardPath.add(new Path.Waypoint(new Translation2d(distance - 20, 0), 30));
        forwardPath.add(new Path.Waypoint(new Translation2d(distance, 0), 30));
        forwardPath.add(new Path.Waypoint(new Translation2d(distance + (10 * Math.cos(Math.PI / 3)), -(10 * Math.sin(Math.PI / 3))), 30));
        forwardPath.add(new Path.Waypoint(new Translation2d(distance + ((distance_to_peg_polar - 20) * Math.cos(Math.PI / 3)), -((distance_to_peg_polar - 20) * Math.sin(Math.PI / 3))), 20));
        forwardPath.add(new Path.Waypoint(new Translation2d(distance + (distance_to_peg_polar * Math.cos(Math.PI / 3)), -(distance_to_peg_polar * Math.sin(Math.PI / 3))), 20));

        List<Path.Waypoint> backwardPath = Lists.newArrayList();
        backwardPath.add(new Path.Waypoint(new Translation2d(distance + (distance_to_peg_polar * Math.cos(Math.PI / 3)), -(distance_to_peg_polar * Math.sin(Math.PI / 3))), 12));
        backwardPath.add(new Path.Waypoint(new Translation2d(distance + (45 * Math.cos(Math.PI / 3)), -(45 * Math.sin(Math.PI / 3))), 60));
        backwardPath.add(new Path.Waypoint(new Translation2d(distance, 0), 60, "CloseGearMan"));
        backwardPath.add(new Path.Waypoint(new Translation2d(distance - 20, 0), 60));

        List<Path.Waypoint> middleFieldPath = Lists.newArrayList();
        middleFieldPath.add(new Path.Waypoint(new Translation2d(distance - 20, 0), 60));
        middleFieldPath.add(new Path.Waypoint(new Translation2d(distance + 50, 0), 60));

        //Drive up to the peg and drop gear
        runAction(new SeriesAction(Arrays.asList(new FollowPathAction(new Path(forwardPath), false), new DropGearAction())));

        //Drive back and drive towards center of field
        runAction(new ParallelAction(Arrays.asList(
                new SeriesAction(Arrays.asList(new FollowPathAction(new Path(backwardPath), true), new FollowPathAction(new Path(middleFieldPath), false))),
                new SeriesAction(Arrays.asList(new WaitForPathMarkerAction("CloseGearMan"), new CloseGearAction()))
        )));
    }
}