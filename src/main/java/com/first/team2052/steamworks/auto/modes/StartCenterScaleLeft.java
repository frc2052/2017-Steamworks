package com.first.team2052.steamworks.auto.modes;

import com.first.team2052.lib.path.Path;
import com.first.team2052.lib.vec.Translation2d;
import com.first.team2052.steamworks.auto.AutoMode;
import com.first.team2052.steamworks.auto.AutoModeEndedException;
import com.first.team2052.steamworks.auto.actions.*;
import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.List;


public class StartCenterScaleLeft extends AutoMode {
    @Override
    protected void init() throws AutoModeEndedException {
        double fwd = 68.0;
        double peg = 64.5;

        double cosA = Math.cos(Math.PI / 3);
        double sinA = Math.sin(Math.PI / 3);

        List<Path.Waypoint> forwardPath = Lists.newArrayList();
        forwardPath.add(new Path.Waypoint(new Translation2d(0, 0), 40));
        forwardPath.add(new Path.Waypoint(new Translation2d(0, 50), 40));
        forwardPath.add(new Path.Waypoint(new Translation2d(107, 50), 30));
        runAction(new SeriesAction(Arrays.asList(
                new TimeoutAction(new FollowPathAction(new Path(forwardPath), false), 7.0),;
    }
}
