package com.first.team2052.trajectory.generate.io;

import com.first.team2052.trajectory.common.Path;

/**
 * Interface for methods that serialize a Path or Trajectory.
 *
 * @author Jared341
 */
public interface IPathSerializer {

    public String serialize(Path path);
}
