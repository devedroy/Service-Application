package com.devedroy.serviceapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.devedroy.serviceapplication.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private Intent serviceIntent;
    private boolean mStopLoop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Log.i("MyService", "in onCreateMain " + Thread.currentThread().getId());

        serviceIntent = new Intent(getApplicationContext(), MyService.class);

        binding.btnStartService.setOnClickListener(v -> {
            Log.i("MyService", "btnStartService clicked");
            mStopLoop = true;
            startService(serviceIntent);
        });
    }


}