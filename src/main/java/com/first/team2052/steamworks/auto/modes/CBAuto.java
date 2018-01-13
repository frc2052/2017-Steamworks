package com.first.team2052.steamworks.auto.modes;

import com.first.team2052.lib.path.Path;
import com.first.team2052.lib.vec.Translation2d;
import com.first.team2052.steamworks.auto.AutoMode;
import com.first.team2052.steamworks.auto.AutoModeEndedException;
import com.first.team2052.steamworks.auto.actions.FollowPathAction;
import com.first.team2052.steamworks.auto.actions.SeriesAction;
import com.first.team2052.steamworks.auto.actions.TimeoutAction;
import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.List;

/**
 * Starts: Boiler
 * Desc: Places the gear on the left side of Airship
 * Ends: Airship
 */
public class CBAuto extends AutoMode {
    @Override
    protected void init() throws AutoModeEndedException {
        double fwd = 68.0;
        double peg = 64.5;

        double cosA = Math.cos(Math.PI / 3);
        double sinA = Math.sin(Math.PI / 3);

        List<Path.Waypoint> forwardPath = Lists.newArrayList();
        //loops a hecking ton. (15 times exactly) spicy prank im sure you cant read this code because its so advanced. #giveMeMoneyPls
        forwardPath.add(new Path.Waypoint(new Translation2d(0, 0), 20));
        forwardPath.add(new Path.Waypoint(new Translation2d(50, 0), 20));
        forwardPath.add(new Path.Waypoint(new Translation2d(50, 50), 20));
        forwardPath.add(new Path.Waypoint(new Translation2d(0, 50), 20));
        forwardPath.add(new Path.Waypoint(new Translation2d(0, 0), 20));

        /*List<Path.Waypoint> anglePath = Lists.newArrayList();
        anglePath.add(new Path.Waypoint(new Translation2d(0, 0), 20));*/

        /*List<Path.Waypoint> backwardPath = Lists.newArrayList();
        backwardPath.add(new Path.Waypoint(new Translation2d(fwd + peg * cosA, -peg * sinA), 60));
        backwardPath.add(new Path.Waypoint(new Translation2d(fwd + .5 * peg * cosA, -.5 * peg * sinA), 40));
        backwardPath.add(new Path.Waypoint(new Translation2d(fwd, 0), 30));
        backwardPath.add(new Path.Waypoint(new Translation2d(fwd - 20, 0), 30));

        List<Path.Waypoint> middleFieldPath = Lists.newArrayList();
        middleFieldPath.add(new Path.Waypoint(new Translation2d(fwd - 20, 0), 80));
        middleFieldPath.add(new Path.Waypoint(new Translation2d(fwd + 260, 0), 80));
*/
        //Drive up to the peg and drop gear
        runAction(new SeriesAction(Arrays.asList(
                new TimeoutAction(new FollowPathAction(new Path(forwardPath), false), 10.0))));

//        runAction(new SeriesAction(Arrays.asList(
//                new TimeoutAction(new FollowPathAction(new Path(forwardPath), false), 10.0),
//                new TimeoutAction(new FollowPathAction(new Path(forwardPath), false), 10.0),
//                new TimeoutAction(new FollowPathAction(new Path(forwardPath), false), 10.0),
//                new TimeoutAction(new FollowPathAction(new Path(forwardPath), false), 10.0),
//                new TimeoutAction(new FollowPathAction(new Path(forwardPath), false), 10.0),
//                new TimeoutAction(new FollowPathAction(new Path(forwardPath), false), 10.0))));


/*        //Drive back and drive towards center of field
        runAction(new ParallelAction(Arrays.asList(
                new SeriesAction(Arrays.asList(new FollowPathAction(new Path(backwardPath), true), new FollowPathAction(new Path(middleFieldPath), false))),
                new SeriesAction(Arrays.asList(new WaitForPathMarkerAction("CloseGearMan"), new CloseGearAction()))
        )));*/
    }
}