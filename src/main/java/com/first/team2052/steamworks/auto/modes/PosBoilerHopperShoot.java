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
 * Desc: Places the gear on the right side of Airship
 * Ends: Airship
 */
public class PosBoilerHopperShoot extends AutoMode {
    @Override
    protected void init() throws AutoModeEndedException {
        double fwd = 200.0;

        double cosA = Math.cos(Math.PI / 3);
        double sinA = Math.sin(Math.PI / 3);


        List<Path.Waypoint> forwardPath = Lists.newArrayList();
        forwardPath.add(new Path.Waypoint(new Translation2d(0, 0), 80));
        forwardPath.add(new Path.Waypoint(new Translation2d(fwd - 20, 0), 50));
        forwardPath.add(new Path.Waypoint(new Translation2d(fwd - 10, 5), 30));
        forwardPath.add(new Path.Waypoint(new Translation2d(fwd, 20), 30));
        forwardPath.add(new Path.Waypoint(new Translation2d(fwd, 55), 30));


        List<Path.Waypoint> backwardPath = Lists.newArrayList();
        backwardPath.add(new Path.Waypoint(new Translation2d(fwd, 55), 60));
        backwardPath.add(new Path.Waypoint(new Translation2d(fwd, 20 ), 40));
        backwardPath.add(new Path.Waypoint(new Translation2d(fwd - 10,5 ), 30));
        backwardPath.add(new Path.Waypoint(new Translation2d(fwd - 20, 0), 30));
        backwardPath.add(new Path.Waypoint(new Translation2d(42-15 * cosA, 15 * sinA), 30));
        backwardPath.add(new Path.Waypoint(new Translation2d(42-39 * cosA, 39 * sinA), 30));

        //Drive up to the hopper
        runAction(new SeriesAction(Arrays.asList(new FollowPathAction(new Path(forwardPath), false))));

        //Drive back and drive towards boiler
        runAction(new ParallelAction(Arrays.asList(
                new SeriesAction(Arrays.asList(new FollowPathAction(new Path(backwardPath), true))),
                new SeriesAction(Arrays.asList(new WaitForPathMarkerAction("CloseGearMan"), new CloseGearAction()));
    }
}
