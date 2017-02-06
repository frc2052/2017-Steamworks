package com.first.team2052.steamworks;

public class Constants {
    public static final double kControlLoopPeriod = 1.0 / 100.0;

    public static class CAN {
        public static final int kDriveLeft1Id = 1;
        public static final int kDriveLeft2Id = 2;
        public static final int kDriveLeft3Id = 3;

        public static final int kDriveRight1Id = 4;
        public static final int kDriveRight2Id = 5;
        public static final int kDriveRight3Id = 6;

        public static final int kLeftAgitatorMotorPort = 7;
        public static final int kRightAgitatorMotorPort = 8;
        public static final int kShooterMotorPort = 9;
    }

    public static class Solenoid {
        public static int kDriveOutSolenoidId = 1;
        public static int kDriveInSolenoidId = 0;
    }

    public static class Drive {
        // Default state of the drive train transmission when in teleopInit, autoInit, and robotInit
        public static boolean kDriveDefaultHighGear = false;

        private static final int kDriveTicksPerRot = 256;
        private static final double kDriveThirdStageGearRatio = 54.0 / 30.0;
        // VEX 3 CIM ball shift with 3rd stage 3 rotations per gearbox output shaft rotation * ratio for gearbox
        public static int kDriveEncoderTicksPerRot = (int) (3 * kDriveTicksPerRot * kDriveThirdStageGearRatio);

        // All constants are for LOW GEAR
        public static double kDriveMaxVelocity = 75.0;
        public static double kDriveMaxAcceleration = 107.0;
        public static double kDriveStraightKp = 0.0;
        public static double kDriveStraightKi = 0.0;
        public static double kDriveStraightKd = 0.0;
        public static double kDriveStraightKv = 1.0 / kDriveMaxVelocity;
        public static double kDriveStraightKa = 0.0;

        public static double kDriveWheelDiameterInches = 4.0;

        public static double kDrivePathkTurn = 0.03;

    }

    public static class Shooter {
        public static double kShooterMotorSpeed = 1.0;
        public static double kTatorSpeed = 0.5;
        public static double kTatorSpeedBack = -0.5;
    }
}
