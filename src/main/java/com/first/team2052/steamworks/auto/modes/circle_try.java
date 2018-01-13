package com.first.team2052.steamworks.auto.modes;

import com.first.team2052.lib.path.Path;
import com.first.team2052.lib.vec.Translation2d;
import com.first.team2052.steamworks.auto.AutoMode;
import com.first.team2052.steamworks.auto.AutoModeEndedException;
import com.first.team2052.steamworks.auto.actions.*;
import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.List;

public class circle_try extends AutoMode {
    @Override
    protected void init() throws AutoModeEndedException {
        //Generate path waypoints
        List<Path.Waypoint> forwardPath = Lists.newArrayList();
        forwardPath.add(new Path.Waypoint(new Translation2d(0, 0), 20));
        forwardPath.add(new Path.Waypoint(new Translation2d(30, 30), 20));
        forwardPath.add(new Path.Waypoint(new Translation2d(0, 60), 20));
        forwardPath.add(new Path.Waypoint(new Translation2d(-30, 30), 20));
        forwardPath.add(new Path.Waypoint(new Translation2d(0, 0), 20));
        forwardPath.add(new Path.Waypoint(new Translation2d(30, 30), 20));
        forwardPath.add(new Path.Waypoint(new Translation2d(0, 60), 20));
        forwardPath.add(new Path.Waypoint(new Translation2d(-30, 30), 20));



        runAction(new SeriesAction(Arrays.asList(
                new TimeoutAction(new FollowPathAction(new Path(forwardPath), false), 20.0),
                new DropGearAction())));

        {
        }
    }}