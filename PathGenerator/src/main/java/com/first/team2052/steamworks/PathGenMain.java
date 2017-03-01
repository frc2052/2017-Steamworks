package com.first.team2052.steamworks;

import com.first.team2052.trajectory.common.Path;
import com.first.team2052.trajectory.generate.PathGenerator;
import com.first.team2052.trajectory.generate.TrajectoryGenerator;
import com.first.team2052.trajectory.generate.WaypointSequence;
import com.first.team2052.trajectory.generate.io.TextFileSerializer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class PathGenMain {

    public static String joinPath(String path1, String path2) throws Exception {
        File file2 = new File(new File(path1).getCanonicalFile() + "/paths/" + path2);
        return file2.getPath();
    }

    public static void writePath(Path path, String directory, String path_name) throws Exception {
        // Outputs to the directory supplied as the first argument.
        TextFileSerializer js = new TextFileSerializer();
        String serialized = js.serialize(path);
        String fullpath = joinPath(directory, path_name + ".txt");
        if (!writeFile(fullpath, serialized)) {
            System.err.println(fullpath + " could not be written!!!!1");
            System.exit(1);
        } else {
            System.out.println("Wrote " + path_name);
        }
    }

    private static boolean writeFile(String path, String data) {
        try {
            File file = new File(path);
            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(data);
            bw.close();
        } catch (IOException e) {
            return false;
        }

        return true;
    }

    public static void main(String[] args) throws Exception {
        String directory = args[0];

        TrajectoryGenerator.Config config = new TrajectoryGenerator.Config();
        config.dt = 1.0 / 100.0;
        final double kWheelbaseWidth = 27.25;
        {
            final String path_name = "TestPath";
            config.max_acc = 80.0;
            config.max_jerk = 300.0;
            config.max_vel = 2 * 12;

            WaypointSequence p = new WaypointSequence(2);
            p.addWaypoint(new WaypointSequence.Waypoint(0, 0, 0));
            p.addWaypoint(new WaypointSequence.Waypoint(5 * 12, 5 * 12, 0));

            Path path = PathGenerator.makePath(p, config, kWheelbaseWidth, path_name);
            writePath(path, directory, path_name);
        }
        {
            final String path_name = "PosEdgeGear";
            config.max_acc = 80.0;
            config.max_jerk = 300.0;
            config.max_vel = 2 * 12;

            WaypointSequence p = new WaypointSequence(4);
            p.addWaypoint(new WaypointSequence.Waypoint(0, 0, 0));
            p.addWaypoint(new WaypointSequence.Waypoint(20, 0, 0));
            p.addWaypoint(new WaypointSequence.Waypoint(40, 20, Math.PI / 3));
            p.addWaypoint(new WaypointSequence.Waypoint(60, 40, Math.PI / 3));

            Path path = PathGenerator.makePath(p, config, kWheelbaseWidth, path_name);
            writePath(path, directory, path_name);
        }
        {
            final String path_name = "PosCenterGear";
            config.max_acc = 80.0;
            config.max_jerk = 300.0;
            config.max_vel = 2 * 12;

            WaypointSequence p = new WaypointSequence(2);
            p.addWaypoint(new WaypointSequence.Waypoint(0, 0, 0));
            p.addWaypoint(new WaypointSequence.Waypoint(71, 0, 0));

            Path path = PathGenerator.makePath(p, config, kWheelbaseWidth, path_name);
            writePath(path, directory, path_name);
        }
        {
            final String path_name = "PosBoilerShoot";
            config.max_acc = 80.0;
            config.max_jerk = 600.0;
            config.max_vel = 2 * 12;

            WaypointSequence p = new WaypointSequence(4);
            p.addWaypoint(new WaypointSequence.Waypoint(0, 0, Math.PI / 6));
            p.addWaypoint(new WaypointSequence.Waypoint(40, -12, 0));
            p.addWaypoint(new WaypointSequence.Waypoint(80, 0, Math.PI / 3));
            p.addWaypoint(new WaypointSequence.Waypoint(12, 36, 3 * Math.PI / 4));

            Path path = PathGenerator.makePath(p, config, kWheelbaseWidth, path_name);
            writePath(path, directory, path_name);
        }
        /*
        {
            final String path_name = "PosBoilerHopper";
            config.max_acc = 80.0;
            config.max_jerk = 300.0;
            config.max_vel = 4 * 12;

            WaypointSequence p = new WaypointSequence(2);
            p.addWaypoint(new WaypointSequence.Waypoint(0, 0, 0));
            p.addWaypoint(new WaypointSequence.Waypoint(90, 0, 0));
            p.addWaypoint(new WaypointSequence.Waypoint(105, 44, Math.PI / 2));
            Path path = PathGenerator.makePath(p, config, kWheelbaseWidth, path_name);
            writePath(path, directory, path_name);
        }
        {
            final String path_name = "PosBoilerHopperToBoiler";
            config.max_acc = 80.0;
            config.max_jerk = 300.0;
            config.max_vel = 4 * 12;

            WaypointSequence p = new WaypointSequence(2);
            p.addWaypoint(new WaypointSequence.Waypoint(0, 0, 0));
            p.addWaypoint(new WaypointSequence.Waypoint(28, 73, (2*Math.PI)-Math.PI / 4));
            Path path = PathGenerator.makePath(p, config, kWheelbaseWidth, path_name);
            writePath(path, directory, path_name);
        }*/
//        {
//            final String path_name = "Pos1Gear&Hopper";
//            config.max_acc = 80.0;
//            config.max_jerk = 300.0;
//            config.max_vel = 4 * 12;
//
//            WaypointSequence p = new WaypointSequence(6);
//            p.addWaypoint(new WaypointSequence.Waypoint(0, 0, 0));
//            p.addWaypoint(new WaypointSequence.Waypoint(75, 0, 0));
//            p.addWaypoint(new WaypointSequence.Waypoint(111, 84, Math.PI/3));
//            p.addWaypoint(new WaypointSequence.Waypoint(105, 63, 0));
//            p.addWaypoint(new WaypointSequence.Waypoint(105, 44, Math.PI/2));
//
//            Path path = PathGenerator.makePath(p, config, kWheelbaseWidth, path_name);
//            writePath(path, directory, path_name);
//        }
//        {
//            final String path_name = "Pos3Gear&Hopper";
//            config.max_acc = 80.0;
//            config.max_jerk = 300.0;
//            config.max_vel = 4 * 12;
//
//            WaypointSequence p = new WaypointSequence(6);
//            p.addWaypoint(new WaypointSequence.Waypoint(0, 0, 0));
//            p.addWaypoint(new WaypointSequence.Waypoint(75, 0, 0));
//            p.addWaypoint(new WaypointSequence.Waypoint(111, 84, Math.PI/3));
//            p.addWaypoint(new WaypointSequence.Waypoint(75, 0, 0));
//            p.addWaypoint(new WaypointSequence.Waypoint(200, 0, 0));
//            p.addWaypoint(new WaypointSequence.Waypoint(200, 55, Math.PI/2));
//
//            Path path = PathGenerator.makePath(p, config, kWheelbaseWidth, path_name);
//            writePath(path, directory, path_name);
//        }
//        {
//            final String path_name = "LPos2Gear&Hopper";
//            config.max_acc = 80.0;
//            config.max_jerk = 300.0;
//            config.max_vel = 4 * 12;
//
//            WaypointSequence p = new WaypointSequence(6);
//            p.addWaypoint(new WaypointSequence.Waypoint(0, 0, 0));
//            p.addWaypoint(new WaypointSequence.Waypoint(76, 0, 0));
//            p.addWaypoint(new WaypointSequence.Waypoint(38, 0, Math.PI/2));
//            p.addWaypoint(new WaypointSequence.Waypoint(38, 130, 0));
//            p.addWaypoint(new WaypointSequence.Waypoint(200, 130, 0));
//            p.addWaypoint(new WaypointSequence.Waypoint(200, 160, Math.PI/2));
//
//            Path path = PathGenerator.makePath(p, config, kWheelbaseWidth, path_name);
//            writePath(path, directory, path_name);
//        }
//        {
//            final String path_name = "RPos2Gear&Hopper";
//            config.max_acc = 80.0;
//            config.max_jerk = 300.0;
//            config.max_vel = 4 * 12;
//
//            WaypointSequence p = new WaypointSequence(6);
//            p.addWaypoint(new WaypointSequence.Waypoint(0, 0, 0));
//            p.addWaypoint(new WaypointSequence.Waypoint(76, 0, 0));
//            p.addWaypoint(new WaypointSequence.Waypoint(38, 0, Math.PI/2));
//            p.addWaypoint(new WaypointSequence.Waypoint(38, 130, 0));
//            p.addWaypoint(new WaypointSequence.Waypoint(110, 130, 0));
//            p.addWaypoint(new WaypointSequence.Waypoint(110, 160, Math.PI/2));
//
//            Path path = PathGenerator.makePath(p, config, kWheelbaseWidth, path_name);
//            writePath(path, directory, path_name);
//        }
//        {
//            final String path_name = "Pos1chaos";
//            config.max_acc = 80.0;
//            config.max_jerk = 300.0;
//            config.max_vel = 4 * 12;
//
//            WaypointSequence p = new WaypointSequence(6);
//            p.addWaypoint(new WaypointSequence.Waypoint(0, 0, 0));
//            p.addWaypoint(new WaypointSequence.Waypoint(105, 0, 0));
//            p.addWaypoint(new WaypointSequence.Waypoint(105, 55, Math.PI/2));
//            p.addWaypoint(new WaypointSequence.Waypoint(105, 0, 0));
//            p.addWaypoint(new WaypointSequence.Waypoint(315, 0, 0));
//            p.addWaypoint(new WaypointSequence.Waypoint(315, 55, Math.PI/2));
//
//            Path path = PathGenerator.makePath(p, config, kWheelbaseWidth, path_name);
//            writePath(path, directory, path_name);
//        }
//        {
//            final String path_name = "Pos3chaos";
//            config.max_acc = 80.0;
//            config.max_jerk = 300.0;
//            config.max_vel = 4 * 12;
//
//            WaypointSequence p = new WaypointSequence(6);
//            p.addWaypoint(new WaypointSequence.Waypoint(0, 0, 0));
//            p.addWaypoint(new WaypointSequence.Waypoint(200, 0, 0));
//            p.addWaypoint(new WaypointSequence.Waypoint(200, 55, Math.PI/2));
//            p.addWaypoint(new WaypointSequence.Waypoint(200, 0, 0));
//            p.addWaypoint(new WaypointSequence.Waypoint(438, 0, 0));
//            p.addWaypoint(new WaypointSequence.Waypoint(438, 55, Math.PI/2));
//
//            Path path = PathGenerator.makePath(p, config, kWheelbaseWidth, path_name);
//            writePath(path, directory, path_name);
//        }
//        {
//            final String path_name = "Pos1Gear&Hopper&shoot";
//            config.max_acc = 80.0;
//            config.max_jerk = 300.0;
//            config.max_vel = 4 * 12;
//
//            WaypointSequence p = new WaypointSequence(8);
//            p.addWaypoint(new WaypointSequence.Waypoint(0, 0, 0));
//            p.addWaypoint(new WaypointSequence.Waypoint(75, 0, 0));
//            p.addWaypoint(new WaypointSequence.Waypoint(111, 84, Math.PI/3));
//            p.addWaypoint(new WaypointSequence.Waypoint(105, 63, 0));
//            p.addWaypoint(new WaypointSequence.Waypoint(105, 44, Math.PI/2));
//            p.addWaypoint(new WaypointSequence.Waypoint(105, 8, Math.PI));
//            p.addWaypoint(new WaypointSequence.Waypoint(53, 8, Math.PI));
//            p.addWaypoint(new WaypointSequence.Waypoint(10, 45, Math.PI/4));
//
//            Path path = PathGenerator.makePath(p, config, kWheelbaseWidth, path_name);
//            writePath(path, directory, path_name);
//        }
//
//        {
//            final String path_name = "Pos1Hopper&Shoot";
//            config.max_acc = 80.0;
//            config.max_jerk = 300.0;
//            config.max_vel = 4 * 12;
//
//            WaypointSequence p = new WaypointSequence(2);
//            p.addWaypoint(new WaypointSequence.Waypoint(0, 0, 0));
//            p.addWaypoint(new WaypointSequence.Waypoint(150, 0, 0));
//            p.addWaypoint(new WaypointSequence.Waypoint(150, 30, Math.PI/2));
//            p.addWaypoint(new WaypointSequence.Waypoint(150, 22, Math.PI));
//            p.addWaypoint(new WaypointSequence.Waypoint(98, 22, Math.PI/4));
//
//            Path path = PathGenerator.makePath(p, config, kWheelbaseWidth, path_name);
//            writePath(path, directory, path_name);
//        }
    }
}
