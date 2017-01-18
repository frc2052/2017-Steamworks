package com.first.team2052.trajectory.generate.io;

import com.first.team2052.trajectory.common.Path;
import com.first.team2052.trajectory.common.Trajectory;

/**
 * Serialize a path to a Java file that can be compiled into a J2ME project.
 *
 * @author Jared341
 */
public class JavaSerializer implements IPathSerializer {

    /**
     * Generate a Java source code file from a Path
     * <p>
     * For example output, see the unit test.
     *
     * @param path The path to serialize.
     * @return A complete Java file as a string.
     */
    public String serialize(Path path) {
        String contents = "package com.first.team2052.stronghold.subsystems.drive.paths;\n\n";
        contents += "import com.first.team2052.lib.trajectory.Trajectory;\n";
        contents += "import com.first.team2052.lib.trajectory.Path;\n\n";
        contents += "public class " + path.getName() + " extends Path {\n";
        path.goLeft();

        contents += "public Trajectory.Segment t(double pos, double vel, double acc, double jerk, double heading, double dt, double x, double y){\n";
        contents += "return new Trajectory.Segment(pos, vel, acc, jerk, heading, dt, x, y);\n}\n";

        contents += serializeTrajectory("kLeftWheel",
                path.getLeftWheelTrajectory());
        contents += serializeTrajectory("kRightWheel",
                path.getRightWheelTrajectory());

        contents += "  public " + path.getName() + "() {\n";
        contents += "    this.name_ = \"" + path.getName() + "\";\n";
        contents += "    this.go_left_pair_ = new Trajectory.Pair(kLeftWheel, kRightWheel);\n";
        contents += "  }\n\n";

        contents += "}\n";
        return contents;
    }

    private String serializeTrajectory(String name, Trajectory traj) {
        String contents =
                "  private final Trajectory " + name + " = new Trajectory( new Trajectory.Segment[] {\n";
        for (int i = 0; i < traj.getNumSegments(); ++i) {
            Trajectory.Segment seg = traj.getSegment(i);
            contents += "    t("
                    + seg.pos + ", " + seg.vel + ", " + seg.acc + ", "
                    + seg.jerk + ", " + seg.heading + ", " + seg.dt + ", "
                    + seg.x + ", " + seg.y + "),\n";
        }
        contents += "  });\n\n";
        return contents;
    }

}
