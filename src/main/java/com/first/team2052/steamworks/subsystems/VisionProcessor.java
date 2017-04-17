package com.first.team2052.steamworks.subsystems;

import com.first.team2052.lib.Loopable;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

import java.util.ArrayList;

/**
 * Created by KnightKrawler on 2/20/2017.
 * <p>
 * Camera Values must be:
 * Color Level: 50
 * Brightness: 16
 * Sharpness: 50
 * Contrast: 50
 * Exposure Value: 3
 */
public class VisionProcessor implements Loopable {

    private static VisionProcessor instance = new VisionProcessor();

    private final String networkTablePath = "GRIP/myContoursReport";
    private final int xResolution = 240, yResolution = 180;
    private NetworkTable gripTable;

    private VisionTrackingTurnAngleResult latestTargetResult = new VisionTrackingTurnAngleResult(false, 0.0);

    public VisionProcessor() {
        NetworkTable.setIPAddress("localhost");
        gripTable = NetworkTable.getTable(networkTablePath);
    }

    public VisionTrackingTurnAngleResult getLatestTargetResult() {
        return latestTargetResult;
    }

    public static VisionProcessor getInstance() {
        return instance;
    }


    @Override
    public void update() {
// returns angle in degrees to turn, or it
        // returns the x angle of view if something went wrong
        double[] rawXCenters = gripTable.getNumberArray("centerX", new double[0]);
        double[] centers = new double[2];


        if (rawXCenters.length == 0) {
            System.out.println("No objects found");
            updateTarget(false, 0.0);
            return;
        }
        if (rawXCenters.length == 1) {
            //need to backup
            System.out.println("Only one tape found");
            updateTarget(false, 0.0);
            return;
        } else if (rawXCenters.length == 2) {
            centers = rawXCenters;
        } else {
            //System.out.println("More than 2 objects found");
            double[] rawYCenters = gripTable.getNumberArray("centerY", new double[0]);
            double[] rawWidths = gripTable.getNumberArray("width", new double[0]);
            double[] rawHeights = gripTable.getNumberArray("height", new double[0]);
            double[] rawAreas = gripTable.getNumberArray("area", new double[0]);

            if (rawYCenters.length != rawXCenters.length
                    || rawWidths.length != rawXCenters.length
                    || rawHeights.length != rawXCenters.length
                    || rawAreas.length != rawXCenters.length) {
                //all the arrays were not the same length, we can't make objects if arrays are not all the same length
                updateTarget(false, 0.0);
                return;
            }

            ArrayList<VisionTrackingShape> shapes = new ArrayList<VisionTrackingShape>();
            //create object representing every shape the camera seen
            for (int i = 0; i < rawXCenters.length; i++) {
                //if(rawYCenters[i] > yResolution*.5) { //only add if on bottom half of screen
                VisionTrackingShape s = new VisionTrackingShape();
                s.xCenter = rawXCenters[i];
                s.yCenter = rawYCenters[i];
                s.height = rawHeights[i];
                s.width = rawWidths[i];
                s.area = rawAreas[i];
                shapes.add(s);
                //System.out.println("Valid Shape with area " + rawAreas[i] + " had a Y value of " + rawYCenters[i] + " out of a possible value of yResolution " + yResolution);
                // }
                //else
                //{
                //    System.out.println("Invalid Shape with area " + rawAreas[i] + " had a Y value of " + rawYCenters[i] + " out of a possible value of yResolution " + yResolution);
                //}
            }

            if (shapes.size() == 2) {
                centers[0] = shapes.get(0).xCenter;
                centers[1] = shapes.get(1).xCenter;
            } else if (shapes.size() == 0) {
                System.out.println("0 shapes on bottom of screen");
                updateTarget(false, 0.0);
                return;
            } else if (shapes.size() == 1) {
                System.out.println("1 shapes on bottom of screen");
                updateTarget(false, 0.0);
                return;
            } else if (shapes.size() > 3) {
                System.out.println("more than 3 shapes on bottom of screen");
                updateTarget(false, 0.0);
                return;
            } else {
                double shape0X = shapes.get(0).xCenter;
                double shape1X = shapes.get(1).xCenter;
                double shape2X = shapes.get(2).xCenter;
                if ((Math.abs(shape0X - shape1X) < Math.abs(shape1X - shape2X)) && (Math.abs(shape0X - shape1X) < Math.abs(shape0X - shape2X))) //first 2 shapes are on the same side of the peg
                {
                    centers[0] = shape0X; //shape 1 should be in the same or close spot
                    centers[1] = shape2X;
                } else if ((Math.abs(shape1X - shape2X) < Math.abs(shape0X - shape2X)) && (Math.abs(shape1X - shape2X) < Math.abs(shape0X - shape1X))) //last 2 shapes are on the same side of the peg
                {
                    centers[0] = shape0X;
                    centers[1] = shape1X; //shape 2 should be in the same or close spot
                } else {
                    centers[0] = shape1X;
                    centers[1] = shape2X; //shape 0 should be in the same or close spot
                }
            }
        }


        //calculate turn angle
        double avg = (centers[0] + centers[1]) / 2; //takes the average of the x centers on the two blobs. This is where the peg should be.
        double delta = (xResolution / 2) - avg; //how far from center of the screen the peg is

        //positive delta means turn right, negative means turn left
        double percentageScreenDelta = delta / (xResolution / 2);
        double turnPercent = 23.25 * percentageScreenDelta; //23.25 is half of horz view angle of the camera
        System.out.println("Robot needs to turn " + turnPercent + " degrees");
        updateTarget(true, turnPercent);
    }

    private void updateTarget(boolean valid, double angle) {
        latestTargetResult.isValid = valid;
        latestTargetResult.turnAngle = angle;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }
}



