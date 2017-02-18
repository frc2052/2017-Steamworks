package com.first.team2052.steamworks.subsystems.controllers;

import com.first.team2052.steamworks.subsystems.drive.DriveTrain;

public abstract class DriveController {
    protected DriveTrain driveTrain;

    public DriveController(DriveTrain driveTrain) {
        this.driveTrain = driveTrain;
    }

    public abstract DriveSignal calculate();

    public abstract boolean isFinished();
}
