package com.airgear.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DirectoryPathUtil {
    private static final Logger logger = LoggerFactory.getLogger(DirectoryPathUtil.class);

    public static Path getBasePath() {
        String basePath = new java.io.File(DirectoryPathUtil.class.getProtectionDomain()
                .getCodeSource()
                .getLocation()
                .getPath())
                .getParent();
        Path imagesPath = Paths.get(basePath).resolve("../../images").normalize();

        if (!Files.exists(imagesPath)) {
            try {
                Files.createDirectories(imagesPath);
                logger.info("Directory created: {}", imagesPath);
            } catch (IOException e) {
                logger.error("Failed to create directory: {}", imagesPath, e);
            }
        }

        return imagesPath;
    }
}
