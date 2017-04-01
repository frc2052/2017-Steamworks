package com.first.team2052.steamworks;

import com.first.team2052.lib.ControlLoop;
import com.first.team2052.lib.RevRoboticsPressureSensor;
import com.first.team2052.lib.vec.RigidTransform2d;
import com.first.team2052.steamworks.auto.AutoModeRunner;
import com.first.team2052.steamworks.auto.AutoModeSelector;
import com.first.team2052.steamworks.subsystems.Climber;
import com.first.team2052.steamworks.subsystems.GearMan;
import com.first.team2052.steamworks.subsystems.Pickup;
import com.first.team2052.steamworks.subsystems.shooter.Shooter;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {
    private ControlLoop controlLoop;
    private Controls controls;
    private GearMan gearMan;
    private Pickup pickup;
    private Shooter shooter;
    private Climber climber;
    private RevRoboticsPressureSensor revRoboticsPressureSensor;
    private AutoModeRunner autoModeRunner;
    private PowerDistributionPanel pdp;



    @Override
    public void robotInit() {
        controlLoop = new ControlLoop(Constants.kControlLoopPeriod);

        controls = Controls.getInstance();
        gearMan = GearMan.getInstance();
        pickup = Pickup.getInstance();
        shooter = Shooter.getInstance();
        climber = Climber.getInstance();
        revRoboticsPressureSensor = new RevRoboticsPressureSensor(0);
        pdp = new PowerDistributionPanel();

        controlLoop.addLoopable(shooter);
        controlLoop.addLoopable(gearMan);

        AutoModeSelector.putToSmartDashboard();
        autoModeRunner = new AutoModeRunner();
    }

    @Override
    public void autonomousInit() {
        gearMan.setWantOpen(false);

        shooter.setWantShoot(false);

        zeroAllSensors();

        controlLoop.start();
        autoModeRunner.setAutoMode(AutoModeSelector.getAutoInstance());
        autoModeRunner.start();
    }

    @Override
    public void teleopInit() {
        zeroAllSensors();

        autoModeRunner.stop();

        shooter.setWantShoot(false);

        controlLoop.start();

        gearMan.setWantOpen(false);
        pickup.setIntakeState(Pickup.PickupState.STOP);
    }

    @Override
    public void teleopPeriodic() {
        gearMan.setWantOpen(controls.getGearManState());
        pickup.setIntakeState(controls.getIntakeState());

        shooter.setWantShoot(controls.getWantShoot());
        shooter.setWantIdleRampUp(controls.getWantShooterIdle());
        shooter.setWantReverseAgitator(controls.getWantReverseAgitator());

        climber.setClimberState(controls.getClimberState(pdp.getCurrent(2)));

        SmartDashboard.putNumber("psi", revRoboticsPressureSensor.getAirPressurePsi());
        SmartDashboard.putBoolean("gearman", gearMan.getSolenoidState());
        SmartDashboard.putNumber("climb_amp", pdp.getCurrent(2));
    }

    @Override
    public void disabledInit() {
        controlLoop.stop();
        autoModeRunner.stop();
        zeroAllSensors();
    }

    @Override
    public void disabledPeriodic() {
        System.gc();
    }

    public void zeroAllSensors() {
    }
}

