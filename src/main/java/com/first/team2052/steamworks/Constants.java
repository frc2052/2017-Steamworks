package com.first.team2052.steamworks;

public class Constants {
    public static final double kControlLoopPeriod = 1.0 / 100.0;

    public static int kDriveLeft1Id = 1;
    public static int kDriveLeft2Id = 2;
    public static int kDriveLeft3Id = 3;

    public static int kDriveRight1Id = 4;
    public static int kDriveRight2Id = 5;
    public static int kDriveRight3Id = 6;

    public static int kIntakeMotorPort = 7;

    public static double kDriveMaxVelocity = 155.0;
    public static double kDriveMaxAcceleration = 100.0;
    public static double kDriveStraightKp = 0.15;
    public static double kDriveStraightKi = 0.015;
    public static double kDriveStraightKd = 0.0;
    public static double kDriveStraightKv = 1.0 / 160.0;
    public static double kDriveStraightKa = 0.0015;
    public static double kDriveWheelDiameterInches = 4.0;

    public static double kDrivePathkTurn = 0.03;

    public static double kIntakeMotorSpeedIn = 1.0;
    public static double kIntakeMotorSpeedOut = -1.0;
}
