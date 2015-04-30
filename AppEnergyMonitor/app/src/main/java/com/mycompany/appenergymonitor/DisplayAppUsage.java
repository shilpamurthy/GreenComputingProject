package com.mycompany.appenergymonitor;

import android.app.Activity;
import android.app.ListActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


public class DisplayAppUsage extends Activity implements AdapterView.OnItemClickListener {

    ArrayList<AppInfo> appInfo = CPUUsage.getInfo();//{"app name"};//new String[1];
    ListView listViewApps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("STARTING", "NOW");
        setContentView(R.layout.activity_display_app_usage);
        Log.d("appInfo = ", "" + this.appInfo);
        listViewApps = (ListView) findViewById(R.id.list_apps);
        ListAppsAdapter adapter = new ListAppsAdapter(this, appInfo);
        listViewApps.setAdapter(adapter);
        listViewApps.setOnItemClickListener(this);
        /*ArrayAdapter<String> adapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked, this.appInfo);
        ListView lv= getListView();//(ListView)findViewById(R.id.list);
        lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        lv.setAdapter(adapter);
        //CPUUsage.getInfo();
        /*lv.setOnClickListener(new onClickListener() {

            //@TODO: update app arrayList
            public void onClick(ListView lv) {
                //do integration stuff here
            }
        });*/
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i("click", "click");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_display_app_usage, menu);
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
}
