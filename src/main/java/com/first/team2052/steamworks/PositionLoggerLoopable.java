package com.first.team2052.steamworks;

import com.first.team2052.lib.Loopable;
import com.first.team2052.lib.interpolables.InterpolatingDouble;
import com.first.team2052.lib.vec.RigidTransform2d;

import java.util.Map;

public class PositionLoggerLoopable implements Loopable{

    private static PositionLoggerLoopable instance = new PositionLoggerLoopable();

    public static PositionLoggerLoopable getInstance() {
        return instance;
    }

    private PositionLoggerLoopable() {
    }

    @Override
    public void update() {
        Map.Entry<InterpolatingDouble, RigidTransform2d> latestFieldToVehicle = RobotState.getInstance().getLatestFieldToVehicle();
        System.out.printf("%s %s%n", latestFieldToVehicle.getKey().value, latestFieldToVehicle.getValue());
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }
}
