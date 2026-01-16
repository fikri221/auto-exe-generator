package com.laz.app_launcher.service;

import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.stereotype.Service;

import com.laz.app_launcher.model.App;
import com.laz.app_launcher.util.BatTemplate;

@Service
public class FileGenerator {
    public byte[] generateBat(String fileName, List<App> apps) {
        validate(fileName, apps);
        String content = BatTemplate.buildBatTemplate(apps);
        return content.getBytes(StandardCharsets.UTF_8);
    }

    private void validate(String fileName, List<App> apps) {
        if (fileName == null || fileName.trim().isEmpty()) {
            throw new IllegalArgumentException("File name is required");
        }

        if (fileName.length() > 100) {
            throw new IllegalArgumentException("File name is too long");
        }

        if (fileName.contains("&") || fileName.contains("||") || fileName.contains(">") || fileName.contains("<")
                || fileName.contains("%") || fileName.contains("!")) {
            throw new IllegalArgumentException("Invalid characters detected in file name");
        }

        if (apps == null || apps.isEmpty()) {
            throw new IllegalArgumentException("At least one application is required");
        }

        if (apps.size() > 10) {
            throw new IllegalArgumentException("Maximum 10 applications allowed");
        }

        for (App app : apps) {
            System.out.println("App: " + app.getPath());
            if (!validatePath(app)) {
                throw new IllegalArgumentException("Only .exe files are allowed");
            }

            if (app.getPath().contains("&") || app.getPath().contains("||") || app.getPath().contains(">")
                    || app.getPath().contains("<") || app.getPath().contains("%") || app.getPath().contains("!")) {
                throw new IllegalArgumentException("Invalid characters detected in application path");
            }
        }
    }

    private boolean validatePath(App app) {
        return app.getPath().toLowerCase().endsWith(".exe");
    }
}
