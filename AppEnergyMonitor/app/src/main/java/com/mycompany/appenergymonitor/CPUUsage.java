package com.mycompany.appenergymonitor;

import android.app.Activity;
import android.app.ActivityManager.*;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.Exception;
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


    public static ArrayList<AppInfo> getInfo(Context context){
        ArrayList<AppInfo> result = new ArrayList<AppInfo>();
        try {
            Process p = Runtime.getRuntime().exec("top -n 1");
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = reader.readLine();
            int i = 0;
            while (line != null) {
                //left it in for debugging
                //Log.i("Line " + i + " = ", line);
                if (i >= 7){
                    AppInfo newApp = getAppInfo(line, context);
                    String name = newApp.getName();
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
                    }
                }
                line = reader.readLine();
                i++;
            }
            p.waitFor();
            return result;
        }
        catch (Exception e){
            e.printStackTrace();
            Log.i("NO APPSSSSS", "FOUNDDDDDD");
            ArrayList<AppInfo> a = new ArrayList<AppInfo>();
            a.add(new AppInfo(1,1,"hi",1));
            return a;
        }
    }

    public static AppInfo getAppInfo(String line, Context context){
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

        int pid = 0;// Integer.parseInt(toks[0]);
        boolean i = Threshold.isAboveThreshold(name);
        AppInfo appi = new AppInfo(cpupercent, memUse, title, pid, i);
        return appi;
    }

}

