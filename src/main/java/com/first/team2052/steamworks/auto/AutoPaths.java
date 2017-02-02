package com.first.team2052.steamworks.auto;


import com.first.team2052.trajectory.common.Path;
import com.first.team2052.trajectory.common.io.TextFileDeserializer;
import com.google.common.base.Charsets;
import com.google.common.base.Optional;
import com.google.common.collect.Maps;
import com.google.common.io.Resources;

import java.util.HashMap;

public class AutoPaths {
    String[] pathsNames = new String[]{"TestPath"};
    HashMap<String, Path> paths = Maps.newHashMap();
    private static AutoPaths instance = new AutoPaths();

    private AutoPaths() {
        loadPaths();
    }

    private void loadPaths() {
        System.out.println("Loading Paths...");
        TextFileDeserializer deserializer = new TextFileDeserializer();

        for (String pathName : pathsNames) {
            String content = null;

            try {
                content = Resources.toString(Resources.getResource("paths/" + pathName + ".txt"), Charsets.UTF_8);
            } catch (Exception exception) {
                System.out.println("Error: Couldn't find " + pathName + " path not loaded");
            }

            if (content == null) {
                continue;
            }

            Path path = deserializer.deserialize(content);

            paths.put(pathName, path);
        }
        System.out.println("Finished Loading Paths...");
    }

    public Optional<Path> getPath(String pathName) {
        return paths.containsKey(pathName) ? Optional.of(paths.get(pathName)) : Optional.absent();
    }

    public static AutoPaths getInstance() {
        return instance;
    }
}