package com.first.team2052.steamworks;

import com.first.team2052.lib.ControlLoop;
import com.first.team2052.steamworks.subsystems.Climber;
import com.first.team2052.steamworks.subsystems.GearMan;
import com.first.team2052.steamworks.subsystems.Pickup;
import com.first.team2052.steamworks.subsystems.drive.DriveTrain;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Robot extends IterativeRobot {
    private ControlLoop controlLoop;
    private DriveTrain driveTrain;
    private Controls controls;
    private GearMan gearMan;
    private Pickup pickup;
    private Climber climber;

    @Override
    public void robotInit() {
        controlLoop = new ControlLoop(Constants.kControlLoopPeriod);

        driveTrain = DriveTrain.getInstance();
        controls = Controls.getInstance();
        gearMan = GearMan.getInstance();
        pickup = Pickup.getInstance();
        climber = Climber.getInstance();

        controlLoop.addLoopable(driveTrain.getLoopable());
    }

    @Override
    public void autonomousInit() {
        driveTrain.setHighGear(Constants.Drive.kDriveDefaultHighGear);
        gearMan.setGearManState(GearMan.GearManState.CLOSED);

        zeroAllSensors();

        controlLoop.start();
        driveTrain.setDistanceTrajectory(5 * 12);
    }

    @Override
    public void teleopInit() {
        zeroAllSensors();

        controlLoop.start();

        driveTrain.setOpenLoop(0.0, 0.0);
        driveTrain.setHighGear(Constants.Drive.kDriveDefaultHighGear);

        gearMan.setGearManState(GearMan.GearManState.CLOSED);
        pickup.setIntakeState(Pickup.IntakeState.STOP);

        driveTrain.zeroEncoders();
    }

    @Override
    public void teleopPeriodic() {
        driveTrain.setHighGear(controls.getHighGear());

        double turn = controls.getTurn();
        double tank = controls.getTank();

        driveTrain.setOpenLoop(tank + turn, tank - turn);
        gearMan.setGearManState(controls.getGearManState());


        pickup.setIntakeState(controls.getIntakeState());
        climber.setClimberState(controls.getClimberState());
        SmartDashboard.putNumber("gyro", driveTrain.getGyroAngleDegrees());
        SmartDashboard.putNumber("distance", driveTrain.getLeftDistanceInches());
        SmartDashboard.putNumber("velocity", driveTrain.getLeftVelocityInchesPerSec());
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

