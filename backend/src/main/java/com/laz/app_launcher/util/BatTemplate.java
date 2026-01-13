package com.laz.app_launcher.util;

import java.util.List;

public class BatTemplate {

    public static String buildBatTemplate(List<String> apps) {
        StringBuilder template = new StringBuilder();
        template.append("@echo off\n");
        template.append("title Auto EXE Generator\n");
        template.append("echo Opening applications...\n");
        template.append("echo.\n");
        for (String app : apps) {
            template.append("start ").append(app).append("\n");
        }
        template.append("echo Applications opened successfully\n");
        template.append("exit\n");
        return template.toString();
    }

}