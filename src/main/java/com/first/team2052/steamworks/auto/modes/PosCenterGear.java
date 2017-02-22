package com.first.team2052.steamworks.auto.modes;

import com.first.team2052.steamworks.auto.AutoMode;
import com.first.team2052.steamworks.auto.AutoModeEndedException;
import com.first.team2052.steamworks.auto.AutoPaths;
import com.first.team2052.steamworks.subsystems.GearMan;
import com.first.team2052.steamworks.subsystems.drive.DriveTrain;

/**
 * Starts: Center against alliance wall
 * Desc: Brings a gear to the airship
 * Ends: Airship
 */
public class PosCenterGear extends AutoMode {
    @Override
    protected void init() throws AutoModeEndedException {
        //drive backwards 71 inches at a velocity of 24 in/sec
        //gear man is on back of robot, so robot faces backwards at start of match
        driveStraightDistance(-71, 24);
        //actuate gearman
        GearMan.getInstance().setGearManState(GearMan.GearManState.OPEN);
        driveStraightDistance(24, 24);
        GearMan.getInstance().setGearManState(GearMan.GearManState.CLOSED);
    }
}
