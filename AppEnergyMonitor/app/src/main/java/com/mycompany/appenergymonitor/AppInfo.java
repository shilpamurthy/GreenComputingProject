package com.mycompany.appenergymonitor;

import android.app.Activity;
import android.app.ActivityManager.*;
import android.app.ActivityManager;
import android.content.Context;

import java.lang.Exception;
import java.lang.Process;
import java.lang.String;
import java.lang.reflect.Method;
import java.util.List;


public class AppInfo {

    private int CPUUse;
    private int memUse;
    private String name;
    private int pid;

    public AppInfo(int c, int m, String n, int p){
        this.CPUUse = c;
        this.memUse = m;
        this.name = n;
        this.pid = p;
    }

    public void setCPUUse(int cpuUse){
        this.CPUUse = cpuUse;
    }

    public void setMemUse(int memUse) {
        this.memUse = memUse;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getCPUUse() {
        return CPUUse;
    }

    public int getMemUse() {
        return memUse;
    }

    public int getPid() {
        return pid;
    }

    public String getName() {
        return name;
    }
}