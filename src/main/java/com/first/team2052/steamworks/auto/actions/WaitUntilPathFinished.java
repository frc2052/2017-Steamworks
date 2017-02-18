package com.first.team2052.steamworks.auto.actions;

import com.first.team2052.steamworks.auto.AutoModeBase;
import com.first.team2052.steamworks.subsystems.controllers.DriveController;
import com.first.team2052.steamworks.subsystems.controllers.DrivePathController;
import com.first.team2052.steamworks.subsystems.drive.DriveTrain;

public class WaitUntilPathFinished extends AutoModeBase.Action {
    @Override
    public void done() {
    }

    @Override
    public boolean isFinished() {
        DriveController controller = DriveTrain.getInstance().getController();
        if (controller != null) {
            if (controller instanceof DrivePathController) {
                return controller.isFinished();
            } else {
                return false;
            }
        }
        return false;
    }

    @Override
    public void start() {
    }

    @Override
    public void update() {
    }
}
