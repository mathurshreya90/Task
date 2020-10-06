package com.example.task;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.widget.Toast;

import org.json.JSONException;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();
        getWindow().getDecorView().setSystemUiVisibility(RecyclerView.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        if (InternetConnection.checkConnection(getApplicationContext())) {
            // Its Available...
            new ApiCall().execute();

        } else {
            InternetConnection.showAlertDialog(SplashScreenActivity.this);
            if (InternetConnection.showAlertDialog(SplashScreenActivity.this)) {
                new ApiCall().execute();
            } else
                InternetConnection.showAlertDialog(SplashScreenActivity.this);

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (InternetConnection.checkConnection(getApplicationContext())) {
            Intent i = new Intent(getApplicationContext(), SplashScreenActivity.class);
            startActivity(i);
        } else
            InternetConnection.showAlertDialog(SplashScreenActivity.this);
    }

    private class ApiCall extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {

            try {
                CallApi.CallWebMethod();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            if (CallApi.error.isEmpty())
                startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
            else
                Toast.makeText(getApplicationContext(), CallApi.error, Toast.LENGTH_LONG).show();
        }
    }
}