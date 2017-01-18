package com.first.team2052.trajectory.common.io;

import com.first.team2052.trajectory.common.Path;

/**
 * Interface for methods that deserializes a Path or Trajectory.
 *
 * @author Jared341
 */
public interface IPathDeserializer {

    public Path deserialize(String serialized);
}
