package com.first.team2052.steamworks.auto.modes;

import com.first.team2052.steamworks.auto.AutoMode;
import com.first.team2052.steamworks.auto.AutoModeEndedException;
import com.first.team2052.steamworks.auto.AutoModeSelector;
import com.first.team2052.steamworks.auto.AutoPaths;
import com.first.team2052.steamworks.subsystems.drive.DriveTrain;
import com.first.team2052.trajectory.common.Path;
import com.google.common.base.Optional;
import edu.wpi.first.wpilibj.DriverStation;

/**
 * Starts: Boiler
 * Desc: Turns and shoots
 * Ends: Back at the Boiler
 */
public class PosBoilerShoot extends AutoMode {
    @Override
    protected void init() throws AutoModeEndedException {
        drivePath(AutoPaths.getInstance().getPath("PosBoilerShoot"), true, false);
    }
}
