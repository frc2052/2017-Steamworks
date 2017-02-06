package com.first.team2052.steamworks.subsystems.controllers;

public class DriveSignal {
    public double left, right;

    public DriveSignal(double left, double right) {
        this.left = left;
        this.right = right;
    }

    public static DriveSignal arcadeSignal(double tank, double turn) {
        return new DriveSignal(tank + turn, tank - turn);
    }
}