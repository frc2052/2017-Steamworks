package com.first.team2052.steamworks.auto;

import com.first.team2052.steamworks.auto.modes.*;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

public class AutoModeSelector {
    private static SendableChooser<AutoModeDefinition> sendableChooserAutoMode;

    public enum AutoModeDefinition {
        DONT_MOVE("Don't Move", DontMove.class),
        POS_LEFT_GEAR("Pos Left Gear", PosLeftGear.class),
        POS_RIGHT_GEAR("Pos Right Gear", PosLeftGear.class),
        POS_CENTER_GEAR("Pos Center Gear", PosCenterGear.class),
        POS_BOILER_SHOOT("Pos Boiler Shoot", PosBoilerShoot.class),
        POS_BOILER_HOPPER("Pos Boiler Hopper", PosBoilerHopper.class),
        POS_BOILER_HOPPER_SHOOT("Pos Boiler Hopper Shoot", PosBoilerHopperShoot.class);

        private final Class<? extends AutoMode> clazz;
        private final String name;

        AutoModeDefinition(String name, Class<? extends AutoMode> clazz) {
            this.clazz = clazz;
            this.name = name;
        }

        public AutoModeBase getInstance() {
            AutoModeBase instance;
            try {
                instance = clazz.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                return null;
            }
            return instance;
        }
    }

    public static void putToSmartDashboard() {
        sendableChooserAutoMode = new SendableChooser<AutoModeDefinition>();
        for (int i = 0; i < AutoModeDefinition.values().length; i++) {
            AutoModeDefinition mode = AutoModeDefinition.values()[i];
            if (i == 0) {
                sendableChooserAutoMode.addDefault(mode.name, mode);
            } else {
                sendableChooserAutoMode.addObject(mode.name, mode);
            }
        }
    }
    public static boolean isOnBlue() {
        DriverStation.Alliance color;
        color = DriverStation.getInstance().getAlliance();
        if(color == DriverStation.Alliance.Blue){
            return true;
        }else{
            return false;
        }
    }

    public static AutoModeBase getAutoInstance() {
        return sendableChooserAutoMode.getSelected().getInstance();
    }
}
