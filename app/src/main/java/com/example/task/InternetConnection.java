package com.example.task;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;

public class InternetConnection {
    static boolean status = false;

    //    CHECK WHETHER INTERNET CONNECTION IS AVAILABLE OR NOT

    public static boolean checkConnection(Context context) {
        final ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connMgr != null) {
            NetworkInfo activeNetworkInfo = connMgr.getActiveNetworkInfo();

            if (activeNetworkInfo != null) { // connected to the internet
                // connected to the mobile provider's data plan
                if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    // connected to wifi
                    return true;
                } else return activeNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE;
            }
        }

        return false;
    }

    public static boolean showAlertDialog(final Context context) {

        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("No Internet connection");
        alertDialog.setMessage("Your device doesn't have Internet connection");
        alertDialog.setIcon(R.drawable.fail);
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_DATA_USAGE_SETTINGS);
                context.startActivity(intent);
                status = checkConnection(context);
            }
        });
        alertDialog.show();
        return status;
    }
}