package com.example.task;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;
import android.widget.TextView;

import org.json.JSONException;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    RecyclerViewAdapter adapter;
    RecyclerView recyclerView;
    SwipeRefreshLayout pullToRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        getWindow().getDecorView().setSystemUiVisibility(RecyclerView.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        android.widget.SearchView searchView = (android.widget.SearchView) findViewById(R.id.searchView2);
        TextView TextViewMarketCap = (TextView) findViewById(R.id.textView_marketcap);
        DecimalFormat df = new DecimalFormat("#.##");
        TextViewMarketCap.setText("Global Market Cap: $" + df.format(CallApi.TotalCap));
        TextViewMarketCap.setBackgroundColor(R.color.gray);
        SetRecyclerView();
        pullToRefresh = findViewById(R.id.pullToRefresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new ApiCall().execute();
                adapter.notifyDataSetChanged();
                pullToRefresh.setRefreshing(false);
            }
        });
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    public void SetRecyclerView() {
        recyclerView = findViewById(R.id.recylerview);
        adapter = new RecyclerViewAdapter(CallApi.DataList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private class ApiCall extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            try {
                CallApi.CallWebMethod();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}