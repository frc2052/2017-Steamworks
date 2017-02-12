package com.first.team2052.steamworks;

import com.first.team2052.lib.ControlLoop;
import com.first.team2052.steamworks.subsystems.Shooter;
import com.first.team2052.steamworks.subsystems.drive.DriveTrain;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Robot extends IterativeRobot {
    private ControlLoop controlLoop = new ControlLoop(Constants.kControlLoopPeriod);
    private DriveTrain driveTrain = DriveTrain.getInstance();
    private Controls controls = Controls.getInstance();
    private Shooter shooter = Shooter.getInstance();

    @Override
    public void robotInit() {
        controlLoop.addLoopable(driveTrain.getLoopable());
        controlLoop.addLoopable(shooter);
    }

    @Override
    public void autonomousInit() {
        driveTrain.setHighGear(Constants.Drive.kDriveDefaultHighGear);
        zeroAllSensors();

        controlLoop.start();
        driveTrain.setDistanceTrajectory(20 * 12);
    }

    @Override
    public void teleopInit() {
        zeroAllSensors();

        controlLoop.start();

        driveTrain.setOpenLoop(0.0, 0.0);
        driveTrain.setHighGear(Constants.Drive.kDriveDefaultHighGear);

        driveTrain.zeroEncoders();
    }

    @Override
    public void teleopPeriodic() {
        driveTrain.setHighGear(controls.getHighGear());

        double turn = controls.getTurn();
        double tank = controls.getTank();

        driveTrain.setOpenLoop(tank + turn, tank - turn);

        SmartDashboard.putNumber("gyro", driveTrain.getGyroAngleDegrees());

        shooter.setWantShoot(controls.getWantShoot());
    }

    @Override
    public void disabledInit() {
        controlLoop.stop();
        zeroAllSensors();
    }

    public void zeroAllSensors() {
        driveTrain.zeroEncoders();
        driveTrain.zeroGyro();
    }
}

