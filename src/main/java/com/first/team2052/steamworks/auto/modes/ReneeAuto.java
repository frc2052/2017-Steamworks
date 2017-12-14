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

public class ReneeAuto extends AutoMode {
    @Override
    protected void init() throws AutoModeEndedException {
        List<Path.Waypoint> ForwardPath = Lists.newArrayList();
        ForwardPath.add(new Path.Waypoint(new Translation2d(0, 0), 30));
        ForwardPath.add(new Path.Waypoint(new Translation2d(153.5, 0), 30));
        ForwardPath.add(new Path.Waypoint(new Translation2d(153.5 + (30 * Math.cos(Math.toRadians(45))), (30 * Math.sin(Math.toRadians(45)))), 30));
        ForwardPath.add(new Path.Waypoint(new Translation2d(153.5 + (30 * Math.cos(Math.toRadians(45))), (30 * Math.sin(Math.toRadians(45)) + 50)), 30));


        runAction(new SeriesAction(Arrays.asList()));
        new FollowPathAction(new Path(ForwardPath), false);
    }
}
