package com.mycompany.appenergymonitor;

import android.app.Activity;
import android.app.ActivityManager.*;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.os.BatteryManager;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Process;
import java.lang.Runtime;
import java.lang.String;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class CPUUsage extends Activity{

    static HashMap<String, ArrayList<AppInfo>> timeLog = new HashMap<String, ArrayList<AppInfo>>();

    /*@Override
    protected void onCreate(Bundle savedInstance){
        app = this.getApplication();
    }*/


    public static ArrayList<AppInfo> getInfo(Context context, ActivityManager localActivityManager, long ratio){
        ArrayList<AppInfo> result = new ArrayList<AppInfo>();
        try {
            Process p = Runtime.getRuntime().exec("top -n 1");
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = reader.readLine();
            int i = 0;
            int[] pidsForMem = new int[100];
            int counterMem = 0;
            while (line != null) {
                //left it in for debugging
                //Log.i("Line " + i + " = ", line);
                if (i >= 7){
                    AppInfo newApp = getAppInfo(line, context);
                    String name = newApp.getName();
                    Log.i("name: ", name);
                    if(!name.equalsIgnoreCase("none")) {
                        result.add(newApp);

                        ArrayList<AppInfo> appLog;
                        if (timeLog.containsKey(name)) {
                            appLog = timeLog.get(name);

                            if (appLog.size() == 20)
                                appLog.remove(0);
                        } else {
                            appLog = new ArrayList<AppInfo>();
                        }
                        appLog.add(newApp);
                        timeLog.put(name, appLog);
                        pidsForMem[counterMem] = newApp.getPid();
                        counterMem++;
                    }
                }
                line = reader.readLine();
                i++;
            }

            Debug.MemoryInfo[] procsMemInfo = localActivityManager.getProcessMemoryInfo(pidsForMem);

            for (int j = 0; j < procsMemInfo.length; j++)
                (result.get(i)).setMemUse(procsMemInfo[j].getTotalPss());

            p.waitFor();
            return result;
        }
        catch (Exception e){
            e.printStackTrace();
            Log.i("NO APPSSSSS", "FOUNDDDDDD");
            ArrayList<AppInfo> a = new ArrayList<AppInfo>();
            a.add(new AppInfo(1,1,"hi",1, false));
            return a;
        }
    }


    public static ArrayList<AppInfo> getThresholded(Context context, ActivityManager localActivityManager){
        ArrayList<AppInfo> result = new ArrayList<AppInfo>();
        try {
            Process p = Runtime.getRuntime().exec("top -n 1");
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = reader.readLine();
            int i = 0;
            int[] pidsForMem = new int[100];
            int counterMem = 0;
            while (line != null) {
                //left it in for debugging
                //Log.i("Line " + i + " = ", line);
                if (i >= 7){
                    AppInfo newApp = getAppInfo(line, context);
                    String name = newApp.getName();
                    if(!name.equalsIgnoreCase("none") && newApp.isAboveThreshHold()) {
                        result.add(newApp);

                        ArrayList<AppInfo> appLog;
                        if (timeLog.containsKey(name)) {
                            appLog = timeLog.get(name);

                            if (appLog.size() == 20)
                                appLog.remove(0);
                        } else {
                            appLog = new ArrayList<AppInfo>();
                        }
                        appLog.add(newApp);
                        timeLog.put(name, appLog);
                        pidsForMem[counterMem] = newApp.getPid();
                        counterMem++;
                    }
                }
                line = reader.readLine();
                i++;
            }

            Debug.MemoryInfo[] procsMemInfo = localActivityManager.getProcessMemoryInfo(pidsForMem);

            for (int j = 0; j < procsMemInfo.length; j++)
                (result.get(i)).setMemUse(procsMemInfo[j].getTotalPss());

            p.waitFor();
            return result;
        }
        catch (Exception e){
            e.printStackTrace();
            Log.i("NO APPSSSSS", "FOUNDDDDDD");
            ArrayList<AppInfo> a = new ArrayList<AppInfo>();
            a.add(new AppInfo(1,1,"hi",1, false));
            return a;
        }
    }

    public static AppInfo getAppInfo(String line, Context context, long ratio){
        //Log.i("Line: ", line);
        String[] toks = line.split(" +");
        /*Log.i("Whole line: ", line);
        for (int i = 0; i < toks.length; i++){
            Log.i("The " + i + "th Token: ", toks[i]);
        }*/
        String cp = toks[3];
        int cpupercent = Integer.parseInt(cp.substring(0, cp.length() - 1));
        int memUse = 0;
        String name = toks[toks.length - 1];
        cpupercent = (int) ratio*cpupercent;

        Log.i("CONTEXT IS NULL?:", Boolean.toString(context == null));
        PackageManager packageManager = context.getPackageManager();
        ApplicationInfo applicationInfo = null;
        try {
            applicationInfo = packageManager.getApplicationInfo(name.trim(), 0);
        }
        catch (final PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String title = (String)((applicationInfo != null) ? packageManager.getApplicationLabel(applicationInfo) : "None");
        String prePid = toks[0];
        String digits = prePid.replaceAll("[^0-9]", "");
        int pid = Integer.parseInt(digits);
        boolean i = Threshold.isAboveThreshold(name, cpupercent);
        AppInfo appi = new AppInfo(cpupercent, memUse, title, pid, i);
        return appi;
    }

    public long getEnergyFromLoad(){
        BatteryManager bm = new BatterManager();
        int firstP, secondP;
        long firstE, secondE;
        Process p = null;
        p = Runtime.getRuntime().exec("top -n 1");

        BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line = reader.readLine();
        int i = 0;
        while (line != null) {
            //left it in for debugging
            //Log.i("Line " + i + " = ", line);
            if (i == 3){
                firstP = getCPUPercentage(line);
            }
            line = reader.readLine();
            i++;
        }

        firstE = bm.BATTERY_PROPERTY_ENERGY_COUNTER;

        Thread.sleep(5400000);

        p = Runtime.getRuntime().exec("top -n 1");

        reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
        line = reader.readLine();
        int i = 0;
        while (line != null) {
            //left it in for debugging
            //Log.i("Line " + i + " = ", line);
            if (i == 3){
                secondP = getCPUPercentage(line);
            }
            line = reader.readLine();
            i++;
        }

        secondE = bm.BATTERY_PROPERTY_ENERGY_COUNTER;

        return (long)  ((firstE - secondE) / (firstP - secondP));

    }


    private String getCPUPercentage(String line) {
        String[] toks = line.split(" +");
        String user = toks[3];
        String system = toks[5];
        user = user.replaceAll("[^0-9]", "");
        int userE = Integer.parseInt(user);
        system = system.replaceAll("[^0-9]", "");
        int systemE = Integer.parseInt(system);
    }

}

