package com.first.team2052.steamworks.auto.modes;

import com.first.team2052.steamworks.auto.AutoMode;
import com.first.team2052.steamworks.auto.AutoModeEndedException;
import com.first.team2052.steamworks.subsystems.DriveTrain;

public class DontMove extends AutoMode {
    @Override
    protected void init() throws AutoModeEndedException {
        DriveTrain.getInstance().setOpenLeftRight(0.0, 0.0);
    }
}
