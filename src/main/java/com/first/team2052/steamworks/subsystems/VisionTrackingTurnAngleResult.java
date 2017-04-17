package com.first.team2052.steamworks.subsystems;

public class VisionTrackingTurnAngleResult {
    public boolean isValid;
    public double turnAngle;

    public VisionTrackingTurnAngleResult(boolean isValid, double turnAngle) {
        this.isValid = isValid;
        this.turnAngle = turnAngle;
    }
}
