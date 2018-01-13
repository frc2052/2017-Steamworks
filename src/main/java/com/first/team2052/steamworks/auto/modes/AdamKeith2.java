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
 * Starts: Center against alliance wall
 * Desc: Brings a gear to the airship
 * Ends: Airship
 */
public class AdamKeith2 extends AutoMode {
    @Override
    protected void init() throws AutoModeEndedException {
        //Generate path waypoints
        List<Path.Waypoint> forwardPath = Lists.newArrayList();
        forwardPath.add(new Path.Waypoint(new Translation2d(0, 0), 50));
        forwardPath.add(new Path.Waypoint(new Translation2d(30, 30), 20));
        forwardPath.add(new Path.Waypoint(new Translation2d(-30, 60), 20));
        forwardPath.add(new Path.Waypoint(new Translation2d(30, 90), 20));
        forwardPath.add(new Path.Waypoint(new Translation2d(-30, 120), 20));

        //Start running the shooter, but don't shoot
        Shooter.getInstance().setWantIdleRampUp(true);

        runAction(new SeriesAction(Arrays.asList(new FollowPathAction(new Path(forwardPath), true)/*, new StartShootingAction()*/, new WaitAction(8.0))));

        Shooter.getInstance().setWantIdleRampUp(false);
        Shooter.getInstance().setWantShoot(false);



    }
}


