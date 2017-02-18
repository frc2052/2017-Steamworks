package com.first.team2052.steamworks;

public class Constants {
    public static final double kControlLoopPeriod = 1.0 / 100.0;
    public static double kDriveSpeedCurveTurn = 0.75;

    public static class CAN {
        public static final int kDriveLeft1Id = 1;
        public static final int kDriveLeft2Id = 2;
        public static final int kDriveLeft3Id = 3;

        public static final int kDriveRight1Id = 4;
        public static final int kDriveRight2Id = 5;
        public static final int kDriveRight3Id = 6;


        public static final int kRightAgitatorMotorPort = 7;
        public static final int kLeftAgitatorMotorPort = 8;

        public static final int kShooterMotorPort = 9;
        public static final int kShooterMotorSlavePort = 10;

        public static int kIntakeMotorPort = 11;

        public static final int kClimbId = 12;
    }

    public static class Drive {
        //Solenoid Id's for shifting
        public static int kDriveOutSolenoidId = 1;
        public static int kDriveInSolenoidId = 0;

        // Default state of the drive train transmission when in teleopInit, autoInit, and robotInit
        public static boolean kDriveDefaultHighGear = false;

        private static final int kDriveTicksPerRot = 256;
        private static final double kDriveThirdStageGearRatio = 54.0 / 30.0;
        // VEX 3 CIM ball shift with 3rd stage 3 rotations per gearbox output shaft rotation * ratio for gearbox
        public static int kDriveEncoderTicksPerRot = (int) (3 * kDriveTicksPerRot * kDriveThirdStageGearRatio);

        // All constants are for LOW GEAR
        public static double kDriveMaxVelocity = 75.0; //inches per sec
        public static double kDriveMaxAcceleration = 107.0;
        public static double kDriveStraightKp = 0.03;
        public static double kDriveStraightKi = 0.0;
        public static double kDriveStraightKd = 0.0;
        public static double kDriveStraightKv = 1.0 / kDriveMaxVelocity;
        public static double kDriveStraightKa = 0.0;

        public static double kDriveWheelDiameterInches = 4.0;

        public static double kDrivePathkTurn = 0.03;

        public static double kDriveStraightTurnKp = -0.025;

    }

    public static class GearMan {
        //Solenoid Id's
        public static int kGearManInSolenoidId = 2;
        public static int kGearManOutSolenoidId = 3;
    }

    public static class Pickup {
        //Intake speed in %
        public static final double kIntakeMotorSpeedIn = 0.8;
        public static final double kIntakeMotorSpeedOut = -0.5;
    }

    public static class Shooter {
        //Use cable to Mag encoder or use Magnetic Encoder converted to a Quad encoder
        //Talk to Adam or Nate about this
        public static final boolean kUseDoubleEncoderExtender = true;

        public static final double kShooterVelocityKp = 0.045;
        public static final double kShooterVelocityKi = 0.0;
        public static final double kShooterVelocityKd = 0.5;
        public static final double kShooterVelocityKf = 0.03568;
        public static final int kShooterVelocityIZone = 0;
        public static final int kShooterVelocityCloseLoopRampRate = 0;

        //Agitator speed in %
        public static double kTatorSpeed = 0.50;

        //RPM (MAX 4200rpm) Never set above 4200 because we might not get to that exact RPM
        public static final int kShooterKeyVelocity = 3100;
        public static final int kShooterVelocityWindow = 300;
    }

    public static class Climber {
        public static final double kClimberMotorSpeedUp = 0.5;
    }

    public static class Testing {
        public static boolean kDisableDriveCode = false;
    }
}
