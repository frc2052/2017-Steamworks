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
        final double kWheelbaseWidth = 24.375;

        {
            final String path_name = "TestPath";
            config.max_acc = 107.0;
            config.max_jerk = 600.0;
            config.max_vel = 4 * 12;

            WaypointSequence p = new WaypointSequence(2);
            p.addWaypoint(new WaypointSequence.Waypoint(0, 0, 0));
            p.addWaypoint(new WaypointSequence.Waypoint(5 * 12, 5 * 12, 0));

            Path path = PathGenerator.makePath(p, config, kWheelbaseWidth, path_name);
            writePath(path, directory, path_name);
        }
    }
}
