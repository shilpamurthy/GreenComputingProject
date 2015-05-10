package com.mycompany.appenergymonitor;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;


public class HomeScreen extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home_screen, menu);
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

    public void viewUsage(View view){
        Intent intent = new Intent(this, DisplayAppUsage.class);
        startActivity(intent);
    }

    public void viewThreshold(View view){
        Intent intent = new Intent(this, DisplayThreshold.class);
        startActivity(intent);
    }

    public void onCalibrate(View view){
        long rat = 1; //CPUUsage.getEnergyFromLoad();
        if (rat<1) rat=1;
        Context context = this.getApplicationContext();
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new
                    File(getFilesDir() + File.separator + "Ratio.txt")));
            bufferedWriter.write(Long.toString(rat));
            bufferedWriter.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
