package com.mycompany.appenergymonitor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * http://www.android-ios-tutorials.com/android/android-custom-listview-example/
 * Created by Vikram on 4/30/15.
 */
public class ListAppsAdapter extends BaseAdapter {

    Context context;

    protected ArrayList<AppInfo> listApps;
    LayoutInflater inflater;

    public ListAppsAdapter(Context context, ArrayList<AppInfo> listApps) {
        this.listApps = listApps;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }

    public int getCount() {
        return listApps.size();
    }

    public AppInfo getItem(int position) {
        return listApps.get(position);
    }

    public long getItemId(int position) {
        return (long)listApps.get(position).getPid();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {

            holder = new ViewHolder();
            convertView = this.inflater.inflate(R.layout.layout_list_item,
                    parent, false);

            holder.txtName = (TextView) convertView
                    .findViewById(R.id.txt_app_name);
            holder.txtEnergy = (TextView) convertView
                    .findViewById(R.id.txt_app_energy);
            holder.txtMemory = (TextView) convertView
                    .findViewById(R.id.txt_app_memory);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        AppInfo app = listApps.get(position);
        holder.txtName.setText(app.getName());
        holder.txtEnergy.setText(Integer.toString(app.getCPUUse()));
        holder.txtMemory.setText(Integer.toString(app.getMemUse()));

        return convertView;
    }

    private class ViewHolder {
        TextView txtName;
        TextView txtEnergy;
        TextView txtMemory;
    }

}
