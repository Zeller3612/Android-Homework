package com.bytedance.chapter2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        RecyclerView recyclerView = findViewById(R.id.list);
        SearchAdapter searchAdapter = new SearchAdapter();
        recyclerView.setAdapter(searchAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<String> list = new ArrayList<>();
        for(int i = 0; i < 100; i++){
            list.add("这是第 " + i + " 行");
        }

        SearchLayout searchLayout = findViewById(R.id.search);
        searchLayout.setOnInputChangedListener(new SearchLayout.OnInputChangedListener() {
            @Override
            public void onChanged(String text) {
                List<String> filters = new ArrayList<>();
                for(String str : list){
                    if(str.contains(text)) {
                        filters.add(str);
                    }
                }

                searchAdapter.notifyItems(filters);

                Log.d("TAG", "onChanged:" + text);

            }
        });

        TextView et = findViewById(R.id.input);
        Button button = findViewById(R.id.btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                et.setText("");
                Log.d("TAG", "clicked");
            }
        });

        searchAdapter.notifyItems(list);
    }
}