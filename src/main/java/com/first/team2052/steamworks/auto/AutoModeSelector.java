package com.first.team2052.steamworks.auto;

import com.first.team2052.steamworks.auto.modes.*;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoModeSelector {
    private static SendableChooser<AutoModeDefinition> sendableChooserAutoMode;
    private static SendableChooser<Side> sendableChooserSide;

    public enum AutoModeDefinition {
        DONT_MOVE("Don't Move", DontMove.class),
        POS_LEFT_GEAR("Left Gear", PosLeftGear.class),
        POS_RIGHT_GEAR("Right Gear", PosRightGear.class),
        POS_CENTER_GEAR("Center Gear", PosCenterGear.class),
        POS_BOILER_SHOOT("Boiler Shoot Baseline", PosBoilerShoot.class);

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

    public enum Side {
        RED, BLUE
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
        sendableChooserSide = new SendableChooser<Side>();
        sendableChooserSide.addDefault("Red", Side.RED);
        sendableChooserSide.addObject("Blue", Side.BLUE);

        SmartDashboard.putData("alliance", sendableChooserSide);
        SmartDashboard.putData("auto_modes", sendableChooserAutoMode);
    }

    public static boolean isOnBlue() {
        DriverStation.Alliance color;
        color = DriverStation.getInstance().getAlliance();
        if (color == DriverStation.Alliance.Blue) {
            return true;
        } else {
            return false;
        }
    }

    public static AutoModeBase getAutoInstance() {
        return sendableChooserAutoMode.getSelected().getInstance();
    }

    public static Side getSide() {
        return sendableChooserSide.getSelected();
    }
}