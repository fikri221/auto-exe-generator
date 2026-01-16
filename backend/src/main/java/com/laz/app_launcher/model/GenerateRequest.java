package com.laz.app_launcher.model;

import java.util.List;

public class GenerateRequest {
    private String version;
    private String profileName;
    private String runMode;
    private int delayBetweenAppsMs;
    private List<App> apps;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public String getRunMode() {
        return runMode;
    }

    public void setRunMode(String runMode) {
        this.runMode = runMode;
    }

    public int getDelayBetweenAppsMs() {
        return delayBetweenAppsMs;
    }

    public void setDelayBetweenAppsMs(int delayBetweenAppsMs) {
        this.delayBetweenAppsMs = delayBetweenAppsMs;
    }

    public List<App> getApps() {
        return apps;
    }

    public void setApps(List<App> apps) {
        this.apps = apps;
    }
}
