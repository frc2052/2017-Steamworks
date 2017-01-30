package com.first.team2052.steamworks.auto;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

public class AutoModeSelector {
    private static SendableChooser sendableChooserAutoMode;

    public enum AutoModeDefinition {
        DONT_MOVE("Don't Move", null);

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
        sendableChooserAutoMode = new SendableChooser();
        for (int i = 0; i < AutoModeDefinition.values().length; i++) {
            AutoModeDefinition mode = AutoModeDefinition.values()[i];
            if (i == 0) {
                sendableChooserAutoMode.addDefault(mode.name, mode);
            } else {
                sendableChooserAutoMode.addObject(mode.name, mode);
            }
        }
    }
}
