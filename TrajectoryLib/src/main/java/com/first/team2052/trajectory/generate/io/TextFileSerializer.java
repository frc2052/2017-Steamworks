package com.first.team2052.trajectory.generate.io;

import com.first.team2052.trajectory.common.Path;
import com.first.team2052.trajectory.common.Trajectory;
import com.first.team2052.trajectory.common.Trajectory.Segment;

/**
 * Serializes a Path to a simple space and CR separated text file.
 *
 * @author Jared341
 */
public class TextFileSerializer implements IPathSerializer {

    /**
     * Format: PathName NumSegments LeftSegment1 ... LeftSegmentN RightSegment1
     * ... RightSegmentN
     * <p>
     * Each segment is in the format: pos vel acc jerk heading dt x y
     *
     * @param path The path to serialize.
     * @return A string representation.
     */
    public String serialize(Path path) {
        String content = path.getName() + "\n";
        path.goLeft();
        content += path.getLeftWheelTrajectory().getNumSegments() + "\n";
        content += serializeTrajectory(path.getLeftWheelTrajectory());
        content += serializeTrajectory(path.getRightWheelTrajectory());
        return content;
    }

    private String serializeTrajectory(Trajectory trajectory) {
        String content = "";
        for (int i = 0; i < trajectory.getNumSegments(); ++i) {
            Segment segment = trajectory.getSegment(i);
            content += String.format("%.3f %.3f %.3f %.3f %.3f %.3f %.3f %.3f\n", segment.pos, segment.vel, segment.acc, segment.jerk, segment.heading, segment.dt, segment.x, segment.y);
        }
        return content;
    }

}
