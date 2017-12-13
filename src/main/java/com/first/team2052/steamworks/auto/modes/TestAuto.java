package com.first.team2052.steamworks.auto.modes;

import com.first.team2052.lib.path.Path;
import com.first.team2052.lib.vec.Translation2d;
import com.first.team2052.steamworks.auto.AutoMode;
import com.first.team2052.steamworks.auto.AutoModeEndedException;
import com.first.team2052.steamworks.auto.actions.DropGearAction;
import com.first.team2052.steamworks.auto.actions.FollowPathAction;
import com.first.team2052.steamworks.auto.actions.SeriesAction;
import com.first.team2052.steamworks.auto.actions.TimeoutAction;
import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Ian on 12/13/2017.
 */
public class TestAuto extends AutoMode{
    @Override
    protected void init() throws AutoModeEndedException {
        List<Path.Waypoint> ForwardPath = Lists.newArrayList(); //crete a list of type path.waypoint, named forward path
        ForwardPath.add(new Path.Waypoint(new Translation2d(0, 0), 20));
        ForwardPath.add(new Path.Waypoint(new Translation2d(50, 0), 20));

        runAction(new SeriesAction(Arrays.asList()));
        new FollowPathAction(new Path(ForwardPath), false);
        new DropGearAction();
    }
}
