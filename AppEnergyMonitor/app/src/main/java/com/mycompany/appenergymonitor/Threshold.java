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

public class Threshold{

	static HashMap<String, Integer> thresholdMap = new HashMap<>();

	public static void addParam(String name, int t){
		thresholdMap.put(name, t);
	}

	public static boolean isAboveThreshold(String appName, int value){
		if (thresholdMap.containsKey(appName)){
			return (thresholdMap.get(appName) > value);
		}
		else{
			thresholdMap.put(appName, value);
			return false;
		}
	}
}