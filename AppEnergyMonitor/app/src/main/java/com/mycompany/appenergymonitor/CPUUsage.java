package com.mycompany.appenergymonitor;

import android.app.Activity;
import android.app.ActivityManager.*;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.Exception;
import java.lang.Process;
import java.lang.Runtime;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


public class CPUUsage extends Activity {

    public static String[] getStringInfo(){

        ArrayList<AppInfo> appi = CPUUsage.getInfo();
        if(appi == null){
            String[] s = {"No apps found"};
            Log.i("NO APPSSSSS", "FOUNDDDDDD");
            return s;
        }
        String[] appString = new String[appi.size()];
        for(int i = 0; i < appi.size(); i++){
            AppInfo ai = appi.get(i);
            String s = "";
            s += ai.getName();
            s += "\t";
            s += Integer.toString(ai.getCPUUse());
            s += "\t";
            s += Integer.toString(ai.getMemUse());
            appString[i] = s;
        }
        return appString;
    }

    public static ArrayList<AppInfo> getInfo(){
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
                    result.add(getAppInfo(line));
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

    public static AppInfo getAppInfo(String line){
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
        int pid = 0;// Integer.parseInt(toks[0]);
        AppInfo appi = new AppInfo(cpupercent, memUse, name, pid);
        return appi;
    }

}