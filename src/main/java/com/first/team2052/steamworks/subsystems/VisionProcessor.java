package com.first.team2052.steamworks.subsystems;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Created by KnightKrawler on 2/20/2017.
 */
public class VisionProcessor {

    private static VisionProcessor instance = new VisionProcessor();


    private String networkTablePath = "GRIP/myContoursReport";
    private int xResolution = 320, yResolution = 240;
    private NetworkTable gripTable;


    public VisionProcessor()
    {
        networkTablePath = "10.20.52.11";
        NetworkTable.setClientMode();
        NetworkTable.setIPAddress("localhost");
        gripTable = NetworkTable.getTable(networkTablePath);

    }

    public double getXAngleFromCenter() {
        // returns angle in degrees, or it
        // returns the x angle of view if something went wrong
        double[] centers = gripTable.getNumberArray("centerX", new double[0]);
        if (centers.length == 1) {
            //need to backup
            SmartDashboard.putString("VisionStatus", "Only one tape found");
            return 0;
        } else if (centers.length == 2) {
            double avg = (centers[0] + centers[1]) / 2;
            double delta = (xResolution / 2) - avg; //center of the screen minus center of tape
            if (Math.abs(delta) < (xResolution *.05))
            {
                SmartDashboard.putString("VisionStatus", "Pointing straight ahead");
                return 0;
            }
            else if (delta < 0) //need to turn right
            {
                SmartDashboard.putString("VisionStatus", "Turn -2");
                return -2;
            }
            else //need to turn right
            {
                SmartDashboard.putString("VisionStatus", "Turn 2");
                return 2;
            }
        } else {
            SmartDashboard.putString("VisionStatus", "More than 2 objects found");
            return 0.0;
        }
    }

    public static VisionProcessor getInstance()
    {
        return instance;
    }
}
