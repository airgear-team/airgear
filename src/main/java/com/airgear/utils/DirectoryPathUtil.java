package com.airgear.utils;

import java.nio.file.Path;
import java.nio.file.Paths;

public class DirectoryPathUtil {
    public static Path getBasePath() {
        String basePath = new java.io.File(DirectoryPathUtil.class.getProtectionDomain()
                .getCodeSource()
                .getLocation()
                .getPath())
                .getParent();
        return Paths.get(basePath).resolve("../../images").normalize();
    }
}