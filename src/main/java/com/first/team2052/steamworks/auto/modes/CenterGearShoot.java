package com.first.team2052.steamworks.auto.modes;

import com.first.team2052.lib.path.Path;
import com.first.team2052.lib.vec.Translation2d;
import com.first.team2052.steamworks.auto.AutoMode;
import com.first.team2052.steamworks.auto.AutoModeEndedException;
import com.first.team2052.steamworks.auto.actions.DropGearAction;
import com.first.team2052.steamworks.auto.actions.FollowPathAction;
import com.first.team2052.steamworks.auto.actions.SeriesAction;
import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.List;

/**
 * Starts: Center against alliance wall
 * Desc: Brings a gear to the airship then shoots
 * Ends: Boiler
 */
public class CenterGearShoot extends AutoMode {
    @Override
    protected void init() throws AutoModeEndedException {
        //Generate path waypoints
        List<Path.Waypoint> forwardPath = Lists.newArrayList();
        forwardPath.add(new Path.Waypoint(new Translation2d(0, 0), 20));
        forwardPath.add(new Path.Waypoint(new Translation2d(39, 30), 20));
        forwardPath.add(new Path.Waypoint(new Translation2d(74, -5), 20));
        forwardPath.add(new Path.Waypoint(new Translation2d(40, -18), 20));
        forwardPath.add(new Path.Waypoint(new Translation2d(0, 0), 20));


        //Drive up to the peg and drop gear
        runAction(new SeriesAction(Arrays.asList(
                new FollowPathAction(new Path(forwardPath), false),
                new DropGearAction())));
    }
}