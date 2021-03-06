package com.mycompany.appenergymonitor;

/**
 * Created by Vikram on 4/30/15.
 */
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class DisplayThreshold extends ActionBarActivity implements AdapterView.OnItemClickListener {
    Context context;
    ArrayList<AppInfo> appInfo;//{"app name"};//new String[1];
    ListView listViewApps;
    ActivityManager localActivityManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("STARTING", "NOW");
        setContentView(R.layout.activity_display_thresholds);
        Log.d("appInfo = ", "" + this.appInfo);
        context = this.getApplicationContext();
        localActivityManager = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(new
                    File(getFilesDir()+File.separator+"Ratio.txt")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        String read;
        StringBuilder builder = new StringBuilder("");

        try {
            while((read = bufferedReader.readLine()) != null){
                builder.append(read);
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        long ratio = Long.parseLong(builder.toString());
        Log.d("Output", builder.toString());
        //long ratio = CPUUsage.getEnergyFromLoad();
        Log.d("RATIO:", ""+ratio);
        appInfo = CPUUsage.getThresholded(context, localActivityManager, ratio);
        listViewApps = (ListView) findViewById(R.id.list_thresholds);
        ListAppsAdapter adapter = new ListAppsAdapter(this, appInfo);
        listViewApps.setAdapter(adapter);
        listViewApps.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i("click", "click");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_display_app_threshold, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void homeScreen(View view) {
        Intent intent = new Intent(this, HomeScreen.class);
        startActivity(intent);
    }
}

