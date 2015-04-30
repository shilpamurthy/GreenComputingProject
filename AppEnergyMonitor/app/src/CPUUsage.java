import android.app.Activity;
import android.app.ActivityManager.*;
import android.app.ActivityManager;
import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.Exception;
import java.lang.Process;
import java.lang.Runtime;
import java.lang.reflect.Method;
import java.util.List;


public class CPUUsage extends Activity {

    public static void getInfo(){

        try {
            Process p = Runtime.getRuntime().exec("top -n 1");

            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line = reader.readLine();
            int i = 0;

            while (line != null) {
                Log.i("Line " + i + " = ", line);
                line = reader.readLine();
                i++;
            }

            p.waitFor();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}