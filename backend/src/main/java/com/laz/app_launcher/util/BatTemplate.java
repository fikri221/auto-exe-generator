package com.laz.app_launcher.util;

import java.util.List;
import java.util.regex.Pattern;

import com.laz.app_launcher.model.App;

public class BatTemplate {
    private static final Pattern EXE_PATH_PATTERN = Pattern.compile("^[A-Za-z]:\\\\[^:*?\"<>|]+\\.exe$");

    public static String buildBatTemplate(List<App> apps) {
        StringBuilder template = new StringBuilder();
        template.append("@echo off\n");
        template.append("title Auto EXE Generator\n");
        template.append("echo Opening applications...\n");
        template.append("echo.\n");
        for (App app : apps) {
            template.append("start \"\" \"")
                    .append(EXE_PATH_PATTERN.matcher(app.getPath()))
                    .append("\"\n");

        }
        template.append("echo Applications opened successfully\n");
        template.append("exit\n");
        return template.toString();
    }

}