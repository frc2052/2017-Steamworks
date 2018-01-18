package com.first.team2052.steamworks.auto.modes;

import com.first.team2052.lib.path.Path;
import com.first.team2052.lib.vec.Translation2d;
import com.first.team2052.steamworks.auto.AutoMode;
import com.first.team2052.steamworks.auto.AutoModeEndedException;
import com.first.team2052.steamworks.auto.actions.*;
import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.List;


public class auto2 extends AutoMode {
    @Override
    protected void init() throws AutoModeEndedException {
        //Generate path waypoints
        List<Path.Waypoint> forwardPath = Lists.newArrayList();
        forwardPath.add(new Path.Waypoint(new Translation2d(0, 0), 20));
        forwardPath.add(new Path.Waypoint(new Translation2d(0, 90), 40));


        List<Path.Waypoint> forwardPath2 = Lists.newArrayList();
        forwardPath.add(new Path.Waypoint(new Translation2d(0, 50), 20));






        runAction(new SeriesAction(Arrays.asList(
                new TimeoutAction(new FollowPathAction(new Path(forwardPath), false), 7.0),
                new DropGearAction(),
                new TimeoutAction(new FollowPathAction(new Path(forwardPath2), false), 7.0))));








    }
}